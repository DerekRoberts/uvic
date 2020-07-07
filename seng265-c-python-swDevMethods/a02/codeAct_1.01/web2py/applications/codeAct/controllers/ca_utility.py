import sys
import time
import ca_log_create as log_create

'''
purpose
        initialize a list of nested dict for storing answers
precondition
        quiz is a quiz object
'''
def init_answer_list(quiz):
        d = []
        for i in range(quiz.get_count()):
                d.append({ })
                q = quiz.get_question(i)
                # loop through 'code','stdin','stdout'
                for k in ['code','argvs','stdin','stdout']:
                        d[i][k] = { }
                        # loop through each parameter
                        if k in q.parameter_types.keys():
                                for p in q.parameter_types[k].keys():
                                        d[i][k][p] = None
	return d

'''
purpose
        update answer_list[q_num-1] if request is a submit
precondition
        question is a question object
        0 < q_num < quiz.get_count()
        answer_list is a list of nested dictionary
	vars is a dictionary
'''
def update_answer(question,q_num,answer_list,vars):
        if is_submit(vars):
                # translate answer into the nested dictionary format
                answer = translate_answer(vars,question.parameter_types)
                # update answer_list
                answer_list[q_num-1] = answer

'''
purpose
        put result in message cell with corresponding font color
precondition
        '$MESSAGE' and 'FONTCOLOR' are in code
        question is a question object
        0 < q_num <= quiz.get_count()
        is_logged is a boolean flag
	log_dir is a python string
	login is a python string
	answer is a nested dictionary
	vars is a dictionary 
	is_practice is a boolean
	start_time is an int
'''
def get_result(code,question,q_num,is_logged,log_dir,is_practice,login,answer,
 vars,start_time):
	result = ''
	color = 'black'
        if is_submit(vars):
                mark = question.mark
                # check answer
                if question.check_answer(answer):
                        result = 'Correct'
                        color = 'green'
                else:
                        result = 'Incorrect. Try Again.'
                        color = 'darkred'
                        mark = 0
                # write log if in logged mode
                if is_logged:
                        log_create.write_log(log_dir,login,q_num,question.path,
			 mark,answer,int(time.time())-start_time)
	# display result in parctice mode
	if is_practice:
		code = code.replace('$MESSAGE',result,1)
		code = code.replace('$FONTCOLOR',color)
	else:
		code = code.replace('$MESSAGE','',1)
		code = code.replace('$FONTCOLOR','black')
        return code

'''
purpose
        disable previous button if q_num is 1 and not in standalone
        disable next button if q_num == quiz.get_count() and not in standalone
precondition
        code is a legal python string
        1 <= q_num <= quiz.get_count()
'''
def disable_navi_buttons(code,q_num,quiz):
	if not quiz.standalone:
		if q_num == 1:
			code = code.replace('$DISABLE_PREVIOUS','disabled')
		else:
			code = code.replace('$DISABLE_PREVIOUS','')
		if q_num == quiz.get_count():
			code = code.replace('$DISABLE_NEXT','disabled')
		else:
			code = code.replace('$DISABLE_NEXT','')
        return code

'''
prupose
        add label on the submit button
precondition
        '$BUTTON_LABEL' in code
'''
def add_submit_label(code,is_practice_mode):
        if is_practice_mode:
                code = code.replace('$BUTTON_LABEL','Check answer')
        else:
                code = code.replace('$BUTTON_LABEL','Submit answer')
        return code

'''
purpose
        return a nested dictionaries of answers
precondition
        rv is a valid request.vars object
        parameter_types is a nested dic of parameter name and type pairs
'''
def translate_answer(rv,parameter_types):
        d = {'code':{},'stdin':{},'stdout':{},'argvs':{}}
        for param in rv.keys():
                if 'code' in parameter_types.keys():
                        if param in parameter_types['code'].keys():
                                d['code'][param] = rv[param]
                                continue
                if 'stdin' in parameter_types.keys():
                        if param in parameter_types['stdin'].keys():
                                d['stdin'][param] = rv[param]
                                continue
                if 'stdout' in parameter_types.keys():
                        if param in parameter_types['stdout'].keys():
                                d['stdout'][param] = rv[param]
                                continue
                if 'argvs' in parameter_types.keys():
                        if param in parameter_types['argvs'].keys():
                                d['argvs'][param] = rv[param]
                                continue
        return d
                                                                            
'''
Purpose
        if 'previous' is pressed
                return the current question number - 1
        if 'next' is pressed
                return the current question number + 1
        if 'submit question' is pressed
                return the current question number
        if no button is pressed
                return 1
Precondition
        rv is a valid request.vars object
'''
def get_question_number(rv):
        if 'currqn' in rv and 'previousq' in rv:
                return int(rv['currqn'])-1
        if 'currqn' in rv and 'nextq' in rv:
                return int(rv['currqn'])+1
        if 'currqn' in rv and 'but' in rv:
                return int(rv['currqn'])
        else:
                return 1

'''
purpose
        return true if but is in rv
precondition
        rv is a dictionary
'''
def is_submit(rv):
        return 'but' in rv.keys()

