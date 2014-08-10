game_type = 'input_output'
source_language = 'C'

parameter_list = [
	['$i0','int'],['$i1','int'],['$i2','int'],		# $i_ = array input values
	['$n0','int'],						# $n_ = number of sort iterations
	['$y0','int']						# $y_ = stdout
]

tuple_list = [
	['bubble_sort_swap_count_',	[8,6,4,3],[2,1,3,2],[5,9,6,1],[18,19,19,2],[13,21,111,3]],
]

global_code_template = '''\
d #include &lt;stdio.h>
x #include <stdio.h>
dx
dx void bubble_sort(int a[],int n)
dx {
dx         int i,j,temp,swap_count;
dx
dx         swap_count = 0;
dx
dx         for (i = 0; i < n-1; i++) {
dx            for (j = n-1; j > i; j--) {
dx               if (a[j] < a[j-1]) {
dx                  temp = a[j];
dx                  a[j] = a[j-1];
dx                  a[j-1] = temp;
dx
dx                  swap_count++;
dx               }
dx            }
dx         }
dx         printf("%d\\n", swap_count);
dx }
dx
'''

main_code_template = '''\
dx         int a[3] = {$i0,$i1,$i2};
dx
dx         bubble_sort(a,$n0);
dx
'''

argv_template = ''

stdin_template = ''

stdout_template = '''\
$y0
'''
