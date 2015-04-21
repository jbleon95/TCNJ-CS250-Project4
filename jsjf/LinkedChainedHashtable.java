/**
* Assignment: Project 4
* Due date: 12/5/14
* Instructor: Dr. DePasquale
* Submitted by: Hunter Dubel and Jeremy Leon
*/

/**
* The hash table used by the processor and driver. Uses an array of linkedlists from the API to store
* elements. Supports generics. Contains methods to add elements, find elements, give the size and the state 
* of the table and put the elements into an array.
* @author Jeremy Leon
* @author Hunter Dubel
*/

package jsjf;

import java.util.*;

public class LinkedChainedHashtable<T> implements HashTableADT<T>
{
	/**
	* ints that keep track of the number of elements and the number of buckets
	**/
	private int count, bucketSize;

	/**
	* The array of LinkedLists that makes up the hash table.
	**/
	private LinkedList<T>[] table;

	/**
	* The constructor of the class. Sets the count to zero and creates the array of LinkedLists
	* based on the size given. Then it populates the array with empty linked lists.
	* @param size the number of buckets the hash table will contain
	**/
	public LinkedChainedHashtable(int size)
	{
		count = 0;
		table = (LinkedList<T>[])new LinkedList[size];
		bucketSize = size;
		for (int i=0; i < size; i++)
		{
			table[i] = new LinkedList<T>();
		}
	}

	/**
	 * Adds the element to the hash table. Finds the index of the element using the hashing function, 
	 * then looks through the table to make sure the element does not already exist. If not, it adds it to the
	 * proper bucket and returns false. Otherwise nothing is added and it returns false.
	 * @param element element to be placed in hash table
	 * @return result true if the element was added
	 */
	public boolean addElement(T element)
	{
		boolean result = false;
		int index = hash(element);

		/**
		 * Uses a listiterator to go through the hash table to make sure the element hasn't been added.
		 */
		ListIterator<T> iterator = table[index].listIterator(0);
		boolean found = false;
		while (iterator.hasNext() == true && found == false)
		{
			T compare = iterator.next();
			int compared = ((Comparable)compare).compareTo(element);
			if (compared == 0)
				found = true;
		}

		/**
		 * If element wasn't found adds it to the proper bucket.
		 */
		if (found == false)
		{
			table[index].add(element);
			result = true;
			count++;
		}
		return result;
	}

	/**
	 * Looks through the hash table to see if the specified element exists. If it does,
	 * it is then returned. If element does not exist in the table an exception if thrown.
	 * @throws NoSuchElementException exception thrown if element is not found
	 * @param element element to be found in the hash table
	 * @return search element that was searched for
	 */
	public T find(T element)
	{
		int index = hash(element);
		boolean found = false;
		T search = null;
		/**
		 * Uses a listiterator to go through the hash table and find the specified element
		 */
		ListIterator<T> iterator = table[index].listIterator(0);
		while (iterator.hasNext() == true && found == false)
		{
			T compare = iterator.next();
			int compared = ((Comparable)compare).compareTo(element);
			if (compared == 0)
			{
				found = true;
				search = compare;
			}
		}
		if (found == false)
			throw new NoSuchElementException();
		return search;
	}

	/**
	 * Uses the objects hashCode method to get a hash number. This number is then folded on itself
	 * by being split into two numbers that are added. This number is then modulus divided by the bucketsize
	 * to get the bucket number that the object will be placed in.
	 * @param element element that hash key is getting
	 * @return key hash key
	 */
	private int hash(T element)
	{
		int num = element.hashCode();
		String temp = Integer.toString(num);
		int length = temp.length();
		int div = length/2;
		int num1 = Integer.parseInt(temp.substring(0, div));
		int num2 = Integer.parseInt(temp.substring(div));
		int key = (num1 + num2)%bucketSize;
		return key;
	}

	/**
	 * Finds the largest chain in the hash table and displays the bucket number and chain size
	 * of it. Also returns the largest linkedlist found.
	 * @return largest the largest linkedlist found
	 */
	public LinkedList<T> getLargeChain()
	{
		int n1 = table[0].size();
		int n2 = 0;
		for (int i=1; i < bucketSize; i++)
		{
			n2 = table[i].size();
			n1 = Math.max(n1, n2);
		}
		int index = 0;
		while(n1 != table[index].size())
			index++;
		LinkedList<T> largest = table[index];
		System.out.println("Bucket " + index + "\nSize: " + n1);
		return largest;

	}
	/**
	 * Returns the number of elements in the hashtable.
	 * @return count number of elements in table
	 */
	public int size()
	{
		return count;
	}

	/**
	 * Returns true if the table is empty otherwise it returns false
	 * @return result boolean value of empty state
	 */
	public boolean isEmpty()
	{	
		boolean result = false;
		if (size() == 0);
			result = true;
		return result;
	}

	/**
	 * Returns the size, number of buckets, and if the table is empty as a string
	 * @return String of information
	 */
	public String toString()
	{
		return ("Elements: " + size() + " Buckets: " + bucketSize + " Empty: " + isEmpty());
	}

	/**
	 * Adds all the elements in the hash table to one array.
	 * @return hashArray array of all the elements
	 */
	public T[] getElements()
	{
		int arraySize = size();
		T[] hashArray = (T[])(new Comparable[arraySize]);
		int arrayIndex = 0;
		for (int i=0; i < bucketSize; i++)
		{
			ListIterator<T> iterator = table[i].listIterator(0);
			while (iterator.hasNext() == true)
			{
				T temp = iterator.next();
				hashArray[arrayIndex] = temp;
				arrayIndex++;
			}
		}
		return hashArray;
	}
}