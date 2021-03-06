/**
 * LLStackTester.java
 *
 * Some test cases for a stack.
 */
public class LLStackTester
{
	public static void testOne (Stack<Integer> s)
	{
		try
		{
			
			if (s.size() != 0 || !s.isEmpty())
				System.out.println("1: Failed size or isEmpty.");
			s.push(1);
			s.push(2);
			
			if (s.size() != 2 || s.isEmpty())
				System.out.println("2: Failed size or isEmpty.");
			
			if (!s.pop().equals(2))
				System.out.println("3: Failed pop");
				
			if (!s.peek().equals(1))
				System.out.println("4: Failed peek");
			
			if (!s.pop().equals(1))
				System.out.println("5: Failed pop");
			
			if (s.size() != 0 || !s.isEmpty() )
				System.out.println("6: Failed size or isEmpty.");
		}
		catch (StackEmptyException e)
		{
			System.out.println(e);
		}	
	}
	
	public static void testTwo (Stack<Integer> s)
	{
		try
		{		
			for (int i = 0; i < 100; i++)
			{
				s.push(i);
			}
			
			if (s.size() != 100)
				System.out.println("7: Failed size.");
				
			for (int i = 99; i >= 0; i--)
			{
				if (!s.pop().equals(i))
				{
					System.out.println("Failed pop for: " + i);
					break;
				}	
			}
		}
		catch (StackEmptyException e)
		{
			System.out.println("Failed testTwo.");
			System.out.println(e);
		}	
	}
	
	public static void testThree (Stack<Integer> s)
	{
		try
		{
			s.makeEmpty();
			s.pop();
			System.out.println("Failed empty stack test.");
		}
		catch (StackEmptyException e)
		{
			/* If we get here, we 
			 * passed the previous test.
			 */
		}
	}
	
	public static void main (String args[])
	{
		Stack<Integer> s1 = new LLStack<Integer>();
		Stack<Integer> s2 = new LLStack<Integer>();
		Stack<Integer> s3 = new LLStack<Integer>();
		
		testOne(s1);
		testTwo(s2);
		testThree(s3);
		System.out.println("If you see nothing before this, all tests passed.");
	}
}
