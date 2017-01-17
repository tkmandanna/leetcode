import java.util.*;
public class LFUCache {

    Map<Integer,Integer> cache;
    Map<Integer,MapVal>bucketMap;
	AccessBucketList list;
	int capacity =0;
	/*
		{key 1|  key 2|  key 3}   --bucketMap ..where key is the key for which the value has been requisitioned and the value is the accessbucket node
			|	  |        |
			|	  |		   |
		(count 1    ,   count2 )  -- doubly linked list to maintain lfu order..all elements with the samecount will be in the same bucket
		(linkedhashset 1, linkedhashset2) -- linkedhashset containing keys with the same number of access in lru order. In this example, elements 1 and 2 have had the same number
												of accesses.
											A node in the doubly linked list will contain a count and a linkedhashset.
											
											Get(key)=>  return value from cache.
														in bucketMap find bucketNode, remove element from the linkedhashset and move to hashset of bucketNode of count + 1 
														which is next bucket
														If a bucket becomes empty ,delete bucket so that next bucket will contain elements with the least number of accesses
														
											set(key,value) => if cache is not full, add to cache/overwrite existing value
																// check if put also counts as an access..if so logic required here
															   if cache is full,
															   add to cache/overwrite existing value
															   in bucketMap , findHeadNode and then first element in hashset i.e. hashset.iterator.next() should give
															   least recently accessed element because in a linkedhashset elements were added in the order in which they were inserted
	*/
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
		cache = new HashMap<Integer,Integer>();
		bucketMap = new HashMap<Integer,MapVal>();
		list = new AccessBucketList();
    }
    
    public int get(int key) {
		
        return -1;
    }
	
	
    
    public void put(int key, int value) {
        /*cache.put(new Integer(key),new Integer(value);
		MapVal mapval = new MapVal();
		
		if(cache.size()<=capacity)
		{
			
		}
		else{
			
		}*/
		if(!cache.containsKey(new Integer(key)))
		{
			if(cache.size()<=capacity)
			{
				list.insertKey(key);
			}
		}
    }
    
	
    class Node{
        int count=0;
        LinkedHashSet<Integer> bucket;
		
		Node prev;
		Node next;
		
		
    }
	//doubly linked list
	class AccessBucketList{
		Node head;
		Node tail;
		
		AccessBucketList(){
			head = new Node();
			tail = new Node();
			head.count  = tail.count = -1;
			head.next= tail;
			tail.prev = head;
		}
		
		void moveToNextBucket(Node node, int key){
			
		}
		
		void evictLFUElement(){
			
		}
		
		Node insertKey(int key)
		{
			if(head.next.count != 0 )
			{
				Node newNode = new Node();
				newNode.count = 0;
				newNode.next = head.next;
				newNode.prev = head;
				head.next.prev = newNode;
				head.next = newNode;
				
				newNode.bucket = new LinkedHashSet<Integer>();
				newNode.bucket.add(new Integer(key));
				return newNode;
			}
			else{
				head.next.bucket.add(new Integer(key));
				return head.next;
			}
		}
		
		Node insertKey(int key, Node bucket){
			return null;
		}
	}
	
	class MapVal{
		Node node;
	}
	
	public static void main(String args[])
	{
		LFUCache lfucache = new LFUCache(2);
		lfucache.put(1,1);
		lfucache.put(2,2);
		System.out.println(lfucache.list.toString());
	}
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
