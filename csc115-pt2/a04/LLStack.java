/**
 * LLStack.java by Derek Roberts (v00698880)
 */

public class LLStack<T> implements Stack<T>{
	//Attributes
	private	LLNode	tail;
	private 	int		size;
	
	//Constructor
	public LLStack(){
		size = 0;
	}

	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		if( size < 1 )
			return true;
		else
			return false;
	}
	
	public void push ( T element ){
		if( size== 0 ){
			tail = new LLNode( element );
			tail.setItem( element );
			tail.setPrev( null );
		} else{
			LLNode nIn = new LLNode( element );
			nIn.setPrev( tail );
			tail = nIn;
		}
		size++;
	}
	
	public T pop() throws StackEmptyException{
		T toReturn = null;
		if( size < 1 )
			throw new StackEmptyException( "Stack empty" );
		else if( size == 1 ){
			toReturn = (T)tail.getItem();
			tail = null;
		} else {
			toReturn = (T)tail.getItem();
			tail = tail.getPrev();
		}
		size--;
		return toReturn;
	}
	
	public T peek() throws StackEmptyException{
		if( size <1 )
			throw new StackEmptyException("Stack empty");
		return (T)tail.getItem();
	}
	
	/**
	* When size < 1 pop and peek throw a StackEmptyException
	* and push loads up a fresh node with the previous = null,
	* so all that's needed to clear the stack is a size of 0.
	*
	* Is that good or bad?  ...I don't know any better yet.
	*/
	public void	makeEmpty(){
		size = 0;
	}
}
	
/**
 * CHECKLIST
 * 
 * int size();							= DONE!
 * boolean isEmpty();					= DONE!
 * void push (T element);					= DONE!
 * T pop() throws StackEmptyException;		= DONE!
 * T peek() throws StackEmptyException;	= DONE!
 * void makeEmpty();					= DONE!
*/
