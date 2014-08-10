from Tkinter import *

root = Tk()

entry = Entry(root)
entry.grid(row = 0,column = 0)

# use button callback to observe focus after mainloop started
def callback():
	print 'root focus:', root.focus_get() is entry
	button.focus_set()
button = Button(root,text = 'Check focus',command = callback)
button.grid(row = 0,column = 1)

root.mainloop()
