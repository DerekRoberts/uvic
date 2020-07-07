import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MyLinkedListIterator.java
 * @author Bette Bultena
 */

/**
 * MyLinkedListIterator is an Iterator for MyLinkedList class
 */

class MyLinkedListIterator<E> implements Iterator<E> {

	private ListNode<E> current;

	MyLinkedListIterator( ListNode<E> start ) {
		current = start;
	}

	public boolean hasNext() {
		return (current != null);
	}

	public E next() {
		if (current == null) {
			throw new NoSuchElementException("iteration has no more elements");
		}
		E item = current.getElement();
		current = current.next;
		return item;
	}

	public void remove() {
		throw new UnsupportedOperationException("Not allowed to remove during iteration");
	}
}
	
/**
 * The following code is from MyLinkedList, which this Iterator extends
 *
 * @return An Iterator object for this linked list.
 *
	public Iterator<E> iterator() {
		return new MyLinkedListIterator<E>( first );
	}
 */
