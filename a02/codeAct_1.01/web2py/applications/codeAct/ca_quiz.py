import sys
import os
import ca_question as question
import ca_quiz_spec as Quiz_spec

class quiz:
	'''
	Purpose
		create necessary questions
	Precondition
		quiz_spec is the file name of a valid quiz specification
	'''
	def __init__(self,quiz_spec):
		# parse the path and import quiz spec
		path = os.path.split(quiz_spec)
		sys.path.append(os.path.abspath(path[0]))
		# import quiz_spec_file
		quiz_spec_file = __import__(path[1])
		# construct quiz_spec_object
		spec = Quiz_spec.Quiz_spec(quiz_spec_file.code_lib_path,
		 quiz_spec_file.question_list,quiz_spec_file.practice_mode,
		 quiz_spec_file.standalone,quiz_spec_file.logged,
		 quiz_spec_file.log_dir)
		self.question_list = []
		self.practice_mode = spec.practice_mode
		self.standalone = spec.standalone
		self.logged = spec.logged
		self.log_dir = spec.log_dir
		for q in spec.get_questions():
			print q
			types = os.path.join(q[1],'types.py')
			exec(open(types,'r').read())
			if game_type == 'Input/output':
				question_obj = question.input_output(q[1],q[0])
			elif game_type == 'Bullseye':
				question_obj = question.bullseye(q[1],q[0])
			elif game_type == 'Liar-liar':
				question_obj = question.liar_liar(q[1],q[0])
			elif game_type == 'Find the failure':
				question_obj = question.find_the_failure(q[1],
				 q[0])
			else:
				print 'Error: wrong question type.'
				sys.exit(-1)
			self.question_list.append(question_obj)
	
	'''
	Purpose
		return the total number of questions
	Precondition
		none
	'''
	def get_count(self):
		return len(self.question_list) 

	'''
	Purpose
		return the ith question
	Precondition
		index > 0 and index < len(self.question_list)
	'''
	def get_question(self,index):
		return self.question_list[index]
		# if index is out of range, should we return the first question
		# instead of throw an exception
