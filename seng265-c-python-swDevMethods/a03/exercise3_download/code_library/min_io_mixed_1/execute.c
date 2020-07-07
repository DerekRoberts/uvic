int ca_argv_1;
#include <stdio.h>

int min(int a, int b) {
	if (a < b) {
		return a;
	} else {
		return b;
	}
}
int main(int argc, char* argv[])
{
ca_argv_1 = atoi(argv[argc-1+0]);
	printf("%d\n", min(3,ca_argv_1));
	return 0;
}
