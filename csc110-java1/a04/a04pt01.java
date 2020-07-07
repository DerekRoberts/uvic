/** Assignment:	4, part 1
    Author:	Derek Roberts
    Student ID:	V00698880
    Date:	November 17, 2010
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
    Input:		Several sound files are imported.
    Output:		Sound clips edited by use of my methods.
    Purpose:	To annoy anyone within earshot.
*/


public class a04pt01
{
	// Copied from SoundCheck.java
	//
	public static double [] getSampleValues(Sound s)
	{
			int nSamples = s.getNumSamples();
			double [] samples = new double [nSamples];
			double nextSample;

			for( int i=0 ; i<nSamples ; i++ )
			{
				nextSample = s.getSampleValueAt(i);
				if( nextSample < 0 )
					samples[i] = nextSample / 32768;
				else
					samples[i] = nextSample / 32767;
			}
			return samples;
	}

	// Copied from SoundCheck.java
	//
	public static Sound setSampleValues(Sound sound, double [] samples)
	{
			if( sound.getNumSamples() != samples.length )
				sound = new Sound( samples.length );
			double temp;

			for( int i=0 ; i<samples.length ; i++ ){
				if( samples[i] > 0 )
					temp = samples[i] * 32767;
				else
					temp = samples[i] * 32768;

				sound.setSampleValueAt( i , (int)temp );
			}
			return sound;
	}

	// Copied from SoundCheck.java
	//		
	public static void addEcho(double [] samples, int delay, double factor )
	{
		double [] origSamples = new double[samples.length];
		for( int i=0 ; i<samples.length ; i++ )
			origSamples[i] = samples[i];

		double value = 0;

		for( int i = delay ; i < samples.length ; i++ )
		{
			value = origSamples[ i - delay ] * factor;
			samples[i] += value;
		}
	}
	
//Part 1.A).
	//
	//This method adjusts the volume of a sound array.
	//factor > 1 = volume up
	//factor < -1 = volume up
	//-1 < factor < 1 = volume down
	//
	public static void adjustVolume (double[] samples, int startIndex, int endIndex, double factor)
	{
		for (int i=0; i<endIndex; i++)
			samples[i] = samples[i]*factor;
	}
	
	
//Part 1.B).
	//
	//add samples2 to samples1
	//calls clipStopper method to detect and correct clipping
	//
	public static void add (double[] samples1, double [] samples2 ) 
	{
		//sets samplesLength the shorter of samples1 or samples2
		int samplesLength = 0;
				
		if (samples1.length<samples2.length)
			samplesLength = samples1.length;
		else
			samplesLength = samples2.length;
		
		//this is where samples1[i] and samples2[i] are added
		for (int i=0; i<samplesLength; i++)
			samples1[i] = samples1[i] + samples2[i];

		//calls clipStopper to handle any clipping in samples1
		clipStopper (samples1);
	}
	
	//This method uses maxVol to check if a sound array is at any point
	//over 1 or under -1 and will repeatedly lower that sample's volume
	//by 10% until maxVol is under 1.  This is done to correct clipping.
	//The actual adjusting is done by the adjustVolume method.
	//
	public static void clipStopper (double[] samples)
	{
		//If maxVol (of samples) is over 1 then samples's volume will
		//be repeatedly reduced by 10% until maxVol is under 1.
		while (maxVol(samples) > 1 || maxVol(samples) < -1)
			adjustVolume (samples, 0, samples.length, 0.9);
	}	

	//This method is used to find the maximum volume level for a double
	//(of a sound file) in order to detect clipping.  The clipStopper
	//method can be used elsewhere to actually correct the clipping.
	//
	public static double maxVol (double[] samples)
	{
		double maxVol = -1000000;										//absurd default value
				
		for (int i=0; i<samples.length; i++)
			if (samples[i] > maxVol)
				maxVol = samples[i];
		
		return maxVol;
	}


//Part 1.C).
	//	
	//reverses the order of the integers in the samples array
	//
	public static void reverse (double[] samples)
	{
		//juggler is used to hold values while shuffling for i and j
		double juggler = 0;
		//j is spot i will be switched with
		int j = 0;
		//and here's where the magic happens
		//i counts until half the length of samples
		for (int i=0; i<(samples.length/2); i++)
		{
			j = samples.length-1-i;
			juggler = samples[i];
			samples[i] = samples[j];
			samples[j] = juggler;
		}
	}


//Part 1.D).
	//	
	//creates clipping like this: +1 to x >= 0 and -1 to x < 0
	//
	public static void forceToExtremes( double[] samples )
	{
		int samplesLength = samples.length;
		for( int i = 0 ; i < samplesLength ; i++ )
		{
			if( samples[i] >= 1 )
				samples[i] += 1;
			else
				samples[i] -= 1;
		}
	}


//Part 1.E).
	//	
	//splices two songs together
	//
	public static void splice( double[] srcSamples, int srcStart, int srcStop, double[] destSamples, int destStart )
	{
		//index counters srcIndex and destIndex = srcStart and destStart
		int srcIndex  = srcStart;
		int destIndex = destStart;
		
		//srcSamples[] assigned to destSamples[] repeatedly
		while( srcIndex < srcStop && destIndex <  destSamples.length)
		{
			destSamples[destIndex] = srcSamples[srcIndex];
			srcIndex++;
			destIndex++;
		}
		//While conditions keep indexes from going higher than arrays
		//can handle.  Anything out of bounds will just not be done.
	}

	
//Part 1.F).
	//
	//This method shortens (divides) the audible portion of a sound
	//array by the double called factor.
	//E.g. factor = 2 means that only 1/2 the song would have sound.
	//
	//factor > 1 = audible portion of song now length/factor long
	//factor < 1 = no change
	//
	public static void adjustFrequency( double[] samples, double factor )
	{
		double original[] = samples;
		double origIndex = 0;
		
		for( int index = 0 ; index < (samples.length - 1) ; index++)
		{
			if( origIndex >= original.length )
			{
				samples[index] = 0;
			}
			else
			{
				samples[index] = original[index];
			}
			
			origIndex = origIndex + factor;
		}
	}


	//I got really sick of listening to whole sound clips and wrote this
	//method to restrict playback to a certain number of seconds.
	//
	public static void playXSeconds( double[] samples, double seconds )
	{
		int secondsX24000 = ( int ) ( seconds * 24000 );
		double shortenedSamples[] = new double[ secondsX24000 ]; 
		
		for( int i = 0 ; i < secondsX24000 ; i++ )
			shortenedSamples[i] = samples[i];

		Sound shortenedSound = new Sound( ( int ) ( seconds * 22050 ) );
		shortenedSound = setSampleValues( shortenedSound, shortenedSamples );
		shortenedSound.blockingPlay();
	}
	
	public static void main( String[] args )
	{
		//create sounds for all the clips I liked
		Sound sound25 = new Sound( "_25_.au" );							// mildly evil, forced grandness
		Sound sound30 = new Sound( "_30_.au" );							// U2 - New Year's Day
		Sound sound32 = new Sound( "_32_.au" );							// dolphins throwing up
		Sound sound38 = new Sound( "_38_.au" );							// Moist - Gasoline
		
		//make arrays containing each sound sample
		double[] samples25       = getSampleValues( sound25 );
		double[] samples30       = getSampleValues( sound30 );
		double[] samples32       = getSampleValues( sound32 );
		double[] samples38       = getSampleValues( sound38 );
		double[] u2WithDolphins  = getSampleValues( sound30 );			//version to be overwritten
		double[] tempDolphins    = getSampleValues( sound30 );			//version to be overwritten
		double[] tempGasoline    = getSampleValues( sound38 );			//version to be overwritten

		int xSec = 4;													//change this to set the playback times

//Part 1.A).
		System.out.println( "\nPart 1.A), adjustVolume method" );
		System.out.println( "\tSong: U2 - New Year's Day" );
		System.out.println( "\tBefore (playing)" );
		playXSeconds( samples30, xSec );								//play for xSec seconds
		
		System.out.println( "\tDoubled in volume (playing)" );
		adjustVolume( samples30, 0, samples30.length, 2 );				//double the volume
		playXSeconds( samples30, xSec );								//play for xSec seconds
		adjustVolume( samples30, 0, samples30.length, 0.5 );			//restore the volume
				
		System.out.println( "\tWith just the first two seconds loud (playing)" );
		adjustVolume( samples30, 0, 22050*2, 2 );						//volume up for just two seconds
		playXSeconds( samples30, xSec );								//play for xSec seconds
		adjustVolume( samples30, 0, 22050*2, 0.5 );						//restore the volume


//Part 1.B).
		System.out.println( "\nPart 1.B), add method" );
		System.out.println( "\tSong: U2 - New Year's Day" );
		System.out.println( "\tU2 with dolphins! (playing)" );
		add( u2WithDolphins, samples32 );								//add the dolphins
		playXSeconds( u2WithDolphins, xSec );							//play for xSec seconds
		u2WithDolphins = samples30;										//restore the dolphin-less version
		
//Part 1.C).
		System.out.println( "\nPart 1.C), reverse method" );
		System.out.println( "\tSong: U2 - New Year's Day" );
		System.out.println( "\tU2 BACKWARDS with dolphins! (playing)" );
		reverse( u2WithDolphins );										//reverses the dolphins
		add( u2WithDolphins, samples32 );								//add the backwards dolphins
		playXSeconds( u2WithDolphins, xSec );							//play for xSec seconds
		u2WithDolphins = samples30;										//restore the dolphin-less version

//Part 1.D).
		System.out.println( "\nPart 1.D), forceToExtremes method" );
		System.out.println( "\tSong: U2 - New Year's Day" );
		System.out.println( "\tU2 with dolphins TO THE EXTREME! (playing)" );
		forceToExtremes( tempDolphins );								//force dolphins to the extreme!
		add( u2WithDolphins, tempDolphins );							//add the extreme dolphins
		playXSeconds( u2WithDolphins, xSec );							//play for xSec seconds
		tempDolphins = samples32;										//restore dolphins
		u2WithDolphins = samples30;										//restore the dolphin-less version


//Part 1.E).
		System.out.println( "\nPart 1.E), splice method" );
		System.out.println( "\tSong: Moist - Gasoline" );
		System.out.println( "\tBefore (playing)" );
		playXSeconds( samples38, xSec );								//play for xSec seconds
		System.out.println( "\tSome noisy thing and then Gasoline (playing)" );
		splice( samples25, 0, 22050*2, tempGasoline, 0);				//splice 2 seconds onto Gasoline
		playXSeconds( tempGasoline, xSec );								//play for xSec seconds
		tempGasoline = samples38;										//restores Gasoline

//Part 1.F).
		System.out.println( "\nPart 1.F), adjustFrequency method" );
		System.out.println( "\tSong: Moist - Gasoline" );
		System.out.println( "\tBefore (playing)" );
		playXSeconds( tempGasoline, xSec );								//play for xSec seconds
		adjustFrequency( tempGasoline, 12 );							//shortened to about 2 seconds
		System.out.println( "\tShortened to about 2 seconds using adjustFrequency (playing)" );
		playXSeconds( tempGasoline, xSec );								//play for xSec seconds
		tempGasoline = samples38;										//restores Gasoline
		
		System.out.println( "\nTHE END :D\n" );
	}
}
