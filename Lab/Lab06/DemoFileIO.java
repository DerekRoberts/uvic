import java.io.*;
import java.util.*;

public class DemoFileIO
{
	public static void main(String[] args) throws IOException
	{
		File infile = new File("tuidata.txt");
		Scanner input = new Scanner(infile);
		echoFile(input);
		input.close();
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
}


