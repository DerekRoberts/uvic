/**
 * CSc 360: Operating Systems
 * Section: A01 (Summer 2012)
 * 
 * Assignment 2: Multi-Flow Scheduler (MFS)
 * 
 * By Derek Roberts (v00698880) 
 */

#include <stdio.h>				// reading from file
#include <stdlib.h>				// atoi
#include <string.h>				// string handling
#include <pthread.h>			// pthread
#include <unistd.h> 			// usleep
#include <time.h>				// clock stuff

// Define functions for flows 2d int array
// Set using i:
#define fNOi flows[ i ][ 0 ]	// number
#define fARi flows[ i ][ 1 ]	// arrival time
#define fTRi flows[ i ][ 2 ]	// transmission time
#define fPRi flows[ i ][ 3 ]	// priority
#define fSFi flows[ i ][ 4 ]	// status flag
// Set using j:
#define fNOj flows[ j ][ 0 ]	// number
#define fARj flows[ j ][ 1 ]	// arrival time
#define fTRj flows[ j ][ 2 ]	// transmission time
#define fPRj flows[ j ][ 3 ]	// priority
#define fSFj flows[ j ][ 4 ]	// status flag

// Global variables:
int** flows;					// pointer for 2d int array
int waiting = -1;				// flows all start when waiting=0
pthread_mutex_t mu1 = PTHREAD_MUTEX_INITIALIZER;	// mutex
pthread_cond_t cv1 = PTHREAD_COND_INITIALIZER;	// condvar


// Sends the next flow, if one is available
int nextUp( int flowTotal )
{
	// i = index, j = next to go
	int i = 0, j = 0;
	
	// Get to the first arrived flow (status flag = 1)
	while(( fSFi != 1 )&&( ++i < flowTotal - 1 )){ }
	
	// If fSFi != 1 then no flows are ready
	if( fSFi != 1)
		return 1;
	
	for( j = i; i < flowTotal; i++ )
	{
		// If flow i has arrived (status flag = 1)
		if( fSFi == 1 )
		{
			// ...then compare prioritities
			if( fPRi < fPRj )
			{
				j = i;
			}
			else if( fPRi == fPRj )
			{
				// If equal then compare arrival times
				if( fARi < fARj )
				{
					j = i;
				}
				else if( fARi == fARj )
				{
					// If equal then compare transmission times
					// ...and tie goes to first in the list (that's j)
					if( fTRi < fTRj )
					{
						j = i;
					}
				}
			}
		}
	}
	
	// Now that we have a winner send it out! (set flag=2)
	fSFj = 2;
	
	// Signal flag change using conditional variable cv1
	pthread_cond_broadcast( &cv1 );
	
	// Simulate transmission
	while( fSFj == 2 ){}

	return 0;
}


// Returns the number of flows that have completed (status flag == 3)
int flowsCompleted( int flowTotal)
{
	int i = 0, completed = 0;
	for( i = 0; i < flowTotal; i++ )
		if( fSFi == 3 )
			completed++;

	return completed;
}


// Starting place for flows (okay, threads representing flows)
void *flowRunner( void* index )
{
	// Index in flows[][] of current thread
	long i = ( long )index;
		
	// Wait for variable to change from -1 to 0 (by main) and go!
	while( waiting < 0 ){ }
	
	// Start the clock
	double start = (double)clock()/CLOCKS_PER_SEC;
	
	// Use usleep to stall until arrival time
	int count = 0;
	while( count++ < fARi )
		usleep( 100000 );
	
	// Mark flow as having arrived (=1) and stop the clock
	fSFi = 1;
	double stop = (double)clock()/CLOCKS_PER_SEC;

	// Provide status details
	printf( "Flow %d arrives: arrival time %.2f, transmission time %.1f, priority %d.\n",
			fNOi, stop-start, (double)fTRi/10, fPRi );

	// Provide status details and wait until 1 (arrived) changed to 2 (transmit)
	//  => logic: if arrived, timer has started and does not have token
	if(( fSFi == 1 )&&( waiting > 0 )&&( fSFi != waiting ))
		printf( "Flow %d waits for the finish of flow %d.\n", fSFi, waiting );
	
	// Start of selection and transmission (critical section)!
	
	// Mutex lock
	pthread_mutex_lock( &mu1 );
	
	while( fSFi == 1 )
		pthread_cond_wait( &cv1, &mu1 );
	
	// Mark this thread/flow as the one the other flows are waiting on
	waiting = fNOi;
		
	// Provide status details
	start = (double)clock()/CLOCKS_PER_SEC - start;
	printf( "Flow %d has begun its transmission at time %.2f.\n", fNOi, start );
	
	// Simulate transmission for assigned time (in seconds)
	count = 0;
	while( count++ < fTRi )
		usleep( 100000 );
	
	stop = (double)clock()/CLOCKS_PER_SEC;
	
	// Provide status details
	printf( "Flow %d has finished transmission at time %.2f\n", fNOi, stop );
	
	// When complete set status flag to 3 (complete)
	fSFi = 3;
	
	pthread_mutex_unlock( &mu1 );
	
	// End of critical section!
	
	// Flow done so end thread
	pthread_exit( 0 );
}


// Verifies command line arguments and that input is not null
int checkArgs( int argc, char* argv[], FILE *inputFile )
{
	if( argc < 2 )
	{
		printf( "\nUsage %s <input.txt>\n\n", argv[0] );
		return 1;
	}
	else if( argc > 2 )
	{
		printf( "\nToo many arguments!\n" );
		printf( "Proceeding with input file => %s\n", argv[1] );
	}

	// Open file passed in command argument arg[1]
	if( inputFile == NULL )
	{
		printf( "\nUnable to read %s!\n", argv[1] );
		return 1;
	}
	
	int fclose( FILE *inputFile );
	
	return 0;
}

int main( int argc, char* argv[] )
{
	// Counter, used in multiple sections
	int i = 0;
	
	// String (max length=80) for parsing input file
	char lineIn[80];

	// Create, open and pass off input to checkArgs() function
	FILE *inputFile = fopen( argv[1], "r" );
	if( checkArgs( argc, argv, inputFile ))
	{
		int fclose( FILE *inputFile );
		return 1;
	}

	// Grab the total number of flows (first in inputFile)
	fgets( lineIn, 80, inputFile );

	// ...and store it in flowTotal	
	int flowTotal = atoi( lineIn );
	
	// Allocate space in flows for that 2d array
	flows = malloc( sizeof( int* )*flowTotal );
	for( i = 0; i < flowTotal; i++ )
		flows[i] = malloc( sizeof( int )*5 );
	
	// Create the appropriate number of threads (array)
	pthread_t threads[flowTotal];
	// Fill array with thread details and addresses	
	i = 0;	
	while( i < flowTotal )
	{
		// Grab the next line
		fgets( lineIn, 80, inputFile );
		
		// Format: flows[i][x] where x = integer in [0-4]
		// fNOi => x=0: flow number
		// fARi => x=1: arrival time
		// fTRi => x=2: transmission time
		// fPRi => x=3: priority
		// fSFi => x=4: status flag
		//      => 0=created, 1=arrived, 2=transmitting, 3=complete
		fNOi = atoi( strtok( lineIn, ":" ));
		fARi = atoi( strtok( NULL, "," ));
		fTRi = atoi( strtok( NULL, "," ));
		fPRi = atoi( strtok( NULL, "\n" ));
		fSFi = 0;
		
		if(( fNOi < 0 )||( fARi < 0)||( fTRi < 0)||( fPRi < 0 ))
			{
				printf( "Passed one or more negative values!\n" );
				return 1;
			}
		
		// Create thread in flowRunner and provide it an index number
		pthread_create( &threads[ i ], NULL, flowRunner, (void *)(long)i );

		i++;
	}

	// Done with inputFile, so close it
	int fclose( FILE *inputFile );

	// Let those flows go!
	waiting = 0;
	
	// Polls continuously until all threads have been transmitted	
	while( flowsCompleted( flowTotal )< flowTotal )
		nextUp( flowTotal );
	
	// Deallocate memory
	for( i = 0; i < flowTotal; i++ )
		free( flows[i] );
	free( flows );
	
	// Be gracious (poorly)
	printf( "\nThanks for using my program!  :D\n" );
	printf( "\nTHE END\n\n" );
	
	return 0;
}
