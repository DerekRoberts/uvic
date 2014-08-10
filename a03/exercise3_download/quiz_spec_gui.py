import os
import sys

from Tkinter import *

def stub():
	print "Implement me!"


# check command line arguments
if len(sys.argv) != 2:
	print 'Usage: python qspec_gui.py CODE_LIBRARY_PATH'
	sys.exit(-1)
	code_library_path = sys.argv[1]

# tkinter root window
master = Tk()

# set up menus
menubar = Menu(master)
filemenu = Menu(menubar,tearoff = 0)
filemenu.add_command(label = 'Open',command = stub)
filemenu.add_command(label = 'Save',command = stub)
filemenu.add_command(label = 'Save as',command = stub)
filemenu.add_command(label = 'Exit',command = master.quit)
menubar.add_cascade(label = 'File',menu = filemenu)
groupsmenu = Menu(menubar,tearoff = 0)
groupsmenu.add_command(label = "Add group before",command = stub)
groupsmenu.add_command(label = "Add group after",command = stub)
groupsmenu.add_command(label = "Delete group",command = stub)
groupsmenu.add_command(label = "Clear group buffer",command = stub)
groupsmenu.add_command(label = "Add directory",command = stub)
groupsmenu.add_command(label = "Remove directory",command = stub)
menubar.add_cascade(label = "Groups", menu = groupsmenu)
master.config(menu = menubar)

# create the titles for the three columns
mark_label = Label(master, text = "Mark")
mark_label.grid(row = 0,column = 1,sticky = W+N)
count_label = Label(master, text = "Count")
count_label.grid(row = 0,column = 2,sticky = W+N)
directories_label = Label(master, text = "Directories")
directories_label.grid(row = 0,column = 3,sticky = W+N)

mainloop()
