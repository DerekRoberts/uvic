/**
 * CSc 360: Operating Systems
 * Section: A01 (Summer 2012)
 * 
 * Assignment 3: A Simple File System (SFS)
 *       Part 3: diskget
 * 
 * By Derek Roberts (v00698880) 
 */


#include "functions.h"


// Copy specified file from specified image to current directory 
int main( int argc, char* argv[] )
{	
	// Check that there are three command line arguments, print feedback
	if( checkArgs( argc, argv, 3 ))
		return 1;
			
	// Try to open the file passed by argv[1] (from command line)
	if (!(fp=fopen(argv[1],"r")))
	{
		printf("Failed to open the image file.\n");
		return 1;
	}

	// Use grabInt and grabStr functions to populate variables
	int bytesPerSector = grabInt(  2, 11L );
	int totalSectors   = grabInt(  2, 19L );
	
	// Use findName to grab the address matching arg[2] or 0 if none
	int byteMatch = findName( bytesPerSector, totalSectors, argv[2] );
	
	// If there was a match then byteMatch is not 0
	if( byteMatch )
	{
		// Grab file cluster (change to physical sectors)
		int fromHere = grabInt( 2, byteMatch+26 )+31;
				
		// Grab size in integers
		int thisMany = grabInt( 4, byteMatch+28);
		
		// Use fileOut and print an error if unsuccessful
		if( fileOut( argv[2], thisMany, fromHere*bytesPerSector ))
			printf( "Unable to copy %s to current directory.\n", argv[2] );
		else
			printf( "%s saved to current directory.\n", argv[2] );
	}
	else
	{
		// If we're here then the file was not found
		printf( "File not found.\n" );
	}
	
	// Close the file pointer
	fclose( fp );
	
	// Free ptr (for passing from grabStr function)
	free( ptr );
	return 0;
}
