/**	Comments
*/

import java.util.*;

public class lab05ex02
{
	public static void main (String [] args)
	{
		//Initialize Scanner to stdin
		Scanner stdin = new Scanner(System.in);

		//Variables
		int x	= 1;
		int y	= 2;
		int z	= 3;

		//runs a
//		System.out.println("The biggest integer is: " + bestOfThree (x, y, z));

		//runs b
//		System.out.println("Winner/Loser : " + checkGuess (x, y));

		//runs c
//		takeGuess (14);				//I like 14.

		//runs d
//		getGrade(stdin);

		//runs e
		countGrades(stdin);
	}

	//a
	public static int bestOfThree (int x, int y, int z)
	{
		if (x >= y && x >= z)
		{
			return x;
		}
		else if (y >= x && y >= z)
		{
			return y;
		}
		else
		{
			return z;
		}
	}

	//b
	public static String checkGuess (int guess, int target)
	{
		if (guess > target)
		{
			System.out.println("  ...Too high!");
			return "Loser!";
		}
		else if (guess < target)
		{
			System.out.println("  ...Too low!");
			return "Loser!";
		}
		else if (guess == target)
		{
			System.out.println("  Congratulations, you win!");
			return "Winner!";
		}
		else
		{
			System.out.println("Error!");
			return "Loser!";
		}
	}

	//c
	public static void takeGuess (int yourTarget)
	{
		Scanner stdin = new Scanner(System.in);
		System.out.println("Welcome to takeGuess!");
		System.out.println("Guess a number 1-100.  Enter 0 or a negative number to quit.");

		String gameStatus 	= "Loser";
		int loopCount 		= 0;
		int yourGuess		= 0;

		while (gameStatus != "Winner!")
		{
			loopCount = loopCount + 1;
			System.out.print("  Guess #" + loopCount + ": ");
			yourGuess = stdin.nextInt();
			if (yourGuess <= 0)
			{
				System.out.println("Quitter!  Get out of here!");
				break;
			}
			gameStatus = checkGuess (yourGuess, yourTarget);
		}
	}

	//d
	public static void getGrade (Scanner stdin)
	{
		System.out.println("Welcome to getGrade!");
		System.out.print("Grade (0-100): ");
		int yourGrade = stdin.nextInt();
		if (yourGrade  >= 85)
		{
			System.out.println("A");
		}
		else if (yourGrade >=70)
		{
			System.out.println("B");
		}
		else if (yourGrade >= 55)
		{
			System.out.println("C");
		}
		else
		{
			System.out.println("N");
		}
	}

	//e
	public static void countGrades (Scanner stdin)
	{
		int numA 		= 0;
		int numB 		= 0;
		int numC 		= 0;
		int yourGrade 	= 0;

		for (int i = 1; i <= 10; i++)
		{

		}
		System.out.print("As: " + numA);
		drawStars (numA);

		System.out.print("Bs: " + numB);
		drawStars (numB);

		System.out.print("Cs: " + numC);
		drawStars (numC);
	}

	public static void drawStars (int stars)
	{
		for (int i = 1; i <= stars; i++)
		{
			System.out.print("*");
		}
	}
}
