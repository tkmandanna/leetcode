package com.dstillery.minibidder;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.dstillery.minibidder.filter.brq.BidRequestFilter;
import com.dstillery.minibidder.filter.campaign.CampaignFilter;
import com.dstillery.minibidder.model.Ad;
import com.dstillery.minibidder.model.AuctionContext;
import com.dstillery.minibidder.model.BidRequest;
import com.dstillery.minibidder.model.BidResponse;
import com.dstillery.minibidder.model.Campaign;
import com.dstillery.minibidder.util.Pair;
import com.google.common.reflect.TypeToken;

/**
 * The MiniBidder is an exchange-agnostic RTB bidding engine.  It takes a
 * BidRequest and runs an internal auction to generate a BidResponse.
 *
 * The internal auction runs the bid request through a series of bid request
 * filters which populate the auction context or potentially abort the bidding
 * process. It then matches the BidRequest to interested campaigns, and creates 
 * a BidResponse from the highest bidder. 
 */
public class MiniBidder {
	
	public static final AuctionContext.Key<BidRequest> BRQ = 
		new AuctionContext.Key<>("BRQ", TypeToken.of(BidRequest.class));

	private final Supplier<List<BidRequestFilter>> brqFilters;
	private final Function<Ad.Size, Set<Campaign>> campaigns;
	private final Supplier<List<CampaignFilter>> camFilters;
	
	public MiniBidder(Supplier<List<BidRequestFilter>> brqFilters,
			          Function<Ad.Size, Set<Campaign>> campaigns,
			          Supplier<List<CampaignFilter>> camFilters) {
		this.brqFilters = brqFilters;
		this.campaigns = campaigns;		
		this.camFilters = camFilters;
	}
	
	public BidResponse runAuction(BidRequest brq) {
		AuctionContext ctx = new AuctionContext();
		ctx.set(BRQ, brq);
		
		// Determine if we want to handle the bid request and populate
		// the auction context 
		for(BidRequestFilter f: brqFilters.get()) {
			if(f.test(ctx, brq) == BidRequestFilter.Result.NO_BID) {
				return BidResponse.NO_BID;
			}
		}
		
		// Find the initial set of interested campaigns.
		Set<Campaign> initial = campaigns.apply(brq.getAdSize());
        Iterator iter = initial.iterator();
        while (iter.hasNext()) {
            Campaign camp = (Campaign)iter.next();
            System.out.println("Description  = "+camp.getDescription());
        }
		if(initial.isEmpty()) {
			return BidResponse.NO_BID;
		}
		
		// Filter out uninterested campaigns and run the auction.
		//
		// Note: if you are unfamiliar with Java 8, this block of code uses
		// a new feature called "Streams". Streams allow you to push items 
		// through a pipeline of operations, without having to create 
		// intermediate collections or use deeply nested loops.

		return addCampaignFilters(ctx, initial.stream())
			.map(c -> new Pair<>(c.getFinalBidCents(ctx), c))
			.max((p1, p2) -> {
                int result = Integer.compare(p1.getA(), p2.getA());

                if (result == 0) {
                    //possible that they have same Adsize,baseBidCents and BidStrategy .in that case we have to randomize the Campaign returned
                    Campaign c1 = (Campaign) p1.getB();
                    Campaign c2 = (Campaign) p2.getB();

                    Ad.Size s1 = c1.getAd().getSize();
                    Ad.Size s2 = c2.getAd().getSize();
                    //Compairing Ad size, baseBidCents and the BidStrategy of the two campaigns which had equal finalBidCents
                    if (s1.equals(s2) &&
                            Objects.equals(c1.getBaseBidCents(), c2.getBaseBidCents()) &&
                            Objects.equals(c1.getBidStrategy(), c2.getBidStrategy())) {

                        return ThreadLocalRandom.current().nextBoolean() ? 1 : -1;
                    } else {

                        return 0;
                    }
                } else {

                    return result;
                }
            }
            )
			.map(p -> new BidResponse(p.getA(),
					                  Optional.of(p.getB().getAd())))
		    .orElse(BidResponse.NO_BID);
        /*return addCampaignFilters(ctx, initial.stream())
                .map(c -> new Pair<>(c.getFinalBidCents(ctx), c))
                .max((p1, p2) ->
                    Integer.compare(p1.getA(), p2.getA()))
                .map(p -> new BidResponse(p.getA(),
                        Optional.of(p.getB().getAd())))
                .orElse(BidResponse.NO_BID);*/
	}
	
	// Augment the input Stream with the configured campaign filters.
	private Stream<Campaign> addCampaignFilters(AuctionContext ctx, Stream<Campaign> campaigns) {
		Stream<Campaign> out = campaigns;
		for(CampaignFilter f: camFilters.get()) {
			out = out.filter(c -> f.test(ctx, c) == CampaignFilter.Result.INTERESTED);
		}
		return out;
	}
}
