int ca_argv_1;
#include <stdio.h>

void selection_sort(int a[],int n)
{
        int i,j,temp;
           for (i = 0; i < n-1; i++) {
              for (j = i + 1; j < n; j++) {
                 if (a[i] > a[j]) {
                    temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = temp;
              }
           }
        }
}

int main(int argc, char* argv[])
{
ca_argv_1 = atoi(argv[argc-1+0]);
        int i;
        int a[3] = {ca_argv_1,4,4};

        selection_sort(a,2);

        for(i = 0; i < 3; i++)
           printf("%d\n",a[i]);

	return 0;
}
