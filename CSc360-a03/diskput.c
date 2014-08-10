/**
 * CSc 360: Operating Systems
 * Section: A01 (Summer 2012)
 * 
 * Assignment 3: A Simple File System (SFS)
 *       Part 4: diskput
 * 
 * By Derek Roberts (v00698880) 
 */


#include "functions.h"


// Copy specified file to specified image
int main( int argc, char* argv[] )
{	
	// Check that there are three command line arguments, print feedback
	if( checkArgs( argc, argv, 3 ))
		return 1;
	
	// Try to open the file passed by argv[1] (from command line)
	if (!(fp=fopen(argv[1],"r+")))
	{
		printf("Failed to open the image file.\n");
		return 1;
	}

	// Use grabInt and grabStr functions to populate variables
	int bytesPerSector = grabInt(  2, 11L );
	int totalSectors   = grabInt(  2, 19L );

	// These ones require separate functions
	int freeSpace      = spaceFree( bytesPerSector, totalSectors );
		
	// Check if the file exists already
	if( findName( bytesPerSector, totalSectors, argv[2] ))
	{
		// If it does exist then notify and terminate
		printf( "File %s already exists!\n", argv[2] );
		return 1;
	}
	else
	{
		// ...otherwise add that file in!
		fileIn( bytesPerSector, totalSectors, freeSpace, argv[2] );
	}
		
	// Close the file pointer
	fclose( fp );
	
	// Free ptr (for passing from grabStr function)
	free( ptr );
	return 0;
}
