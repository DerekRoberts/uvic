#include <stdio.h>

void insertion_sort(int a[],int n)
{
        int i,j,k,temp;
        int theMin = a[0];

        for (i = 1; i < 3; i++)
           if (a[i] < theMin)
              theMin = a[i];

        for (i = 1; i < n; i++) {
           j = i;
           while (j > 0 && a[j-1] > a[j]) {
              temp = a[j];
              a[j] = a[j-1];
              a[j-1] = temp;
              j--;

              for (k = 0; k < 3; k++)
                 if (a[k] == theMin)
                    printf("%d\n",k);
           }
        }
}

int main(int argc, char* argv[])
{
        int a[3] = {39,28,17};

        insertion_sort(a,3);

	return 0;
}
