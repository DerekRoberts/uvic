#include <stdio.h>

void insertion_sort(int a[],int n)
{
        int i,j,temp,swap_count;

        swap_count = 0;

        for (i = 1; i < n; i++) {
           j = i;
           while (j > 0 && a[j-1] > a[j]) {
              temp = a[j];
              a[j] = a[j-1];
              a[j-1] = temp;
              j--;

              swap_count++;
           }
        }
        printf("%d\n", swap_count);
}

int main(int argc, char* argv[])
{
        int a[3] = {18,11,10};

        insertion_sort(a,3);

	return 0;
}
