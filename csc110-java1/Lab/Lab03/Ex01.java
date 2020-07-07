/** Comments
*/

public class Ex01
{
	public static void twoDBox (int x, int y)
	{
		for (int i = 1; i <= y; i++)
		{
			for (int j = 1; j <= x; j++)
			{
				System.out.print("*");
			}
			System.out.println();
		}
	}
	public static void main (String [] args)
	{
		int y = 2;
		int x = 4;
		twoDBox (x, y);
	}
}
