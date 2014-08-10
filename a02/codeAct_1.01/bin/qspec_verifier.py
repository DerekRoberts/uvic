#!/usr/bin/python

import sys
import os
import glob

'''
purpose
	print error message and exit
precondition
	msg is a legal python string
'''
def perror(msg):
	print 'Error: '+msg 
	sys.exit(-1)

# check command line arguments
if len(sys.argv) != 2:
	print 'Usage: qspec_verifier.py quiz_spec_file'
	sys.exit(-1)
codelib = sys.argv[1]

# try to import the target spec
path = os.path.split(codelib)
try:
	sys.path.append(path[0])
	spec = __import__(path[1].replace('.py',''))
except:
	perror('can not import '+path[1])

# local variable existence check
L = ['code_lib_path','question_list','practice_mode','standalone','logged',
 'log_dir']
for v in L:
	if not v in dir(spec):
		perror(v+' can not be found')

# code_lib_path check
if type(spec.code_lib_path) != str:
	perror('code_lib_path must be of type string.')
code_lib_path = os.path.join(path[0],spec.code_lib_path)
if not os.path.isdir(code_lib_path):
	perror('code_lib_path: '+spec.code_lib_path+' does not exist.')

# question_list check
if type(spec.question_list) != list:
	perror('question_list must be of type list.')
for t in spec.question_list:
	# each tuple is of type tuple
	if type(t) != tuple:
		perror(t+' must be of type tuple.')
	# mark must be non negative
	if type(t[0]) != int or (type(t[0] == int) and t[0] < 0):
		perror(str(t)+': '+str(t[0])+' must be a non negative int.')
	# count must be non negative
	if type(t[1]) != int or (type(t[1] == int) and t[1] < 0):
                perror(str(t)+': '+str(t[1])+' must be a non negative int.')
 
# practice_mode check
if type(spec.practice_mode) != bool:
	perror('practice_mode must be of type bool.')

# standalone check
if type(spec.standalone) != bool:
	perror('standalone must be of type bool.')
if spec.standalone:
	if len(spec.question_list) > 1 or spec.question_list[0][1] != 1:
		perror('standalone template with multiple questions')

# logged check
if type(spec.logged) != bool:
	perror('logged must be of type bool.')

# log_dir check
if type(spec.log_dir) != str:
	perror('log_dir must be of type string.')
if spec.logged and spec.log_dir == '':
	perror('log_dir cannot be empty.')

# question existance check
for t in spec.question_list:
	for d in t[2]:
		questions = []
		questions += glob.glob(os.path.abspath(os.path.join(\
		 code_lib_path,d)))
		if len(questions) == 0:
			perror('question '+d+' in '+str(t)+' does not exist.')
		for q in questions:
			if not os.path.isdir(q):
				perror('question '+d+' in '+str(t)+\
				 ' does not exist.') 
