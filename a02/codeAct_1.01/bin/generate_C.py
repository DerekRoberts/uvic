import os

main_beginning = 'int main(int argc, char* argv[])\n{\n'
main_ending = '\treturn 0;\n}\n'
suffix = 'c'
argc = 'argc'
terminator = ';'

'''
purpose
	return the c code for declare name of type
precondition
	type is a legal python string
	name is a legal python string
'''
def declare_var(type,name):
	if type == 'string':
		type = 'char*'
	return type+' '+name+';\n'

'''
purpose
	return the customized assert function
precondition
	targets is a list of strings
'''
def gen_assert(targets):
	count = 0
	s = 'void ca_assert(int n,int expr) {\n\tif (!expr) {\n'
	# if certain target is hit, increase the corresponding flag
	for i in range(len(targets)):
                if targets[i] != '':
                        s += '\t\tif (n == '+str(count)+\
                         ')\n\t\t\tca_assert_'+str(count)+'++;\n'
                        count += 1
        s += '\t}\n}\n'
        return s

'''
purpose
	return the string to compile the binary
precondition
	path is dir contians file
	binary is a string
'''
def gen_compile(path,binary,file):
	return 'gcc -o '+os.path.join(path,binary)+' '+os.path.join(path,file)

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
        s = 'if ('
        for i in range(len(targets)):
                if targets[i]:
                        s += prefix+str(count)+' && '
                        count += 1
        s = s[0:-4]+')\n\treturn 0;\n'
        s += '\telse\n\treturn 1;\n'
        return s

'''
purpose
	create write ./execute $@ in file name in dir path
precondition
	path is a legal python string
'''
def gen_run(path):
	f = open(os.path.join(path,'run.sh'),'w')
	f.write('#!/bin/sh\n./execute $@')
	f.close
	os.system('chmod u+x '+os.path.join(path,'run.sh'))

'''
purpose
	create write ./execute $@ in faulty.sh in dir path
precondition
	path is a legal python string
'''
def gen_faulty(path):
	f = open(os.path.join(path,'faulty.sh'),'w')
	f.write('#!/bin/sh\n./faulty $@')
	f.close
	os.system('chmod u+x '+os.path.join(path,'faulty.sh'))

def set_bullseye(target,code,var):
	return var+' = 1;'

def asign(dst,src):
	return dst+' = '+src+';\n'

def argv_ref(n):
	return 'argv['+str(n)+']'

def stoi(s):
	return 'atoi('+s+')'

def stof(s):
	return 'atof('+s+')'

