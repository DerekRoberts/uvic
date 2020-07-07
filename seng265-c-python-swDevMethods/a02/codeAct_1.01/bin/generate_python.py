import os
import string

main_beginning = ''
main_ending = ''
suffix = 'py'
argc = 'len(sys.argv)'
terminator = ''

'''
purpose
	return the python code for declare name of type
precondition
	type is a legal python string
	name is a legal python string
'''
def declare_var(type,name):
	if type == 'int':
		return name+' = 0\n'
	if type == 'float':
		return name+' = 0.0\n'
	if type == 'string':
		return name+" = ''\n"

'''
purpose
	return the customized assert function
precondition
	targets is a list of strings
'''
def gen_assert(targets):
	count = 0
	s = 'def ca_assert(n,expr):\n\tif not expr:\n'
	# if certain target is hit, increase the corresponding flag
	for i in range(len(targets)):
		if targets[i] != '':
			s += '\t\tglobal ca_assert_'+str(count)+'\n'
			s += '\t\tif n == '+str(count)+':\n'+\
			 '\t\t\tca_assert_'+str(count)+' += 1\n'
			count += 1
	return s

'''
purpose
	return the string to compile the binary
precondition
	path is dir contians file
	binary is a string
'''
def gen_compile(path,binary,file):
	return ''

'''
purpose
	return the code which checks whether all targets are hit
precondition
	targets is a list of strings
	game_type is a legal python string
'''
def gen_check_targets(targets,game_type):
	# decide prefix
	prefix = 'ca_target_'
	if game_type == 'liar_liar':
		prefix = 'ca_assert_'

	# check target_0 ... target_N
	count = 0
	s = 'if '
	for i in range(len(targets)):
		if targets[i]:
			s += prefix+str(count)+' and '
			count += 1
	s = s[0:-4]+':\n\tsys.exit(0)\n'
	s += 'else:\n\tsys.exit(1)\n'
	return s

'''
purpose
	generate run.sh in dir path
precondition
	path is a legal python string
'''
def gen_run(path):
	f = open(os.path.join(path,'run.sh'),'w')
	f.write('#!/bin/sh\npython execute.py $@')
	f.close
	os.system('chmod u+x '+os.path.join(path,'run.sh'))

'''
purpose
	generate file faulty.sh in dir path
precondition
	path is a legal python string
'''
def gen_faulty(path):
	f = open(os.path.join(path,'faulty.sh'),'w')
	f.write('#!/bin/sh\npython faulty.py $@')
	f.close
	os.system('chmod u+x '+os.path.join(path,'faulty.sh'))
'''
purpose
	return the code set bullseye target code
precondition
	target is a legal python string
	code is a legal python string
	target in code
	var is a legal python string
'''
def set_bullseye(target,code,var):
	# find the line that contains the target
	target_line = ''
	lines = code.split('\n')
	for line in lines:
		if target in line:
			target_line = line
			break
	# determine the level of indentation
	index = target_line.find(target)
	prefix = target_line[:index] 
	# replace the target with set flag code
	return 'global '+var+'\n'+prefix+var+' = 1'

def asign(dst,src):
	return dst+' = '+src+'\n'

def argv_ref(n):
	return 'sys.argv['+str(n)+']'

def stoi(s):
	return 'int('+s+')'

def stof(s):
	return 'float('+s+')'
