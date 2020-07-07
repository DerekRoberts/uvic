class Question_template:
	def __init__(self,
	 game_type = None,source_language = None,
	 parameter_list = None,tuple_list = None,
	 global_code_template = None,main_code_template = None,
	 argv_template = None,stdin_template = None,stdout_template = None):
		self.game_type = game_type
		self.source_language = source_language
		self.parameter_list = parameter_list
		self.tuple_list = tuple_list
		self.global_code_template = global_code_template
		self.main_code_template = main_code_template
		self.argv_template = argv_template
		self.stdin_template = stdin_template
		self.stdout_template = stdout_template
