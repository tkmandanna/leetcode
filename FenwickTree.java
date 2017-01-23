/*
	Given a set of numbers arr[0-n], let's say the user wants to perform two types of operations:
	1.) Update arr[i] where 0<=i<numbers
	2.) Find the sum of all elements upto index "i" i.e sum+= arr[j] where 0<=j<index
	
	One way to do this is to precalculate the sums from 0 to i for every i and store in another array. But every time
	an arr[i] is updated , all the sums need to be recalculated. So update(arr[i]) is O(n) while sum(arr[i]) is O(1)
	
	Another way is to leave the array as is, and calculate the sum from arr[0-i] whenever required.
	Then update(arr[i]) is O(1) while sum(arr[i]) is O(n).
	
	Fenwick Tree is a datastructure that allows us to do both these operations in O(log(n)). The initial cost of constructing the tree is however O(nlog(n)).
	The memory constraint is O(2n) = O(n). 
	
	Another datastructure that can be used for this is known as Segment Tree but that is however O(4n) in terms of memory consumption.
	
	Tutorial for this datastructure: https://www.youtube.com/watch?v=CWDQJGaN1gY
	
*/
import java.lang.Math.*;
import java.util.Random;
public class FenwickTree{

	int []tree;
	int []arr;
	
	FenwickTree(int n){
		tree = new int[n+1];
		arr = new int[n];
		
		for(int i=0;i<n;i++)
		{
			//arr[i] =(int)Math.random()*10+1;  //Math.random() generates a number between 0 and 0.999 . This into * 10 gives 0 to 9.999 ..truncated because of int datatype gives an integer between 0 to 9. 
			Random random = new Random();
			arr[i] = random.nextInt(10)+1;
			System.out.print(arr[i]+" , ");
			updateTree(i,arr[i]);
		}
		System.out.println("");
	}
	
	updateTree(int pos, int val){
		arr[pos] = val;
		/*
			obtaining the parent given an index.
		*/
		
	}
	public static void main(String args[])
	{
		FenwickTree ftree = new FenwickTree(5);
		
	}
}
