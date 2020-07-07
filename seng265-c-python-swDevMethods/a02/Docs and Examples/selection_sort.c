#include <stdio.h>

void selection_sort(int a[],int n)
{
	int i,j,temp;

	for (i = 0; i < n-1; i++) {
		for (j = i + 1; j < n; j++) {
			if (a[i] > a[j]) {
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		}
	}
}

int main(int argc,char *argv[])
{
	int i;
	int a[3] = {3,1,2};

	selection_sort(a,3);        

	for (i = 0; i < 3; i++)
		printf("%d\n",a[i]);

	return 0;
}
