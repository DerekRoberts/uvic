/**
 * ListNode is a positional indicator for a linked list.
 * It contains an element and holds a position in a list.
 * It only has one public method.
 */

public class ListNode<E> {

	E element;
	ListNode<E> prev;
	ListNode<E> next; 
/*
 * I use this tag as a security measure.
 * When a node is created within a list, it is given a list code.
 * When it is removed from the list, its code is also removed.
 */
	long code;

	ListNode( long code, E element, ListNode<E> prev, ListNode<E> next ) {
		this.code = code;
		this.element = element;
		this.prev = prev;
		this.next = next;
	}	

	ListNode( long code, E element ) {
		this( code, element, null, null );
	}
	
	ListNode() {
		this(0, null);
	}

	public E getElement() {
		return element;
	}

/**
 * @return a String representation of the element associated with the position.
 */
	public String toString() {
		return element.toString();
	}
}
