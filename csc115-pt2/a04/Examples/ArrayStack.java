/**
 * ArrayStack.java
 *
 * An implementation of the Stack ADT using an array.
 *
 */
public class ArrayStack<T> implements Stack<T>
{
	private static final int MAXSIZE = 10;
	
	/** 
	 * position is the index of the next
	 * free space in the stack.
	 */
	private	int position;

	/** Notice that this is of type Object, for reasons
	 *  yet to be discussed.
	 */
	private Object[] storage;
	
	public ArrayStack()
	{
		position = 0;
		storage = new Object[MAXSIZE];
	}
	
	public int size()
	{
		return position;
	}
	public boolean isEmpty()
	{
		return size() == 0;
	}

	private void growArray()
	{
		Object[] bigger = new Object[storage.length*2];
		for (int i=0;i<position;i++)
			bigger[i] = storage[i];
		storage = bigger;
	}	

	public void push (T o)
	{
		if (position == storage.length-1)
		{
			growArray();
		}
		storage[position++] = o;
	}
	
	public T pop() throws StackEmptyException
	{
		if (isEmpty())
		{
			throw new StackEmptyException("Stack empty");
		}
		T val = (T)storage[--position];
		return val;
	}
	
	public T peek() throws StackEmptyException
	{
		if (isEmpty())
			throw new StackEmptyException("Stack empty");
		return (T)storage[position-1];
	}
	
	public void makeEmpty()
	{
		for (int i = 0; i<position;i++)
		{
			storage[i] = null;
		}
		position = 0;
	}
}

