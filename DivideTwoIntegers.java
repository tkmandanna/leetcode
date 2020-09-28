import java.lang.*;
//29. Divide Two Integers
class DivideTwoIntegers {
    
    public static long absdiv;
    
    public int divide(int dividend, int divisor) {
        
        if(dividend==0)
            return 0;
        
        if((divisor ==-1) && (dividend == Integer.MIN_VALUE))
            return Integer.MAX_VALUE;
        
        if(divisor == 1)
            return dividend;
        
        if(divisor == -1)
            return -dividend;
        
       
        //do long conversion inside of Math.abs
        absdiv= Math.abs((long)divisor);
        //use long types to prevent overflow error like when dividend is Integer.MAX_VALUE
        long remdividend = Math.abs((long)dividend);
        long lshiftCount = 1;
        
        if(remdividend<absdiv)
        {
            return 0;
        }

        lshiftCount=myDivide(remdividend);
        
        if((dividend<0)&&(divisor>0))
            return -(int)lshiftCount;
        if((divisor<0)&&(dividend>0))
            return -(int)lshiftCount;
        
        return (int)lshiftCount;
        
    }
    
    public long myDivide(long remdividend){
        if(remdividend==0)
            return 0;
        long posdivisor = (long)absdiv;
        long lshiftCount = 1;
        long total=0;
        while(remdividend >= absdiv){
            
            while(remdividend >= (posdivisor<<1)){
                posdivisor=posdivisor<<1;
                lshiftCount = lshiftCount<<1;  
            }
            remdividend = remdividend - posdivisor;
            posdivisor = absdiv;
            total += lshiftCount;
            lshiftCount=1;
        }
            
          
        return total;
    }
    
}
