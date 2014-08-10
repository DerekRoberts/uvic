#include <stdio.h>

void bubble_sort(int a[],int n)
{
        int i,j,k,temp;
        int theMin = a[0];

        for (i = 1; i < 3; i++)
           if (a[i] < theMin)
              theMin = a[i];
              
        for (i = 0; i < n-1; i++)
           for (j = n-1; j > i; j--)
              if (a[j] < a[j-1]) {
                 temp = a[j];
                 a[j] = a[j-1];
                 a[j-1] = temp;

                 for (k = 0; k < 3; k++)
                    if (a[k] == theMin)
                       printf("%d\n",k);
                 }
}
int main(int argc, char* argv[])
{
        int a[3] = {8,7,4};

        bubble_sort(a,3);

	return 0;
}
