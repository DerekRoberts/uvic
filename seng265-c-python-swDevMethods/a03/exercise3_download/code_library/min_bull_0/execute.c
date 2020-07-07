int ca_argv_1;
int ca_target_0;
#include <stdio.h>

int min(int a, int b) {
	if (a < b) { 
		ca_target_0 = 1;
		return a;
	} else { 
		
		return b;
	}
}
int main(int argc, char* argv[])
{
ca_argv_1 = atoi(argv[argc-1+0]);
	min(1, ca_argv_1);
if (ca_target_0)
	return 0;
	else
	return 1;
	return 0;
}
