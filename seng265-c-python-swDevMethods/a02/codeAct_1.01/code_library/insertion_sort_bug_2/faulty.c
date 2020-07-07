int ca_argv_1;
#include <stdio.h>

void insertion_sort(int a[],int n)
{
        int i,j,temp;
        for (i = 1; i < n; i++) {
       j = i;
           while (j > 0 && a[j-1] > a[j]) {
                 temp = a[j-1];
                 a[j-1] = a[j-1];
                 a[j-1] = temp;
              j--;
           }
        }
}

int main(int argc, char* argv[])
{
ca_argv_1 = atoi(argv[argc-1+0]);
        int i;
        int a[3] = {2,ca_argv_1,6};

        insertion_sort(a,3);

        for(i = 0; i < 3; i++)
           printf("%d\n",a[i]);

	return 0;
}
