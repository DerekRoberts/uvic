#include <string.h>
#include <stdlib.h>

#include "word_storage.h"

#define INITIAL_MAX_WORDS 100
static char **word_list;
static int word_list_length;
static int num_words;

/* Notes on internal data structure:
* 1. word_list[0..word_list_length-1] is allocated from the heap
* 2. each element of word_list[0..num_words-1] is a pointer to a legal C string
* 3. word_list[0..num_words-1] is sorted in ascending order, case-insensitive
*/

int ws_init(void)
{
	// create an empty word_list with default space
	word_list_length = INITIAL_MAX_WORDS;
	num_words = 0;
	word_list = malloc(word_list_length * sizeof(char*));
	if (word_list == NULL) {
		return MEMORY_ERROR;
	} else {
		return SUCCESS;
	}
}

int ws_add_word(char *word)
{
	int insert_position,i,n;
	char **new_word_list;

	// find position to insert word
	for (insert_position = 0;
	insert_position < num_words;
	insert_position++) {
		n = strcasecmp(word,word_list[insert_position]);
		if (n == 0) { // already in word_list: ignore
			return SUCCESS;
		} else if (n < 0) { // insert position found
			break;
		}
	}

	// if word_list is full, double its size
	if (num_words >= word_list_length) {
		// alloc new space
		new_word_list = malloc(2*word_list_length*sizeof(char*));
		if (new_word_list == NULL) {
			return MEMORY_ERROR;
		}

		// copy word pointers from old space to new space
		for (i = 0; i < word_list_length; i++)
			new_word_list[i] = word_list[i];

		// free old space
		free(word_list);

		// update location, length of new space
		word_list = new_word_list;
		word_list_length *= 2;
	}

	// shift word_list[i..num_words-1] to the right
	num_words++;
	for (i = num_words-1; i > insert_position; i--) {
		word_list[i] = word_list[i-1];
	}

	// insert word in place
	word_list[insert_position] = malloc(strlen(word)+1);
	if (word_list[insert_position] == NULL) {
		return MEMORY_ERROR;
	}
	strcpy(word_list[insert_position],word);

	return SUCCESS;
}

int ws_find_word(const char *word)
{
	int high,low,mid,compare;

	low = 0;
	high = num_words-1;
	while (low <= high) {
		mid = (low+high)/2;
		compare = strcasecmp(word,word_list[mid]);
		if (compare < 0)
			high = mid-1;
		else if (compare == 0)
			return 1;
		else
			low = mid+1;
	}
	return 0;
}

void ws_free(void)
{
	int i;

	// free the words
	for (i = 0; i < num_words; i++)
		free(word_list[i]);

	// free word_list
	free(word_list);
}
