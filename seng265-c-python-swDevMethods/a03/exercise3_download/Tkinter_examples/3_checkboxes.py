#!/usr/bin/python

# demonstrates how to check which checkboxes in a list are checked

from Tkinter import *

items = ["narf","poit","zort","troz","fjord"]
items_state = []

def list_checked():
	checked = []
	for i in range(len(items)):
		if items_state[i].get(): #note the .get() !
			checked.append(items[i])
	print "The following items are checked: "+str(checked)

root = Tk()
#make a checkbox for each item and keep track of its state variable
for i in range(len(items)):
	items_state.append(IntVar())
	checkbox = Checkbutton(root,variable = items_state[i])
	checkbox.grid(row = i,column = 0)
	label = Label(root,text = items[i])
	label.grid(row = i,column = 1)
button = Button(root,text = "Enumerate",command = list_checked)
button.grid(row = len(items),columnspan = 99,pady = 10)
mainloop()
