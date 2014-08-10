/**
 * CSc 360: Operating Systems
 * Section: A01 (Summer 2012)
 * 
 * Assignment 3: A Simple File System (SFS)
 * 
 * This file contains all the functions
 * 
 * By Derek Roberts (v00698880) 
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>


// Global variables:
// File and string/char* pointers
FILE *fp, *in;
char* ptr;


// Checks command line arguments
int checkArgs( int argc, char* argv[], int expected )
{
	if( argc < expected )
	{
		// If not enough arguments print expected usage and return 1
		if( expected == 2 )
			printf( "\nUsage %s <image.IMA>\n\n", argv[0] );
		else if( expected == 3 )
			printf( "\nUsage %s <image.IMA> <file.foo>\n\n", argv[0] );

		return 1;
	}
	else if( argc > expected )
	{
		// Too many arguments, print message, but ignore extra input
		printf( "\nToo many arguments!  Ignoring extras.\n" );
	}
	
	return 0;
}


// Checks input file
int checkInput( char* argv[], FILE *inputFile )
{
	// Open file passed in command argument arg[1]
	if( inputFile == NULL )
	{
		printf( "\nUnable to read %s!\n", argv[1] );
		return 1;
	}
	
	int fclose( FILE *inputFile );
	return 0;
}


// Read and return the total number of sectors
int grabInt( int thisMany, int fromHere )
{
	// Integer to return
	int toReturn = 0;
	
	// Integer pointerss
	int *in = calloc( 1, sizeof( int ));
	
	fseek( fp, fromHere, SEEK_SET );
	
	int i;
	for( i=0; i<thisMany; i++ )
	{
		fread( in, 1, 1, fp );
		toReturn += ( *in << (8*i) );
	}
	
	// Free that pointer
	free( in );
	
	return toReturn;
}


// Read and return a string (okay, char*)
char* grabStr( int thisMany, int fromHere )
{
	ptr = calloc( thisMany, sizeof( char ));
	fseek( fp, fromHere, SEEK_SET );
	fread( ptr, 1, thisMany, fp );
	return ptr;
}


// Read and return the size of a file (argv[2] from command prompt)
int inputSize()
{
	// Jump to the end of the input file
	fseek( in, 0L, SEEK_END );
	
	// ...because it's location is equal to its total size in bytes
	int size = ftell( in );
	
	fseek( in, 0L, SEEK_SET );
	return size;
}


// Writes one cluster and returns how many must still be written
int writeClusters( int bytesPerSector, int cluster )
{
	int i;
	
	// Get the size of the input file
	int size = inputSize();
	
	// Figure out how many sectors will be needed
	int sectors = size / bytesPerSector;
	
	// ...and add one if bytesPerSector does not divide size perfectly
	if( size%bytesPerSector )
		sectors++;
	
	// Navigate to the physical sector (converted from cluster)
	fseek( fp, (cluster+31)*bytesPerSector, SEEK_SET );

	// Write those characters in
	for( i=0; i<size; i++ )
		fputc( fgetc( in ), fp );
	
	// ...but remember to pad the rest of that cluster/sector!
	while( i++<bytesPerSector )
		fputc( 0, fp );
	
	return 0;
}


// Dump an integer of 12 bits or less to a specific FAT address
int FATDump( int bytesPerSector, int toWrite, int cluster )
{
	// Track the number of FATs read, start with entry 2 (0, 1 reserved)
	int FATsRead = 2;
	
	// Physical byte in FAT1 table being checked
	int i = 0;
	
	// Start in sector 1 (FAT table 1) two bytes in (first two reserved)
	fseek( fp, bytesPerSector+3, SEEK_SET );
	
	// ...and take 1 or 2 byte steps until at desired FAT/cluster number
	for( i=bytesPerSector+3; FATsRead<cluster; i++, FATsRead++ )
		if( FATsRead%2==1 )
			i++;
	
	// Grab the current values of the two bytes
	int bk1 = grabInt( 1, i );
	int bk2 = grabInt( 1, i+1 );
	
	// Put the input pointer back in place
	fseek( fp, i, SEEK_SET );
	
	// Break down toWrite into one (high) and two (low), process
	if( cluster%2==0 )
	{
		int one = toWrite>>8;
		int two = toWrite&255;
		fputc( two, fp );
		fputc(( bk2&240 )+( one&15 ), fp );
	}
	else
	{
		int one = toWrite&15;
		int two = toWrite>>4;
		fputc(( one<<4 )+( bk1&15 ), fp );
		fputc( two, fp );
	}
	
	return 0;
}


// Write a file name into a directory
int dumpToDir( int bytesPerSector, char* fileName, int cluster,
	int rootFree )
{
	int thisMany = inputSize();
	
	// Reject and return if longer than than the allowed 11 characters
	if( strlen( fileName ) > 11 )
	{
		printf( "Too many characters for file name!\n" );
		return 1;
	}
	
	// If you're here then there's space to write the file's name
	fseek( fp, rootFree, SEEK_SET );
	
	// Use fputc to write out the name, but in the correct format
	int i;
	for( i=0; i<strlen( fileName ); i++ )
	{
		if( fileName[i]=='.' )
		{
			// If current='.' write ' ' until at space 8
			while( i<8 )
			{
				fputc( ' ', fp );
				i++;
			}
			
			// ...and then write out the extension
			fputc( fileName[strlen(fileName)-3],fp);
			fputc( fileName[strlen(fileName)-2],fp);
			fputc( fileName[strlen(fileName)-1],fp);
		}
		else
		{
			// If current !='.' then write it out
			fputc( fileName[i], fp );
		}
	}
	
	// Write the attribute with no flags
	fputc( 0, fp );
	
	// Write the first logical cluster
	fseek( fp, rootFree+26, SEEK_SET );
	fputc( cluster&255, fp );
	fputc( cluster>>8, fp );
	
	// Write the file size
	fseek( fp, rootFree+28, SEEK_SET );
	fputc(( thisMany     )&255, fp );
	fputc(( thisMany>> 8 )&255, fp );
	fputc(( thisMany>>16 )&255, fp );
	fputc(( thisMany>>24 )   , fp );
	
	// Write those clusters out
	writeClusters( bytesPerSector, cluster );
	
	// Write 0xFFF to corresponding FAT entry
	FATDump( bytesPerSector, 0xFFF, cluster );
	
	printf( "Wrote %d to FAT1 @%d\n", 0xFFF, cluster );					// DEBUG!
	
	return 0;
}


// Scrub root directory and return volume label
char* grabLabel( int bytesPerSector )
{
	ptr = calloc( 12, sizeof( char ));
		
	// Start in sector 19 (root directory)
	fseek( fp, bytesPerSector*19, SEEK_SET );
	
	// ...and take 32 byte steps, stopping before data area (sector 33)
	int i;
	for( i=bytesPerSector*19; i < bytesPerSector*33 ; i+=32 )
	{
		// If a file (32 bytes) has attrib=8 (label)
		if( grabInt( 1, i+11 )==8 )
		{
			// ...then return its name, because that's the label
			ptr = grabStr( 11, i );
			return ptr;
		}
	}
	// No label found, so use the one in the boot directory instead
	// (and yes, its okay for this to be empty)
	return grabStr( 11, 43L );
}


// Check file to see a file is used (free when first bit =0xE5 or =0x00)
int checkUsed( int fromHere )
{
	// Return 0 if free and return 1 if used
	int i = grabInt( 1, fromHere );
	if(( i==0xE5 )||( i==0x00 ))
		return 0;
	else
		return 1;
}


// Count the number of files in the root directory
int countRoot( int bytesPerSector )
{
	// Int to count files in the root directory
	int count = 0;
	
	// Start in sector 19 (root directory)
	fseek( fp, bytesPerSector*19, SEEK_SET );
	
	// ...and take 32 byte steps, stopping before data area (sector 33)
	int i;
	for( i=bytesPerSector*19; i < bytesPerSector*33 ; i+=32 )
	{
		// For each file, if used then increment the counter
		int j = grabInt( 1, i );
		int k = grabInt( 1, i+11 );

		if(( j!=0x00 )&&( j!=0xE5 )&&( k!=0x08 )&&( k!=0x0F ))
			count++;
	}
	// Return the count
	return count;
}


// Count the amount of free space by checking the first FAT table
int spaceFree( int bytesPerSector, int totalSectors )
{
	// Count FATs pointing to free space (clusters of 512 bytes)
	int freeCount = 0;
	
	// Track the number of FATs read
	int FATsRead = 0;
	
	// Byte in FAT1 table being checked
	int i = 0;
	
	// Holder for 1.5 bytes (12 bits)
	int byteAndAHalf;
	
	// Start in sector 1 (FAT table 1) two bytes in (first two reserved)
	fseek( fp, bytesPerSector+3, SEEK_SET );
	
	// ...and take 1 or 2 byte steps until all clusters are checked
	// (Reading one FAT record/sector, incrementing b and FATsRead)
	for( i=bytesPerSector+3; FATsRead<totalSectors-33; i++, FATsRead++ )
	{
		// Holders for 2 bytes at a time
		int one = grabInt( 1, i   );
		int two = grabInt( 1, i+1 );
		
		// Numbers handled differently depending on even/odd reads
		if( FATsRead%2==0 )
		{
			// Even - calculate accordingly (An Overview of FAT12, pg 9)
			byteAndAHalf = one + ((two&15)<<8);
		}
		else 
		{
			// Odd - calculate accordingly
			byteAndAHalf = ((one&240)>>4) + (two<<4);
			
			// ...and step forward an extra byte
			i++;
		}
		
		// If 1.5 byte block (12 bits) is free increment count
		if(( byteAndAHalf == 0x00 )||( byteAndAHalf == 0xE5 ))
			freeCount++;
	}	
	
	// Return the free space = freeCount*512, for each 512 byte cluster
	return freeCount*bytesPerSector;
}


// Print the contents of the root directory
int printRoot( int bytesPerSector )
{
	// Start in sector 19 (root directory)
	fseek( fp, bytesPerSector*19, SEEK_SET );
	
	// ...and take 32 byte steps, stopping before data area (sector 33)
	int i;
	for( i=bytesPerSector*19; i < bytesPerSector*33 ; i+=32 )
	{
		// For each entry marked as used:
		if( checkUsed( i ))
		{
			// ...display D or F (skip vol label, long filename parts)
			int attrib = grabInt( 1, i+11L );
			if( attrib==0x08 || attrib==0x0F )
				continue;
			else if( attrib&0x10 )
				printf( "D " );
			else
				printf( "F " );
			
			// ...followed by the size
			int fileSize = grabInt( 4, i+28L );
			printf( "%10d bytes ", fileSize );
			
			// ...and then the name
			char* fileName = grabStr( 8, i );
			
			// Cut off any trailing spaces
			strcpy( fileName, strtok( fileName, " "));

			// If there's an extension then add it, but after a "."
			if( strcmp( grabStr( 1, i+8 ), " " ))
				strcat( strcat( fileName, "."), grabStr( 3, i+8 ));
			
			// Print the file name
			printf( "%20s ", fileName );
			
			// Grab and print the creation date (chop into
			int crDate = grabInt( 2, i+16 );
			int year   = (crDate>>9)+1980;
			int month  = (crDate>>5)&15;
			int day    = crDate&31;
			printf( "%04d/%02d/%02d ", year, month, day );
			
			// Grab and print the creation time (chop into hh:mm:ss*2)
			int crTime  = grabInt( 2, i+14 );
			int hours   = crTime>>11;
			int minutes = (crTime>>5)&63;
			int seconds = (crTime<<1)&63;
			printf( "%02d:%02d:%02d\n", hours, minutes, seconds );
		}
	}
	
	// Return success
	return 0;
}


// Create and write to a file in the current Linux directory
int fileOut( char* fileName, int thisMany, int fromHere )
{
	FILE *out;
	out = fopen( fileName, "w+" );
	
	int i;
	for( i=0; i<thisMany; i++)
	{
		char charOut = grabStr( 1, fromHere+i)[0];
		fputc( charOut, out );
	}
	
	fclose( out );
	return 0;
}


// Find and return first free address in root directory or 0 for none
int freeInRoot( int bytesPerSector )
{
	// Start in sector 19 (root directory)
	fseek( fp, bytesPerSector*19, SEEK_SET );
	
	// ...and take 32 byte steps, stopping before data area (sector 33)
	int b;
	for( b=bytesPerSector*19; b < bytesPerSector*33 ; b+=32 )
	{
		// For each 32 byte block grab the first byte
		int j = grabInt( 1, b );
		
		// ...and if it's free then return that value
		if(( j==0x00 )||( j==0xE5 ))
			return b;
	}

	// If here then none found, so return 0
	return 0;
}


// Find and return first free address in root directory or 0 for none
int freeInFAT( int bytesPerSector, int totalSectors )
{
	// Track the number of FATs read
	int FATsRead = 2;
	
	// Byte in FAT1 table being checked
	int i = 0;
	
	// Holder for 1.5 bytes (12 bits)
	int byteAndAHalf;
	
	// Start in sector 1 (FAT table 1) two bytes in (first two reserved)
	fseek( fp, bytesPerSector+3, SEEK_SET );
	
	// ...and take 1 or 2 byte steps until all clusters are checked
	// (Reading one FAT record/sector, incrementing b and FATsRead)
	for( i=bytesPerSector+3; FATsRead<totalSectors-37; i++, FATsRead++ )
	{
		// Holders for 2 bytes at a time
		int one = grabInt( 1, i   );
		int two = grabInt( 1, i+1 );
		
		// Numbers handled differently depending on even/odd reads
		if( FATsRead%2==0 )
		{
			// Even - calculate accordingly (An Overview of FAT12, pg 9)
			byteAndAHalf = one + ((two&15)<<8);
		}
		else 
		{
			// Odd - calculate accordingly
			byteAndAHalf = ((one&240)>>4) + (two<<4);
			
			// ...otherwise take an extra step forward
			i++;
		}
		
		// If 1.5 byte block (12 bits) is free return that FAT entry
		if(( byteAndAHalf == 0x00 )||( byteAndAHalf == 0xE5 ))
			return FATsRead;
	}	
	
	// If down here then there's no space, so return 0 (FAT starts at 2)
	return 0;
}


// Write an input file to the root directory
int writeInRoot( int bytesPerSector, int totalSectors, char* fileName )
{
	// Grab the address where we'll be writing
	int writeHere = freeInRoot( bytesPerSector );
	
	// Grab the address of a free sector in FAT
	int cluster = freeInFAT( bytesPerSector, totalSectors );
	
	// Write name to root directory and return success/fail
	return dumpToDir( bytesPerSector, fileName, cluster, writeHere );
}


// Create and write to a file into the image
int fileIn( int bytesPerSector, int totalSectors, int freeSpace, char* fileName )
{
	// Try to open that file and keep going if successful
	if (!(in=fopen( fileName, "r" )))
	{
		// ...but return 1 and bail out if unsuccesful
		printf("Failed to open %s as input.\n", fileName );
		fclose( in );
		return 1;
	}
		
	// Grab the total size of the input file
	int size = inputSize();
	
	// Compare requested (input) and available space (in disk image)
	if( size > freeSpace )
	{
		// If not enough room then print an error and return 1
		printf( "\nNot enough free space to add %s!\n", fileName );
		printf( "\tRequested: %10d bytes\n", size );
		printf( "\tAvailable: %10d bytes\n", freeSpace );
		printf( "\tShortfall: %10d bytes\n", size-freeSpace );
		return 1;
	}
	
	// Get the result of writeInRoot to return as success/failure
	int toReturn = writeInRoot( bytesPerSector, totalSectors, fileName );
	
	// Close the input file and (what is hopefully) success!
	fclose( in );
	return toReturn;
}


// Count the amount of free space by checking the first FAT table
int findName( int bytesPerSector, int totalSectors, char* wantName )
{
	// Start in sector 19 (root directory)
	fseek( fp, bytesPerSector*19, SEEK_SET );
	
	// ...and take 32 byte steps, stopping before data area (sector 33)
	int b;
	for( b=bytesPerSector*19; b < bytesPerSector*33 ; b+=32 )
	{
		// For each entry marked as used:
		if( checkUsed( b ))
		{
			// Grab the attribute
			int attrib = grabInt( 1, b+11 );
			
			// Skip the volume label and parts of long file names
			if( attrib==0x08 || attrib==0x0F )
				continue;
			
			// Now grab the name to compare with thisName
			char* fileName = grabStr( 8, b );
			
			// Cut off any trailing spaces
			strcpy( fileName, strtok( fileName, " "));

			// If there's an extension then add it, but after a "."
			if( strcmp( grabStr( 1, b+8 ), " " ))
				strcat( strcat( fileName, "."), grabStr( 3, b+8 ));		
			
			// Compare fileName to wantName
			if( !strcmp( wantName, fileName ))
			{
				// If wantName=fileName then return the address
				return b;
			}
		}
	}
	
	// If down here then we don't have a match, return 0
	return 0;
}
