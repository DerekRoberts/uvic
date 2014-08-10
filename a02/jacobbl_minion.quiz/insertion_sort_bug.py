game_type = 'find_the_failure'
source_language = 'C'

parameter_list = [
  	['$i0','int'],['$i1','int'],['$i2','int'],		# $i_ = array input values
	['$n0','int'],						# $n_ = number of sort iterations
	['$a0','int'],						# $a_ = array index to printf
	['$v0','string'],['$v1','string'],			# $v_ = variables holding array indexes
	['$y0','string'],['$y1','string'],['$y2','string']	# $y_ = stdout
]

tuple_list = [
	['insertion_sort_bug_',	[4,None,6,3,1,'j','i'],[6,7,None,3,1,'i','j-1'],[2,None,6,3,1,'j-1','j-1'],[None,3,3,2,1,'j','j-1'],[None,9,11,2,1,'j','j-1']],
]

global_code_template = '''\
d #include &lt;stdio.h>
xX #include <stdio.h>
dxX
dxX void insertion_sort(int a[],int n)
dxX {
dxX         int i,j,temp;
dxX         for (i = 1; i < n; i++) {
dxX	       j = i;
dxX            while (j > 0 && a[j-1] > a[j]) {

x                  temp = a[j];
dX                  temp = a[$v0];

x                  a[j] = a[j-1];
dX                  a[$v0] = a[$v1];

x                  a[j-1] = temp;
dX                  a[$v1] = temp;

dxX               j--;
dxX            }
dxX         }
dxX }
dxX
'''

main_code_template = '''\
dxX         int i;
dxX         int a[3] = {$i0,$i1,$i2};
dxX

x         insertion_sort(a,3);
dX         insertion_sort(a,$n0);

dxX
dxX         for(i = 0; i < 3; i++)
dxX            printf("%d\\n",a[i]);
dxX
'''

argv_template = ''

stdin_template = ''

stdout_template = '''\
$y0
$y1
$y2
'''
