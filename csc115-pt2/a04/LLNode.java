/**
 * 
 * LLNode.java
 * 
 */

public class LLNode{
	//Attributes
	private	Object	item;
	private	LLNode	prev;
	
	//Constructor
	public LLNode( Object oIn){
		item = oIn;
		prev = null;
	}
	
	//Methods
	public void	setItem( Object oIn){
		item = oIn;
	}
	
	public Object	getItem(){
		return item;
	}
	
	public void	setPrev( LLNode nIn){
		prev = nIn;
	}
	
	public LLNode	getPrev(){
		return prev;
	}
		
	//Main method	
	public static void main( String[] args){
		
		LLNode nTest = new LLNode( 0);
		LLNode nTest2 = new LLNode( 0);

		System.out.println( nTest.getItem() );
		nTest.setItem( 1);
		System.out.println( nTest.getItem() );
		nTest.setPrev( nTest2);
		
		System.out.println( nTest.getPrev().getItem() );
		
		System.out.println( nTest.getItem() );
		System.out.println( "Nothing to see here.  Move along..." );
	}
}
