package com.dstillery.minibidder.model;

import com.google.common.reflect.TypeToken;
import org.joda.time.LocalTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkmandanna on 12/27/15.
 */
public class BidStrategyTest {

    @Test
    public void testBidStrategy()
    {
        Campaign campaign =  new Campaign(666,"Nike shoes",new Ad(7,Ad.Size._300x250),200,BidStrategy.TIME_OF_DAY_BID);
        AuctionContext ctx = new AuctionContext();
        TypeToken<String> type = TypeToken.of(String.class);
        AuctionContext.Key<String> key = new AuctionContext.Key<String>("String",type);


        ctx.set(key,"String");

        System.out.println(campaign.getFinalBidCents(ctx));
        /*testBid has the same functionality as BidStrategy.TIME_OF_DAY_BID but with the addition of giving a time string as parameter
        to test the return value of TIME_OF_DAY_BID */
        testBid("01:55:00");
        testBid("03:00:00");

    }

    public int testBid(String time_str){
        int bidCents = 200;
        LocalTime now =null;
        if(time_str.equals(""))
            now= LocalTime.now();
        else
            now = LocalTime.parse(time_str);

        double hour = now.getHourOfDay();
        double numOfHours=0;
        double time=0;
        double hoursPassed=0;
        System.out.println("Hours = "+hour);
        System.out.println("minutes = "+now.getMinuteOfHour());
        time=(numOfHours*60)+now.getMinuteOfHour();
        System.out.println("time = "+time);
        if(hour >= 11)
        {
            numOfHours=hour-11;
            time=(numOfHours*60)+now.getMinuteOfHour();
            hoursPassed=time/60;
            System.out.println("Hours = "+hour);
            System.out.println("minutes = "+now.getMinuteOfHour());
            time=(numOfHours*60)+now.getMinuteOfHour();
        }
        else if(hour<2)
        {
            numOfHours=hour+13;

            time=(numOfHours*60)+now.getMinuteOfHour();
            hoursPassed=time/60;
            System.out.println("Hours = "+hour);
            System.out.println("minutes = "+now.getMinuteOfHour());
            time=(numOfHours*60)+now.getMinuteOfHour();
        }
        if(hoursPassed>0)
        {

            //hoursPassed=5.2833;
            System.out.println("hours passed = "+hoursPassed);
            double temp_pow=Math.pow((hoursPassed-7),2);
            System.out.println("Temp pow = "+temp_pow);
            double power = (7 - (temp_pow/7));
            System.out.println("power = "+power);
            double newBidCents = bidCents * (Math.pow(1.07,power));
            System.out.println(newBidCents);
            return (int)newBidCents;
        }
        else
            System.out.println(bidCents);
            return bidCents;
    }

}
