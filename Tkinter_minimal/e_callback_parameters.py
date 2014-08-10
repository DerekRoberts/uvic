# bound commands in menus and buttons cannot have parameters.
# This demonstrates how to simulate parameter passing.

from Tkinter import *

def button_handler(x):
	print 'button_handler:', x

def callback1():
	button_handler('1')

def callback2():
	button_handler('2')

root = Tk()

button1 = Button(root,text = 'Button 1',command = callback1)
button1.grid(row = 0)

button2 = Button(root,text = 'Button 2',command = callback2)
button2.grid(row = 1)

mainloop()
