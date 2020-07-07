/**
 * ListTester.java
 * 
 * A simple program to test the IntList.
 *
 * Note: 
 *	This file is of limited usefulness when completing
 *	Part I of the assignment.
 */
public class ListTester
{
	private static final int LIST_SIZE = 10000;
	
	public static void main (String[] args)
	{
		boolean	passed;
		IntList l1 = new IntList(LIST_SIZE);
		l1.fill();
		
		passed = true;
		System.out.print("Testing binary search...");
		for (int i =0; i < LIST_SIZE;i++)
		{
			
			if (l1.find(i, true) != i)
			{
				System.out.println("failed find for:" + i);
				passed = false;
				break;
			}
		}
		if (passed)
		{
			System.out.println("passed.");
		}
		
		passed = true;
		System.out.print("Testing linear search...");
		for (int i =0; i < LIST_SIZE;i++)
		{
			
			if (l1.find(i, false) != i)
			{
				System.out.println("failed find for:" + i);
				passed = false;
				break;
			}
		}
		if (passed)
		{
			System.out.println("passed.");
		}

	}
}
