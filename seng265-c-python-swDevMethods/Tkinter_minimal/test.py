from Tkinter import *

class App:
	def __init__(self, master):
		frame = Frame(master)
		frame.pack()
		
		self.text = Label(frame, text="Frame thing!")
		self.text.pack(side=RIGHT)
		
		btn = Button(frame, text="Button thing!",command=self.cmdresult)
		btn.pack(side=LEFT)
		
		self.textField = Entry(frame)
		self.textField.pack()
		
	def cmdresult(self):
		print "result dumped to command prompt!"
		

root = Tk()
application = App(root)
root.mainloop()
