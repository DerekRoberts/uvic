public class Time {

	//method to calculate an amount of time in hours
	//given a starting hour and ending hour
	public static void calcDuration(int startHour, int endHour)
	{
		int duration = endHour - startHour;
		System.out.println("Duration is:  " + duration + " hours");
	}

	//
	public static void main(String[] args)
	{
		System.out.println("Calculating duration of time...");
		calcDuration(2, 14);
	}
}