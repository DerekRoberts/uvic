#!/usr/bin/python

# demonstrates how to display open/save dialogues

import os
from Tkinter import *
from tkFileDialog import *

def fileopen():
	handle = askopenfile(mode = 'r')
	print "name of file you picked = "+str(handle.name)
	print "first line of data from file: "+handle.readline()

def filesaveas():
	handle = asksaveasfile(mode = 'w')
	print "name of file you picked = "+str(handle.name)
	handle.write("Some data to save into the file\n")


#set up menus and main window
root = Tk()
menubar = Menu(root)
filemenu = Menu(menubar,tearoff = 0)
filemenu.add_command(label = 'Open...',command = fileopen)
filemenu.add_command(label = 'Save as...',command = filesaveas)
filemenu.add_command(label = 'Exit',command = exit) #python's builtin exit()
menubar.add_cascade(label = 'File',menu = filemenu)
root.config(menu = menubar)
mainloop()
