/*	Methods creating a test main
	Below are a number of methods.

	Your task is to create main calls such that:
	if there is an even number of parameters use literals
	of the correct type as parameters.  If there
	is an odd number of parameters use variables to test.

	For each method that returns a value use a println to
	show what was returned.

*/

public class Methods
{
	public static void main (String [] args)
	{
		//Variables
		double	doubleA	= 1;
		double	doubleB	= 2;
		int		intA	= 1;
		int		intB	= 2;

		System.out.println("Doubles  -1 and 2");
		System.out.println("Integers -1 and 2");
		System.out.println("First value always passed to first request and so forth.");

		// Calls to methods
		add (doubleA, doubleB);

		add (intA, intB);

		max (doubleA, doubleB);

	//	isNegative (doubleA);

	}

	//
	// return sum of two doubles
	public static double add(double a, double b)
	{
		double returnWhat = a+b;
		System.out.println("add (double, double) = " + returnWhat);
		return a+b;

	}

	// return sum of two integers
	public static int add(int a, int b)
	{
		int returnWhat = a+b;
		System.out.println("add (int, int)       = " + returnWhat);
		return a+b;
	}

	// return greater of two numbers
	public static double max(double a, double b)
	{
		if (a > b)
		{
			double returnWhat = a;
			System.out.println("max (int, int)       = " + returnWhat);
			return a;
		}
		else
		{
			double returnWhat = b;
			System.out.println("max (int, int)       = " + returnWhat);
			return b;
		}
	}

	// return true if negative, false otherwise
	public static boolean isNegative(double n)
	{
		double returnWhat = n;
			System.out.println("isNegative (double)  = " + returnWhat);
		return n < 0.0;
	}

	// return absolute value
	public static int abs(int n)
	{
		if (isNegative(n))
			return n*-1;
		else
			return n;
	}

	// greatest common divisor is determined by subtracting
	// the lower from the higher until they are equal.
	public static int gcd(int a, int b)
	{
		int temp;
		a = abs(a);
		b = abs(b);
		while(a!=b)
		{
			if (a > b) a = a-b;
			else b = b-a;
		}
		return b;
	}

	// lowest common multiple of 2 numbers is determined by
	// dividing their product by their greatest common divisor
	public static int lcm(int a, int b)
	{
		a = abs(a);
		b = abs(b);
		return a*b/gcd(a,b);
	}

}