/*
 * Contact.java
 *
 * Store the infomation about a contact.
 *
 * In this implementation, we store a single name and a phone number.
 * Both are stored as Strings.
 *
 */
public class Contact
{
	/* Instance variables = CHECK */
	private String	cName;
	private String	cNumber;


	/*
	 * Create a new contact with name and phoneNumber
	 */
	public Contact (String name, String phoneNumber)
	{
		/* Set cName and cNumber = CHECK */
		cName = 	name;
		cNumber = 	phoneNumber;
	}
	
	/*
	 * Return the name associated with this contact 
	 */
	public String getName()
	{
		/* Return cName = CHECK */
		return cName;
	}
	
	/*
	 * Return the phoneNumber associated with this contact
	 */
	public String getPhoneNumber()
	{
		/* Return cNumber = CHECK */
		return cNumber;
	}
	
	/*
	 * Return a string representation of this contact.
	 *
	 * The format should be: name followed by a single space then phonenumber.
	 */
	public String toString()
	{
		/* Return cName, " " and cNumber and a string = CHECK */
		return cName + cNumber;
	}
}
