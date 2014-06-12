import java.util.Iterator;
import java.util.NoSuchElementException;

class MyLinkedListIterator<E> implements Iterator<E>{
	//attributes
	ListNode<E> current;

	//constructor
	MyLinkedListIterator(ListNode<E> first){
		current = first;
	}

	//methods
	public boolean hasNext<>{
		return false;
	}

	public E next(){
		if(current == null){
			throw new NoSuchElementException ( "no next!!" );
		} else {
			E temp = current.element;
			current = current.next;
			return temp;
		}
	}

	public void remove() throws UnsupportedOperationException{
		throw new UnsupportedOperationException( "nope" );
	}
}


/** Add to MyLinkedList!
public MyLinkedListIterator<E> iterator(){
	return new MyLinkedListIterator<E>(first);
}
*/
