/*
2D Fenwick Tree

https://discuss.leetcode.com/uploads/files/1475260639048-screen-shot-2016-09-30-at-2.36.01-pm.png

https://discuss.leetcode.com/topic/30343/java-2d-binary-indexed-tree-solution-clean-and-short-17ms/20

*/
public class FenwickTreeTwoDimensional {

    int[][] tree;
    int[][] nums;
    int m;
    int n;
    
    public FenwickTreeTwoDimensional(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return;
        m = matrix.length;
        n = matrix[0].length;
        tree = new int[m+1][n+1];
        nums = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    public void update(int row, int col, int val) {
        if (m == 0 || n == 0) return;
        int delta = val - nums[row][col];
        nums[row][col] = val;
		
		System.out.println("for ("+row+" , "+col+" ) in matrix and therefore (i+1 ,j+1 ) in the tree, it's children are : ");
        for (int i = row + 1; i <= m; i += i & (-i)) {
            for (int j = col + 1; j <= n; j += j & (-j)) {
				System.out.println("( "+i+" , "+j+" )");
                tree[i][j] += delta;
            }
        }
		
		printTree();
    }

	public void printTree(){
		System.out.println("------ printing the tree --------");
		for(int i=0;i<tree.length;i++)
		{
			for(int j = 0;j<tree[0].length;j++)
				{
					//arr[i][j] = i*3 + j;
					System.out.print(tree[i][j]+" , ");
				}
			System.out.println("");
		}
	}
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (m == 0 || n == 0) return 0;
        return sum(row2+1, col2+1) + sum(row1, col1) - sum(row1, col2+1) - sum(row2+1, col1);
    }
    
    public int sum(int row, int col) {
        int sum = 0;
		System.out.println("for ("+row+" , "+col+" ) in matrix and therefore (i+1 ,j+1 ) in the tree, it's parents are : ");
        for (int i = row; i > 0; i -= i & (-i)) {
            for (int j = col; j > 0; j -= j & (-j)) {
				System.out.println("( "+i+" , "+j+" )");
                sum += tree[i][j];
            }
        }
        return sum;
    }
	
	public static void main(String args[])
	{
		int arr[][] = new int[3][3];
		for(int i=0;i<3;i++)
		{
			for(int j = 0;j<3;j++)
				{
					arr[i][j] = i*3 + j;
					System.out.print(arr[i][j]+" , ");
				}
			System.out.println("");
		}
		//System.out.println(Integer.toBinaryString(-2));
		FenwickTreeTwoDimensional nm = new FenwickTreeTwoDimensional(arr);
		nm.sum(1,1);
		nm.sum(2,2);
		//NumMatrix nm = 
	}
}
// time should be O(log(m) * log(n))
