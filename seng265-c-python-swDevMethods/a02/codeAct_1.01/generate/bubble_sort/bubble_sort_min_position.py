game_type = 'input_output'
source_language = 'C'

parameter_list = [
	['$i0','int'],['$i1','int'],['$i2','int'],		# $i_ = array input values
	['$n0','int'],						# $n_ = number of sort iterations
	['$y0','string'],['$y1','string'],['$y2','string']	# $y_ = stdout
]

tuple_list = [
	['bubble_sort_min_position_',	[2,4,6,3],[8,7,4,3],[10,8,6,2],[20,2,12,3],[12,10,14,-3]],
]

global_code_template = '''\
d #include &lt;stdio.h>
x #include <stdio.h>
dx
dx void bubble_sort(int a[],int n)
dx {
dx         int i,j,k,temp;
dx         int theMin = a[0];
dx
dx         for (i = 1; i < 3; i++)
dx            if (a[i] < theMin)
dx               theMin = a[i];
dx               
dx         for (i = 0; i < n-1; i++)
dx            for (j = n-1; j > i; j--)
dx               if (a[j] < a[j-1]) {
dx                  temp = a[j];
dx                  a[j] = a[j-1];
dx                  a[j-1] = temp;
dx
dx                  for (k = 0; k < 3; k++)
dx                     if (a[k] == theMin)
dx                        printf("%d\\n",k);
dx                  }
dx }
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
$y1
$y2
'''
