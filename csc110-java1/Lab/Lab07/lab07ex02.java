/** Comments
*/

import java.util.*;
import java.io.*;

public class lab07ex02
{
	public static void main (String [] args) throws IOException
	{
		File scannerIn 	= new File ("tuidata.txt");
		Scanner input 	= new Scanner (scannerIn);

		String[] pad	= new String [2];		//saves the first two lines of text
		String[] pNames	= new String [11];		//province names
		int[] year07	= new int [11];			//tuition for 2006-07
		int[] year08	= new int [11];			//tuition for 2007-08
		int[] year09	= new int [11];			//tuition for 2008-09
		int[] year10	= new int [11];			//tuition for 2009-10
		int[] year11	= new int [11];			//tuition for 2010-11

		tuiReader(input, pad, pNames);

		System.out.println("pad[0] = " + pad[0]);
		System.out.println("pad[1] = " + pad[1]);

		input.close();
	}
	public static void tuiReader(Scanner input, String pad[], String pNames[])
	{
		for (int i = 0; i <= 11; i++)
		{
//			System.out.println("pad[" + i + "] = "+ input.nextLine());
			pad[i] = input.nextLine();
		}
		for (int i = 0; i <= 11; i++)
		{
		}
	}
}

