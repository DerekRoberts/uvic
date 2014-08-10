/**
 * CSc 360: Operating Systems
 * Section: A01 (Summer 2012)
 * 
 * Assignment 3: A Simple File System (SFS)
 *       Part 2: disklist
 * 
 * By Derek Roberts (v00698880) 
 */


#include "functions.h"


// List the contents of the root directory in the specified image
int main( int argc, char* argv[] )
{	
	// Check that there are two command line arguments, print feedback
	if( checkArgs( argc, argv, 2 ))
		return 1;
			
	// Try to open the file passed by argv[1] (from command line)
	if (!(fp=fopen(argv[1],"r")))
	{
		printf("Failed to open the image file.\n");
		return 1;
	}

	// Use grabInt and grabStr functions to populate variables
	int bytesPerSector = grabInt(  2, 11L );

	
	printRoot( bytesPerSector );
		
	// Close the file pointer
	fclose( fp );
	
	// Free ptr (for passing from grabStr function)
	free( ptr );
	return 0;
}
