from Tkinter import *

root = Tk()

var = IntVar()

check = Checkbutton(root,variable = var)
check.grid(row = 0)

print 'checkbutton state:', str(var.get())

root.mainloop()
