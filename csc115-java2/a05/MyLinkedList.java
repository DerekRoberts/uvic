import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MyLinkedList.java
 * @author Bette Bultena
 */

/**
 * MyLinkedList is a linked list ADT specifically developed as a teaching tool for UVIC CSC115
 */
public class MyLinkedList<E> {

	private ListNode<E> first, last;
	private int size;
// I put this in to identify non-list nodes
	private static final long ID = 128351245;

/**
 * Creates an empty list.
 */
	public MyLinkedList() {
		size = 0;
	}
/**
 * @return the number of elements in the list.
 */
	public int size() {
		return size;
	}

/**
 * @return True if the list is empty.
 */
	public boolean isEmpty() {
		return (size==0);
	}

/**
 * Inserts a new element into the first position of the list.
 * @param element to insert.
 * @return The position of the newly inserted element.
 */
	public ListNode<E> insertFirst( E element ) {
		if (size == 0) {
			last = first = new ListNode<E>( ID, element );
			size++;
			return first;
		} else {
			return insertBefore( first, element );
		}	
	}

/**
 * Inserts a new element into the last position of the list.
 * @param element to insert.
 * @return The position of the newly inserted element.
 */
	public ListNode<E> insertLast( E element ) {
		if (size == 0) {
			first = last = new ListNode<E>( ID, element );
			size++;	
			return last;
		} else {
			return insertAfter( last, element );
		}
	}

/*
 * Check to make sure node is not null and belongs to this list.
 * This is a private method that handles the error checking and is the source
 * of any thrown exceptions, which are tossed to the various methods that call this one.
 */
	private boolean check( ListNode<E> theNode ) {
		if (theNode == null) {
			throw new NullPointerException("null position");
		}
		if (theNode.code != ID) {
			throw new NoSuchElementException("non-list member position");
		}
		return true;
	}

/**
 * Inserts a new element before the current position in the list.
 * @param current A valid position in the list.
 * @param element to insert.
 * @return The position of the new element.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public ListNode<E> insertBefore( ListNode<E> current, E element ) {
		check( current );
		ListNode<E> node = new ListNode<E>( ID, element, current.prev, current );
		current.prev = node;
		if (current == first) {
			first = node;
		} else {
			node.prev.next = node;
		}
		size++;
		return node;
	}

/**
 * Inserts a new element after the current position in the list.
 * @param current A valid position in the list.
 * @param element to insert.
 * @return The node for the new element.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public ListNode<E> insertAfter( ListNode<E> current, E element ) {
		check( current );
		ListNode<E> node = new ListNode<E>( ID, element, current, current.next );
		current.next = node;
		if (current == last) {
			last = node;
		} else {
			node.next.prev = node;
		}
		size++;
		return node;
	}

/**
 * @return the first position in the list or null if the list is empty.
 */
	public ListNode<E> getFirst() {
		return first;
	}

/**
 * @return the last position in the list, or null if the list is empty.
 */
	public ListNode<E> getLast() {
		return last;
	}

/**
 * @param current A valid position in this list. 
 * @return the next position.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public ListNode<E> getNext( ListNode<E> current ) {
		check( current );
		return current.next;
	}

/**
 * @param current A valid position in this list
 * @return True if the positon has a next, false otherwise.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */ 
	public boolean hasNext( ListNode<E> current ) {
		check( current );
		return (current.next != null);
	}

/**
 * @param current A valid position in this list.
 * @return The previous position in the list or null if current is the first.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public ListNode<E> getPrev( ListNode<E> current ) {
		check( current );
		return current.prev;
	}

/**
 * @param current A valid position in this list. 
 * @return True if the position has a previous position, false otherwise.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public boolean hasPrevious( ListNode<E> current ) {
		check( current );
		return (current.prev != null);
	}

/**
 * Gets the position at index (where 0 &le; index &le; size-1).
 * @param index of the position requested.
 * @return The position at that index or null if the index is out of range.
 */
	public ListNode<E> getPositionAtIndex( int index ) {
		if (index < 0 || index >= size) {
			System.out.println("index is out of range");
			return null;
		}
		ListNode<E> t = first;
		for (int i=0; i<index; i++) {
			t = t.next;
		}
		return t;
	}

/**
 * Returns the index of the given position.
 * @param current A valid position in this list. 
 * @return The index (0 ... size-1) of the position in the list, or -1 if current is null.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public int getIndexAtPosition( ListNode<E> current ) {
		check( current );
		int index = 0;
		for (ListNode<E> t = first; t != current; t = t.next) {
			index++;
		}
		return index;
	}

/**
 * Remove the element and its position from the from the list.
 * @param current A valid position in this list. 
 * @return The element in the given position, or null if the position is not a valid position in the list.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public E removeNode( ListNode<E> current ) {
		check( current );
		if (current.prev != null) {
			current.prev.next = current.next;
		} else {
			first = current.next;
		}
		if (current.next != null) {
			current.next.prev = current.prev;
		} else {
			last = current.prev;
		}
		size--;
		current.code = -1; // user cannot use this to reference the list anymore
		return current.element;
	}

/**
 * Removes the first element in the list.
 * @return The element or null if the list is empty.
 */
	public E removeFirst() {
		if (size==0) {
			return null;
		}
		return removeNode( first );
	}

/**
 * Removes the last element in the list.
 * @return The element or null if the list is empty.
 */
	public E removeLast() {
		if (size==0) {
			return null;
		}
		return removeNode( last );
	}

/**
 * Finds the position of the next occurrence of the element
 * from the given position, which is included in the search.
 * @param current A valid position in this list.
 * @param element the element we are looking to match.
 * @return the position containing the element or null 
 * if it does not exist beyond the current position.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public ListNode<E> findNext( ListNode<E> current, E element ) {
		check( current );
		for (ListNode<E> t = current; t != null; t = t.next) {
			if (t.element.equals(element)) {
				return t;
			}
		}
		return null;
	}

/**
 * Finds the position of the first occurrence of the element.
 * @param element The element we are looking to match.
 * @return the position of the element or null if it does not exist in the list.
 */
	public ListNode<E> find( E element ) {
		if (first == null) {
			return null;
		}
		return findNext( first, element );
	}

/**
 * Finds the position of the previous occurrence of the element
 * from the given position, which is included in the search.
 * @param current A valid position in the list.
 * @param element the element we are looking to match.
 * @return the position containing the element or null
 * if is does not exist beyond the current position.
 * @throws NullPointerException if the current node is null
 * @throws NoSuchElementException if the current node is not a member of the list.
 */
	public ListNode<E> findPrev( ListNode<E> current, E element) {
		check( current );
		for (ListNode<E> t = current; t!= null; t = t.prev) {
			if (t.element.equals( element )) {
				return t;
			}
		}
		return null;
	}

/**
 * @return An Iterator object for this linked list.
 */
	public Iterator<E> iterator() {
		return new MyLinkedListIterator<E>( first );
	}

/**
 * @return A string representation of the list, just the elements.
 */
	public String toString() {
		String s = "List of "+size+" elements:\n";
		ListNode<E> t = null;
		for (t = first; t != null; t = t.next) {
			s += t.element+"\n";
		}
		return s;
	}

/**
 * Used for testing purposes only
 */
	public static void main( String args[] ) {
		MyLinkedList<String> list = new MyLinkedList<String>();
		ListNode<String> current;
		String first = "Bette";
		String second = "Tyler";
		String third = "Piper";
		list.insertFirst( third );
		list.insertFirst( first );
		current = list.getFirst();
		current = list.insertAfter(current, second);
		System.out.println( list );
		String check = new String("Bette");
		current = list.find( check );
		if (current == null) {
			System.out.println("equals is not behaving");
		} else {
			System.out.println("equals is behaving");
		}
		list.removeNode( current );
		System.out.println("removing bet\n"+list);
		System.out.println("Checking the handling of an illegal node");
		try {
			list.getNext( current );
		} catch (NoSuchElementException e) {
			System.out.println("error caught correctly: "+e.getMessage());
		}
		String check2 = "Jeremy";
		String check3 = "Jason";
		String check4 = "Nishat";
		list.insertLast(check2);
		current = list.getLast();
		System.out.println(list);
		System.out.println("The last in the list is "+current.getElement());
		current = list.getPrev( current );
		current = list.getPrev( current );
		System.out.println("The third from the end is "+current.getElement());
		current = list.getNext( current );
		list.insertAfter( current, check3 );
		list.insertBefore( current, check4 );
		current = list.insertFirst( "Bette" );
		current = list.getPositionAtIndex( 4 );
		current = list.insertBefore( current, "Bette" );
		list.insertLast("Bette");
		System.out.println(list);
// check the iterator.
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			System.out.println( it.next() );
		}
		System.out.println("current is at index position "+list.getIndexAtPosition(current));
		System.out.println("And that element is "+list.getPositionAtIndex(4).getElement());
		

	}
}
