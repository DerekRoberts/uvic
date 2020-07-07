/** Comments
*/

import java.io.*;
import java.util.*;

public class ex01
{
	public static void main(String [] args) throws IOException
	{
		File infile = new File("tuidata.txt");
		Scanner input = new Scanner(infile);

		String provFind = "Ont.";		//Because everybody else will choose BC
		int provTuition = 0;

		provTuition = findFor (provFind);

//		input.close();
	}
	public static int findFor(String provFind)
	{
		int returnThing = 1234;
		return returnThing;
	}
}

