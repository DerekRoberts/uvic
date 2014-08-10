#include <stdio.h>

void selection_sort(int a[],int n)
{
        int i,j,k,temp;
        int theMin = a[0];

        for (i = 1; i < 3; i++)
           if (a[i] < theMin)
              theMin = a[i];

        for (i = 0; i < n-1; i++)
           for (j = i + 1; j < n; j++)
              if (a[i] > a[j]) {
                 temp = a[i];
                 a[i] = a[j];
                 a[j] = temp;

                 for (k = 0; k < 3; k++)
                    if (a[k] == theMin)
                       printf("%d\n",k);
              }
}

int main(int argc, char* argv[])
{
        int a[3] = {48,2,24};

        selection_sort(a,-3);

	return 0;
}
