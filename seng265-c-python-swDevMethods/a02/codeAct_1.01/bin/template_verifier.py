#!/usr/bin/python

import sys
import os
import string

'''
purpose
	print s and exit the program
precondition
	s is a legal python string
'''
def exit(s):
	print s
	sys.exit()

# check if the command line arguments is of the correct length
if len(sys.argv) != 2:
        print 'Usange:',sys.argv[0],'<module_name>'
	sys.exit(-1)

'''
purpose
	exit the program with a error message if 
		code is not of type string
		code contains ca_
precondition
	name is legal python of the name of the template being checked
'''
def check_code(code,name):
	if type(code) != str:
		exit(name+' is not of type string')
	if 'ca_' in code:
		exit('ca_ is found in '+name)

# check if the module can be imported
try:
	path = os.path.split(sys.argv[1])
	sys.path.append(path[0])
        template = __import__(path[1].replace('.py',''))
except ImportError:
	exit(sys.argv[1]+' can not be imported.')

# check necessary variables
for v in ['game_type','parameter_list','tuple_list']:
	if not v in dir(template):
		exit('Variable '+v+' can not be found in '+sys.argv[1])
if template.game_type == 'input_output':
	for v in ['global_code_template','main_code_template','stdin_template',
	 'stdout_template','argv_template','source_language']:
		if not v in dir(template):
			exit('Variable '+v+' can not be found in '+sys.argv[1])
elif template.game_type in ['bullseye','liar_liar','find_the_failure']:
	for v in ['global_code_template','main_code_template','stdin_template',
	 'argv_template','source_language']:
		if not v in dir(template):
			exit('Variable '+v+' can not be found in '+sys.argv[1])

# game_type must be of type string
if type(template.game_type) != str:
	exit('Variable game_type is not of type string.')
# does game_type have legal value
if not template.game_type in ['input_output','bullseye','liar_liar',
 'find_the_failure']:
	exit('unknown game type')

# check global_code
check_code(template.global_code_template,'global_code_template')
# loop through each line
line_number = 1
for line in template.global_code_template.split('\n'):
	prefix = ''
	# the first whitespace character must be '/t'
	for n in range(len(line)):
		if line[n] in string.whitespace:
			if line[n] != '\t':
				exit("global_code line "+str(line_number)+\
				 ': '+line+\
				 "\nexpecting a '\\t' after line prefix.")
			break;
	# only 'x','X','d' is allowed to be in prefix
	prefix = line[:n]
	for c in prefix:
		if not c in 'dXx':
			exit('global_code line '+str(line_number)+': '+line+\
			 '\nwrong prefix')
	line_number += 1

# check main_code
check_code(template.main_code_template,'main_code_template')
# loop through each line
line_number = 1
for line in template.main_code_template.split('\n'):
	prefix = ''
	# the first whitespace character must be '/t'
	for i in range(len(line)):
		if line[n] in string.whitespace:
			if line[n] != '\t':
				exit("main_code_template line "+\
				 str(line_number)+': '+line+\
				 "\nexpecting a '\\t' after line prefix.")
			break;
	# only 'x','X','d' is allowed to be in prefix
	prefix = line[:n]
	for c in prefix:
		if not c in 'dXx':
			exit('main_code line '+str(line_number)+': '+line+\
			 '\nwrong prefix')
	line_number += 1

# check argv_template
if type(template.argv_template) != str:
	exit('argv_template is not of type string')

# check stdin_template
if type(template.stdin_template) != str:
	exit('stdin_template is not of type string')

# check stdout_template 
if type(template.stdin_template) != str:
	exit('stdout_template is not of type string')
