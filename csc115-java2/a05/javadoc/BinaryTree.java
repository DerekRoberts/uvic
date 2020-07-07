/**
* BinaryTree.java
* @author me
*/

//For DrawableBTree
import java.util.Iterator;
import javax.swing.*;
import java.awt.*;

public class BinaryTree <E>{
	//Attributes
	TreeNode<E> root;
	int size;

	/**
	* Constructor
	* @param element the contents of the root
	* @param left the left subtree
	* @param right the right subtree
	*/
	public BinaryTree (E element, E left, E right){
		root 			= new TreeNode<E>( element );
		root.leftChild 	= left.setRoot( left );
		root.rightChild	= right.setRoot( right );
	}

	/**
	* @param element the root's element
	*/
	public BinaryTree (E element){
		root = new TreeNode<E>(element);
	}

	public BinaryTree(){
		this(null, null, null);
	}


	//methods

	/**
	* isEmpty
	* @return True if empty
	*/
	public boolean isEmpty(){
		if( root.getElement() == null )
			return true;
		else
			return false;
	}

	/**
	* getRoot
	* @return root is a TreeNode at the top of the tree
	*/
	public TreeNode<E> getRoot(){
		return root;
	}

	/**
	* getHeight
	* @return the height of the tree
	*/
	public int getHeight(){
//		if( root == null)
			return 0;
//		return rh(root);

	}

	/**
	* size
	* @return the size of the tree (number of nodes)
	*/
	public int size(){
		return size;
	}

	//MAIN METHOD FOR TESTS<<<
	public static void main (String[] args){
		BinaryTree<String> t = new BinaryTree<String>( "root" );
		t.leftChild( "l01h01" );
		t.rightChild( "r01h01" );


		System.out.println( "t.size() = " + t.size());

		System.out.println( "t.getRoot().getElement() = " + t.getRoot().getElement() );

		if( t.isEmpty() == false ){
		//	DrawableBTree tree = new DrawableBTree(t);
		//	tree.showFrame();
		}
	}
}