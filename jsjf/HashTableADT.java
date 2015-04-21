/**
* Assignment: Project 4
* Due date: 12/5/14
* Instructor: Dr. DePasquale
* Submitted by: Hunter Dubel and Jeremy Leon
*/

/**
* Defines the interface for the hash table.
* @author Jeremy Leon
* @author Hunter Dubel
*/

package jsjf;

public interface HashTableADT<T>
{
	/**
	 * Adds the element to the hash table and returns true if it was succesfully
	 * put in otherwise returns false.
	 * @param element element to be placed in hash table
	 */
	public boolean addElement (T element);

	/**
	 * Finds the specified element if it is in the table
	 * @param element element to be found in table.
	 */
	public T find (T element);

	/**
	 * Returns the number of elements in the hashtable.
	 */
	public int size();

	/**
	 * Returns true if the table is empty, else it returns false.
	 */
	public boolean isEmpty();

	/**
	 * Returns basic information about the hashtable as a string
	 */
	public String toString();

	/**
	 * Converts the hashtable into an array of all the elements contained in the table.
	 */
	public T[] getElements();
}