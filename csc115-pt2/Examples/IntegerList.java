public interface IntegerList
{
	/*
	 * Add the element x to the front of the list
	 * If the list was {1,2,3} and addFront(9) is called
	 * the new list is {9,1,2,3}.
	 */
	public void addFront (int x);

	/* Return the element at position pos in the list.
	 * The first position in the list is 0.
	 * 
	 * The results of this method are undefined if 
	 * pos < 0 or pos >= size
	 */
	public int  atPosition (int pos);
	
	public void removeAt (int pos);
	
	public int size();
	
	public String toString();
}
