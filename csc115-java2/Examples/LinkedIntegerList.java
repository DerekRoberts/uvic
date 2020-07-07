public class LinkedIntegerList implements IntegerList
{
	private IntegerNode 	head;
	private int 		size;

	public LinkedIntegerList()
	{
		head = null;
		size = 0;
	}
	
	/*
	 * Add the element x to the front of the list
	 * If the list was {1,2,3} and addFront(9) is called
	 * the new list is {9,1,2,3}.
	 */
	public void addFront (int x)
	{
		IntegerNode n = new IntegerNode();
		n.element = x;
		n.next = head;
		head = n;
		size++;
	}

	/* Return the element at position pos in the list.
	 * The first position in the list is 0.
	 * 
	 * The results of this method are undefined if 
	 * pos < 0 or pos >= size
	 */
	public int  atPosition (int pos)
	{
		if (pos < 0 || pos >= size)
			return -1;
		return 0;
	}
	
	public void removeAt (int pos)
	{
	}
	
	public int size()
	{
		return size;
	}
	
	public String toString()
	{
		String s = "{";
		IntegerNode p = head;
		while (p!=null)
		{
			s+=p.element;
			if (p.next != null)
				s+= ",";
			p=p.next;
		}
		s+= "}";
		return s;
	}
}
