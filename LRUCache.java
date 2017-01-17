import java.util.*;

/*
	LinkedHashMap - A hashmap that maintains insertion order.
	
	Arguments for a hashmap constructor are loadFactor, Capacity and AccessOrder.
	Capacity - initial number of buckets (default val = 16)
	LoadFactor - number of buckets that need to be occupied before the Hashmap needs to grow/rehashed. (Optimum value = 0.75)
	
	This problem can be done using the LinkedHashMap however, the access order flag must be set to true.
	This is because otherwise the latest entry will be removed from the cache on the basis of the one that was last accessed.
	Adding a previously existing element to the cache is considered as an access.
	Hence, if the flag was not set to true, then:
	Cache size =2;
	put (1,1);
	put (2,2);
	put (1,1);
	put (3,3); this removes (2,2) instead of (1,1) which was inserted first because the second push of (1,1) is considered an access.
	
	The LinkedHashMap might need to be synchronized using the Collections class as well.
	
	The code below assumes that a set for a previously existing element is an access and so that element is updated to the most recently accessed.
	
	LRU Cache dataStructure:
	
			(1 | 2) 		(3 | 4)  ----- hashmap<key, node> where node contains a reference to it's position in the key list and the value
				 |				 |
				 |				 |
	(head)<---->(1)<----------->(3)<---->(tail) --- doubly linked list to maintain lru order
*/

public class LRUCache {
    Map<Integer,Integer> cache;
	KeyList list;
	
	int capacity;
	int count =0;
    public LRUCache(int capacity) {
        this.capacity = capacity;
		cache = new HashMap<Integer,KeyNode>();
		list = new KeyList();
    }
    
    public int get(int key) {
        if(cache.containsKey(new Integer(key)))
		{			
			list.moveToBeginning(key);
			System.out.println("Cache = "+cache.toString());
			System.out.println("List after reordering :");
			list.iterateList();
			
			return cache.get(new Integer(key));
		}
		else
		{
			System.out.println("Cache = "+cache.toString());
			System.out.println("List :");
			list.iterateList();
			return -1;
		}
    }
    
    public void set(int key, int value) {
        if(count<capacity)
		{
			if(!cache.containsKey(new Integer(key)))
			{
				count++;
				list.insertAtHead(new KeyNode(key));
			}
			else{
				list.moveToBeginning(key);
			}
			cache.put(new Integer(key), new Integer(value));
		}
		else{
			
			if(!cache.containsKey(new Integer(key)))
			{
				System.out.println(""+key+" is not in the list.");
				list.insertAtHead(new KeyNode(key));
				System.out.println("List after insertion:");
				list.iterateList();
				KeyNode removed = list.removeEldest();
				
				System.out.println("After removal new key List :");
				list.iterateList();
				cache.remove(new Integer(removed.key));
			}
			else{
				/*This code below assumes that a set for a previously existing element is an access and so that element is updated to the most recently accessed.*/
				list.moveToBeginning(key);
			}
			
			cache.put(new Integer(key), new Integer(value));
			
		}
		
		System.out.println("Cache = "+cache.toString());
		System.out.println("List :");
		list.iterateList();
    }
	
	public void example(){
		KeyNode k1 = new KeyNode(1);
		KeyNode k2 = new KeyNode(2);
		KeyList list = new KeyList();
		list.insertAtHead(k1);
		list.insertAtHead(k2);
		list.iterateList();
		list.removeEldest();
		list.iterateList();
	}
	private class KeyList{
		ListNode head;
		ListNode tail;
		
		ListNode(){
			head = new ListNode(0);
			tail = new ListNode(0);
			System.out.println("head = "+head.toString());
			System.out.println("tail = "+tail.toString());
			head.next = tail;
			tail.prev=head;
			head.prev = null;
			tail.next=null;
			
		}
		
		void insertAtHead(KeyNode node)
		{
			if(head.next == tail)
				{
					
					node.next = tail;
					head.next=node;
					tail.prev=node;
					node.prev=head;
					
				}
			else{
				node.next=head.next;
				head.next.prev = node;
				head.next = node;
				node.prev = head;
			}
		}
		
		//remove last element in the doubly linked list
		ListNode removeEldest(){
			if(tail.prev == head)
				return null;
			
			ListNode newLast = tail.prev.prev;
			System.out.println("newLast = "+newLast.key+" address = "+newLast.toString());
			ListNode oldLast = tail.prev;
			System.out.println("oldLast = "+oldLast.key+" address = "+oldLast.toString());
			newLast.next =tail;
			tail.prev = newLast;
			
			if(oldLast != head)
				return oldLast;
			else
				return null;
		}
		
		
		void iterateList(){
			ListNode temp =head.next;
			System.out.println("head = "+head.toString());
			while(temp!=tail)
			{
				temp.print();
				System.out.println("temp = "+temp.toString());
				temp = temp.next;
			}
			
			System.out.println("tail = "+tail.toString());
			
			System.out.println("------- BACKWARDS --------");
			temp =tail.prev;
			System.out.println("tail = "+tail.toString());
			
			while(temp!=head)
			{
				temp.print();
				System.out.println("temp = "+temp.toString());
				temp = temp.prev;
			}
			System.out.println("head = "+head.toString());
			
		}
		
		
		void moveToBeginning(int key)
		{
			ListNode temp =head.next;
			if(temp.next == tail )  // list has only one element
				return;
				
			//This assumes key always exists in the list
			while((temp.key!=key)&&(temp!=tail))
			{		
				System.out.println("temp key ="+temp.key);
				temp = temp.next;
			}
			System.out.println("temp key after while="+temp.key);
			
			
			
			ListNode current = temp;
			ListNode prev = temp.prev;
			ListNode next = temp.next;
			
			prev.next = current.next;
			next.prev = current.prev;
			current.next = null;
			current.prev = null;
			insertAtHead(current);
			
		}
	}
	private class ListNode{
	
		int key; 
		ListNode next;
		ListNode prev;
		
		ListNode(int val)
		{
			this.key = val;
		}
		
		void print(){
			System.out.println(""+this.key);
		}
	}
	
	public static void main(String args[])
	{
		LRUCache cache = new LRUCache(2);
		cache.set(2,1);
		cache.set(1,1);
		cache.set(2,3);
		cache.set(4,1);
		System.out.println("output = "+cache.get(1));
		
		System.out.println("output = "+cache.get(2));
		/*cache.set(1,5);
		cache.set(1,2);
		System.out.println("output = "+cache.get(1));				
		System.out.println("output = "+cache.get(2));*/
		//cache.example();
	}
}