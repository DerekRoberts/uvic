'''
purpose
	return a pair of parameters dictionary and tuples dictionary
preconditions
	tl is a question_template object
		tuple_list: a nested list contains values of parameters 
		parameter_list: a nested list of parameters,type sub-lists
		game_type: a legal python string
		global_code: a legal python string
		main_code: a legal python string
		stdin is: a legal python string
		stdout is: a legal python string (shell,IO,FF) or None (BE,LL)
'''
def convert_tuples(tl):
 	# create the nested dictionaries format of parameters
	parameters = { }
	for p in tl.parameter_list:
		# shell parameters are always in code
		if p[1] == None:
			if not 'code' in parameters.keys():
				parameters['code'] = { }
			parameters['code'][p[0]] = p[1]
			continue
		# ignore target or assert place holders
		if p[1] == 'target' or p[1] == 'assert':
			continue
		# parameters in code
		if p[0] in tl.global_code_template or p[0] in tl.main_code_template:
			if not 'code' in parameters.keys():
				parameters['code'] = { }
			parameters['code'][p[0]] = p[1]
		# parameters in stdin
		if p[0] in tl.stdin_template:
			if not 'stdin' in parameters.keys():
				parameters['stdin'] = { }
			parameters['stdin'][p[0]] = p[1]
		# parameters in stdout
		if 'stdout_template' in dir(tl)  and p[0] in tl.stdout_template:
			if not 'stdout' in parameters.keys():
				parameters['stdout'] = { }
			parameters['stdout'][p[0]] = p[1]
		# parameters in argvs
		if 'argv_template' in dir(tl)  and p[0] in tl.argv_template:
			if not 'argvs' in parameters.keys():
				parameters['argvs'] = { }
			parameters['argvs'][p[0]] = p[1]

        tuple_dic_list = [ ]
        # loop through each group
        for i in range(len(tl.tuple_list)):
                group = tl.tuple_list[i]
                # loop through each tuple in a group
                for j in range(1,len(group)):
			dic = { }
			tuple = group[j]
			# create target and target_value in dic
			if tl.game_type in ['bullseye','liar_liar']:
				dic['targets'] = [ ]
				dic['target_values'] = [ ]
				for k in range(len(tl.parameter_list)):
					p = tl.parameter_list[k]
					if p[1] in ['target','assert']:
						dic['targets'].append(p[0])
						dic['target_values'].\
						 append(tuple[k])

			# loop through each parameter value
			for k in parameters.keys():
				dic[k] = { }
                        for k in range(len(tuple)):
				p = tl.parameter_list[k]
				for t in parameters.keys():
					if parameters[t].has_key(p[0]):
						dic[t][p[0]] = tuple[k]
			dic['prefix'] = group[0]
			tuple_dic_list.append(dic)
        return (parameters,tuple_dic_list)
