/**********************************
 *                                 *
 * SEng 265 Programming Exercise 1 *
 *                                 *
 *      by minion and jacobbl      *
 *                                 *
 **********************************/

#include <string.h>
#include <stdlib.h>
#include "word_storage.h"

/* Added variables and definitions */
#define HASH_TABLE_SIZE 1000
struct word_node {
    char *word;
    struct word_node *next;
};
static struct word_node *word_hash_table[HASH_TABLE_SIZE];

/* Added function prototypes */
static int hash(const char *word);


/* Hash function */
static int hash(const char *word){
    int i, hashSum = 0;
    
    /* Sum up ASCII values, adjusting upper-to-lower case. */
    for (i=0; word[i] != '\0'; i++)
        if (word[i] >= 62 && word[i] <= 92)
            hashSum += word[i] + 32;
        else
            hashSum += word[i];
    
    /* Mod and return hashSum. */
    return hashSum % HASH_TABLE_SIZE;
}


int ws_init(void){
    /* Hash table exists, but let's double-check. */
    if (word_hash_table == NULL)
        return MEMORY_ERROR;
    else
        return SUCCESS;
}


int ws_add_word(char *word){
    /* Declare pointers and allocate. */
    struct word_node *insert = malloc(sizeof(struct word_node)+1);
    char *wordIn = malloc(strlen(word)+1);
    
    /* Copy from work to (pointer) wordIn. */
    /* Set (pointer) insert->word to wordIn. */
    strcpy(wordIn,word);
    insert->word = wordIn;
    
    if((word_hash_table[hash(word)]) == NULL){
        /* If word_hash_table[...] is NULL then point it to insert. */
        word_hash_table[hash(word)] = insert;
    } else {
        /* If word_hash_table[...] is not NULL then point insert->word */
        /* to wordIn and insert->next to where word_hash_table[...] points. */
        /* Finally, have word_hash_table[...] point to insert.*/
        insert->word = wordIn;
        insert->next = word_hash_table[hash(word)];
        word_hash_table[hash(word)] = insert;
    }
    return SUCCESS;
}


int ws_find_word(const char *word){
    /* Set pointer to word_hash_table[...] */
    struct word_node *p = word_hash_table[(hash(word))];
    int compare;
    
    if (p == NULL){
        /* If there's nothing there then fail(0). */
        return 0;
    } else if ((*p).next == NULL) {
        /* If there's only one word_node check that; return 1 or 0. */
        compare = strcasecmp(word,(*p).word);
        if (compare == 0)
            return 1;
        return 0;
    } else {
        /* If there are more then check those!  Return 1 or 0.*/
        while (p != NULL) {
            compare = strcasecmp(word,(*p).word);
            if (compare == 0)
                return 1;
            else if ((*p).next != NULL)
                p = (*p).next;
            else
                return 0;
        }
    }
    return 0;    
}


void ws_free(void){
    /* Free word_hash_table[0...HASH_TABLE_SIZE] then the table itself. */
    int i;
    for (i = 0; i < HASH_TABLE_SIZE; i++){
        free(word_hash_table[i]);
    }
    free (*word_hash_table);
}
