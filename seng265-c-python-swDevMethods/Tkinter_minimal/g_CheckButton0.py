from Tkinter import *

root = Tk()

var = IntVar()

check = Checkbutton(root,variable = var)
check.grid(row = 0)

# use button callback to observe check button state after mainloop started
def callback():
	print 'checkbutton state:', str(var.get())
button = Button(root,text = 'check', command = callback)
button.grid(row = 0,column = 1)

root.mainloop()
