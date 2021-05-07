import java.io.*;
import java.util.*;

public class MyHashSet implements HS_Interface
{	private int numBuckets;
	private Node[] bucketArray; 
	private int size; // total # keys stored in set right now
	
	// THIS IS A TYPICAL AVERAGE BUCKET SIZE. IF YOU GET A LOT BIGGER THEN YOU ARE MOVING AWAY FROM (1) 
	private final int MAX_ACCEPTABLE_AVE_BUCKET_SIZE = 20;  
	
	public MyHashSet( int numBuckets )
	{	size=0;
		this.numBuckets = numBuckets;
		bucketArray = new Node[numBuckets]; // array of linked lists
		System.out.format("IN CONSTRUCTOR: INITIAL TABLE LENGTH=%d RESIZE WILL OCCUR EVERY TIME AVE BUCKET LENGTH EXCEEDS %d\n", numBuckets, MAX_ACCEPTABLE_AVE_BUCKET_SIZE );
	}
   
   private int hashOf(String key) // h MUST BE IN [0..numBuckets-1]
	{
      int returnValue = 0;
     
      for(int i = 0; i < key.length(); i++)
         {          
          int num = (int)key.charAt(i);
          returnValue = ((returnValue * 31) + num); 
         }
       
      return Math.abs(returnValue % this.numBuckets);
    }
   
   public boolean contains(String key)
	{
		
      int bucketIndex = hashOf(key);
		Node head = bucketArray[bucketIndex];
		Node curr = head;
		
      while(curr != null)
		{
         if(curr.data.equals(key))
           {
             return true;
           }
         else 
           {
             curr = curr.next;
           }   
		}
      return false;
	}

	public boolean add(String key)
	{
      if(contains(key))
          {
            return false;
          }
		
      int bucketIndex = hashOf(key);
		
		
      Node currentHead = bucketArray[bucketIndex];
				
      Node newHead = new Node(key,currentHead);
		
      bucketArray[bucketIndex] = newHead;
      	
      ++size;
		
      if(size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE*numBuckets)
		{
			
         upSize_ReHash_AllKeys();
		}

		
      return true;

	}
   
   public boolean remove(String key)
   {
   
      int bucketIndex = hashOf(key);
		
      Node head = bucketArray[bucketIndex];
  		
		
      if(head == null)
        {
          return false;
        }
		
		
      if(head.data.equals(key))
		{
		 	
          bucketArray[bucketIndex] = head.next; 
			
         return true;
		}

		
		
      Node curr = head;
		
      while(curr.next != null)
		{
			
         if(curr.next.data.equals(key))
         
			{
				
            curr.next = curr.next.next;
				
            return true;
			}
			
         else 
         {
           curr = curr.next;
         }  
		}
		
      return false;
     }
	
	private void upSize_ReHash_AllKeys()
	{	System.out.format("KEYS HASHED=%10d UPSIZING TABLE FROM %8d to %8d REHASHING ALL KEYS\n",
						   size, bucketArray.length, bucketArray.length*2 );
		Node[] biggerArray = new Node[ bucketArray.length * 2 ];
		this.numBuckets = biggerArray.length;
		
      for(int i = 0; i < bucketArray.length; i++)
		{
			
         Node head = bucketArray[i];
			
         Node curr = head;
			
         while(curr != null)
			{
            
                   
            int bucketIndex = hashOf(curr.data);
           		
            Node temp = new Node(curr.data, biggerArray[bucketIndex]);
				
            biggerArray[bucketIndex] = temp;
                     
            curr = curr.next;
			}

		}
		this.bucketArray = biggerArray;
      
      
	} // END UPSIZE & REHASH
	
   public boolean isEmpty()
   {
     if(size() == 0)
       {
         return true;
       }
     return false;   
   }	
   
   public int size()
   {
     return size; 
   }
   
   public void clear()
   {
     for(int i = 0; i < bucketArray.length;i++)
        {
          bucketArray[i] = null;
        }
   }	

} //END MyHashSet CLASS

class Node
{	String data;
	Node next;  
	public Node ( String data, Node next )
	{ 	this.data = data;
		this.next = next;
	}
} 


