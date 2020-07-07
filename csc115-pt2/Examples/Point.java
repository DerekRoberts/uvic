public class Point
{
	private int	x;
	private int	y;

	/* default constructor */	
	public Point()
	{
		this(-1,-1);
	}
	
	/* Constructor that specifies the initial position */
	public Point(int initialX, int initialY)
	{
		x = initialX;
		y = initialY;
	}
	
	public int getX ()
	{
		return x;
	}
	
	public void moveTo (int newX, int newY)
	{
		x = newX;
		y = newY;
	}
	
	public String toString()
	{
		String s = "(";
		s = s + x;
		s = s + ",";
		s = s + y;
		s = s + ")";
		
		return s;
	}
	
}
