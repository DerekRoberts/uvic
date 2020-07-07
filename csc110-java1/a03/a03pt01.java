/** Assignment:	3, part 1
    Author:	Derek Roberts
    Student ID:	V00698880
    Date:	October 27, 2010
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
    Input:	Student number, minimum acceptable exam score and all other 
    		related marks grades for CSC 110.
    Output:	Student ID, mark a a number and mark as a letter.  Numbers
    		input out of range will be rejected and prompted for again.
    		A minimum grade needs to be met on the final exam, otherwise F.
    Purpose:	To write a program that calculates conditional, weighted marks.
*/

import java.util.*;

public class a03pt01
{
	public static void main (String [] args)
	{
		double[] a0xpt01 = new double [6];				//assignment part 1s

		double[] a0xpt02 = new double [6];				//assignment part 2s
		
		double[] quizzes = new double [3];				//quizzes
		
		double gradeMark = 0;						//accumulates marks
		
		//note: There's an extra value in each array because of loops on
		//      arrays causing OutOfBounds errors.  Hopefully I can
		//      find a better solution soon.
		
		
		//string used for passing complicated strings to prompter method
		String prompterStr = "";					
		
		
		System.out.println("\nC O U R S E  G R A D E  C A L C U L A T O R");
		System.out.println("\n Author: \tDerek Roberts");
		System.out.println(" Purpose:  \tAccept input to be used for");
		System.out.println("           \tfinal grades in CSC 110.");
		System.out.println("           \tActual calculations in pt02.");

		
		//prompt for passing grade for final and student ID number
		
		double finalMin = prompter ("\nPassing Grade for Final ==> ", 100);
		double idNumber = prompter ("\nInput student ID number ==> v", 99999999);


		//loop five times to collect five assignment0#pt1s
		//a0xpt01[i] weighted and added to gradeMark

		System.out.println("\nInput Assignments (Part 1), max. 100:");
		for (int i = 1; i <= 5; ++i)
		{
			prompterStr = " #" + i + "==> ";
			a0xpt01[i] = prompter (prompterStr, 100);
			gradeMark += (a0xpt01[i]/5)*(0.05);
		}
		
		
		//loop five times to collect five assignment0#pt2s
		//a0xpt02[i] weighted and added to gradeMark

		System.out.println("\nInput Assignments (Part 2), max. 100:");
		for (int i = 1; i <= 5; ++i)
		{
			prompterStr = " #" + i + "==> ";
			a0xpt02[i] = prompter (prompterStr, 100);
			gradeMark += (a0xpt02[i]/5)*(0.15);
		}
		
		
		//prompt for lab attendance
		//labAttendance weighted (at *1) and added to gradeMark

		System.out.println("\nInput Lab Attendance, max. 5:");
		double labAttendance = prompter (" #1==> ", 5);
		gradeMark += labAttendance;


		//loop twice to collect two quizz marks
		//the higher of quizzes[i] weighted and added to gradeMark

		System.out.println("\nInput Quizzes, max. 20");
		for (int i = 1; i <= 2; ++i)
		{
			prompterStr = " #" + i + "==> ";
			quizzes[i] = prompter (prompterStr, 20);
		}
		if (quizzes[1] >= quizzes[2])
		{
			gradeMark += (quizzes[1]*5)*(0.03);
		}
		else
		{
			gradeMark += (quizzes[2]*5)*(0.03);
		}

		
		System.out.println("\nInput Midterm, max. 100:");
		double midterm = prompter (" #1==> ", 100);
		//midterm weighted and added to gradeMark
		gradeMark += midterm*(0.22);
		
		System.out.println("\nInput Final Exam, max. 100:");
		double finalExam = prompter (" #1==> ", 100);
		//finalExam weighted and added to gradeMark
		gradeMark += finalExam*(0.5);


		//(int)idNumber used, because prompter returns doubles
		System.out.print("v00" + (int)idNumber + " Grade = " + gradeMark + ", Letter = ");
		System.out.println(gradeLetter(gradeMark, finalExam, finalMin));
		
		if (finalExam < finalMin && gradeMark >= 50)
		{
			System.out.println("\nWe're sorry, but a minmum grade of " + (int)finalMin + " on the final");
			System.out.println("exam must be achieved to pass this class. :'(");
		}
	}
	
	
	//prompter prompts the user for values, but will only stop when
	//rangeChecker says the values are acceptable (aka true).
	public static double prompter (String askWhat, int rangeMax)
	{
		Scanner prompterScanner = new Scanner (System.in);
		double getWhat = -1;
		while (true)
		{
			System.out.print(askWhat);
			getWhat = prompterScanner.nextInt();
			if (rangeChecker(getWhat, rangeMax) == true)
			{
				break;
			}
			else
			{
				System.out.println("Error.  Please enter a value between 0 and " + rangeMax + ".");
			}
			
		}
		return getWhat;
	}
	
	
	//rangeChecker returns a boolean value of true if rangeValue is between
	//0 and rangeMax, otherwise it returns false.
	public static boolean rangeChecker (double rangeValue, int rangeMax)
	{
		if (rangeValue >= 0 && rangeValue <= rangeMax)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	//gradeLetter returns a string (letter) for marks out of 100
	//it also checks to see if finalExam >= finalMin, otherwise F
	public static String gradeLetter (double gradeMark, double finalExam, double finalMin)
	{
		if (gradeMark >= 95 && finalExam >= finalMin)
		{
			return "A+";
		}
		else if (gradeMark >= 90 && finalExam >= finalMin)
		{
			return "A";
		}
		else if (gradeMark >= 85 && finalExam >= finalMin)
		{
			return "A-";
		}
		else if (gradeMark >= 80 && finalExam >= finalMin)
		{
			return "B+";
		}
		else if (gradeMark >= 75 && finalExam >= finalMin)
		{
			return "B";
		}		
		else if (gradeMark >= 70 && finalExam >= finalMin)
		{
			return "B-";
		}		
		else if (gradeMark >= 63 && finalExam >= finalMin)
		{
			return "C+";
		}		
		else if (gradeMark >= 55 && finalExam >= finalMin)
		{
			return "C";
		}		
		else if (gradeMark >= 50 && finalExam >= finalMin)
		{
			return "D";
		}		
		else
		{
			return "F";
		}
	}
}
