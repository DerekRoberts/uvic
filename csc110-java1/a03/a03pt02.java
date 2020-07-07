/** Assignment:	3, part 2
    Author:	Derek Roberts
    Student ID:	V00698880
    Date:	November 2, 2010
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
    Input:		User enters choice of input method.  Manual method uses
				direct user input, while fromFile reads from a file.
    Output:		Manual method outputs a mark and letter grade with some
				explanation if an F is given for failing the final, but
				passing based on average.
				fromFile method outputs student numbers, marks and
				letter grades, followed by a count of how many times
				each letter grade occurred.
    Purpose:	To forcibly help lazy students reflect on their marks.
*/

//Sorry, but I used a new editor and am now correcting the formatting manually
//Please excuse anything ugly I missed.
//
//This assignment is currently set to accept scores4.txt

import java.util.*;
import java.io.*;

public class a03pt02 
{
	public static void main (String [] args) throws IOException
	{
		Scanner scan1or2 = new Scanner (System.in);
		
		
		System.out.println("\nC O U R S E  G R A D E  C A L C U L A T O R\n");  //changed inputs to suit
		System.out.println("INPUT OPTIONS: 0- Interactive (for one student)");  //prompter method from
		System.out.println("               1- From File (for many students)");  //assignement 03, pt 01

		int double1or2 = (int)prompter ("  Choose 0 or 1 ==> ", 1);
		
		//manual or fromFile method called based on user's input
		if (double1or2 == 0)
		{
			System.out.println("\nWelcome to Interactive Mode!\n");
			manual();
		}
		else
		{
			System.out.println("\nWelcome to File Reader Mode!\n");
			fromFile();
		}
	}
	
	
	//This method does most of the heavy lifting in this part (02) of
	//assignment 03.  Either this or the manual method will be called
	//based on user input.
	public static void fromFile () throws IOException
	{
		File scannerFileIn = new File ("scores4.txt");			//Scanner set to accept scores4.txt
		Scanner scIn = new Scanner (scannerFileIn);			//Initialize scanner
		
		int rows 		= scIn.nextInt();								//Reads the first entry, rows
		
		int finalMin	= 50;											//Assumes final exam min. of 50%

		//0=ID#, 1-15=marks, 16=gradeMark, 17=finalPass
		int columns 	= 17;											//17 columns
				
		double[][] readIn 	= new double[rows][columns];		//array of rows x columns
		String[] letter		= new String[rows];			//array for letter grades
		
		
		//This is where (for) loops and Scanner read everything in		
		for (int i = 0; i < rows; i++)					//Loop for reading in rows
		{
			for (int j = 0; j < 16; j++)				//Loop for reading in columns
			{
				readIn[i][j] = scIn.nextDouble();
			}
			
			//Only one of two quizzes is counted, so lowest set to 0 			
			if (readIn[i][12] >= readIn[i][13])
			{
				readIn[i][13] = 0;
			}
			else
			{
				readIn[i][12] = 0;
			}
			
			//Final marks are calculated and stored in readIn[i][16]
			//Redundant numbering included to make weighting more clear
			
			//Assignment #1s
			readIn[i][16] = 0.05/5*(readIn[i][1]+readIn[i][2]+readIn[i][3]+readIn[i][4]+readIn[i][5]);

			//Assignment #2s
			readIn[i][16] += 0.15/5*(readIn[i][6]+readIn[i][7]+readIn[i][8]+readIn[i][9]+readIn[i][10]);
			
			//Lab Attendance
			readIn[i][16] += (readIn[i][11]);
			
			//Quizzes, the lower of which has already been set to 0
			readIn[i][16] += 0.03*5*(readIn[i][12]+readIn[i][13]);
			
			//Midterm
			readIn[i][16] += 0.22*(readIn[i][14]);
			
			//Final Exam
			readIn[i][16] += 0.50*(readIn[i][15]);
			
			//Trims final grades down to one decimal place
			//It's multiplied by 10, saved as integer and divided by 10 
			readIn[i][16] = (int)(10*readIn[i][16]);
			readIn[i][16] = readIn[i][16]/10;

			//Grade letter, requiring exam to pass minimum, else F :'(
			letter[i] = gradeLetter(readIn[i][16], readIn[i][15], finalMin);
		}
		
		
		//The student numbers, grades and grade letters are shown here
		for (int i = 0; i < rows; i++)
		{
			System.out.println("v00" + readIn[i][0] + ":\tGrade = " + readIn[i][16] + "\tLetter = " + letter[i]);
		}
		
		//The number of times each grade letter occurs is stored here
		int gradeCounter[] = new int [10];
		
		//This is the list of grades to be checked for
		String gradeList[] = {"A+", "A", "A-","B+","B","B-","C+","C","D","F"};
		
		//Counts the number of times each grade has been assigned
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				if (gradeList[i] == letter[j])
				{
					gradeCounter[i] += 1;
				}
			}
		}
		
		//This is where the grades and number of occurances is displayed
		System.out.println("\nGrade \tCount");
		for (int i = 0; i < 10; i++)
		{
			System.out.println(" " + gradeList[i] + "\t " + gradeCounter[i]);
		}
	}
	
	
	//Method reused from part 01 of assignment 03
	//
	//...but this was largely the main method in that assignment
	//It will be called in the event manual entry mode is selected
	public static void manual ()
	{
		double[] a0xpt01 = new double [6];				//assignment part 1s

		double[] a0xpt02 = new double [6];				//assignment part 2s
		
		double[] quizzes = new double [3];				//quizzes
		
		double gradeMark = 0;											//accumulates marks
		
		//note: There's an extra value in each array because of loops on
		//      arrays causing OutOfBounds errors.  Hopefully I can
		//      find a better solution soon.
		//note:	I know why this happened is assigment #1, but have not
		//	corrected the code yet
		
		//string used for passing complicated strings to prompter method
		String prompterStr = "";					
		
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
	
	
	//Method reused unchanged from part 01 of assignment 03
	//
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
	
	
	//Method reused unchanged from part 01 of assignment 03
	//
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
	
	
	//Method reused unchanged from part 01 of assignment 03
	//	
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
