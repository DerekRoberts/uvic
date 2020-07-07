from Tkinter import *

def callback():
	popup_window = Toplevel()
	label = Label(popup_window,text = "window label")
	label.grid(row = 0)

root = Tk()

button = Button(root,text = "Popup",command = callback)
button.grid(row = 0)

root.mainloop()
