/**
* Assignment: Project 4
* Due date: 12/5/14
* Instructor: Dr. DePasquale
* Submitted by: Hunter Dubel and Jeremy Leon
*/

/**
* The HTTPLog object class. Controls all of the information for the given line.
* @author Jeremy Leon
* @author Hunter Dubel
*/

package edu.tcnj.csc250;

public class HTTPLog implements Comparable<HTTPLog>
{
	/**
	* Creates private strings for the object. Implemants the Comparable interface.
	**/
	private String date, resource, statusCode;
	/**
	* Creates private ints for the objects.
	**/
	private int byteSize, requestnum;

	/**
	* Creates an HTTPLog object from the data given.
	* @param date 	The date and time stamp given by the read line.
	* @param resource 	The resource name.
	* @param statusCode	Returned status code from the resource.
	* @param byteSize	The total number of bytes for the resource.
	**/
	public HTTPLog(String date, String resource, String statusCode, int byteSize)
	{
		this.date = date;
		this.resource = resource;
		this.statusCode = statusCode;
		this.byteSize = byteSize;
		requestnum = 1;
	}


	/**
	* Creates the hashcode for the object created by using the resource name.
	* @return int result The created hashcode.
	**/
	public int hashCode()
	{
		String temp = resource;
		int sum = 0;
		int length = temp.length();
		for (int i=0; i < length; i++)
		{
			char charval = temp.charAt(i);
			sum += charval;
		}
		int result = (sum/11) + 2014;
		return result;
	}

	/**
	* Returns the date for the object.
	* @return String date The stored date of the HTTPLog object.
	**/
	public String getDate()
	{
		return date;
	}

	/**
	* Returns the byte size for the object.
	* @reutrn int byteSize the total byteSize for the object.
	**/
	public int getByte()
	{
		return byteSize;
	}

	/**
	* Returns the request number for the object.
	* @return int requestnum The request number for the object.
	**/
	public int getRequest()
	{
		return requestnum;
	}

	/**
	* Updates the date to the last referenced date and updates the byteSize by adding them. Increments the request number.
	* @param date The new date for the object to store.
	* @param newByte An amount of bytes that will be added to byteSize.
	**/
	public void update(String date, int newByte)
	{
		this.date = date;
		byteSize += newByte;
		requestnum++;
	}

	/**
	* Returns all of the information in a string.
	* @return description returns the collected information in a string.
	**/
	public String toString()
	{
		String description = ("Resource Name: " + resource + " Status Code: " + statusCode + " Date: " + date + " Byte Size: " + byteSize + " Requested: " + requestnum);
		return description;
	}

	/**
	* Returns the resource name.
	* @return String resource Returns the stored resource name.
	**/
	public String getResource()
	{
		return resource;
	}

	/**
	* Compares the resource names to each other.
	* @param compare A type HTTPLog object that compares resource names.
	* @return int resourceSort The value of the compareTo sort.
	**/
	public int compareTo(HTTPLog compare)
	{
	 		int resourceSort = resource.compareTo(compare.resource);
	 		return resourceSort; 
	}
}