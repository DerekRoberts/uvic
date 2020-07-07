# using the grid layout

from Tkinter import *

root = Tk()

label = Label(root,text = '(0,0)')
label.grid(row = 0,column = 0)

label = Label(root,text = '(1,0) -----')
label.grid(row = 1,column = 0)

label = Label(root,text = '(0,1)')
label.grid(row = 0,column = 1)

label = Label(root,text = '(2,0)')
label.grid(row = 2,column = 0)

mainloop()
/Users/derekroberts/Documents/Applications/Users/derekroberts/Documents