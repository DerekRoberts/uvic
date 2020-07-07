/**
 * CSc 360: Operating Systems
 * Section: A01 (Summer 2012)
 * 
 * Assignment 3: A Simple File System (SFS)
 *       Part 1: diskinfo
 * 
 * By Derek Roberts (v00698880) 
 */


//#include <stdio.h>
//#include <stdlib.h>
#include "functions.h"


// Global variables:
// File and string/char* pointers
FILE *fp;
char* ptr;


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
	int FATCopies      = grabInt(  1, 16L );
	int totalSectors   = grabInt(  2, 19L );
	int sectorsPerFAT  = grabInt(  2, 22L );
	char* OS           = grabStr(  8,  3L );
	
	// These ones require separate functions
	char* label        = grabLabel( bytesPerSector );
	int rootFiles      = countRoot( bytesPerSector );
	int freeSpace      = spaceFree( bytesPerSector, totalSectors );
	
	// Total size of disk is the total sectors * bytes per sector
	int totalSpace     = totalSectors*bytesPerSector;
	int usableSpace    = (totalSectors-18)*bytesPerSector;
	
	// Print the requested values
	printf( "OS Name: \t%s\n", OS );
	printf( "Label: \t\t%s\n", label );
	printf( "Total size: \t%d bytes\n", totalSpace );
	printf( "Usable size: \t%d bytes\n", usableSpace );
	printf( "Free space: \t%d bytes\n", freeSpace );
	printf( "\n" );
	printf( "==============\n" );
	printf( "Root files: \t%d\n", rootFiles );
	printf( "\t*excludes 0x08 and 0x0F (volume and long names)\n" );
	printf( "==============\n" );
	printf( "FAT copies: \t%d\n", FATCopies );
	printf( "Sectors/FAT: \t%d\n", sectorsPerFAT );
	
	printf( "\nTHE END :D\n" );	
		
	// Close the file pointer
	fclose( fp );
	
	// Free ptr (for passing from grabStr function)
	free( ptr );
	return 0;
}
