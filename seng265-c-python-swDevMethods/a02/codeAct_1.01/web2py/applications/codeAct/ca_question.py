import os
import subprocess
import re
import datetime

class question:
	path = ''
	type = ''
	code_template = ''
	stdin_template = ''
	stdout_template = ''
	argvs = ''
	mark = 0
	parameter_types = {}

	'''
	Purpose
		initialize all member variables (super class and sub class)
	Precondition
		path is a valid question directory
	'''
	def __init__(self,path,mark):
		self.path = path
		self.mark = mark

		# load game type and parameter types 
		types = os.path.join(path,'types.py')
		exec(open(types,'r').read())
		self.type = game_type
		self.parameter_types = parameter_types

		# load code template
		code = os.path.join(path,'display.txt')
		self.code_template = open(code,'r').read()

		# load stdin or stdout if it exists
		stdin = os.path.join(path,'stdin.txt')
		if os.path.exists(stdin):
			self.stdin_template = open(stdin,'r').read()
		stdout = os.path.join(path,'stdout.txt')
		if os.path.exists(stdout):
			self.stdout_template = open(stdout,'r').read()

		# load argvs.txt if it exists
		argvs = os.path.join(path,'argvs.txt')
		if os.path.exists(argvs):
			self.argvs = open(argvs,'r').read()

	'''
	purpose
		if all values in answers are the correct type
			return true
		else
			return false
	precondition
		answer is a nested dictionary contains name-value pairs
		type is a nested dictionary contains name-type pairs
	'''
	def validate_values(self,answer,parameter_types):
		for t in answer.keys():
			for p in answer[t].keys():
				if parameter_types[t][p] == 'int':
					try:
						int(answer[t][p])
					except ValueError:
						return False
				elif parameter_types[t][p] == 'float':
					try:
						float(answer[t][p])
					except ValueError:
						return False
		return True

class input_output(question):
	def lines_in_list(self,s):	
		s = s.split('\n')
		while len(s) > 0 and s[-1] == '':
			s = s[:-1]
		return s

	'''
	Purpose
		run question executable
		if student's answer matches actual output
			return true
		else
			return false
	Precondition
		answers is a dictionary contains three sub-dictionaries
	'''
	def check_answer(self, answer):
		# type validation
		if not question.validate_values(self,answer,
		self.parameter_types):
			return False

		# check command line arguments
		if self.argvs != '':
			L = self.argvs.split(' \t')
			for arg in L:
				if not re.match('^[0-9a-zA-Z-_\$]+$',arg):
					print arg,'did not pass argv test'
					return False

		# construct command line arguments
		com_line_args = self.argvs
		for k in answer['argvs'].keys():
			com_line_args = com_line_args.replace(k,
			 answer['argvs'][k])
		L = answer['code'].keys()
		L.sort()
		for arg in L:
			com_line_args += ' ' + answer['code'][arg]
		
		# construct stdin
		stdin = self.stdin_template
		for param in answer['stdin'].keys():
			stdin = stdin.replace(param,answer['stdin'][param],1)

		# construct student's output
		actual_output = self.stdout_template
		for param in answer['stdout'].keys():
			actual_output = actual_output.replace(param,
			answer['stdout'][param],1)
		
		# get the actual output
		expected_output = ''
		process = subprocess.Popen(
		 'cd '+self.path+';./run.sh '+com_line_args,
		 stdin = subprocess.PIPE,stdout = subprocess.PIPE,shell = True)
		expected_output = process.communicate(stdin)[0]
		process.wait()	

		# compare actual output and student output
		actual_output = self.lines_in_list(actual_output)
		expected_output = self.lines_in_list(expected_output)
		if len(actual_output) != len(expected_output):
			return False
		for i in range(len(actual_output)):
			if not actual_output[i] == expected_output[i]:
				return False
		return True

class bullseye(question):
	'''
	Purpose
		run question executable
		if student's answer matches actual output
			return true
		else
			return false
	Precondition
		answers is a dictionary contains three sub-dictionaries
	'''
	def check_answer(self, answer):
		# type validation
		if not question.validate_values(self,answer,
		self.parameter_types):
			return False
		
		# check command line arguments
		if self.argvs != '':
			L = self.argvs.split(' \t')
			for arg in L:
				if not re.match('^[0-9a-zA-Z-_]+$',arg):
					return False

		# construct command line arguments
		com_line_args = self.argvs
		for k in answer['argvs'].keys():
			com_line_args = com_line_args.replace(k,
			 answer['argvs'][k])
		L = answer['code'].keys()
		L.sort()
		for arg in L:
			com_line_args += ' ' + answer['code'][arg]

		# construct stdin
		stdin = self.stdin_template
		for param in answer['stdin'].keys():
			stdin = stdin.replace(param,answer['stdin'][param],1)
		
		# run executable and check the return value
		process = subprocess.Popen(
		 'cd '+self.path+';./run.sh '+com_line_args,
			stdin = subprocess.PIPE,
			stdout = subprocess.PIPE,
			shell = True)
		output = process.communicate(stdin)[0]
		return_code = process.wait()

		return return_code == 0	

class liar_liar(question):
	'''
	Purpose
		run question executable
		if student's answer matches actual output
			return true
		else
			return false
	Precondition
		answers is a dictionary contains three sub-dictionaries
	'''
	def check_answer(self, answer):
		# type validation
		if not question.validate_values(self,answer,
		self.parameter_types):
			return False
		
		# check command line arguments
		if self.argvs != '':
			L = self.argvs.split(' \t')
			for arg in L:
				if not re.match('^[0-9a-zA-Z-_]+$',arg):
					return False

		# construct command line arguments
		com_line_args = self.argvs
		for k in answer['argvs'].keys():
			com_line_args = com_line_args.replace(k,
			 answer['argvs'][k])
		L = answer['code'].keys()
		L.sort()
		for arg in L:
			com_line_args += ' ' + answer['code'][arg]

		# construct stdin
		stdin = self.stdin_template
		for param in answer['stdin'].keys():
			stdin = stdin.replace(param,answer['stdin'][param],1)
		
		# run executable and check the return value
		process = subprocess.Popen(
		 'cd '+self.path+';./run.sh '+com_line_args,
			stdin = subprocess.PIPE,
			stdout = subprocess.PIPE,
			shell = True)
		output = process.communicate(stdin)[0]
		return_code = process.wait()
		return return_code == 0	

class find_the_failure(question):
	'''
	Purpose
		run question executable
		if student's answer matches actual output
			return true
		else
			return false
	Precondition
		answers is a dictionary contains three sub-dictionaries
	'''
	def check_answer(self, answer):
		# type validation
		if not question.validate_values(self,answer,
		self.parameter_types):
			return False

		# check command line arguments
		if self.argvs != '':
			L = self.argvs.split(' \t')
			for arg in L:
				if not re.match('^[0-9a-zA-Z-_]+$',arg):
					return False

		# construct command line arguments
		com_line_args = self.argvs
		for k in answer['argvs'].keys():
			com_line_args = com_line_args.replace(k,
			 answer['argvs'][k])
		L = answer['code'].keys()
		L.sort()
		for arg in L:
			com_line_args += ' ' + answer['code'][arg]
		
		# construct stdin
		stdin = self.stdin_template
		for param in answer['stdin'].keys():
			stdin = stdin.replace(param,answer['stdin'][param],1)

		# get student's output
		actual_output = ''
		process = subprocess.Popen(
		 'cd '+self.path+';./faulty.sh '+com_line_args,
		 stdin = subprocess.PIPE,stdout = subprocess.PIPE,shell = True)
		actual_output = process.communicate(stdin)[0]
		process.wait()		

		# get the correct output
		correct_output = ''
		process = subprocess.Popen(
		 'cd '+self.path+';./run.sh '+com_line_args,
		 stdin = subprocess.PIPE,stdout = subprocess.PIPE,shell = True)
		correct_output = process.communicate(stdin)[0]
		process.wait()		

		return actual_output != correct_output
