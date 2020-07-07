/** Another copy and paste exercise
	This one demonstrates how to use for loops
 */

public class Lab02e05
{
	//Static class variable represents constant in java
	public static final double PI = 3.14159;

 	public static void main (String [] args)
 	{
		//r1 and r2 represent the radius of two circles
		double area;
		double circumference;
		double ratio;

		//calculate ratios for r values 1 to 10
		for (int r = 1; r <= 10; r++)	//r++ increments r, aka r = r + 1
		{
			circumference = 2 * PI * r;
			area = PI * (r * r); 		//brackets unnecessary
			ratio = circumference/area;
			System.out.println("r is " + r);
			System.out.println(" Ratio is " + ratio);
		}
 	}
}
