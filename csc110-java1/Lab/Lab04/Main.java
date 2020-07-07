/*	Methods the Main's view
	Your task is to write method stubs that have the correct signature
	for the calls in this mains program.  To keep track of the methods
	we will add a siongle println to the stub which prints the
	method's name.

	Take the method calls out of comments as soon as you have the
	stub in place.  Do not delete or alter any of the method stubs
	that you create rather make a new method every time.

	The first one is done already.
*/

public class Main
{
	public static void main (String [] args)
	{
		//Variables
		int x = 3;
		int y = 5;
		double score = 240.0;
		double average = 0;

		String line1= " Mary''s lamb says ";


		// Call to doAvg
		doAvg(score, x);

		//for the following  write stub and uncomment

		// Calls to doAvg.
		doAvg(x, score);
		doAvg(2 + 1, 78.5 + 80.0 + 81.5);

		// Call to returnAvg.
		average = returnAvg(x, score);

		// Calls to critterSays
		//critterSays(line1 + "hello");
		//critterSays(line1, "hello");

	}

	// method1
	// public static void doAvg(double total, int count)
	public static double doAvg(double total, int count)
	{
	//	System.out.println("Running: doAvg (double,int)");
		double returnAvgReturn = total / count;
		System.out.println("doAvg (double,int) ->" + returnAvgReturn);
		return returnAvgReturn;
	}

	// method2 - like method 1, but int-double, instead of double-int
	// public static void doAvg(int count, double total)  //replaced with double instead of void
	public static double doAvg(int count, double total)
	{
		double returnAvgReturn = total / count;
		System.out.println("doAvg (int,double) ->" + returnAvgReturn);
		return returnAvgReturn;
	}

	// method3
	public static double returnAvg(int count, double total)
	{
		double returnAvgReturn = total / count;
		System.out.println("returnAvg          ->" + returnAvgReturn);
		return returnAvgReturn;
	}

	public static String critterSays(String critterIn)
	{
		return critterIn;
	}

	public static String critterSays(String critterIn, String hello)
	{
		String critterInPlusHello =
		return critterIn;
	}
}