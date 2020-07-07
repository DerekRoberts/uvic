/** Comments
 Lots of copying!  Yup, but that's the assignment.
*/

public class Lab02e02
{
	//Here's a method or somthing to call soon
	public static final double PI = 3.14159;

	//Comment
	public static void main(String[] args)
	{
		//r1 and r2 are the radii of circle 1 and circle 2
		int r1 = 3;
		int r2 = 4;
		double area;
		double circumference;
		double ratio;

		//ratio 1
		circumference = 2 * PI * r1;
		area = PI * (r1 * r2);
		ratio = circumference/area;
		System.out.println("With radius " + r1);
		System.out.println("Circle ratio of distance around the outsicde to"+
							" area is " + ratio);
	}
}