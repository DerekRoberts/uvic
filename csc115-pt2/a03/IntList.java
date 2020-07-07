public class IntList
{	
	private	int[]	storage;
	private int 	count;
	
	public IntList ()
	{
		this(100);
	}
	
	public IntList (int size)
	{
		storage = new int[size];
		count = 0;
	}
	
	/**
	 * Fill the list with the values from
	 * 0 to size-1.
	 */
	public void fill ()
	{
		count = storage.length;
		for (int i =0;i<count;i++)
		{
			storage[i] = i;
		}
	}
	
	/**
	 * Searches the list using binary search.
	 *
	 * Returns the position in the list where
	 * value occurs.  Assumes there are no duplicates and
	 * requires the list is in sorted order.
	 *
	 * @param value the value to search for
	 * @return the position value is found, -1 if value is not in the list
	 * 
==>	 * Additions:														<==
	 * @param high = high end of the binary search
	 * @param low  = low end of the binary search
	 * @return if mid doesn't find the value, use if statements to
	 * 		adjust high and low values and call binarySearch again
	 * 		(recursively) until value found or high == low.  If the 
	 * 		value is found it will be passed as a return.  Otherwise -1
	 * 		will be returned to signal not found.
	 */
	private int binarySearch(int value, int low, int high)
	{
		while( low <= high ){
			
			int mid = ( high+low ) / 2;
			
			if( value == storage[mid] ){
				return mid;
			}
			else if( value < storage[mid] ){
				return binarySearch( value, low, mid-1 );
			}
			else if( value > storage[mid] ){
				return binarySearch( value, mid+1, high );
			}
		}
		return -1;
	}

	
	/**
	 * Searches the list using linear search.
	 *
	 * Returns the postion in the list where value occurs.  Assumes
	 * there are no duplicates.  Does NOT require the list to be
	 * in sorted order.
	 * @param value the value to search for
	 * @return the position value is found, -1 if value is not in the list
	 * 
==>	 * Additions:														<==
	 * @param high = high end of the linear search
	 * @param low => not necessary (since always = 0)
	 * @return if not found check the one before by calling linearSearch
	 * 		again (recursively).  Eventually the calls will hit the
	 * 		beginning of the array and return a -1, signalling that the
	 * 		value was not found and passing it all the way to the base
	 * 		case, which will finally return the -1.
	 * 
	 */	 
	private int linearSearch (int value, int high)
	{	
		while( high >= 0 ){
			if( value == storage[ high ]){
				return high;
			}
			else {
				return linearSearch( value, high-1 );
			}
		}
		return -1;
	}

	/**
	 * Performs a binary (if true) or linear (if false) search.
	 * 
	 * Modified parameters for binary to include how and high values.
	 * Modified parameters for linear to include the location checked.
	 */
	public int find (int value, boolean binary)
	{
		if (binary)
			return binarySearch(value, 0, storage.length-1);
		else
			return linearSearch(value, storage.length-1);
	}
}
