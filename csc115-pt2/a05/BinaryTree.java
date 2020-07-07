import java.util.Iterator;
import javax.swing.*;

/**
 * BinaryTree is a basic binary tree to hold generic elements.
 * @author CSC115 LabInstructor
 * @version 1.0.1
 */

public class BinaryTree<E>{

/**
 * The root of the tree.
 */
	protected TreeNode<E> root;
// the list holds the elements in a traversal order to be accessed by the iterators
	private MyLinkedList<E> list;

/**
 * The number of nodes/elements in the tree.
 */
	protected int size;

/**
 * Creates a BinaryTree object that puts the element in the root
 * and attaches the left and right trees as children of the root.
 * @param element The element to put into the root node.
 * @param leftTree The BinaryTree that will be the left child of the root.
 * @param rightTree The BinaryTree that will be the right child of the root.
 */
	public BinaryTree( E element, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
		if (leftTree != null && rightTree != null) {
			root = new TreeNode<E>( element, leftTree.root, rightTree.root );
			size = 1 + leftTree.size + rightTree.size;
		} else if (leftTree == null) {
			root = new TreeNode<E>( element, null, rightTree.root );
			size = 1 + rightTree.size;
		} else if (rightTree == null) {
			root = new TreeNode<E>( element, leftTree.root, null );
			size = 1 + leftTree.size;
		} else {
			root = new TreeNode<E>( element );
			size = 1;
		}
 }

/**
 * Creates a tree with a single root node.
 * @param element The element to put into the root node.
 */
	public BinaryTree( E element ) {
		root = new TreeNode<E>( element );
		size = 1;
	}

/*
 * Creates an empty tree.
 */
	public BinaryTree() {
	}

/**
 * @return The number of elements / nodes in the tree.
 */
	public int size() {
		return size;
	}

/**
 * @return True if the tree is empty, false if it is not.
 */
	public boolean isEmpty() {
		return (size == 0);
	}


/**
 * A non-public method that returns the root node.
 * Should only be accessed by programs that have permission to directly
 * access the BinaryTree.
 */
	TreeNode<E> getRoot() {
		return root;
	}

/**
 * The height of the tree is the maximum number of nodes on a path
 * from the root to a leaf of the BinaryTree.
 * We take this definition from the current CSC115 textbook.
 * @return The height
 */
	public int height() {
		return genHeight( root );
	}

/*
 * A private recursive method for determining the height of a subtree
 * starting at a given node.
 */
	private int genHeight( TreeNode<E> subRoot ) {
		if (subRoot == null) {
			return 0;
		}
		return (1 + Math.max( genHeight( subRoot.leftChild ), genHeight( subRoot.rightChild )));
	}

/**
 * @return An Iterator object that serves the elements in pre-order.
 */

	public Iterator<E> preOrderIterator() {
		list = new MyLinkedList<E>();
		traverse(root, 1);
		return list.iterator();
	}

/**
 * @return An Iterator object that serves the elements in in-order.
 */
	Iterator<E> inOrderIterator() {
		list = new MyLinkedList<E>();
		traverse(root, 2);
		return list.iterator();
	}

/**
 * @return An Iterator object that serves the elements in post-order.
 */
	Iterator<E> postOrderIterator() {
		list = new MyLinkedList<E>();
		traverse(root, 3);
		return list.iterator();
	}

/**
 * @return An Iterator object that serves the elements in level-order.
 */
	Iterator<E> levelOrderIterator() {
		list = new MyLinkedList<E>();
		traverse(root, 4);
		return list.iterator();
	}

/*
 * Prep the list, traverse and fill up the list  according to the order code given.
 */
	private void traverse( TreeNode<E> subRoot, int code ) {

		if (subRoot == null) {
			return;
		}
		switch( code ) {
			case 1:
				list.insertLast( subRoot.element );
				traverse( subRoot.leftChild, code );
				traverse( subRoot.rightChild, code );
				break;
			case 2:
				traverse( subRoot.leftChild, code );
				list.insertLast( subRoot.element );
				traverse( subRoot.rightChild, code );
				break;
			case 3:
				traverse( subRoot.leftChild, code );
				traverse( subRoot.rightChild, code );
				list.insertLast( subRoot.element );
				break;
			case 4: // the only non-recursive algorithm.
				MyLinkedList<TreeNode<E>> queue = new MyLinkedList<TreeNode<E>>();
				TreeNode<E> curr;
				queue.insertFirst( root );
				while (!queue.isEmpty()) {
					curr = queue.removeFirst();
					list.insertLast( curr.element );
					if (curr.leftChild != null) {
						queue.insertLast( curr.leftChild );
					}
					if (curr.rightChild != null) {
						queue.insertLast( curr.rightChild );
					}
				}
				break;
			default:
				System.out.println("programmer made a mistake--should never get here");
		} // end switch statement.
	}

/**
 * Unit tester.
 */
	public static void main( String args[] ) {
		BinaryTree<String> a = new BinaryTree<String>( "help" );
		BinaryTree<String> b = new BinaryTree<String>( "ach" );
		BinaryTree<String> c = new BinaryTree<String>( "bah", a, b );
		BinaryTree<String> d = new BinaryTree<String>( "pi", c, b );
		BinaryTree<String> e = new BinaryTree<String>( "me", d, null );
		BinaryTree<String> f = new BinaryTree<String>( "yes", e, d );
		System.out.println("The node at the top contains "+f.getRoot().getElement());
		System.out.println("The height of the tree is "+f.height());
		System.out.println("pre-order traversal:");
		Iterator<String> it = f.preOrderIterator();
		while (it.hasNext()) {
			System.out.print(it.next() +" ");
		}
		System.out.println();
		System.out.println("level-order traversal:");
		it = f.levelOrderIterator();
		while (it.hasNext()) {
			System.out.print(it.next() + " ");
		}
		System.out.println();
		DrawableBTree<String> tree = new DrawableBTree<String>( f );
		tree.showFrame();
	}
}
