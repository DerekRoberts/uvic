import os
import sys

from re import *
from Tkinter import *
from tkFileDialog import *

#check command line arguments
if len(sys.argv) != 2:
	print 'Usage: python qspec_gui.py CODE_LIBRARY_PATH'
	sys.exit(-1)
else:
	code_library_path = sys.argv[1]

#quizQuestionList is a list of quiz question values to be included in quiz
#format of each list item: [mark value, count value, directory path]
quizQuestionList = []

quizSpecName = './quiz_spec.py'

markboxlist = []
countboxlist = []
groupBuffer = [ (1, 1, []) ]


def update_gui():
	updateQuestionList()
	master.destroy()
	print_gui()


def fileopen():
	global quizQuestionList
	global quizSpecName
		
	#empties quizQuestionList to be replaced by imported file contents
	quizQuestionList = []
	
	#regular expression identifies 'groups' containing (mark,count,'directory')
	groupItem = compile("\s*\((\d+),\s*(\d+),\s*\[(.+\')")

	#imports file of users choice and sets this file as the default save location
	importFile = askopenfile(mode = 'r', initialdir = './')
	quizSpecName = importFile.name
		
	#imports the group values from the file to the quizQuestionList
	for line in importFile:
		m = search(groupItem, line)
		if m:
			#group(1) stored in quizQuestionList[i][0] is the 'mark' value
			#group(2) stored in quizQuestionList[i][1] is the 'count' value
			#group(3) stored in quizQuestionList[i][2] is the directory name
			quizQuestionList += [m.group(1), m.group(2), m.group(3)],
	
	#close file after information is imported
	importFile.close()
	
	#reprints the gui using the new quizQuestionList values
	update_gui()


#creates a quiz_spec file at directory passed, or under quizSpecName
def save(saveFile = None):

	#if not file is passed as parameter, or if user hit 'cancel' create new file
	if not saveFile:
		saveFile = open(quizSpecName, 'w')
	
	updateQuestionList()
	
	saveFile.write("code_lib_path = '" + str(code_library_path) 
		+ "'\nquestion_list = [\n#	(mark,\tcount,\t[directories])\n")
	
	for i in range(len(quizQuestionList)):
		saveFile.write('\t(' + str(quizQuestionList[i][0]) + ',\t'
			+ str(quizQuestionList[i][1]) + ',\t[' 
			+ str(quizQuestionList[i][2]) + ']),\n')
	
	saveFile.write("]\n"
		+ "practice_mode = True\n"
		+ "standalone = False\n"
		+ "logged = False\n"
		+ "log_dir = ''\n")
	
	#saves file after writing
	saveFile.close()


#allows user to choose the directory in which to save the quiz_spec file
def filesaveas():
	global quizSpecName
	
	#opens file dialog and allows user to choose save directory
	#starts in current directory
	#if user hits 'cancel' returns None, which will be caught in def save()
	saveFile = asksaveasfile(mode = 'w', initialdir = './')
	
	#sets the user chosen directory as the default save location
	quizSpecName = saveFile.name
	
	#writes to a file
	save(saveFile)


#changes the mark and count values in the quizQuestionList to match user inputs
def updateQuestionList():
	global quizQuestionList
		
	#length of questionList will not match markbox list only 
	#right after new directories have been added, but before gui refreshes
	#because the markboxes are created in the print_gui def
	if len(quizQuestionList) is not len(markboxlist):
		return None
	
	#terminates def if the display is empty, ie there is no user inputs to update	
	if (len(quizQuestionList) == 0) or (len(countboxlist) == 0):
		return None
	
	#assigns the values for mark and count in the quizQuestionList to match the users inputs
	for i in range(len(quizQuestionList)):
		quizQuestionList[i][0] = markboxlist[i].get()
		quizQuestionList[i][1] = countboxlist[i].get()


#returns and integer representing the index position in the quizQuestionList of the current focus
def getcurrentgroupindex():
		
	#Displays message and returns None if the list is empty	
	if (len(quizQuestionList) == 0) or (len(countboxlist) == 0):
		print 'There are no groups. Open new directories to add groups.'
		return None

	#checks each 'Mark' entry box to determine if it has the focus	
	for i in range(len(markboxlist)):
		if markboxlist[i] is master.focus_get():
			return i
	
	#checks each 'Count' entry box to determine if it has the focus		
	for i in range(len(quizQuestionList)):
		if countboxlist[i] is master.focus_get():
			return i
	
	#if no focus is found, then there is not current: prints message and returns none	
	print 'There is no current group. Place cursor in a Mark or Count box to make it current.'	
	return None


def addgroupbefore():
	global quizQuestionList
	global groupBuffer

	currentIndex = getcurrentgroupindex()
	if currentIndex==None or groupBuffer==[ (1, 1, []) ]:
		return
	
	quizQuestionList.insert(currentIndex, groupBuffer.pop())
	groupBuffer = [ (1, 1, []) ]
	
	update_gui()
	
	
def addgroupafter():
	global quizQuestionList
	global groupBuffer
	
	currentIndex = getcurrentgroupindex()
	if currentIndex==None or groupBuffer==[ (1, 1, []) ]:
		return
	
	quizQuestionList.insert((1 + currentIndex), groupBuffer.pop())
	groupBuffer = [ (1, 1, []) ]	
	update_gui()


def deletegroup():
	global quizQuestionList
	global groupBuffer
	
	currentIndex = getcurrentgroupindex()
	if currentIndex is None:
		return
	
	groupBuffer[0] = quizQuestionList.pop(currentIndex)
	update_gui()


def clearbuffer():
	global groupBuffer
	
	groupBuffer = [ (1, 1, [] ), ]


#and now for something completely different
def print_gui():
	global quizQuestionList
	global master
	global markboxlist
	global countboxlist
	
	master = Tk()
	master.title("Quiz Spec")
	
	markboxlist = []
	countboxlist = []
		
	#create the titles for the three columns
	mark_label = Label(master, text = "Mark")
	mark_label.grid(row = 0,column = 1,sticky = W+N)
	
	count_label = Label(master, text = "Count")
	count_label.grid(row = 0,column = 2,sticky = W+N)
	
	directories_label = Label(master, text = "Directories")
	directories_label.grid(row = 0,column = 3,sticky = W+N)

	for i in range(len(quizQuestionList)):
		markbox = Entry(master)
		markbox.insert(0, quizQuestionList[i][0])
		markbox["width"] = 5
		markbox.grid(row = i+1,column = 1)
		markboxlist.append(markbox)
	
		countbox = Entry(master)
		countbox.insert(0, quizQuestionList[i][1])
		countbox["width"] = 5
		countbox.grid(row = i+1,column = 2)
		countboxlist.append(countbox)
	
		label = Label(master,text = quizQuestionList[i][2])
		label.grid(row = i+1,column = 3)
		
	#set up menus
	menubar = Menu(master)
	filemenu = Menu(menubar,tearoff = 0)
	filemenu.add_command(label = 'Open',command = fileopen)
	filemenu.add_command(label = 'Save',command = save)
	filemenu.add_command(label = 'Save as',command = filesaveas)
	filemenu.add_command(label = 'Exit',command = master.quit)

	menubar.add_cascade(label = 'File',menu = filemenu)
	groupsmenu = Menu(menubar,tearoff = 0)
	groupsmenu.add_command(label = "Add group before",command = addgroupbefore)
	groupsmenu.add_command(label = "Add group after",command = addgroupafter)
	groupsmenu.add_command(label = "Delete group",command = deletegroup)
	groupsmenu.add_command(label = "Clear group buffer",command = clearbuffer)

	groupsmenu.add_command(label = "Add directory",command = addDirs)
	groupsmenu.add_command(label = "Remove directory",command = remDirs)
	menubar.add_cascade(label = "Groups", menu = groupsmenu)
	master.config(menu = menubar)


#adList stores dirs in code_library, populated with adListUpdate()
#directories already in quizQuestionList removed from adList
adList = []
def adListUpdate():
	global adList
	adList = os.walk(code_library_path).next()[1]
	for i in range(len(quizQuestionList)):
		if quizQuestionList[i][2] in adList:
			adList.remove(quizQuestionList[i][2])


#popup used for selecting/checking and adding directories to group
def addDirs():
	#refresh adList
	adListUpdate()
	
	#checkbox tracking array, full of IntVar()s
	cbVar = []
	for i in adList:
		cbVar.append(IntVar())
	
	#create popup menu
	popup = Toplevel()
	popup.title("Add directories")
	for i in range(len(adList)):
		#create checkboxes and list available directories (adList[i])
		cb = Checkbutton(popup,variable=cbVar[i])
		cb.grid(row = i,column = 0)
		lbl = Label(popup,text = adList[i])
		lbl.grid(row = i,column = 1)

	#closes popup and adds checked directories (with mark=1, count=1)
	def addChecks():
		popup.destroy()
		#then saves them into quizQuestionList
		global quizQuestionList
		for i in range(len(adList)):
			if cbVar[i].get():
				quizQuestionList.append([1,1,"'"+adList[i]+"'"])
		update_gui()
	
	#create buttons for Add and Cancel
	btn1 = Button(popup,text = "Add",command = addChecks)
	btn1.grid(row = len(adList), column = 0)
	btn2 = Button(popup,text = "Cancel",command = popup.destroy)
	btn2.grid(row = len(adList), column = 1)


#popup used for selecting/checking and adding directories to group
def remDirs():
	global quizQuestionList
	
	#checkbox tracking array, full of IntVar()s
	cbVar = []
	for i in quizQuestionList:
		cbVar.append(IntVar())
	
	#create popup menu
	popup = Toplevel()
	popup.title("Remove directories")
	
	for i in range(len(quizQuestionList)):
		#create checkboxes and list removable directories (quizQuestionList[i])
		cb = Checkbutton(popup,variable=cbVar[i])
		cb.grid(row = i,column = 0)
		lbl = Label(popup,text = quizQuestionList[i][2])
		lbl.grid(row = i,column = 1)

	#removes checked directories and closes popup
	def remChecks():
		popup.destroy()
		
		global quizQuestionList
		global adList
		
		#create a list of directories to remove from quizQuestionList
		toRemove = []
		for i in range(len(quizQuestionList)):
			if cbVar[i].get():
				toRemove.append(quizQuestionList[i][2])
		
		#remove directories from quizQuestionList and add to adList
		for i in toRemove:
			for j in range(len(quizQuestionList)):
				if quizQuestionList[j][2]==i:
					quizQuestionList.remove(quizQuestionList[j])
					break
		update_gui()
		
	#create buttons for Add and Cancel
	btn1 = Button(popup,text = "Remove",command = remChecks)
	btn1.grid(row = len(quizQuestionList), column = 0)
	btn2 = Button(popup,text = "Cancel",command = popup.destroy)
	btn2.grid(row = len(quizQuestionList), column = 1)

#draw GUI and run mainloop!
print_gui()
mainloop()
