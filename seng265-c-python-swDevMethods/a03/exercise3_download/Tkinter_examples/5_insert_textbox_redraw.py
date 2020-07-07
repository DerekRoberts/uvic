#!/usr/bin/python

# demonstrates inserting a gui element before an existing one
# by clearing and re-generating the gui

from Tkinter import *

def insert_box2():
	global box1
	box1.grid_forget()
	value = StringVar(value = "box 2")
	box2 = Entry(root,width = 5,textvariable = value)
	box2.grid(row = 0,column = 0)
	box1.grid(row = 0,column = 1)

root = Tk()

value = StringVar(value = "box 1")
box1 = Entry(root,width = 5,textvariable = value)
box1.grid(row = 0,column = 0)

button = Button(root,text = "Insert another textbox",command = insert_box2)
button.grid(row = 1,columnspan = 99,pady = 10)

mainloop()
