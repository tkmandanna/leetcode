package com.dstillery.minibidder.model;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * Represents a strategy for adjusting bid prices based on
 * the state of the current auction.  
 */
@FunctionalInterface
public interface BidStrategy {
		
	/**
	 * @param  ctx - the context for the current auction
	 * @param  bid - the previous bid, in cents
	 * @return the strategy-adjusted bid, in cents
	 */
	int adjustBid(AuctionContext ctx, int bidCents);
	
	final BidStrategy NO_CHANGE = (ctx, bidCents) -> bidCents;
	final BidStrategy BID_ZERO = (ctx, bidCents) -> 0;
    final BidStrategy TIME_OF_DAY_BID = (ctx,bidCents)-> {

        LocalTime now = LocalTime.now();

        double hour = now.getHourOfDay();
        double numOfHours=0;
        double time=0;
        double hoursPassed=0;

        time=(numOfHours*60)+now.getMinuteOfHour();
        System.out.println("time = "+time);
        if(hour >= 11)
        {
            numOfHours=hour-11;
            time=(numOfHours*60)+now.getMinuteOfHour();
            hoursPassed=time/60;

            time=(numOfHours*60)+now.getMinuteOfHour();
        }
        else if(hour<2)
        {
            numOfHours=hour+13;

            time=(numOfHours*60)+now.getMinuteOfHour();
            hoursPassed=time/60;
            time=(numOfHours*60)+now.getMinuteOfHour();
        }
        if(hoursPassed>0)
        {


            double temp_pow=Math.pow((hoursPassed-7),2);

            double power = (7 - (temp_pow/7));

            double newBidCents = bidCents * (Math.pow(1.07,power));

            return (int)newBidCents;
        }
        else
            return bidCents;
    };
}
