public class ArrayIntegerList implements IntegerList
{
	private static final int INITIAL_SIZE = 100;
	
	int[]	storage;
	int	count;
	
	public ArrayIntegerList()
	{
		storage = new int[INITIAL_SIZE];
		count = 0;		
	}
	/*
	 * Add the element x to the front of the list
	 * If the list was {1,2,3} and addFront(9) is called
	 * the new list is {9,1,2,3}.
	 */
	public void addFront (int x)
	{
	}

	/* Return the element at position pos in the list.
	 * The first position in the list is 0.
	 * 
	 * The results of this method are undefined if 
	 * pos < 0 or pos >= size
	 */
	public int  atPosition (int pos)
	{
		return 0;
	}
	
	public void removeAt (int pos)
	{
	}
	
	public int size()
	{
		return 0;
	}
	
	public String toString()
	{
		return "poo";
	}
}
