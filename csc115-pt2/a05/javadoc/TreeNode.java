/**
 * A non-public class.
 * TreeNode is a basic Binary Tree node containing a generic
 * element.
 * Only for use in a Binary Tree structure.
 */

class TreeNode<E> {
	E element;
	TreeNode<E> leftChild;
	TreeNode<E> rightChild;

	TreeNode( E element, TreeNode<E> left, TreeNode<E> right ) {
		this.element = element;
		this.leftChild = left;
		this.rightChild = right;
	}

	TreeNode( E element ) {
		this( element, null, null );
	}

	TreeNode() {
		this( null, null, null );
	}

/**
 @ return the element at this position in the tree.
 */
	public E getElement() {
		return element;
	}

	public void leftChild( E gIn ){
		leftChild = new TreeNode<E>( gIn );
	}

	public void rightChild( E gIn ){
		rightChild = new TreeNode<E>( gIn );
	}
}
