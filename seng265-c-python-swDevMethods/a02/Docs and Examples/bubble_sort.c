#include <stdio.h>

void bubble_sort(int a[],int n)
{
	int i,j,temp;

	for (i = 0; i < n-1; i++) {
		for (j = n-1; j > i; j--) {
			if (a[j] < a[j-1]) {
				temp = a[j];
				a[j] = a[j-1];
				a[j-1] = temp;
			}
		}
	} 
}

int main(int argc,char *argv[])
{
	int i;
	int a[3] = {3,1,2};

	bubble_sort(a,3);        

	for (i = 0; i < 3; i++)
		printf("%d\n",a[i]);

	return 0;
}
