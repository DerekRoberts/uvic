from Tkinter import *

root = Tk()

entry = Entry(root)
entry.insert(0,'abc')
entry.grid(row = 0)

print 'entry contents: '+entry.get()

mainloop()
