/*
 * FileExample.java
 *
 * An example of doing file input and output using Java.
 *
 * You are free to use any of this code directly in your assignments.
 *
 * J. Corless, January 2010.
 */

import java.io.*;

public class FileExample
{
	/* 
	 * This method checks to see if a file exists
	 */
	public static boolean exists(String filename)
	{
		File f = new File(filename);
		if (f.exists())
		{
			System.out.println("Yep, I found  " + filename
				+ " in the current directory.");
			return true;
		}
		System.out.println("I didn't find: " + filename);
		return false;
	}
	
	public static boolean delete (String filename)
	{
		try
		{
			File f = new File(filename);
			if (f.exists())
			{
				if (!f.delete())
				{
					System.out.println("Fatal error: cannot delete "
						+ filename);
					return false;
				}
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			System.out.println("Fatal Error: " + e);
			e.printStackTrace();
			return false;
		}	
	}
	
	public static boolean writeFile (String filename)
	{
		try
		{
			/* This creates an object that when we "print" to it, it
			 * writes to the file, instead of the screen.
			 */

			PrintWriter out = new PrintWriter(
						new BufferedWriter(
						new FileWriter(filename)));
			
			out.println("This is the first line in the file");
			out.println("And this is the second.");
			
			out.close();
		}
		catch (Exception e)
		{
			System.out.println("Fatal Error: " + e);
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	
	/* This method will read a file and display its
	 * contents on stdout
	 */
	public static boolean readFile(String filename)
	{
		try
		{
			/* This is the line that creates something, it opens the 
			 * file on the disk and now we can read lines from
			 * it.
			 */
			BufferedReader input = new BufferedReader(
						new FileReader(filename));
			
			String line;
			for (;;)
			{
				/* This reads the next line in the file
				 * If there are no more lines, it returns null
				 */
				line = input.readLine();
				
				if (line == null)
					break;
				System.out.println(line);
			}
			input.close();
		}
		catch (Exception e)
		{
			System.out.println("Fatal Error: " + e);
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	
	public static void main (String[] args)
	{
		if (exists("FileExample.java"))
		{
			readFile("FileExample.java");
		}
		
		writeFile("foo.txt");
		writeFile("foo2.txt");
		delete("foo2.txt");
		
	}
}
