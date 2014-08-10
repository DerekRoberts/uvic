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
	['selection_sort_bug_',	[3,None,6,3,1,'i','j-1'],[5,9,None,3,1,'i','j-1'],[0,None,2,3,1,'j-1','j-1'],[None,4,4,2,1,'j','j-1'],[None,1,9,2,1,'j','j-1']],
]

global_code_template = '''\
d #include &lt;stdio.h>
xX #include <stdio.h>
dxX
dxX void selection_sort(int a[],int n)
dxX {
dxX         int i,j,temp;
dxX            for (i = 0; i < n-1; i++) {
dxX               for (j = i + 1; j < n; j++) {
dxX                  if (a[i] > a[j]) {

x                     temp = a[j];
dX                     temp = a[$v0];

x                     a[j] = a[j-1];
dX                     a[$v0] = a[$v1];

x                     a[j-1] = temp;
dX                     a[$v1] = temp;

dxX               }
dxX            }
dxX         }
dxX }
dxX
'''

main_code_template = '''\
dxX         int i;
dxX         int a[3] = {$i0,$i1,$i2};
dxX

x         selection_sort(a,3);
dX         selection_sort(a,$n0);

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
