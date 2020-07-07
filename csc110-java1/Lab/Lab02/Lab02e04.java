/** Changing the value of *any* changes the
    only word output in this program
*/

public class Lab02e04
{
	public static void main (String[] args)
	{
		String any = "value";

		System.out.println(any);

		//*'s and > added to show in output since tabs and newlines are whitespace.
		System.out.print("*\n*\n*\n>\t>\t>" + any + "\n*\n*\n*");

		//how many \'s and how many /'s will be printed? Why?

		System.out.println("\\\\\\\\\\/////");

		System.out.println("The value of the string variable is \"" + any + "\"");
	}
}