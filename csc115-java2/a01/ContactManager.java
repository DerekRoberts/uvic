/*
 * 	Minimal verification of parameters.  If incorrect, results undefined.
 *
 */
public class ContactManager
{
	public static final String FILENAME = "contacts.txt";
	
	public static void main (String[] args)
	{
		ContactList	l = new ContactList(FILENAME);
		
		if (args.length == 0)
		{
			System.out.println("Usage:");
			System.out.println("  ContactManager add {name} {phone number}");
			System.out.println("  ContactManager list");
			System.out.println("  ContactManager remove {name}");
			System.out.println("  ContactManager lookup {name}");
			return ;
		}
		
		if (args[0].equals("add"))
		{
			if (l.add(args[1],args[2]))
			{
				l.save();
			}
			else
			{
				System.out.println("Failed add.");
			}
		}
		else if (args[0].equals("list"))
		{
			l.list();
		}
		else if (args[0].equals("remove"))
		{
			if (l.remove(args[1]))
			{
				l.save();
			}
			else
			{
				System.out.println("Failed remove.");
			}
		}
		else if (args[0].equals("lookup"))
		{
			l.lookup(args[1]);
		}
		else
		{
			System.out.println("Unknown command: " + args[0]);
		}
	}
}
