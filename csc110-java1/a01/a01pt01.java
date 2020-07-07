/** Assignment 1, part 1
    Author:	Derek Roberts
    Student#:	V00698880
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
*/

public class a01pt01
{
    public static void main(String[] args)					//How do I remember this?  ...pc_psvmSa?  Not very catchy.
    {
        double 	nextTerm 	= 1;						//This is where
        double 	denominator 	= 1;						//I declare
        double 	series 		= 0;						//some variables
	{
	    for (int i = 1; i <= 40; i++)					//for (initialize/assign; condition; increment) {insert fun}
	    {
		series = series + nextTerm / denominator;
		denominator = denominator + 2;
		nextTerm = -nextTerm;
	    }
	}
    double pi = 4 * series;
    System.out.println ("Pi is: " + pi + " (approximately)");			// BTW the output is 3.116596556793833
    }
}
// I learned how to use for statements here: http://www.youtube.com/user/thenewboston#p/c/FE2CE09D83EE3E28/21/rjkYAs6gAkk
