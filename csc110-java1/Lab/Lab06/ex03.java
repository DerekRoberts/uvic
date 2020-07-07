import java.io.*;
import java.util.*;

public class ex03
{
	public static void main(String[] args) throws IOException
	{
		File infile = new File("tuidata.txt");
		Scanner input = new Scanner(infile);

		System.out.println("");
		System.out.println(input.nextLine());
		System.out.println("");
		System.out.println(input.nextLine());

		String fedRegion = input.next();
		int fed07 = input.nextInt();
		int fed08 = input.nextInt();
		int fed09 = input.nextInt();
		int fed10 = input.nextInt();
		int fed11 = input.nextInt();

		printSix (fedRegion, fed07, fed08, fed09, fed10, fed11);


//		echoFile(input);
//		input.close();

	}

	public static void echoFile(Scanner input)
	{
		String line = "";
		for(int i=0;i<13;i++)
		{
			line = input.nextLine();
			System.out.println(line);
		}
	}

	public static void printSix (String region, int t07, int t08, int t09, int t10, int t11)
	{
		System.out.print(region + "  ");

		int pad = region.length() - 8;

		for (int i = 1; i <= pad; i++)
		{
			System.out.println(pad);
		}

		System.out.print(t07 + "    ");
		System.out.print(t08 + "    ");
		System.out.print(t09 + "    ");
		System.out.print(t10 + "    ");
		System.out.println(t11 + "    ");
	}
}


