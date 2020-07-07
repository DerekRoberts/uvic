from Tkinter import *
from tkFileDialog import *

root = Tk()

# read a file; write it to stdout
handle = askopenfile(mode = 'r')
print handle.name
print handle.read()

# write a file
handle = asksaveasfile(mode = 'w')
print handle.name
handle.write('abc\ndef\n')
handle.close()

mainloop()
