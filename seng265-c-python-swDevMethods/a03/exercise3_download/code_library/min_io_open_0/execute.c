int ca_argv_1;
int ca_argv_2;
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
ca_argv_1 = atoi(argv[argc-2+0]);
ca_argv_2 = atoi(argv[argc-2+1]);
	printf("%d\n", min(ca_argv_1,ca_argv_2));
	return 0;
}
