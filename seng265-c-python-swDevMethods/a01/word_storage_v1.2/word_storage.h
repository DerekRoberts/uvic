#define SUCCESS 0
#define MEMORY_ERROR -1

/*
* Purpose
* 	Initialize the module
* 	Return the appropriate status code
* Preconditions
* 	ws_init has not been called
* 	ws_free has not been called
*/
int ws_init(void);

/*
* Purpose
*	Add word to the currently stored list of words
*	if not enough memory is available to store word
*		return MEMORY_ERROR
*	else
*		return SUCCESS
* Preconditions
*	word is a legal C string
*	ws_init has been called and returned SUCCESS
*	ws_free has not been called
*/
int ws_add_word(char *word);

/*
* Purpose
*	Return 1 if word is in the currently stored list and 0 otherwise
*	When searching, the comparisons must be case insensitive
*	For example, "aaa" and "aAa" are considered equal
* Preconditions
*	word is a legal C string
*	ws_init has been called and returned SUCCESS
*	ws_free has not been called
*/
int ws_find_word(const char *word);

/*
* Purpose
*	Release all memory dynamically by this module
* Precondition
*	ws_init has been called and returned SUCCESS
*	ws_free has not been called
*/
void ws_free(void);
