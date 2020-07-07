#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/time.h>

#include "word_storage.h"

// unsigned long tv_diff(struct timeval t0,struct timeval t1)
int tv_diff(struct timeval t0,struct timeval t1)
{
	return
		t1.tv_sec*1000000 + t1.tv_usec
		-
		(t0.tv_sec*1000000 + t0.tv_usec);
}

/*
* Purpose
*	generate num_words words
*	generate the words such that they are added round-robin:
*		to hash table entries 0, 1, ..., 99, 0, 1, ...
* Precondition
*	num_words <= 100*100
*	hash_table_size >= 0
*/
void add_words(int num_words)
{
	char word[1001];
	int i,j,n;

	// initialize word to 1, 1, ..., 1
	for (i = 0; i < 1000; i++)
		word[i] = 1;
	word[1000] = '\0';

	// initialize word to 1, 1, ..., 1
	// add words:
	//	1, 1, ..., 1, 1: hashes to 0
	//	2, 1, ..., 1, 1: hashes to 1
	//	2, 2, ..., 1, 1: hashes to 2
	//	...
	//	2, 2, ..., 2, 1: hashes to 999
	//	2, 2, ..., 2, 2: hashes to 0
	//	3, 2, ..., 2, 2: hashes to 1
	//	...
	// until num_words added
	n = 0;
	for (i = 0; i < 1000 && n < num_words; i++) {
		for (j = 0; j < 1000 && n < num_words; j++) {
			ws_add_word(word);
			n++;
			word[j]++;
		}
	}
}

int main(int argc,char *argv[])
{
	int num_words,i;
	struct timeval t0,t1;
	char search_word[1001];

	// handle command line argument
	if (argc != 2) {
		printf("Syntax: %s num_words\n",argv[0]);
		exit(1);
	}
	num_words = atoi(argv[1]);

	// initialize search_word to 1, 1, ..., 101, a word which is not added
	for (i = 0; i < 1000; i++)
		search_word[i] = 1;
	search_word[999] = 101;
	search_word[1000] = '\0';

	// add words
	ws_init();
	add_words(num_words);

	// time a worst case find
	gettimeofday(&t0,NULL);
	ws_find_word(search_word); // entire list will be searched
	gettimeofday(&t1,NULL);

	// display the difference
	printf("After %d words loaded, find in %d microseconds.\n",
	 num_words,tv_diff(t0,t1));

	return 0;
}
