from Tkinter import *

def callback():
	print 'button clicked'

root = Tk()

button = Button(root,text = 'Button',command = callback)
button.grid(row = 0)

mainloop()
