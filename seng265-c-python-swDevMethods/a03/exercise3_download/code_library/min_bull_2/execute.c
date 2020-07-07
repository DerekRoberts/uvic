int ca_argv_1;
int ca_target_0;
#include <stdio.h>

int min(int a, int b) {
	if (a < b) { 
		
		return a;
	} else { 
		ca_target_0 = 1;
		return b;
	}
}
int main(int argc, char* argv[])
{
ca_argv_1 = atoi(argv[argc-1+0]);
	min(ca_argv_1, 7);
if (ca_target_0)
	return 0;
	else
	return 1;
	return 0;
}
