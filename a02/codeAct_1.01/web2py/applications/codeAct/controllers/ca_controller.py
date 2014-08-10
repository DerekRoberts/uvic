import sys
import time
import os

import ca_quiz as Quiz
import ca_log_create as log_create

sys.path.append('applications/codeAct/controllers')
import ca_utility as utility

sys.path.append('applications/codeAct/views/ca_controller')
import ca_question_HTML as question_HTML

SERVERNAME = 'cdoku'

def ca_login():
	if not 'username' in request.vars.keys(): # not a submission
		return dict(msg='')
	else: # request is a submission
		# get user list
		user_list = 'allowed_users.txt'
		# check if student is registered in SENG265
		users = open(os.path.join('../users/',user_list),
		 'r').read().split('\n')
		if not request.vars['username'] in users:
			return dict(
			 msg = 'Not registered in the current session.')
		# authenticate username and password
		if os.uname()[1] == SERVERNAME:
			seng_auth = __import__('ca_seng_auth')
			if seng_auth.authenticate(request.vars['username'],
			 request.vars['password']) != 0:
				return dict(msg='Wrong username or password.')
		session.login = request.vars['username']
		redirect(URL(r=request, f='quiz'))

def quiz():
	# the beginning of the quiz, create quiz and init answer_list
	if 'quiz_spec' in request.vars.keys():
		session['quiz'] = Quiz.quiz(request.vars['quiz_spec'])
		session['answer_list'] = utility.init_answer_list(
		 session['quiz'])
		# initialize question_timer
		session['question_timer'] = int(time.time())
		# reset the login
		if 'login' in session.keys():
			del session['login']

	# if quiz does not exist, redirect to home page
	if not 'quiz' in session.keys():
		redirect(URL(r=request, f='ca_index'))

	# check login in marked mode
	if not 'login' in session.keys():
		if not session['quiz'].practice_mode: 
			redirect(URL(r=request, f='ca_login'))
		else:
			session['login'] = ''

	# init log_dir and log
	if session['quiz'].logged:
		log_create.create_log(session['quiz'].log_dir,
		 session['login'])

	# generate HTML code
	question_number = utility.get_question_number(request.vars)
	question = session['quiz'].get_question(question_number-1)
	utility.update_answer(question,question_number,session['answer_list'],
	 request.vars)
	code = question_HTML.generate_html(session['quiz'],question_number,
	 session['answer_list'][question_number-1])
	code = utility.get_result(code,question,question_number,
	 session['quiz'].logged,session['quiz'].log_dir,
	 session['quiz'].practice_mode,session['login'],
	 session['answer_list'][question_number-1],
	 request.vars,session['question_timer'])
	code = utility.add_submit_label(code,session['quiz'].practice_mode)
	code = utility.disable_navi_buttons(code,question_number,
	 session['quiz'])

	# update question timer
	session['question_timer'] = int(time.time())
	return code
