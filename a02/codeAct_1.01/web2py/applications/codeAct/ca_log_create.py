import os

def create_log(log_dir,login):
	'''
	Purpose
		Create a log file located at log_dir/login.xml.
	Precondition
		log_dir is a valid path.
		login is a string.
	'''
	# create log_dir if not exist
	if not os.path.isdir(log_dir):
		os.mkdir(log_dir)

	# create log file
	filename = os.path.join(log_dir,login+'.xml')
	if not os.path.exists(filename):
		file_handle = open(filename,'w')
		file_handle.close()

def write_log(log_dir,login,index,path,result,answer,time):
	'''
	Purpose
		Append a <question> element to log_dir/login.xml.
		A <question> element consists of index, path, result, answer,
		 and time.
	Precondition
		log_dir is a valid path.
		login and path are strings.
		index and result are integers.
		answer is a nested dictionary with the following keys: code,
		 argvs, stdin, and stdout.
	'''
	# open log file for reading
        file_handle = open(os.path.join(log_dir,login+'.xml'),'a')

	# write <question> element to file
        file_handle.write('<question>\n')
        file_handle.write('\t<index>'+str(index)+'</index>\n')
        file_handle.write('\t<path>'+path+'</path>\n')
        file_handle.write('\t<result>'+str(result)+'</result>\n')
        file_handle.write('\t<answer>\n')
        file_handle.write('\t\t<code>'+str(answer['code'])+'</code>\n')
        file_handle.write('\t\t<argv>'+str(answer['argvs'])+'</argv>\n')
        file_handle.write('\t\t<stdin>'+str(answer['stdin'])+'</stdin>\n')
        file_handle.write('\t\t<stdout>'+str(answer['stdout'])+'</stdout>\n')
        file_handle.write('\t</answer>\n')
        file_handle.write('\t<time>'+str(time)+'</time>\n')
        file_handle.write('</question>\n')

	# close log file
        file_handle.close()
