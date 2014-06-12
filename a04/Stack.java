/**
 * Stack.java
 *
 * A specification of the Stack ADT
 *
 */
public interface Stack<T>
{
	int size();
	boolean isEmpty();
	void push (T element);
	T pop() throws StackEmptyException;
	T peek() throws StackEmptyException;
	void makeEmpty();
}

