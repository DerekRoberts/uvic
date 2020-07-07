/* Denostration program uses Scanner variable.
*/
// Scanner is in the util library
import java.util.*;

public class UseScanner
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int sum;
		String word;
		String line;
		// Prompt for what you need

		System.out.println("Enter a line of text");
		line = input.nextLine();
		System.out.println("You entered: " + line + "\n" );

//		//Initialize word to empty.
//		word = "";
//		//Concatenate words in a loop
//		for(int i=0; i<5; i++)
//		{
//			System.out.println("Enter a word ");
//			word = word + input.next() +" ";
//		}
//		//Get rid of the end of line
//		input.nextLine();
//		System.out.println("The words were: " + word);

		//Use a method to read 5 integers and return the sum
		sum = sumNumbers(input);
		System.out.println("The sum of the numbers entered is: " + sum);
		input.close();
	}

	/*	Passed the Scanner variable as a parameter sumNumbers
		prompts the user for 5 integers and returns the sum
		Fill in the logic for this stub.  Design this on paper
		using pseudocode.
	*/
	public static int sumNumbers(Scanner input)
	{
		int total 	= 0;
		int tmp  	= 0;

		for (int i = 1; i <= 5; i++)
		{
			System.out.print("Enter an integer: ");
			tmp 	= input.nextInt();
			total 	= total + tmp;
		}
		return total;
	}
}


