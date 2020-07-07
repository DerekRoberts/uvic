#include <stdio.h>

void selection_sort(int a[],int n)
{
        int i,j,temp,swap_count;

        swap_count = 0;

        for (i = 0; i < n-1; i++) {
           for (j = i + 1; j < n; j++) {
              if (a[i] > a[j]) {
                 temp = a[i];
                 a[i] = a[j];
                 a[j] = temp;

                 swap_count++;
              }
           }
        }
        printf("%d\n", swap_count);
}

int main(int argc, char* argv[])
{
        int a[3] = {32,12,91};

        selection_sort(a,2);

	return 0;
}
