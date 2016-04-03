import java.io.*;
import java.util.*;

class Node
{
	int data;
	Node left;
	Node right;

	Node(int data)
	{
		this.data = data;
	}
}
class BTree
{
	Node root;
	public void insertNode(Node node)
	{
		if(root == null)
		root = node;
		else
		{
			Node temp = root;
			Node prev = root;
			while(temp!=null)
			{
				if(temp.data <=node.data)
				{
					prev=temp;
					temp=temp.right;
				}
				else
				{
					prev = temp;
					temp=temp.left;
				}
			}

			if(prev.data <=node.data)
                                {
                                        prev.right=node;
                                }
                                else
                                {
                                        prev.left = node;
                                }

		}
		
	}

	public Node invertTree1(Node root)// method to invert a binary tree using recursion
	{
	    /*                                 4                        4
					      /  \                     / \
	                                     /    \   =>              /   \
                                            2      7                 7     2
                                           / \    / \               / \   / \
                                          1   3  6   9             9   6 1   3
	    */ 
		if(root==null)
		return null;
		Node leftChild = invertTree1(root.left);            
		Node rightChild = invertTree1(root.right);
		root.right=leftChild;
		root.left=rightChild;
		return root;
		
	}

	public Node invertTree2(Node root)// method to invert a binary tree using recursion
        {
            /*                                 4                        4
                                              /  \                     / \
                                             /    \   =>              /   \
                                            2      7                 7     2
                                           / \    / \               / \   / \
                                          1   3  6   9             9   6 1   3
            */
                if(root==null)
                return null;
                
		Deque<Node> stack = new LinkedList<Node>();

		stack.push(root);
		while(!stack.isEmpty())
		{
			Node node = stack.pop();
			Node temp = node.left;
			node.left=node.right;
			node.right=temp;

			if(node.left!=null)
			stack.push(node.left);
			if(node.right!=null)
			stack.push(node.right);
		}
		
                return root;

        }

	public void inorderTrav(Node root)
	{
		if(root == null)
		return;
		
		inorderTrav(root.left);
		System.out.print(""+root.data+", ");
		inorderTrav(root.right);
	}
}
public class InvertBTree
{
	public static void main(String args[])throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int data = 1;
		
		BTree btree = new BTree();
		while(data!=0)
		{
			System.out.println("Enter data");
			data=Integer.parseInt(br.readLine());
			if(data==0)
			break;

			Node node = new Node(data);
			btree.insertNode(node);
		}

		btree.inorderTrav(btree.root);
		btree.root=btree.invertTree2(btree.root);
		System.out.println("\nAfter inversion\n");
		btree.inorderTrav(btree.root);
	}
}
