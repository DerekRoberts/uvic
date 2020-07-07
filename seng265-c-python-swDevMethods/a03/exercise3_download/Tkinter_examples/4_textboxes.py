#!/usr/bin/python

# demonstrates how to read a bunch of textboxes

import os

from Tkinter import *

textboxes = []

def read_textboxes():
	os.system("clear")
	for i in range(len(textboxes)):
		print "textbox "+str(i)+": "+textboxes[i].get()

root = Tk()
for i in range(5):
	# note: you don't *have* to use StrVal & textvariable
	textboxes.append(Entry(root))
	textboxes[i].insert(0,"I am textbox #"+str(i))
	textboxes[i].grid(row = i,column = 0)
button = Button(root,text = "Read Textboxes",command = read_textboxes)
button.grid(row = len(textboxes),columnspan = 99,pady = 10)
mainloop()
