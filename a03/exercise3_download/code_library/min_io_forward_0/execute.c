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
	printf("%d\n", min(1,2));
	return 0;
}
