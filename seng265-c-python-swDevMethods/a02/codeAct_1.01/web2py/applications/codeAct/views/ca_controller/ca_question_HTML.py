import string

'''
purpose
        return the html code of a textbox
precondition
        none
'''
def get_textbox(name, value):
        if value == None:
                value = ''
        return '<input type="text" name="'+name+'" value="'+value+'" \
        style="height:24px;width:40px">'

'''
purpose
        remove string 'ca_highlight' 
        highlight the rest of the line 
precondition
        template is a non-empty string
'''
def highlight_target(code):
        start = code.find('ca_highlight')
        while start != -1:
		# find the place holder and target statement
                end = code.find(';',start)
		if end == -1:
			end = code.find('\n',start+len('ca_highlight')+1)
		holder = code[start:end] # ca_highlight and target statement

		# find the target statement
		start += len('ca_highlight') # go to the next line
		while code[start] in string.whitespace:
			start += 1
                target_line = code[start:end]

		# replace the place holder and the target statement
		code = code.replace(holder,'<font color="blue"><b>'+\
		 target_line+'</b></font>',1)
                start = code.find('ca_highlight')
        return code

'''
purpose
        replace place holders in template by textbox
precondition
        template is a non-empty string
        answer is a dictionary of parameter-value pairs
'''
def gen_display(template,answer):
        for p in answer.keys():
                template = template.replace(p,get_textbox(p,answer[p]))
        return template

'''
Purpose
        return the html code of a question
Precondition
        quiz is a question object
	0 < q_num <= quiz.get_count
        answer is a nested dictionary of three sub-dictionaries
'''
def generate_html(quiz,q_num,answer):
        # get question object from quiz
        question = quiz.get_question(q_num-1)

        stdin_stdout_height = 132
	is_standalone = quiz.standalone
        # code cell
        html = html_base.replace('$CODE',
	 gen_display(highlight_target(question.code_template),answer['code']))
        # game type cell
        html = html.replace('$GAME_TYPE',question.type)
        html = html.replace('$BGCOLOR',type_color_dic[question.type])
        # submit button cell
        html = html.replace('$SUBMIT_BUTTON',submit_button_html)
        # argv label and cell
        html = html.replace('$COMMAND_LINE_ARGS',command_line_args)
        html = html.replace('$ARGVS',
	 gen_display(question.argvs,answer['argvs']))
        # question selection cell
        if is_standalone:
                html = html.replace('$QSELECT','')
                html = html.replace('$QINFO','&nbsp;')
                num_rows = 9
                if question.type == 'Input/output':
                        num_rows = 10
                stdin_stdout_height = 157
        else:
                num_rows = 10
                if question.type == 'Input/output':
                        num_rows = 11
                html = html.replace('$QSELECT',qselect_html)
                html = html.replace('$QINFO',qinfo_html)
                html = html.replace('$PREVIOUS_BUTTON',previous_button_html)
                html = html.replace('$NEXT_BUTTON',next_button_html)
        # stdin cell
        html = html.replace('$STDIN_HTML',stdin_html)
        html = html.replace('$STDIN',gen_display(question.stdin_template,
         answer['stdin']))
        html = html.replace('$HEIGHT',str(stdin_stdout_height))
        # stdout cell
        if question.type == 'Input/output':
                html = html.replace('$STDOUT_HTML',stdout_html)
                html = html.replace('$STDOUT',
		 gen_display(question.stdout_template,answer['stdout']))
                html = html.replace('$HEIGHT',str(stdin_stdout_height))
        else:
                html = html.replace('$STDOUT_HTML',empty_one)
                html = html.replace('$HEIGHT',str(stdin_stdout_height+30))
        # question info cell
	if not is_standalone:
		html = html.replace('$MARK',str(question.mark))
		html = html.replace('$TOTAL_QUESTIONS',str(quiz.get_count()))
	html = html.replace('$QUESTION_NUMBER',str(q_num))
        # rowspan
        html = html.replace('$NUM_ROWS',str(num_rows))
        return html

type_color_dic = {'Input/output':'#ff9900','Bullseye':'#ff0033',
 'Liar-liar':'#3399ff','Find the failure':'#66cc33'}

html_base = '''
<html>
<head>
  <style type="text/css">
    table,td,th{border:1px solid}
    .padding {
      padding-left: 3;
    }
  </style>
</head>
<form method="get">
<table border="1" cellspacing="0" bgcolor="lightgrey">
  <tr>
    <td rowspan="$NUM_ROWS" valign="top" class=padding>
      <div style="height:548px;width:500px;overflow:auto">
        <pre><font size="4">$CODE</font></pre>
      </div>
    </td>
    <td stype="height:30px" align="center" bgcolor="$BGCOLOR">
    <b><font size="5">$GAME_TYPE</font></b></td>
  </tr>
  <tr><td align="center" style="height:50px;width:260px">
    $SUBMIT_BUTTON
  </td></tr>
  $QSELECT
  $COMMAND_LINE_ARGS
  $STDIN_HTML
  $STDOUT_HTML
  <tr>
    <th align="left" bgcolor="white">
      <font color="$FONTCOLOR">Message: $MESSAGE</font>
    </th>
    <th bgcolor="white"><p>$QINFO</p></th>
  </tr>
  <input type="hidden" name="currqn" value="$QUESTION_NUMBER">
</table>
</form>
</html>'''

stdin_html = '''
<tr><th style="height:30px">Standard input</th></tr>
  <tr><td valign="top">
    <div style="height:$HEIGHT;width:260px;overflow:auto">
      <pre>$STDIN</pre>
    </div>
  </td></tr>
'''

stdout_html = '''
<tr><th style="height:30px">Standard output</th></tr>
  <tr><td valign="top">
    <div style="height:$HEIGHT;width:260px;overflow:auto">
      <pre>$STDOUT</pre>
    </div>
  </td></tr>
'''

empty_one = '''
<tr><td style="height:$HEIGHT" border="1"><pre>&nbsp;</pre></td></tr>
'''

empty_two = '''
<tr><td style="height:$HEIGHT" border="1"><pre>&nbsp;</pre></td></tr>
'''

qinfo_html = '''\
Question $QUESTION_NUMBER of $TOTAL_QUESTIONS ($MARK marks)'''

qselect_html = '''\
<tr><td align="center" style="height:50px;width:260px">
  $PREVIOUS_BUTTON $NEXT_BUTTON</td></tr>'''

command_line_args = '''\
  <tr><th style="height:30px">Command line arguments</th><tr>
  <tr><td><div style="height:50px;width:260px;overflow-x:auto">
    <pre>$ARGVS</pre>
  </div></td><tr>
'''

submit_button_html =  '''\
<input type="submit" style="height:30;width:180;font-size:100%" name="but" value="$BUTTON_LABEL">'''

previous_button_html = '''\
<input type="submit" style="height:30;width:90;font-size:100%" name="previousq" value="Previous" $DISABLE_PREVIOUS>'''

next_button_html = '''\
<input type="submit" style="height:30;width:90;font-size:100%" name="nextq" value="Next" $DISABLE_NEXT>'''

