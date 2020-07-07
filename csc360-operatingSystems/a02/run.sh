#!/bin/bash
clear
gcc -Wall -pthread a02.c -o a02.o
./a02.o flow.txt
echo -n "Press a Enter..."
read input
rm ./a02.o
