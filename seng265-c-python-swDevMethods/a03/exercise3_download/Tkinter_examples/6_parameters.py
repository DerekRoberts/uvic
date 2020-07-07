#!/usr/bin/python

# bound commands cannot have parameters.
# This demonstrates how to simulate parameter passing.

from Tkinter import *

def command(param):
	print "Param is "+str(param)

def command_1():
	command(1)

def command_2():
	command(2)

root = Tk()
menubar = Menu(root)
menu1 = Menu(menubar,tearoff = 0)
menu1.add_command(label = 'Command with param = 1',command = command_1)
menu1.add_command(label = 'Command with param = 2',command = command_2)
menubar.add_cascade(label = 'Menu with 2 commands',menu = menu1)

root.config(menu = menubar)
mainloop()
