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
               
                A Fenwick tree is most easily understood by considering a one-based array. Each element i whose index is a power of 2 contains the sum of the first i elements. Elements whose indices are the sum of two (distinct) powers of 2 contain the sum of the the elements since the preceding power of 2. In general, each element contains the sum of the values since its parent in the tree, and that parent is found by clearing the least-significant bit in the index i.e the rightmost 1 or set bit.
               
*/
import java.lang.Math.*;
import java.util.Random;
public class FenwickTree{
 
                int []tree;
                int []arr;
               
                FenwickTree(int n){
                                tree = new int[n+1];
                                arr = new int[n];
                                tree[0]=0;
                                /*for(int i=0;i<n;i++)
                                {
                                                //arr[i] =(int)Math.random()*10+1;  //Math.random() generates a number between 0 and 0.999 . This into * 10 gives 0 to 9.999 ..truncated because of int datatype gives an integer between 0 to 9.
                                                Random random = new Random();
                                                arr[i] = random.nextInt(10)+1;
                                                System.out.print(arr[i]+" , ");
                                                //creating initial prefix sum tree. This operation runs in O(n log n).
                                                buildTree(i+1,arr[i]);
                                }*/
                                arr[0]=3;buildTree(1);
                                arr[1]=2;buildTree(2);
                                arr[2]=-1;buildTree(3);
                                arr[3]=6;buildTree(4);
                                arr[4]=5;buildTree(5);
                                arr[5]=4;buildTree(6);
                                arr[6]=-3;buildTree(7);
                                arr[7]=3;buildTree(8);
                                arr[8]=7;buildTree(9);
                                arr[9]=2;buildTree(10);
                                arr[10]=3;buildTree(11);
                                System.out.println("");
                }
               
                int getSum(int start, int end)
                {
                                int sum = 0;
                                for(int i = start;i < end; i++)
                                {
                                                sum+= arr[i];
                                }
                               
                                return sum;
                }
               
                int getPrefixSum(int pos)
                {
                                int parent = pos+1;
                                int sum = 0;
                                while(parent!=0)
                                {
                                                sum += tree[parent];
                                                int newParent = parent - (parent & (-parent)); // walking up the tree
                                                parent = newParent;
                                }
                               
                                return sum;
                }
               
                void updatePos(int pos, int val)
                {
                                int diff = val - arr[pos];
                                arr[pos] = val;
                                updateTree(pos+1, diff);
                }
               
                void updateTree(int pos, int diff){
                                /*
                                                To obtain the child index, the following operations must be done:
                                                1.) Obtain 2's complement of current index.
                                                2.) And 2's complement to current index.
                                                3.) Add the result to current index. // child index is always greater so add
                                               
                                                Eg: current index = 6.
                                                    110 in binary.
                                                                1's complement is 001
                                                                2's complement is 010.
                                                               
                                                                110 & 010 = 010
                                                                110 + 010 = 1000 which is 8 which is the child index.
                                                               
                                                                Once we have, let's old value of arr[6] was 2 and new value is 4.
                                                                Therefore, the difference is 2.
                                                                Add the diff to tree[pos] and all it's subsequent children all the way down the tree.
                                                                i.e tree[6]+=2
                                                                tree[8]+=2
                                                                8's child is 10. Therefore, tree[10] += 2.
                                                                10's child is 16 which is beyond the array in terms of length and therefore method ends.
                                */
                                int child = pos;
                                while(child<tree.length){
                                                tree[child]+= diff;
                                                int newChild = child + (child & (-child));
                                                child = newChild;
                                }
                }
               
                void buildTree(int pos){
                               
                               
                                /*
                                                obtaining the parent given an index.
                                                To obtain the parent of a given index, clear the rightmost bit that was set to 1.
                                                Example, for 11..binary representation is 1101.  Flipping the last one, we get 1100 i.e. the parent is 10.
                                                Hence, tree[11] = arr[10].
                                               
                                                Another example:
                                                Consider, index 6- 110 in binary. Parent is 100 i.e. index 4. Therefore, it will contain arr[4]+arr[5]. In other words, tree[i]
                                                will contain the sum of all the elements from parent index to current index.
                                               
                                                Another example: 4 - 100 in binary. Parent is 000 i.e. 0. Therefore, tree[4] = arr[0]+arr[1]+arr[2]+arr[3].
                                               
                                                To obtain the parent index, the following operations must be done:
                                                1.) Obtain 2's complement of current index.
                                                2.) And 2's complement to current index.
                                                3.) Subtract the result from current index. // parent index is always lesser so subtract
                                               
                                                Eg: current index = 6.
                                                    110 in binary.
                                                                1's complement is 001
                                                                2's complement is 010.
                                                               
                                                                110 & 010 = 010
                                                                110 - 010 = 100 which is 4 which is the parent index.
                                                               
                                                                2's complement of a number is the -ve of that number. For ex: 2's complement of 4 is -4.
                                */
                                int parent = pos - (pos & (-pos));
                                System.out.println("pos = "+pos+" parent = "+parent);
                                tree[pos] = getSum(parent,pos);
                }
               
                void printTree(){
                                System.out.println("Printing tree now");
                                for(int i=0;i< tree.length;i++)
                                {
                                                System.out.println("pos = "+i+" tree[i] = "+tree[i]);
                                }
                               
                }
                public static void main(String args[])
                {
                                FenwickTree ftree = new FenwickTree(11);
                                ftree.printTree();
                               
                                System.out.println("sum of 0 to 6 ="+ftree.getPrefixSum(6));
                                System.out.println("sum of 0 to 3 ="+ftree.getPrefixSum(3));
                                System.out.println("sum of 0 to 9 ="+ftree.getPrefixSum(9));
                                ftree.updatePos(9,5);
                                ftree.printTree();
                                System.out.println("sum of 0 to 9 ="+ftree.getPrefixSum(9));
                }
}
 
