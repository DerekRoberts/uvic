import sys

def min(a,b):
	if a < b:
		return a
	else:
		return b
	
print min(4,
	min(int(sys.argv[1]),int(sys.stdin.read())))
