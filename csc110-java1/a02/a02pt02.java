/** Assignment:	2, part 2
    Author:	Derek Roberts
    Student ID:	V00698880
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
    Purpose:	To get good at brain and make lots of money.
    Purpose II:	To create a program for calculating total hours and costs for a 
    		ferry service.  Runs times include an extra 30 mins for loading
    		and unloading.  Running costs vary for different routes.
*/


//import scanner
import java.util.*;

public class a02pt02
{
	public static void main (String [] args)
	{
		//Variables
		//Hourly for Ogden Point, Victoria <-> Parsons Point, Sooke
		int 	hourlySooke	= 485;
		//Hourly for Ogden Point, Victoria <-> Roberts Point, Sydney
		int 	hourlySydney	= 595;
		//Running daily totals for hours and costs
		double 	totalHours	= 0;
		double 	totalCost	= 0;
		//Container for hours returned by RouteTotalOperationalTime
		double	returnedHours	= 0;

		System.out.println("VANCOUVER ISLAND FERRY SYSTEM");
		System.out.println("   Gross Operational Cost Calculation");
		System.out.println("");
		
		//Call RouteTotalOperationalTime, as directed
		//I'd have cut this down if I could get multiple returns working
		returnedHours 	= RouteTotalOperationalTime ("Victoria to Sooke ");
		totalHours	= totalHours + returnedHours;
		totalCost 	= totalCost + returnedHours * hourlySooke;
		
		returnedHours 	= RouteTotalOperationalTime ("Sooke to Victoria ");
		totalHours	= totalHours + returnedHours;
		totalCost 	= totalCost + returnedHours * hourlySooke;
			
		returnedHours 	= RouteTotalOperationalTime ("Victoria to Sydney");
		totalHours	= totalHours + returnedHours;
		totalCost 	= totalCost + returnedHours * hourlySydney;
		
		returnedHours 	= RouteTotalOperationalTime ("Sydney to Victoria");
		totalHours	= totalHours + returnedHours;
		totalCost 	= totalCost + returnedHours * hourlySydney;
		
		//Print totals
		System.out.println("");
		System.out.println("RESULTS: ");
		System.out.println("   Total daily operational hours: " + totalHours);
		System.out.println("   Gross daily operational cost:  " + totalCost);
	}
	
	
	//RouteTotalOperationalTime, as directed
	public static double RouteTotalOperationalTime (String route)
	{
		//initializing Scanner and variables
		Scanner stdin = new Scanner (System.in);
		
		//Initialize variables
		double time	= 0;

		System.out.print(route           + " - Number of runs ==> ");
		int runs 	= stdin.nextInt();
		
		for (int i = 1; i <= runs; i++)
		{
			System.out.println("   Run #" + i);
			
			System.out.print("   Duration, hours (24 hour format) ==> ");
			int hours 	= stdin.nextInt();
		
			System.out.print("   Duration, minutes (from 0 to 59) ==> ");
			int minutes 	= stdin.nextInt();
		
			time 	= time + 0.5 + convertHoursMinutesToDouble (hours, minutes);
		}
		
		return time;
	}
	
	//convertHoursMinutestoDouble, as directed
	public static double convertHoursMinutesToDouble (int hours, int minutes)
	{
		double doubleMinutes = minutes * 1.0;
		double returnHours = hours + (doubleMinutes / 60);
		return returnHours;
	}
}

