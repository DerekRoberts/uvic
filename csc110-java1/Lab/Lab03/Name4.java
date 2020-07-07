/** Comments
*/

public class Name4
{
	//method greeting given a name prints a greeting
	public static void greeting(String name)
	{
		System.out.println(" Hello " + name + ".  Welcome to the program.");
	}

	// method cheers given a name and a count
	// prints a cheer for the named person, count times.
	public static void cheers (String name, int count)
	{
		System.out.println(count +" cheers for " + name);

		for (int i = 0; i < count; i++)
		{
			System.out.println("Hip hip hooray!");
		}
	}

	public static void main (String [] args)
	{
		String name = "Joe";
		greeting(name);
		cheers(name, 3);
	}
}
