#include <stdio.h>

void bubble_sort(int a[],int n)
{
        int i,j,temp,swap_count;

        swap_count = 0;

        for (i = 0; i < n-1; i++) {
           for (j = n-1; j > i; j--) {
              if (a[j] < a[j-1]) {
                 temp = a[j];
                 a[j] = a[j-1];
                 a[j-1] = temp;

                 swap_count++;
              }
           }
        }
        printf("%d\n", swap_count);
}

int main(int argc, char* argv[])
{
        int a[3] = {5,9,6};

        bubble_sort(a,1);

	return 0;
}
