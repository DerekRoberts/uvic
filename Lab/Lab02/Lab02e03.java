/** This exercise teaches me how to call classes
 */

public class Lab02e03
{
	public static void pointUpTriangle()
	{
		System.out.println("  *  ");
		System.out.println(" *** ");
		System.out.println("*****");
	}

	public static void pointDownTriangle()
	{
		System.out.println("*****");
		System.out.println(" *** ");
		System.out.println("  *  ");
	}

	public static void box()
		{
			System.out.println("*****");
			System.out.println("*****");
			System.out.println("*****");
		}

	public static void diamond()
	{
		pointUpTriangle();
		pointDownTriangle();
	}

	public static void main (String [] args)
	{
		box ();
		diamond();
		diamond();
		box();
	}
}