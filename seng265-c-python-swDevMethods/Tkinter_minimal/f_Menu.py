from Tkinter import *

def callback():
	print 'Command clicked'

# create the root widget
root = Tk()

# add a menubar
menubar = Menu(root)
root.config(menu = menubar)

# create a menu as child of menubar
menu = Menu(menubar,tearoff = 0)

# configure as a pulldown menu
menubar.add_cascade(label = 'Menu',menu = menu)

# add a command
menu.add_command(label = 'Command',command = callback)

# add the Exit command
menu.add_command(label = 'Exit',command = exit)

# start the GUI
root.mainloop()
