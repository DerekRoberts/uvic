/**
 * An interface that defines a list that stores elements of the 
 * primative type <code>double</code>.
 * <p>
 * It is important to note that not all decimal numbers can be stored exactly
 * as double values.  In the same way that it is impossible to write the
 * fraction 2/3 in decimal (it is .66666666 repeating forever) it is impossible
 * to represent some decimal numbers exactly in the primative type <code>double</code>.
 * <p>
 * Consider the following:
 * <p>
 * <code>
 * double d1 = 69.82;<br>
 * double d2 = 69.20 + 0.62;<br>
 * <br>
 * System.out.println (d1 == d2);<br>
 * </code>
 * <p>
 * The output of this code is <code>false</code> because of rounding 
 * errors. (Try it!)
 * <p>
 * It is for the above reason that this interface defines the 
 * methods removeThreshold and withinThreshold.
 * 
 * @see <a href="http://www.cygnus-software.com/papers/comparingfloats/comparingfloats.htm">Discussion of comparing floating point values</a>
 *
 * @author	J. Corless, January 2010.
 */
public interface ListOfDoubles
{
	/**
	 * Add the element <code>d</code> to the front of the list.
	 * <p>
	 * If the list was {1.0,2.3} and addFront(5.0) is invoked
	 * the new list is {5.0,1.0,2.3}
	 *
	 * @param d		the element to add
	 *
	 */
	public void addFront (double d);

	/**
	 * Add the element <code>d</code> to the back of the list.
	 * <p>
	 * If the list was {1.0,2.3} and addBack(5.0) is invoked
	 * the new list is {1.0,2.3,5.0}
	 *
	 * @param d		the element to add
	 *
	 */
	public void addBack  (double d);

	/**
	 *  Remove <b>all</b> occurrences of the value <code>d</code> in the list
	 *
	 *  @param d	the element to remove
	 */
	public void remove (double d);

	/**
	 * Remove all occurrences of values between <code>(d-threshold)</code> and 
	 * <code>(d+threshold)</code> inclusive.
	 *
	 * @param d		the value to remove
	 * @param threshold	the threshold value
	 */
	public void removeThreshold (double d, double threshold);


	/**
	 * Return the number of elements in the list
	 *
 	 * @return	the number of elements
	 */
	public int size();

	/**
	 * Return the number of values in the list that are between 
	 * <code>(d-threshold)</code> and <code>(d+threshold)</code> inclusive
	 *
	 * @param d		the value
	 * @param threshold	the threshold
	 *
	 * @return 	the number of elements between (d-threshold) 
	 *		and (d+threshold) inclusive.
	 * 
	 */
	public int withinThreshold (double d, double threshold);

	/**
	 * Return the maximum value in the list.
	 *	
	 * @see	<a href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/Double.html#NaN">Double.NaN</a>
	 *
	 * @return	the maximum value if size() >= 1, Double.NaN otherwise
	 */	
	public double	maximum();

	/**
	 * Return the minimum value in the list.
	 *
	 * @see	<a href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/Double.html#NaN">Double.NaN</a>
 	 *
	 * @return	the minimum value if size() >= 1, Double.NaN otherwise
	 */
	public double	minimum();

	/**
	 * Return the arithmetic mean of the values in the list.
	 *
	 * @see <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/Double.html#NaN">Double.NaN</a>
	 *
 	 * @return	the arithmetic mean if size() >= 1, Double.NaN otherwise  
	 */
	public double	mean();

	/**
	 * Return the element at position index in the list.
	 * The first position in the list is at index 0.
	 * <p>
	 * The results of this method are undefined if index < 0 or index >= size()
	 *
	 * @param index		the index to return the value from
	 * 
	 * @return	the element at position index, if index >= 0 and index < size()
	 * 		
	 */
	public double	elementAt (int index);

	/**
	 * Return a string representation of the list in the format:
	 * {1.0,2.34,5.834}
	 *
	 * @return 	A string representation of the list
	 */
	public String	toString();
}
