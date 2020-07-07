#!/usr/bin/python

# demonstrates how to add menus to a window

from Tkinter import *

def command():
	print "You clicked a menu item"

root = Tk()
menubar = Menu(root)
#first menu
menu1 = Menu(menubar,tearoff = 0)
menu1.add_command(label = 'A command',command = command)
menu1.add_command(label = 'Exit',command = exit) #python's builtin exit()
menubar.add_cascade(label = 'Menu 1',menu = menu1)
#another menu
menu2 = Menu(menubar,tearoff = 0)
menu2.add_command(label = 'The same command',command = command)
menubar.add_cascade(label = 'Menu 2',menu = menu2)

root.config(menu = menubar)
mainloop()
