public class DoubleNode{
	private double item;
	private DoubleNode next;
	private DoubleNode prev;
	
	/**
	 * Default constructor DoubleNode().
	 * Not very exciting as-is.
	*/ 
	public DoubleNode(){
	}
	
	/**
	 * Constructor DoubleNode() + Object parameter = less typing
	 * Lazy = smart!  ...right?  :D
	 */ 	
	public DoubleNode( double newItem){
		item = newItem;

	}
	
	/**
	public DoubleNode( Object newItem, DoubleNode nextNode){
		item = newItem;
		next = nextNode;
	}
	*/
	
	public void setItem( double newItem){
		item = newItem;
	}
	
	public double getItem(){
		return item;
	}
	
	public void setNext( DoubleNode nextNode){
		next = nextNode;
	}	
	
	public void setPrev( DoubleNode prevNode){
		prev = prevNode;
	}
	
	public DoubleNode getNext(){
		return next;
	}
	
	public DoubleNode getPrev(){
		return prev;
	}
	
	public static void main( String[] args){
		System.out.println( "DoubleNode main.  Not much to see here." );
	}
}
