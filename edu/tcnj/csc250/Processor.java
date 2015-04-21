/**
* Assignment: Project 4
* Due date: 12/5/14
* Instructor: Dr. DePasquale
* Submitted by: Hunter Dubel and Jeremy Leon
**/

/**
* The processor for the program. It gathers all the needed information and separates what
* is needed. for the HTTPLog class. It also checks for input commands from the user so
* that they may search the hashtable.
* @author Jeremy Leon
* @author Hunter Dubel
*/

package edu.tcnj.csc250;

import java.io.*;
import java.util.*;
import jsjf.*;

public class Processor
{
	/**
	* The Linked Chained Hashtable of HTTP objects set to the size of 75.
	**/
	private static LinkedChainedHashtable<HTTPLog> table = new LinkedChainedHashtable<HTTPLog>(75);
	/**
	* Creates the scanner that will read the input file line by line.
	**/
	private static Scanner inputFile;
	/**
	* Creates a PrinterWriter for writing the errors.
	**/
	private static PrintWriter writer;
	/**
	* The processor's method which will run through the entire program and check for sorting.
	**/
	public static void Processor() throws IOException
	{
		/**
		* Checks to see if there is an input file. If not, try the catch and return an error.
		**/
		try
		{
			writer = new PrintWriter(new File("errors.txt"));
			inputFile = new Scanner(new File("access_log.inp"));
		}
		catch(IOException e)
		{
			writer.println("The file access_log.inp could not be found!");
		}
		/**
		* Increments through the input file and for each line creates a new HTTPLog object
		* with the included information from that line.
		**/
		for (int i=0; i < 34963; i++)
		{
			String inputLine = inputFile.nextLine();
			String date = splitDate(inputLine);
			String resource = splitResource(inputLine);
			String code = splitCode(inputLine);
			int byteSize = splitByte(inputLine);
			
			HTTPLog http = new  HTTPLog(date, resource, code, byteSize);
			addLog(http);
		}
		/**
		* Creates a boolean value that checks to see if the user wants to quit out of the program.
		* The while loop continuously checks for user input and runs until the user types in "QUIT"
		**/
		boolean quit = false;
		while (quit == false)
		{
			System.out.println("Enter Command");
			Scanner scan = new Scanner(System.in);
			String input = scan.next();

			/**
			* Takes in the command "DETAIL" followed by a resource name.
			* If no resource name is asked, the user is asked to enter a resource name. If the name is invalid, it is told so.
			* If the user enters a slash, it is determined they want the index.
			**/
			if (input.equals("DETAIL"))
			{
				String request = scan.nextLine();
				if (request.equals(""))
				{
					System.out.println("Please enter a resource name after the command.");
				}
				else
				{	
					request = request.substring(1,request.length());
					
					/**
					* If the name entered is a "/" it corrects it to /index.html.
					**/
					if (request.equals("/"))
					{
						request = "/index.html";
					}

					HTTPLog search = new  HTTPLog("", request, "", 0);
					/**
					* Checks to see if the object is found in the table.
					* If the object cannot be found, an error is returned.
					**/
					try
					{
						search = table.find(search);
						System.out.println(search);
					}
					catch (NoSuchElementException e)
					{
						writer.println("No resource can be found with the name: " + request);
						System.out.println("No resource can be found with the name: " + request);
					}
				}

			}

			/**
			* Takes in the command "TOPTENSERVED".
			* The command creates a new array of the elements and then creates a new HTTPLog array with the original array's contents casted to an HTTPLog type.
			* A new Linked List is then created. The arrays are then compared at each step for their values. If the object is larger than what is in the array it is added, up to ten objects.
			* A loop finishes by restating the new LinkedList array in decending order.
			**/
			else if (input.equals("TOPTENSERVED"))
			{
				Object[] topTen = table.getElements();
				HTTPLog[] httpTen = new HTTPLog[topTen.length];

				for (int i=0; i < topTen.length; i++)
				{
					httpTen[i] = (HTTPLog)(topTen[i]);
				}
				
				LinkedList<HTTPLog> sortTopTen = new LinkedList<HTTPLog>();

				for (int i=0; i < 10; i++)
				{
					int iMax = 0;
					for (int j=1; j < httpTen.length; j++)
					{
						if ((sortTopTen.contains(httpTen[j]) == false) && (httpTen[j].getRequest() > httpTen[iMax].getRequest()))
							iMax = j;
					}
					sortTopTen.add(i, httpTen[iMax]);
				}

				for (int i=0; i < 10; i++)
				{
					System.out.println(sortTopTen.get(i).getResource() + " was accessed " + sortTopTen.get(i).getRequest() + " times.");
				}

			}

				/**
			* Takes in the command "TOPTENSIZE".
			* The command creates a new array of the elements and then creates a new HTTPLog array with the original array's contents casted to an HTTPLog type.
			* A new Linked List is then created. The arrays are then compared at each step for their values. If the object is larger than what is in the array it is added, up to ten objects.
			* A loop finishes by restating the new LinkedList array in decending order.
			**/
			else if (input.equals("TOPTENSIZE"))
			{
				Object[] topTen = table.getElements();
				HTTPLog[] httpTen = new HTTPLog[topTen.length];

				for (int i=0; i < topTen.length; i++)
				{
					httpTen[i] = (HTTPLog)(topTen[i]);
				}	

				LinkedList<HTTPLog> sortTopTen = new LinkedList<HTTPLog>();

				for (int i=0; i < 10; i++)
				{
					int iMax = 0;
					for (int j=1; j < httpTen.length; j++)
					{
						if ((sortTopTen.contains(httpTen[j]) == false) && (httpTen[j].getByte() > httpTen[iMax].getByte()))
							iMax = j;
					}
					sortTopTen.add(i, httpTen[iMax]);
				}

				for (int i=0; i < 10; i++)
				{
					System.out.println(sortTopTen.get(i).getResource() + " totaled " + sortTopTen.get(i).getByte() + " bytes.");
				}

			}
			else if (input.equals("CHAIN"))
			{
				LinkedList<HTTPLog> largest = table.getLargeChain();
				ListIterator<HTTPLog> iterator = largest.listIterator(0);
				while (iterator.hasNext())
				{
					System.out.println(iterator.next().getResource());
				}
				
			}
			else if (input.equals("QUIT"))
			{
				quit = true;
			}
			else 
				System.out.println("Invalid Command");	
		}
		writer.close();
	}

	/**
	* Adds a HTTPLog to the hashtable. If the resource name exists, it updates the name.
	* @param add An object of HTTPLog type.
	**/
	public static void addLog(HTTPLog add)
	{
		boolean result = table.addElement(add);
		if (result == false)
		{
			String newDate = add.getDate();
			int newByte = add.getByte();
			((HTTPLog)table.find(add)).update(newDate, newByte);
		}
	}

	/**
	* Separates the date from the input String and returns it.
	* @param input A string that is the input line to be checked for the date.
	* @return String date The separated date and time stamp for the line of code.
	**/
	public static String splitDate(String input)
	{	
		String inputLine, fixedLine, date;
		inputLine = input;
		Scanner subScanner = new Scanner(inputLine);
		subScanner.useDelimiter("\\[|\\]");
		subScanner.next();
		fixedLine = subScanner.next();
		date = fixedLine;

		return date;
	}

	/**
	* Separates the resource name from the input String and returns it.
	* @param input A string that will have the resource name separated form it.
	* @param resource The separated resource name from the input string.
	**/
	public static String splitResource(String input)
	{	
		String inputLine, fixedLine, resource;

		inputLine = input;
		inputLine = inputLine.replace("?", "\t");

		Scanner subScanner = new Scanner(inputLine);

		subScanner.useDelimiter("\"|\t");
		subScanner.next();
		fixedLine = subScanner.next();

		if (fixedLine.contains("POST"))
		{
			fixedLine = fixedLine.substring(5,fixedLine.length());
		}
		else
		{
			fixedLine = fixedLine.substring(4,fixedLine.length());
		}

		if (fixedLine.contains("HTTP"))
		{
			fixedLine = fixedLine.substring(0,fixedLine.length()-9);	
		}

		if (fixedLine.equals("/"))
		{
			fixedLine = "/index.html";
		}
	
		resource = fixedLine;
		
		return resource;
	}

	/**
	* Separates the access code from the input string and returns it.
	* @param input A string that should have the code separated.
	* @return String code The returned access code.
	**/
	public static String splitCode(String input)
	{	
		String inputLine, fixedLine, code;

		inputLine = input;
		Scanner subScanner = new Scanner(inputLine);
		subScanner.useDelimiter("\"");
		subScanner.next();
		subScanner.next();

		fixedLine = subScanner.next();
		fixedLine = fixedLine.substring(1,4);
		
		
		code = fixedLine;
		
		return code;
	}

	/**
	* Separates the amount of the bytes from the input string and returns the value.
	* @param input A string that should have the amount of bytes separated from it.
	* @return int result The number of bytes from that given line returned in an integer.
	**/
	public static int splitByte(String input)
	{	
		String inputLine, fixedLine, code;
		int result = 0;

		inputLine = input;
		Scanner subScanner = new Scanner(inputLine);
		subScanner.useDelimiter("\"");
		subScanner.next();
		subScanner.next();

		fixedLine = subScanner.next();
		fixedLine = fixedLine.substring(5,fixedLine.length()-1);
		
		code = fixedLine;
		if (!code.equals("-"))
			result = Integer.parseInt(code);
		return result;
	}
}