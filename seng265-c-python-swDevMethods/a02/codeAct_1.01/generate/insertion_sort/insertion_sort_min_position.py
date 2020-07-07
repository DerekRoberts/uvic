game_type = 'input_output'
source_language = 'C'

parameter_list = [
	['$i0','int'],['$i1','int'],['$i2','int'],		# $i_ = array input values
	['$n0','int'],						# $n_ = number of sort iterations
	['$y0','string'],['$y1','string'],['$y2','string']	# $y_ = stdout
]

tuple_list = [
	['insertion_sort_min_position_',	[3,9,27,3],[39,28,17,3],[39,28,17,2],[48,2,24,3],[48,2,24,-3]],
]

global_code_template = '''\
d #include &lt;stdio.h>
x #include <stdio.h>
dx
dx void insertion_sort(int a[],int n)
dx {
dx         int i,j,k,temp;
dx         int theMin = a[0];
dx
dx         for (i = 1; i < 3; i++)
dx            if (a[i] < theMin)
dx               theMin = a[i];
dx
dx         for (i = 1; i < n; i++) {
dx            j = i;
dx            while (j > 0 && a[j-1] > a[j]) {
dx               temp = a[j];
dx               a[j] = a[j-1];
dx               a[j-1] = temp;
dx               j--;
dx
dx               for (k = 0; k < 3; k++)
dx                  if (a[k] == theMin)
dx                     printf("%d\\n",k);
dx            }
dx         }
dx }
dx
'''

main_code_template = '''\
dx         int a[3] = {$i0,$i1,$i2};
dx
dx         insertion_sort(a,$n0);
dx
'''

argv_template = ''

stdin_template = ''

stdout_template = '''\
$y0
$y1
$y2
'''
