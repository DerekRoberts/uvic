#!/usr/bin/python

import string
import os
import sys
import tuple_converter
import question_template

'''
purpose
	create types.py
	write game_type and parameters into types.py
precondition
	path is a directory with write access
	game_type is a legal python string
	parameters is a dictionary of dictionaries of parameter-type pairs
'''
def gen_types(path,game_type,parameters):
	output = open(os.path.join(path,'types.py'),'w')
	if game_type == 'input_output':
		game_type_string = 'Input/output'
	elif game_type == 'bullseye':
		game_type_string = 'Bullseye'
	elif game_type == 'liar_liar':
		game_type_string = 'Liar-liar'
	elif game_type == 'find_the_failure':
		game_type_string = 'Find the failure'
	elif game_type == 'shell':
		game_type_string = 'Shell'
	output.write("game_type = '"+game_type_string+"'\n")
	output.write('parameter_types = '+str(parameters)+'\n')
	output.close()

'''
purpose
	replace parameters in template(stdin or stdout) and write to fp 
precondition
	fp is a file object opened with option 'w'
	template is a legal python string
	parameters is a dictionary of dictionaries of parameter-value pairs
	key is a legal python string
'''
def gen_template(fp,template,parameters,key):
	if key in parameters.keys():
		for param in parameters[key].keys():
			if parameters[key][param] != None:
				template = template.replace(param,
					str(parameters[key][param]))
	fp.write(template)
            
'''
purpose
	write global variable declaration in run and faulty(in find_the_failure)
	substitute each parameter in template with values in parameters
	substitute each parameter in template with corresponding global variable
	write lines marked with 'd' to display
	write lines marked with 'x' to run
	write lines markes with 'X' to faulty in game find_the_failure
precondition
	template is a legal python string
	display and run are file objects created with option 'w'
	game_type is a legal python string
	values is a dictionary of dictionaries of parameter-value pairs 
	 of code template
	types is a dictionary of dictionaries of parameter-type pairs
	values and types are containing the same set of keys
	faulty is a file object created with option 'w' if game_type is 
		find_the_failure otherwise is None
	targets is a list of strings
	target_values is a list of booleans(bullseye) or strings(liar-liar)
	src_lan is an imported python module
'''
def parse_global(template,display,run,faulty,values,types,game_type,
		 targets,target_values,src_lan):
	# declare a global variable per code template parameter
	global_vars = ''
	if 'code' in types.keys():
		arg_count = 1
		L = types['code'].keys()
		L.sort()
		for k in L:
			if values['code'][k] != None:
				continue
			global_vars += src_lan.declare_var(types['code'][k],
			 'ca_argv_'+str(arg_count))
			arg_count += 1

	# declare a global variable per target
	if game_type == 'bullseye':
		arg_count = 0
		for i in range(len(targets)):
			if target_values[i]:
				global_vars += src_lan.declare_var('int',
				 'ca_target_'+str(arg_count))
				arg_count += 1

	# declare a global variable per assert
	if game_type == 'liar_liar':
		arg_count = 0
		for i in range(len(targets)):
			if target_values[i] != '':
				global_vars += src_lan.declare_var('int', 
				 'ca_assert_'+str(arg_count))
				arg_count += 1
	run.write(global_vars)
	if game_type == 'find_the_failure':
		faulty.write(global_vars)

	# write the implementation of ca_assert()
	if game_type == 'liar_liar':
		run.write(src_lan.gen_assert(target_values))


	for line in template.split('\n'):
		# find the line prefix
		prefix = ''
		for n in range(len(line)):
			if line[n] in string.whitespace:
				prefix = line[:n]
				break
		if prefix == '':
			prefix = line
		line = line[n+1:]
		# substitute values with values
		if 'code' in values.keys():
			for k in values['code'].keys():
				line = line.replace(k,
				 str(values['code'][k]))

		# write the line to display
		if 'd' in prefix:
			if game_type == 'bullseye':
				display.write(set_bullseye(line,targets,
					target_values,True,src_lan)+'\n')
			elif game_type == 'liar_liar':
				display.write(set_assert(line,targets,
					target_values,True,src_lan)+'\n')
			else:
				display.write(line+'\n')

		# substitue parameters with global variables
		if 'code' in types.keys():
			arg_count = 1
			for k in L:
				if values['code'][k] == None:
					continue
				line = line.replace(k,
				 'ca_argv_'+str(arg_count))
				arg_count += 1
		
		# set targets
		if game_type == 'bullseye': 
			line = set_bullseye(line,targets,target_values,False,
			 src_lan)
		if game_type == 'liar_liar':
			line = set_assert(line,targets,target_values,False,
			 src_lan)

		# write line to run and faulty
		if 'x' in prefix:
			run.write(line+'\n')
		if 'X' in prefix and game_type == 'find_the_failure':
			faulty.write(line+'\n')
        
'''
purpose
	replace parameters with their values and write to display
	replace parameters with argv[N] and write to run
	replace parameters with argv[N] and write to faulty in game find the 
		failure
	add starting and end of main
precondition
	template is a legal python string
	display and run are file objects opened with option 'w'
	faulty is a file object created with option 'w' if game_type is 
		find_the_failure otherwise is None
	values is a dictionary of dictionaries of parameter-value pairs 
	 of code template
	types is a dictionary of dictionaries of parameter-type pairs
	values and types are containing the same set of keys
	targets is a list of strings
	target_values is a list of booleans(bullseye) or strings(liar-liar)
'''
def parse_main(template,display,run,faulty,values,types,game_type,targets,
 target_values,src_lan):
	# write the beginning of main
	display.write(src_lan.main_beginning)
	run.write(src_lan.main_beginning)
	if game_type == 'find_the_failure':
		faulty.write(src_lan.main_beginning)

	main = template
	M = {}
	num_code_params = 0
	if 'code' in values.keys():
		num_code_params = len(values['code'].keys())
		# replace parameters with their values
		for param in values['code'].keys():
			if values['code'][param] != None:
				num_code_params -= 1
				main = main.replace(param,
				 str(values['code'][param]))

		# assign the value of argv[N] to corresponding global variable
		arg_count = 0
		L = types['code'].keys()
		L.sort()
		s = ''
		for k in L:
			if values['code'][k] != None:
				continue
			if types['code'][k] == 'int':
				s += src_lan.asign('ca_argv_'+str(arg_count+1),
				 src_lan.stoi(src_lan.argv_ref(src_lan.argc+'-'\
				 +str(num_code_params)+'+'+\
				 str(arg_count))))
			elif types['code'][k] == 'float':
				s += src_lan.asign('ca_argv_'+str(arg_count+1),
				 src_lan.stof(src_lan.argv_ref(src_lan.argc+'-'\
				 +str(num_code_params)+'+'+\
				 str(arg_count))))
			else:
				s += src_lan.asign('ca_argv_'+str(arg_count+1),
				 src_lan.argv_ref(src_lan.argc+'-'+\
				 str(num_code_params)+'+'+\
				 str(arg_count)))
			M[k] = 'ca_argv_'+str(arg_count+1)
			arg_count += 1
			
		run.write(s)
		if game_type == 'find_the_failure':
			faulty.write(s)

	# loop through each line
	for line in main.split('\n'):
		# find the line prefix
		prefix = ''
		for n in range(len(line)):
			if line[n] in string.whitespace:
				prefix = line[:n]
				break
		if prefix == '':
			prefix = line
		line = line[n+1:]

		if 'd' in prefix:
			if game_type == 'bullseye':
				display.write(set_bullseye(line,targets,
					target_values,True,src_lan)+'\n')
			elif game_type == 'liar_liar':
				display.write(set_assert(line,targets,
					target_values,True,src_lan)+'\n')
			else:
				display.write(line+'\n')
		# substitute parameter with global variable
		if 'code' in values.keys():
			for k in M.keys():
				line = line.replace(k,M[k])
		# set targets
		if game_type == 'bullseye': 
			line = set_bullseye(line,targets,target_values,False,
			 src_lan)
		if game_type == 'liar_liar':
			line = set_assert(line,targets,target_values,False,
			 src_lan)

		if 'x' in prefix:
			run.write(line+'\n')
		if 'X' in prefix:
			faulty.write(line+'\n')

	# add flag checking code for bullseye
	if game_type == 'bullseye' or game_type == 'liar_liar':
		run.write(src_lan.gen_check_targets(target_values,game_type))

	# write return 0 
	display.write(src_lan.main_ending)
	run.write(src_lan.main_ending)
	if game_type == 'find_the_failure':
		faulty.write(src_lan.main_ending)

'''
purpose
	return a copy of code with
		is_display True:
			target_values[N] is True
				targets[N] change to 'ca_highlight'
		is_display False:
			target_values[N] is True
				targets[N] change to 'ca_target_N++'
		target_values[N] is False
			targets[N] change to ''
precondition
	code is a legal python strings
	targets is a list of strings
	target_values is a list of booleans
	targets and target_values are in the same length
	is_display is a boolean
	src_lan is an imported pythong module
'''
def set_bullseye(code,targets,target_values,is_display,src_lan):
	count = 0
	for i in range(len(targets)):
		if target_values[i]:
			if is_display:
				code = code.replace(targets[i],'ca_highlight')
			else:
				code = code.replace(targets[i],
				 src_lan.set_bullseye(targets[i],code,
				 'ca_target_'+str(count)))
				count += 1
		else:
			code = code.replace(targets[i],'')
	return code

'''
purpose
	return a copy of code with
		is_display True:
			target_values[N] is not None
				targets[N] change to 
				'ca_highlightassert(target_value[N]);'
		is_display False:
			target_values[N] is not None
				targets[N] change to 'ca_assert(N,expr);'
		target_values[N] is ''
			targets[N] change to ''
precondition
	code is a legal python strings
	targets is a list of strings
	target_values is a list of strings
	targets and target_values are in the same length
	is_display is a boolean
	src_lan is an imported pythong module
'''
def set_assert(code,targets,target_values,is_display,src_lan):
	count = 0
	for i in range(len(targets)):
		if target_values[i] != '':
			if is_display:
				code = code.replace(targets[i],
					'ca_highlightassert('+\
					 target_values[i]+');')
			else:
				code = code.replace(targets[i],
				 'ca_assert('+str(count)+','+target_values[i]+\
				 ')'+src_lan.terminator)
				count += 1
		else:
			code = code.replace(targets[i],'')
	return code

'''
purpose
	create question directory, types.py, display.txt, execute and stdin.txt
precondition
	name is a legal python string of question dir name
	tp is a imported question template
	tuple is a dictionary of dictionaries of parameter-value pairs
'''
def create_question(name,tp,tuple,parameters):
	# load source_language module
	module = 'generate_'+tp.source_language
	src_lan = __import__(module)

	#create question dir if not exists
	if not os.path.exists(name):
                os.mkdir(name)

	# generate types.py
        gen_types(name,tp.game_type,parameters)

	# generate display.txt and execute and faulty
	faulty = None
	targets = None
	target_values = None
	suffix = src_lan.suffix
        display = open(os.path.join(name,'display.txt'),'w')
	run = open(os.path.join(name,'execute.'+suffix),'w')
	if tp.game_type == 'find_the_failure':
		faulty = open(os.path.join(name,'faulty.'+suffix),'w')
	if tp.game_type == 'bullseye' or tp.game_type == 'liar_liar':
		targets = tuple['targets']
		target_values = tuple['target_values']
        parse_global(tp.global_code_template,display,run,faulty,tuple,
	 parameters,tp.game_type,targets,target_values,src_lan)
        parse_main(tp.main_code_template,display,run,faulty,tuple,
	 parameters,tp.game_type,targets,target_values,src_lan)
        display.close()
        run.close()
	# compile the code
        os.system(src_lan.gen_compile(name,'execute','execute.'+suffix))
	# create run.sh
	src_lan.gen_run(name)
	# generate faulty
	if tp.game_type == 'find_the_failure':
		faulty.close()
		os.system(src_lan.gen_compile(name,'faulty','faulty.'+suffix))
		# create faulty.sh
		src_lan.gen_faulty(name)

	# generate argvs.txt
	if 'argv_template' in dir(tp):
		argvs = open(os.path.join(name,'argvs.txt'),'w')
		gen_template(argvs,tp.argv_template,tuple,'argvs')
		argvs.close()

	# generate stdin.txt
	stdin = open(os.path.join(name,'stdin.txt'),'w')
	gen_template(stdin,tp.stdin_template,tuple,'stdin')
	stdin.close()

	# generate stdout.txt
	if tp.game_type == 'input_output':
		stdout = open(os.path.join(name,'stdout.txt'),'w')
		gen_template(stdout,tp.stdout_template,tuple,'stdout')
		stdout.close()


# check the number of command line arguments
if len(sys.argv) != 3 and len(sys.argv) != 4:
	print 'Usage: gen_lib.py <template> <codelib> [-cpp]'
	print 'gen_lib will create questions based on template and put them\
	 into codelib'
	sys.exit()

# create dir codelib if not exists
base_dir = os.path.abspath(sys.argv[2])
if not os.path.exists(base_dir):
	os.mkdir(base_dir)

# load question template as a python module
t = os.path.split(sys.argv[1])
sys.path.append(t[0])
qtemp_file = __import__(t[1].replace('.py',''))
# construct question_template_object
tl = question_template.Question_template(qtemp_file.game_type,
 qtemp_file.source_language,qtemp_file.parameter_list,qtemp_file.tuple_list,
 qtemp_file.global_code_template,qtemp_file.main_code_template,
 qtemp_file.argv_template,qtemp_file.stdin_template,qtemp_file.stdout_template)
(parameters,tuples) = tuple_converter.convert_tuples(tl)

question_prefix = ''
# create each question
for tuple in tuples:
	# determine the question directory name
	if tuple['prefix'] != question_prefix:
		question_prefix = tuple['prefix']
		i = 0
	else:
		i += 1
	question_dir = os.path.join(base_dir,question_prefix+str(i))
	# create question files
	create_question(question_dir,tl,tuple,parameters)
	print 'finished creating '+question_dir
