
/**
 * File: SoundCheck.java
 *
 * Description:
 *		This file provides two methods that are necessary for the
 *      completion of CSc 110's Assignment 4 and demonstrates use
 *      of the methods of the Sound class that will be useful for
 *      the assignment.
 **/

public class SoundCheck
{
	///////////////////////////////////////////////////////////////////
	//
	//	COPY THE METHODS getSampleValues and setSampleValues INTO YOUR
	//  PROGRAM. DO NOT MODIFY THEM. You will USE THEM TO ACCESS/MODIFY
	//  THE SOUND SAMPLE ARRAYS.
	//
	///////////////////////////////////////////////////////////////////

	// Name: getSampleValues
	// Purpose: Converts a sound to an array of double values
	// Input: A Sound object
	// Output: returns the samples in an array of doubles
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

	// Name: setSampleValues
	// Purpose: Creates a Sound object from an array of double values.
	// Input:  any Sound object (will be manipulated)
	//         an array of double [] values
	// Output: The Sound object is returned once manipulated to contain the values of the array.
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

	// Name: main
	// Purpose: The entry point of the class and an example of Sound manipulation.
	//
	public static void main( String [] args )
	{

		// CREATING A SOUND: There are 3 WAYS TO CREATE A "Sound" OBJECT

		// 1. Create an empty Sound by specifying the number of samples.
		//
		// Create a new Sound object, containing 44100 audio samples
		// Sampling rate : 22.05KHz  Channels : 1 (mono)
		// This sound with have a duration of 44100/22050 = 2seconds
		Sound sound0 = new Sound( 44100 );

		// 2. Create a Sound that contains the samples from a specified file.
		//
		// Create a new Sound object that stores the audio
		// from "disco.00001.au".
		// The file should be located in the same
		// directory as your sourcecode.
		String fileName = "sound-test.au";
		Sound sound1 = new Sound( fileName );

		// 3. Create a Sound that is a copy of an existing Sound.
		// Create a new Sound object that is a copy of "sound1"
		Sound sound2 = new Sound( sound1 );


		// A NECESSARY STEP:  In order to manipulate a Sound, it MUST be
		//           converted into an array!!
		double [] samples = getSampleValues(sound2);


		// SOUND MANIPULATION: As with all arrays, you can modify or look
		//          at any element.
		samples[5] = 0.75 ;
		System.out.println( "The 11th sample has a value of : " +
							samples[10] );

		// CALLING A METHOD THAT MANIPULATES SOUND: Of course, it is useful
		//          create and call methods to manipulate those sounds.  This
		//          example shows a good way to test such a method: using
		//          specific values (that you know)
		// Incorporate an echo into the sound by creating
		// a 'delay' of 0.4 seconds
		addEcho( samples, 11025, 0.4 );

		// CHECKING THE RESULTS OF SOUNDS MANIPULATIONS: There are 3 ways,
		//  printing out some or all values, listening to the sound or printing the
		//  Sound to a file and later reviewing the file.

		//1.  Printing out the values of the array
		printSoundValues( samples, 0, 1000);

		//2. Play the Sound
		//  First the Sound must be set to (contain the) samples from the array
		sound1 = setSampleValues( sound1, samples);
		// Then play: ("Blocking" refers to the behaviour that execution will
		// not continue beyond this line until the entire Sound
		// has been played.)
		sound1.blockingPlay();

		//3. Print the Sound to a file
		// First the sound must be set to (contain the) samples from the array.
		//    - USE THE LINE ABOVE!
		// Then
		// Write the audio you have created to a file.
		sound1.write( "yourNameGoesHere__audioCollage.wav" );

		// AND FOR FUN:  Display useful information regarding the Sound object.
		System.out.println( sound1 );

	}

	// NAME:  addEcho
	// Purpose: To Echo part of a Sound on top of itself
	// Input: An array of double values (samples)
	//        An integer indicating which sample will first contain the echo effect
	//        A double indicating the factor by which the sound volume is decreased or increased in the echo.
	//           (What is 'factor'?  What if the value of 'factor' is > 1 ? or < 1 ?)
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

	// NAME:  printSoundValues
	// Purpose: To all the values of an array (representing sound) from a start index to an ending index.
	// Input: An array of double values (samples)
	//        An integer indicating which sample will be the first one output
	//        An integer indicating which sample will be the last one output
	//
	public static void printSoundValues( double [] samples, int startIndex, int endIndex)
	{
		//Insert the method here
	}




}