#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "word_storage.h"

// ********** Word Storage module---test driver **********

int main(int argc,char *argv[])
{
	int i,n,status;
	char word[10]; // room for "word" plus n < 1,000

	status = ws_init();
	if (status != SUCCESS) {
		printf("Error. ws_init failure: %d\n",status);
		exit(1);
	}

	// handle command line argument
	if (argc < 2) {
		printf("Syntax: %s word_count\n",argv[0]);
		exit(1);
	}
	n = atoi(argv[1]);

	// add to Word Storage: "word2", "word4", ..., "wordK" where K = 2*n
	for (i = 2; i <= 2*n; i = i + 2) {
		sprintf(word,"word%d",i);
		status = ws_add_word(word);
		if (status != SUCCESS) {
			printf("Error. ws_add_word failure: %d\n",status);
			exit(1);
		}
	}
	// add again to ensure duplicates are handled properly
	for (i = 2; i <= 2*n; i = i + 2) {
		sprintf(word,"word%d",i);
		status = ws_add_word(word);
		if (status != SUCCESS) {
			printf("Error. ws_add_word failure: %d\n",status);
			exit(1);
		}
	}

	// check: "word1", "word2", ..., "wordK" where K = 2*n+1
	for (i = 1; i <= 2*n+1; i++) {
		int found;
		sprintf(word,"word%d",i);
		found = ws_find_word(word);
		if (i%2 == 0 && !found) {
			printf("Error: word \"%s\" not found.\n",word);
		} else if (i%2 == 1 && found) {
			printf("Error: word \"%s\" found.\n",word);
		}
	}

	// check: "Word1", "Word2", ..., "WordK" where K = 2*n+1
	for (i = 1; i <= 2*n+1; i++) {
		int found;
		sprintf(word,"Word%d",i);
		found = ws_find_word(word);
		if (i%2 == 0 && !found) {
			printf("Error: word \"%s\" not found.\n",word);
		} else if (i%2 == 1 && found) {
			printf("Error: word \"%s\" found.\n",word);
		}
	}

	// release the dynamic memory
	ws_free();

	return 0;
}
