#!/usr/bin/python

import sys
import os

'''
purpose
	print s and exit the program
precondition
	s is a legal python string
'''
def exit(s):
	print s
	sys.exit()

'''
purpose
	if all element in L is of type t: return None
	else: return the index of the element which is not of type list
precondition
	L is a legal python list
'''
def check_elements_type(L,t):
	for i in range(len(L)):
		if not type(L[i]) == t:
			return i

# check if the command line arguments is of the correct length
if len(sys.argv) != 2:
	exit('Usange: '+sys.argv[0]+' <module_name>')

# check if the module can be imported
try:
	path = os.path.split(sys.argv[1])
	sys.path.append(path[0])
	exec('import '+path[1].replace('.py','')+' as tl')
except ImportError:
	exit('Error: '+sys.argv[1]+' can not be imported.')

# check parameter_list 
# check if parameter_list is of type list
if not type(tl.parameter_list) == list:
	exit('Error: parameter_list is not of type list')
# check if all sub-lists of tuple_list are of type list
n = check_elements_type(tl.parameter_list,list)
if n != None:
	exit('Error: '+tl.parameter_list[n]+\
	 'in parameter_list is not of type list')
L = []
for p in tl.parameter_list:
	# each sublist only contains 2 elements of type string
	if len(p) != 2:
		exit('Error: '+str(p)+' in parameter_list must of length 2')
	n = check_elements_type(p,str)
	if n != None:
		exit('Error: '+str(p[n])+' in parameter_list '+p+\
		 ' must be of type string')
	# check duplicated parameter names
	if p[0] in L:
		exit('Error: duplicated parameter name is found '+p[0])
	else:
		L.append(p[0])
	# check if all parameters are present
	if not p[0] in tl.global_code_template+tl.main_code_template+\
	 tl.stdin_template+tl.stdout_template:
		exit('Error: parameter '+p[0]+\
		 ' is not found in code/stdin/stdout')

# check tuple_list
# check if tuple_list is of type list
if not type(tl.tuple_list) == list:
	exit('Error: tuple_list is not of type list')
#check if all sub-lists of tuple_list are of type list
n = check_elements_type(tl.tuple_list,list)
if n != None:
	exit('Error: '+tl.tuple_list[n]+\
	 ' in tuple_list is not of type list')
L = []
# loop through tuple_list and check each group
for t in tl.tuple_list:
	# t must be of type list
	if type(t) != list:
		exit('Error: '+str(t)+' in tuple_list is not of type list')
	# group name is of type string
	if type(t[0]) != str:
		exit('Error: '+str(t[0])+' in '+str(t)+' must be a string')
	# check if there is duplicate group names
	if t[0] in L:
		exit('Error: '+t[0]+' is a duplicated group name')
	else:
		L.append(t[0])
	# loop through the group and check each tuple
	for i in range(1,len(t)):
		# t[i] must be of type list
		if type(t[i]) != list:
			exit('Error: '+str(t[i])+' in '+str(t)+\
			 ' must be of type list')
		# t[i] must the the same length as parameter_list
		if len(t[i]) != len(tl.parameter_list):
			exit('Error: '+str(t[i])+' in group '+str(t[0])+\
			' must be of length '+str(len(tl.parameter_list)))
		# each element in t[i] must be of the declared type or None
		for j in range(len(t[i])):
			if t[i][j] == None:
				continue
			if tl.parameter_list[j][1] == 'int':
				if type(t[i][j]) != int:
					exit('Error: '+str(t[i][j])+\
					 ' in '+str(t[i])+' in group '+t[0]+\
					 ' must be of type int') 
			elif tl.parameter_list[j][1] == 'float':
				if type(t[i][j]) != float:
					exit('Error: '+str(t[i][j])+\
					 ' in '+str(t[i])+' in group '+t[0]+\
					 ' must be of type float') 
			elif tl.parameter_list[j][1] == 'target':
				if type(t[i][j]) != bool:
					exit('Error: '+str(t[i][j])+\
                                         ' in '+str(t[i])+' in group '+t[0]+\
                                         ' must be of type bool')
			elif tl.parameter_list[j][1] == 'assert':
				if type(t[i][j]) != str:
					exit('Error: '+str(t[i][j])+\
                                         ' in '+str(t[i])+' in group '+t[0]+\
                                         ' must be of type string')
