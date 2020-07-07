/**
 * CSc 360: Operating Systems
 * Section: A01 (Summer 2012)
 * 
 * Assignment 1 (P1): A Realistic Shell Interpreter (RSI)
 * 
 * By Derek Roberts (v00698880) 
 */


// Include section from Chen's demo.c
#include <unistd.h>     // fork(), execvp()
#include <stdio.h>      // printf(), scanf(), setbuf(), perror()
#include <stdlib.h>     // malloc()
#include <sys/types.h>  // pid_t 
#include <sys/wait.h>   // waitpid()
#include <signal.h>     // kill(), SIGTERM, SIGKILL, SIGSTOP, SIGCONT
#include <errno.h>      // errno

// My only additional #include
#include <string.h>		// string functions


#define maxIn sysconf(_SC_ARG_MAX)	// Max length for CLI input
#define maxPath FILENAME_MAX		// Max length for filename/path
#define maxArg 1024+1				// Max # of arguments (arbitrary)
#define dirHome getenv( "HOME" )	// User's home directory


// I like to pretend C literally supports strings
typedef char* string;


// Forward declarations:
int cmdSet( char* );
int cmdRep( char* );
int cmdPrs( string*, char* );
int cmdExec( string* );
int cmdBkg( string* );


// Global variable(s):
// Current working directory - that's it!
char dirCurrent[ maxPath ];


// Show prompt and return user input
int cmdSet( char toReturn[] )
{
	// Update current directory (dirCurrent)
	getcwd( dirCurrent, sizeof( dirCurrent ));
		
	// Show current directory and get entry with fgets
	printf( "RSI: %s > ", dirCurrent );	
	fgets( toReturn, maxIn, stdin );
	
	// return 1 if no input (i.e. CTRL+D pressed)
	if( toReturn[ strlen( toReturn )-1] != '\n' )
	{
		// Moves insertion point to the next line
		printf( "\n" );
		
		return 1;
	}
	return 0;
}


// Replace/process contents and return 1 if exceeds maxPath (too long!)
// '\n' => ' ' (space); '~' => dirHome (home directory)
int cmdRep( char toReturn[] )
{
	// Replace trailing '\n' with space (' ')
	toReturn[ strlen( toReturn )-1 ] = ' ';
	
	// squigFree counts characters confirmed not to be '~' (tilda)
	int squigFree = 0;
	
	// while loops until all characters in to return have no '~'s
	while( squigFree < strlen( toReturn ))
	{
		// On each loop reset squigFree to 0
		squigFree = 0;
		
		// cmdHolder gets a copy of toReturn
		char cmdHolder[maxIn];
		strcpy( cmdHolder, toReturn );
		
		// Check toReturn, replacing '~' with dirHome
		int i;
		for( i = 0; i < strlen( toReturn ); i++ )
		{
			if( toReturn[ i ] == '~' )
			{
				// Found one!  Copy corrected version into cmdHolder
				strncpy( cmdHolder, toReturn, i );
				cmdHolder[ i++ ] = '\0';
				strcat( cmdHolder, dirHome );
				strcat( cmdHolder, toReturn+i );
			}	
			else
				// Not a '~', so increment squigFree
				squigFree++;
		}
		// Copy (desquiggled) cmdHolder into toReturn
		strcpy( toReturn, cmdHolder );
	}
		
	int i;
	for( i = 0; toReturn[ i ] != '\0'; i++ );
	i--;
	
	if( i > maxPath )
		printf( "Input too long by %d characters.\n", i-maxPath );
	
	return 0;
}


// Put command into toReturn[0] and arguments/params into toReturn[1..n]
int cmdPrs( string* toReturn, char* toParse )
{	
	// Fire up strtok, putting results in wordNew
	char* wordNew;
	wordNew = strtok( toParse, " " );
	
	// Store those commands in toReturn (array of strings/char*s)
	int i = 0;
	while( wordNew != NULL ){
		toReturn[ i++ ] = wordNew;
		wordNew = strtok( NULL, " " );
	}
	// Terminate toReturn with '\0' 
	toReturn[ i ] = '\0';
	
	return 0;
}


// Decide on and execute code appropriately
int cmdExec( string* toExec )
{
	// If toExec's first string is empty, then silently return 1 (fail)
	if( toExec[0] == NULL )
	{
		return 1;
	}
	// If the command to execute is cd then call cd code
	else if( !strcmp( toExec[ 0 ], "cd" ))
	{
		// Look for and handle "cd (null)" like "cd ~"
		if( toExec[ 1 ] == NULL )
			chdir( dirHome );
		else
			chdir( toExec[ 1 ]);
	}
	// ..otherwise pass the command off to execvp
	else {
		// This one is handled with exit codes and not a return
		cmdBkg( toExec );
	}
	return 0;
}


// Background process handler
// Lots of code in this function borrowed from Chen's demo.c!
int cmdBkg( string* toExec )
{
	// Get to the last element in toExec		
	int i;
	for( i = 0; toExec[ i ] != NULL; i++ );
	i--;
	
	// "ls...&" acts stragely, so treak like "ls ~" instead
	if( !strcmp( toExec[ 0 ], "ls" ) && !strcmp( toExec[ i ], "&" ))
		toExec[ i ] = dirHome;
		
	// Child process ID, forked!
	pid_t pid = fork();
	int status;
	
	if( pid == 0 )
	{
		// Child process
		execvp( toExec[ 0 ], toExec );
		
		// Code reachable if execvp fails (i.e. bad command)
		printf( "RSI: %s: command not found\n", toExec[ 0 ] );
		exit( EXIT_FAILURE );
	}
	else if( pid > 0 )
	{
		// Parent process
		// This part is very nearly identical to the code in demo.c
		int opts = WNOHANG | WUNTRACED | WCONTINUED;
		if ( waitpid( pid, &status, opts)  == -1 )
		{
			printf( "OR MAYBE HERE!\n" );
			perror( "Fail at waitpid" );
			exit( EXIT_FAILURE ); 
		}
		
		// Find and check if the last entry in toExec is '&'
		if( strcmp( toExec[ i ], "&" ))
		{
			// ...because if it isn't a '&', then wait
				
			if( waitpid( pid, 0, 0 ) < 0 )
			{
				printf( "\nError: waitpid failed  :'(\n" );
			}
		}
		else
		{
			// This sleep lines up the command prompt
			sleep(1);
		}	
	}
	else
	{
		// Process creation fail ( pid < 0 )
		printf( "Error: failed to create process  :'(\n" );
	}
	
	return 0;
}


// SIGCHLD handler, reaps zombie children and prints their pids/IDs
static void zombieReaper( )
{
	pid_t pid;
	while(( pid = waitpid(-1, NULL, WNOHANG )) > 0 )
	{ 
		printf( "\nProcess %d terminated!", (int)pid );
	}
}


// Main!  Not as interesting as the things it calls...
int main( int argc, char **argv )
{	
	// Start off in user's home directory
	chdir( dirHome );
		
	// Display impersonal greeting and current directory
	printf( "linux.csc.uvic.ca:%s$ ./rsi \n", dirCurrent );
	
	// Create cmdIn to receive user input
	char cmdIn[ maxIn ];
	
	// Prompt repeadtedly until user types "exit"
	while( strcmp( cmdIn, "exit" ))
	{
		// Check if any processes have ended and call zombieReaper
		struct sigaction theSig;
		memset( &theSig, 0, sizeof( theSig ));
		theSig.sa_handler = zombieReaper;
		theSig.sa_flags = SA_NODEFER;
		
		if( sigaction( SIGCHLD, &theSig, 0 ))
		{
			perror( "Error: sigaction failed  :'(\n" );
			return 1;
		}
				
		// cmdSet prompts user and assigns the result to cmdIn
		// This is also where I expect to be interruped by zombieReaper
		if( cmdSet( cmdIn ))
			continue;
		
		// cmdRep replaces the characters '~' and '\n'
		if( cmdRep( cmdIn ))
		{
			printf( "Error: ~ and \n possibly not removed.\n" );
			continue;
		}
		
		// cmdPrs parses cmdIn into param (an array)
		string param[ maxArg ];
		cmdPrs( param, cmdIn );
		
		// Exit if "exit" received
		if( !strcmp( cmdIn, "exit" ))
			exit( EXIT_SUCCESS);
		
		// cmdExec figures out what to do with those parameters
		cmdExec( param );
	}
	return 0;
}
