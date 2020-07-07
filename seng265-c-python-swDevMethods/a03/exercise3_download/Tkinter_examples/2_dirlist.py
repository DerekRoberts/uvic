#!/usr/bin/python

# demonstrates how to display a directory listing in a window

import os
from Tkinter import *

def showdir():
	dirlist = os.listdir(".")
	popup_window = Toplevel()
	popup_window.title("Listing of current directory")
	for i in range(len(dirlist)):
		#create a label for each file and add it to the window's layout grid
		label = Label(popup_window,text = dirlist[i])
		label.grid(row = i,column = 1)

#set up menus and main window
root = Tk()
menubar = Menu(root)
filemenu = Menu(menubar,tearoff = 0)
filemenu.add_command(label = 'Show current dir',command = showdir)
filemenu.add_command(label = 'Exit',command = exit) #python's builtin exit()
menubar.add_cascade(label = 'File',menu = filemenu)
root.config(menu = menubar)
mainloop()
