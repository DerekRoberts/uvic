int ca_argv_1;
int ca_argv_2;
int ca_assert_0;
void ca_assert(int n,int expr) {
	if (!expr) {
		if (n == 0)
			ca_assert_0++;
	}
}
#include <stdio.h>

int min(int a,int b) {
	if (a < b) { 
		
		return a;
	} else { 
		ca_assert(0,b < 6);
		return b;
	}
}
int main(int argc, char* argv[])
{
ca_argv_1 = atoi(argv[argc-2+0]);
ca_argv_2 = atoi(argv[argc-2+1]);
	min(ca_argv_1,ca_argv_2);
if (ca_assert_0)
	return 0;
	else
	return 1;
	return 0;
}
