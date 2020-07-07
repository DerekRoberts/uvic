/*
 * DoubleListTester.java
 *
 * J. Corless, January 2010
 */
public class DoubleListTester
{
	public static int  testCount = 0;

	public static void displayResults (boolean passed)
	{
		/* There is some magic going on here getting the line number 
		 * Borrowed from:
		 * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java-source-code-line-number-file-name-in-code/
		 */
		if (passed)
		{
			System.out.println ("Passed test: " + testCount);
		}
		else
		{
			System.out.println ("Failed test: " + testCount + " at line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		}
		testCount++;
	}

	
	public static void testOne ()
	{
		boolean 	passed;
		ListOfDoubles 	l = new LinkedListOfDoubles();
		
		System.out.println("Basic testing of size(), addFront, addBack, elementAt.");
		displayResults (l.size() == 0);

		l.addFront(10.0);
		displayResults (l.size() == 1);

		l.addBack(9.0);
		displayResults (l.size() == 2);

		l.addFront(7.0);
		displayResults (l.size() == 3);

		displayResults (l.elementAt(0) == 7.0);

		displayResults (l.elementAt(1) == 10.0);

		displayResults (l.elementAt(2)== 9.0);
	}

	public static void addArray (double[] a, ListOfDoubles l, boolean addBack)
	{
		for (int i=0;i<a.length;i++)
		{
			if (addBack)
				l.addBack(a[i]);
			else
				l.addFront(a[i]);
		}
	}

	public static void testTwo()
	{
		ListOfDoubles 	l1 = new LinkedListOfDoubles();
		double[]	a1 = {-1.0,0.0,2.0,3.0};

		ListOfDoubles	l2 = new LinkedListOfDoubles();
		double[]	a2 = {95.0,13.0,12.0,8.0};

		ListOfDoubles	l3 = new LinkedListOfDoubles();
		double[]	a3 = {10.0,10.0,10.0,10.0,10.0,10.0};
		
		System.out.println("Testing minimum, maximum, mean.");
		addArray(a1,l1,true);
		
		displayResults(l1.minimum() == -1.0);
		displayResults(l1.maximum() == 3.0);

		addArray(a2,l2,true);
		displayResults(l2.maximum() == 95.0);
		displayResults(l2.minimum() == 8.0);

		addArray(a3,l3,false);
		displayResults(l3.mean() == 10.0);
		displayResults(l2.mean() == 32.0);
	}

	public static void testThree()
	{
		ListOfDoubles 	l1 = new LinkedListOfDoubles();
		ListOfDoubles 	l2 = new LinkedListOfDoubles();
		double[]	a2 = {-6.2,8.0,4.7,-3,18,2.0,-17,0.4,0.953,-0.18,-4.7};

		System.out.println("Testing withinThreshold.");

		for (int i = -10;i <= 10; i++)
		{
			l1.addFront((double)i);
		}

		displayResults(l1.withinThreshold(0.0,2.0) == 5);
		displayResults(l1.withinThreshold(4.0,0.05) == 1);
		displayResults(l1.withinThreshold(0.0,10) == 21);

		addArray(a2,l2,true);
		displayResults(l2.withinThreshold(0.0,0.5) == 2);
		displayResults(l2.withinThreshold(-15.0,3.18) == 1);		
	}
	
	public static void testFour()
	{
		double[]	a1 = {1.0,2.0,3.0};
		ListOfDoubles	l1 = new LinkedListOfDoubles();
		ListOfDoubles	l2 = new LinkedListOfDoubles();

		System.out.println("Testing toString().");		
		addArray(a1,l1,true);
		addArray(a1,l2,false);

		displayResults(l2.toString().equals("{3.0,2.0,1.0}"));
		displayResults(l1.toString().equals("{1.0,2.0,3.0}"));
	}

	public static void testFive()
	{
		ListOfDoubles	l1 = new LinkedListOfDoubles();
		
		System.out.println("Testing remove.");
		for (int i = -10;i <= 10; i++)
		{
			l1.addFront((double)i);
		}
		for (int i = 10;i >= -10.0; i--)
		{
			l1.remove((double)i);
		}
		displayResults(l1.size() == 0);

		l1.addFront(9.0);
		l1.addFront(8.0);
		l1.addBack(12.0);

		displayResults((l1.elementAt(0) == 8.0) && (l1.elementAt(1) == 9.0) && (l1.elementAt(2) == 12.0));
		l1.remove(9.0);
		displayResults((l1.elementAt(0) == 8.0) && (l1.elementAt(1) == 12.0));
		l1.addBack(13.0);
		l1.addBack(14.0);

		l1.remove(14.0);
		l1.remove(13.0);
		displayResults((l1.elementAt(0) == 8.0) && (l1.elementAt(1) == 12.0) && (l1.size() == 2));
		l1.remove(8.0);
		l1.remove(12.0);
		displayResults(l1.size() == 0);	

		ListOfDoubles l2 = new LinkedListOfDoubles();

		for (int i = 0;i<10;i++)
		{
			l2.addBack(10.0);
		}
		l2.addFront(17.0);
		displayResults(l2.size() == 11);
		l2.remove(10.0);
		displayResults(l2.size() == 1);	
		
	}

	public static void main (String[] args)
	{
		testOne();
		testTwo();
		testThree();
		testFour();
		testFive();
	}
}
