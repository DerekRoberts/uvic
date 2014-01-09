import java.util.*;

public class Guess
{

	private static final int ANSWER = 42;

	public static void main(String []args)
	{

		// declare a scanner variable
		Scanner scannerVariable = new Scanner(System.in);

		System.out.print("Stuff goes here: ");
		int scannerInput = scannerVariable.nextInt();
		System.out.println("Input = " + scannerInput);

		//call the guess method until the number is guessed or 5 guesses

//		guess();

	}

	//function:  prompts a user to guess a number
	//until they guess the correct ANSWER
	//input:  Scanner
	//return: void
	public static void guess(Scanner input) {
		System.out.println("Pick a number from 1-10.");
		System.out.println("You have five guesses.");

		for (int i = 1; i < 5; i++)
		{
			System.out.println(i);
		}
	}

	//function:  compares the passed in value to class's
	//ANSWER
	//input:  int -- the guessed value
	//return: boolean -- true if matches else false
//	public static boolean correct(int num) {

		//FILL ME IN....
//		return true;
//	}
}