clear
gcc -Wall diskput.c -o a.o
rm disk2.IMA
cp original.IMA disk2.IMA
./a.o disk2.IMA TEST.TXT
rm -f a.o
