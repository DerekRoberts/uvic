public class ListTester
{
	public static void main (String[] args)
	{
		IntegerList i = new LinkedIntegerList();
		
		i.addFront(10);
		i.addFront(20);
		
		System.out.println(i);
	}

}
