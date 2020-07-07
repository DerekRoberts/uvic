SHELL=/bin/bash

# flag values for development/debugging
COMPILE_FLAGS = -g -Wall -c -fprofile-arcs -ftest-coverage
LINK_FLAGS = -g -fprofile-arcs -ftest-coverage

# flag values for performance
#COMPILE_FLAGS = -O -Wall -c
#LINK_FLAGS = -O

all_executables: word_storage_test word_storage_find_test

# Word Storage Module
word_storage.o: word_storage.h word_storage.c
	gcc $(COMPILE_FLAGS) word_storage.c

# correctness test
word_storage_test: word_storage_test.o word_storage.o
	gcc $(LINK_FLAGS) -o word_storage_test \
	 word_storage_test.o word_storage.o
word_storage_test.o: word_storage.h word_storage_test.c
	gcc $(COMPILE_FLAGS) word_storage_test.c

# performance test: ws_find_word
word_storage_find_test: word_storage.o word_storage_find_test.o
	gcc $(LINK_FLAGS) -o word_storage_find_test \
	 word_storage_find_test.o word_storage.o
word_storage_find_test.o: word_storage.h word_storage_find_test.c
	gcc $(COMPILE_FLAGS) word_storage_find_test.c

clean:
	rm -f core *.o *.gcov *.gcda *.gcno \
	word_storage_test word_storage_find_test
