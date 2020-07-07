public class PointTester
{
	public static void main (String[] args)
	{		
		Point	p = new Point();
		p.moveTo(10,10);

		Point	s = new Point(12,18);
		
		System.out.println(s);
		System.out.println(p);
	
		p.moveTo(15,15);
		
		System.out.println(p);
	}
}
