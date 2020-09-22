class ShuffleTheArray {
    public int[] shuffle(int[] nums, int n) {
        if(n==1)
            return nums;
        
        
        for(int i=n;i<nums.length;i++)
        {
            nums[i]=nums[i]<<10; //left-shifting y's by 10 bits because largest possible number in array is <1000
            nums[i]=nums[i]|nums[i-n]; // storing x's in lsb of y's
                //this way we have all the pairs stored in the y-half of the array
                // now we just need to place in their right position
        }
        for(int i=0,j=n;i<nums.length&&j<nums.length;i+=2,j++){ 
            nums[i] = nums[j]&1023; //nums[j]|000000001111111111 - this extracts last 10 bits i.e. x component of pair
            nums[i+1] = nums[j]>>10; // right shift nums[j] 10 times to extract y component i.e opposite of loop 1
        }
        
        
        return nums;
        
    }
}
