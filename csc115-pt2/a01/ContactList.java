/*
 * ContactList.java
 *
 * This class implements a list of contacts.
 *
 */
 
import java.io.*;

public class ContactList
{
	/* Our contact list can store a maximum of this 
	 * many entries.  Trying to add more than this results in
	 * the add method returning false.
	 */
	static final int MAX_ENTRIES = 100;
	
	/* XXX -- You need more instance variables here XXX */
	private String 	addressBook[]	= new String[MAX_ENTRIES];
	private int		nextGoesHere	= 0;
	private String 	saveToThisFile	= "contacts.txt";
	
	/*
	 * Create a new ContactList that uses filename for storage.
	 * If filename exists, populate the list with the contacts
	 * stored in the file.
	 */
	public ContactList (String filename)							
	{
		/* The text file can be given a different name by parameter */
		saveToThisFile = filename;
				
		/* Code shamelessly stolen from FileExample.java */
		try
		{
			/* If a file is found this will import its contents by using
			 * a for loop to assign each line to a value in the array
			 * addressBook and incrementing nextGoesHere.
			 */
			BufferedReader input = new BufferedReader(
						new FileReader( saveToThisFile));
			
			String line;
			for (;;)
			{
				line = input.readLine();
				
				if (line == null)
					break;

				addressBook[nextGoesHere] = line;
				nextGoesHere++;
			}
			input.close();
		}
		catch (Exception e)
		{
			/* Not finding a file is not an error and creation of a save
			 * file will be left for the save() method.
			 */
		}
	}
	
	/*
	 * Save the current contact list to the file.  Return true
	 * if successful, false if an error occurs.
	 */
	public boolean save()											
	{
		/* Code shamelessly stolen from FileExample.java */
		try
		{
			/* This creates an object that when we "print" to it, it writes
			 * to the file, instead of the screen.
			 */

			PrintWriter out = new PrintWriter(
						new BufferedWriter(
						new FileWriter( saveToThisFile)));
			
			for( int i= 0; i< nextGoesHere; i++ )
				out.println( addressBook[i] );
			
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
		
	/*
	 * Add a new contact
	 *
	 * No checking is done to see if the name is already in the 
	 * contact list.  Adding the same name twice results in 
	 * undefined behavior when search for that contact later.
	 *
	 * Return true if the contact was added, false otherwise.
	 */
	public boolean add(String name, String phone)					
	{
		/* Adds name and phone, separated by a space, to the end of
		 * addressBook (at nextGoesHere).  Also, increment nextGoesHere.
		 */
		addressBook[ nextGoesHere]= name+ " "+ phone;
		nextGoesHere++;
		return true;
	}
	
	/*
	 * Print the current contacts on stdout.
	 */
	public void list()												
	{
		/* Loops through and prints the contents of addressBook,
		 * starting at [0] and ending at [nextGoesHere].
		 */
		for( int i= 0; i< nextGoesHere; i++)
			System.out.println( addressBook[ i]);
	}
	
	/*
	 * Remove the contact passed as the parameter name.
	 *
	 * If the contact is not currently in the contact list, return
	 * false. 
	 * 
	 * Return true if the contact is removed.
	 */
	public boolean remove(String name)								
	{
		/* killIndex is the index of the entry to be removed.  When
		 * given a name the indexFinder() method that index or -1 if not
		 * found.  -1 is out of range and easy to test for.
		 */	
		int		killIndex = indexFinder( name);	
		
		/* If a killIndex has been set to >= 0 then drop the index,
		 * nextGoesHere, by one.  Then replace the contact in killIndex
		 * with the last (nextGoesHere) contact and finally remove the
		 * last contact.
		 */
		if( killIndex>= 0 ){
			nextGoesHere--;
			addressBook[ killIndex]		= addressBook[ nextGoesHere];
			addressBook[ nextGoesHere]	= "";
			return true;
		}			
		else{
			return false;
		}
	}
	
	/*
	 * Print the contact information for name on stdout.
	 *
	 * Return false if the contact is not found, true otherwise.
	 */
	public boolean lookup(String name)								
	{
		if( indexFinder( name)>= 0){
			System.out.println( addressBook[ indexFinder( name)]);
			return true;
		}
		else{
			System.out.println( "Not found.");
			return false;
		}
	}
	
	/*
	 * Returns the index of a name in addressBook.  Not found = -1.
	 * 
	 * I thought this was a useful method, so now it's made.  :D
	 */	
	public int indexFinder( String name)
	{
		int		returnIndex = -1;
		
		for( int i= 0; i< nextGoesHere; i++){
			if( name.equals( addressBook[ i].substring( 0,( 
				addressBook[ i].indexOf( ' ')))))
			{
				returnIndex = i;
				/* break ensures only the first possible instance of a
				 * name is chosen, otherwise it would pick the last one.
				*/
				break;
			}
		}
		return returnIndex;
	}
}
