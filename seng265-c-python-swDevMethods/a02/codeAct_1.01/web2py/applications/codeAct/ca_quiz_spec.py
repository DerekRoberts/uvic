import random
import glob
import os

class Quiz_spec:
	def __init__(self,code_lib_path = None,question_list = None,
	 is_practice = None,is_standalone = None,
	 is_logged = None,log_dir = None):
		self.practice_mode = is_practice
		self.standalone = is_standalone
		self.question_list = question_list
		self.code_lib_path = code_lib_path
		self.logged = is_logged
		self.log_dir = log_dir

	def get_questions(self):
		questions = []
		for question in self.question_list:
			# select from question list: randomly selection/order
			mark = question[0]
			count = question[1]
			sources = []
			for p in question[2]:
				sources += glob.glob(os.path.abspath(\
				 os.path.join(self.code_lib_path,p)))
				print os.path.abspath(\
                                 os.path.join(self.code_lib_path,p))
			# trim count to avoid random.sample exception
			if count > len(sources):
				count = len(sources)
			L = random.sample(sources,count)
			for source in L:
				questions.append((mark,source))
		return questions
