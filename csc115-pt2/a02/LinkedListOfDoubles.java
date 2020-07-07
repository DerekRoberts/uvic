public class LinkedListOfDoubles implements ListOfDoubles{
	private DoubleNode head = new DoubleNode();
	private DoubleNode tail = new DoubleNode();
	private int size = 0;
	
	/**
	 * Default constructor!
	 * head, tail and size default to values above.
	 */
	public LinkedListOfDoubles(){
	}
	
	/**
	 * addFront( double d) checks if the size is 0 or 1, because in
	 * those cases head=null=tail or head=_?_=tail, which requires a bit
	 * of extra work.
	 *
	 * @param d		the element to add
	 *
	 */
	public void addFront( double d){							
		if( size== 0){
			head.setItem( d);
			tail.setItem( head.getItem());
		}
		else if( size== 1){
			tail.setItem( head.getItem());
			tail.setPrev( head);
			
			head.setItem( d);
			head.setNext( tail);
			
			head.setPrev( null);
			tail.setNext( null);
		}
		else{
			DoubleNode dNode= new DoubleNode( d);
			dNode.setNext( head );
			head .setPrev( dNode);
			tail .setNext( null);
			head = dNode;
		}
		size++;
	}

	/**
	 * addBack( double d) checks if the size is 0 or 1, because in
	 * those cases head=null=tail or head=_?_=tail, which requires a bit
	 * of extra work.
	 *
	 * @param d		the element to add
	 *
	 */
	public void addBack  (double d){							
		if( size == 0){
			tail.setItem( d);
			head.setItem( tail.getItem());
		}
		if( size == 1){
			head.setItem( tail.getItem());
			head.setNext( tail);
			
			tail.setItem( d);
			tail.setPrev( head);
			
			head.setPrev( null);
			tail.setNext( null);
		}
		else{
			DoubleNode dNode= new DoubleNode( d);
			dNode.setPrev( tail );
			tail .setNext( dNode);
			tail = dNode;
		}
		size++;
	}

	/**
	 * I haven't figured out why yet, but without making head and tail
	 * new DoubleNode()s on final-entry removal the tests were failing.
	 * This is supposed to be bad form, but failing is even worse form,
	 * so I guess there's a lesson to be learned in all of this.  :p
	 *
	 *  @param d	the element to remove
	 */
	public void remove (double d){								
		DoubleNode p = head;
		
		while( p!= null ){
			
			if( size == 0)
				break;
			
			if( p.getItem() == d){
				if( p == head && size == 1){
					head = new DoubleNode();
					tail = new DoubleNode();
				}
				else if( p == head){
					head = p.getNext();
				}
				else if( p == tail){
					tail = p.getPrev();
				}
				else{
					p.getPrev().setNext( p.getNext());
					p.getNext().setPrev( p.getPrev());
				}
				size--;
			}
			if( p != null)
				p = p.getNext();
		}
	}

	/**
	 * Remove all occurrences of values between <code>(d-threshold)</code>
	 * and <code>(d+threshold)</code> inclusive.
	 *
	 * @param d		the value to remove
	 * @param threshold	the threshold value
	 */
	public void removeThreshold (double d, double threshold){	
		DoubleNode p = head;
		
		while( p!= null ){
			
			if( size == 0)
				break;
			
			if( p.getItem()>= (d-threshold) && p.getItem()<= (d+threshold)){
				if( p == head && size == 1){
					head = new DoubleNode();
					tail = new DoubleNode();
				}
				else if( p == head){
					head = p.getNext();
				}
				else if( p == tail){
					tail = p.getPrev();
				}
				else{
				p.getPrev().setNext( p.getNext());
				p.getNext().setPrev( p.getPrev());
				}
				size--;
			}
			p = p.getNext();
		}
	}


	/**
	 * Return the number of elements in the list
	 *
 	 * @return	the number of elements
	 */
	public int size(){											
		return size;
	}


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
	public int withinThreshold (double d, double threshold){	
		DoubleNode p = head;
		int count = 0;
		
		while( p!= null ){
			if( size == 0)
				break;
			
			if( p.getItem()>= (d-threshold) && p.getItem()<= (d+threshold))
				count++;
				
			p = p.getNext();
		}
		return count;
	}

	/**
	 * Return the maximum value in the list.
	 *	
	 * @see	<a href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/
	 * Double.html#NaN">Double.NaN</a>
	 *
	 * @return	the maximum value if size() >= 1, Double.NaN otherwise
	 */	
	public double maximum(){									
		double max = Double.NaN;
		DoubleNode p = head;
		
		if( size > 0)
			max = p.getItem();
		
		while (p!= null && size != 0){
			if( p.getItem() > max){
				max = p.getItem();
			}
			p=p.getNext();
		}		
		return max;
	}

	/**
	 * Return the minimum value in the list.
	 *
	 * @see	<a href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/
	 * Double.html#NaN">Double.NaN</a>
 	 *
	 * @return	the minimum value if size() >= 1, Double.NaN otherwise
	 */
	public double minimum(){									
		double min = Double.NaN;
		DoubleNode p = head;
		
		if( size > 0)
			min = p.getItem();
		
		while (p!= null && size != 0){
			if( p.getItem() < min){
				min = p.getItem();
			}
			p=p.getNext();
		}		
		return min;
	}

	/**
	 * Return the arithmetic mean of the values in the list.
	 *
	 * @see <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/
	 * Double.html#NaN">Double.NaN</a>
	 *
 	 * @return	the arithmetic mean if size() >= 1, Double.NaN otherwise  
	 */
	public double mean(){										
		double mean  = Double.NaN;
		double sum   = 0;
		DoubleNode p = head;
		
		while (p!= null && size != 0){
			sum += p.getItem();
			p=p.getNext();
		}
		if( size != 0 )
			mean = ( sum / size );
		return mean;
	}

	/**
	 * Return the element at position index in the list.
	 * The first position in the list is at index 0.
	 * <p>
	 * The results of this method are undefined if index < 0 or
	 * index >= size()
	 *
	 * @param index		the index to return the value from
	 * 
	 * @return	the element at position index, if index >= 0 and
	 * index < size()
	 * 		
	 */
	public double elementAt (int index){						
		DoubleNode p = head;
		int count = 0;
		while( count < index ){
			count++;
			p = p.getNext();
		}
		return p.getItem();
	}


	/**
	 * Return a string representation of the list in the format:
	 * {1.0,2.34,5.834}
	 *
	 * @return 	A string representation of the list
	 */
	public String toString(){									
		String s = "{";
		DoubleNode p = head;
		while (p!= null && size != 0)
		{
			s+= p.getItem();
			if (p.getNext() != null)
				s+= ",";
			p=p.getNext();
		}
		s+= "}";
		return s;
	}

	/**
	 * I had a main method, but made such a mess out of it that the
	 * contents were deleted in embarassment before submission.
	 */
	public static void main( String[] args){
		System.out.println( "This main method is no longer exciting." );
	}
}
