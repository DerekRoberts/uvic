import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

/**
 * Calculator.java
 * Created and modified from CSC115 assignments in 2001, 2005.
 * Current version is dated 2011
 * @version 2.2.1 (shortened version)
 * @author bbultena
 * @author irbull for modifications in 2005
 */


/**
 * Calculator class is a GUI that demonstrates an ExpressionTree,
 * User can input a postfix or infix expression to populate
 * an Expression tree, which is drawn on the canvas.
 * The value of the expression can be calculated.
 * This class has been specifically created for use as an
 * assignment in UVic CSC115 class.
 */
public class Calculator extends JPanel implements ActionListener {

	private static final long serialVersionUID = 10L;
	private static final int TREEWIDTH = 300;
	private static final int TREEHEIGHT = 300;
	private JButton create, eval;
	private JTextField input;
	private JLabel preOrderExp, inOrderExp, postOrderExp, levelOrderExp, answer, errors;
	private ExpressionTree expTree = null;
	private DrawableBTree<String> treeDrawing = null;
	private JPanel treeArea;

	/**
	 * Creates the GUI layout.
	 * There are three panels:
	 * <ul><li>The work area: where the user enters data or pushes buttons,</li>
	 *	<li>The result area: where the information is presented and </li>
	 *	<li>The tree area, where a binary tree is rendered.</li></ul>
	 */
	public Calculator(  ) {
		
		super ( new BorderLayout() ); // initializes the parent JPanel object

// some fancy borders
		Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border resultBorder = BorderFactory.createLineBorder( Color.black );
		Border border2 = BorderFactory.createLoweredBevelBorder();
		Border border3 = BorderFactory.createEmptyBorder(0,5,10,10);
		
		this.setBorder( border3 );
		
		JPanel workArea = new JPanel();
		workArea.setBorder( border1 );
		input = new JTextField("Enter the Expression:", 30);
		create = new JButton( "Create Tree" );
		eval = new JButton( "Evaluate Exp" );
// register the buttons to listen to user actions (i.e. pushing buttons)
		create.addActionListener( this );
		eval.addActionListener( this );
// add the button drawings to the workArea panel
		workArea.add(input);
		workArea.add(create);
		workArea.add(eval);

		JPanel resultArea = new JPanel();
		resultArea.setLayout( new GridLayout(0,1));
		resultArea.setBorder( border1 );
		JLabel preOrderLabel = new JLabel("Preorder List:");
		preOrderExp = new JLabel();
		JLabel inOrderLabel = new JLabel("Inorder List:");
		inOrderExp = new JLabel();
		JLabel postOrderLabel = new JLabel("Postorder List:");
		postOrderExp = new JLabel();
		JLabel levelOrderLabel = new JLabel("Levelorder List:");
		levelOrderExp = new JLabel();
		JLabel answerLabel = new JLabel("Answer:");
		answer = new JLabel();
		JLabel errorLabel = new JLabel("Error Message:");
		errors = new JLabel();
		Dimension resultSize = new Dimension( 100, 20 );
		preOrderExp.setBorder( resultBorder );
		inOrderExp.setBorder( resultBorder );
		postOrderExp.setBorder( resultBorder );
		levelOrderExp.setBorder( resultBorder );
		answer.setBorder( resultBorder );
		errors.setBorder( resultBorder );
		resultArea.add(preOrderLabel);
		resultArea.add(preOrderExp);
		resultArea.add(inOrderLabel);
		resultArea.add(inOrderExp);
		resultArea.add(postOrderLabel);
		resultArea.add(postOrderExp);
		resultArea.add(levelOrderLabel);
		resultArea.add(levelOrderExp);
		resultArea.add(answerLabel);
		resultArea.add(answer);
		resultArea.add(errorLabel);
		resultArea.add(errors);
		
		treeArea = new JPanel( new BorderLayout(), false );
		treeArea.setPreferredSize( new Dimension( TREEWIDTH, TREEHEIGHT ));
		treeArea.setBorder( border2 );
		treeArea.setOpaque( true );
// start out with an empty tree drawing
		treeDrawing = new DrawableBTree<String>( treeArea.getPreferredSize() );
		treeArea.add( treeDrawing, BorderLayout.CENTER );

// now add all the panels onto "this" panel
		this.add( workArea, BorderLayout.NORTH );
		this.add( resultArea, BorderLayout.WEST );
		this.add( treeArea, BorderLayout.CENTER );
		repaint();
	}
	
/**
 * Required for all java swing components.
 * Paints all the individual panels and buttons
 * whenever windows change.
 * @param g The graphics object associated with this particular JPanel.
 *				I like to think of the graphics object as a paintbrush that
 *				is tied to the particular swing component.
 */
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
	}
	
/**
 * A required method for all objects that implement ActionListener.
 * This one checks to see what the source of the "action" is.
 * Depending on the source, then the required actions are performed.
 * @param e The action object that is passed in from the "listener".
 */
	public void actionPerformed( ActionEvent e) {
		String theString = input.getText();
		if (e.getSource() == create) {
// clear previous clutter
			clearLabels();
			if (treeDrawing != null) {
				treeArea.remove( treeDrawing );
				treeDrawing  = null;
				expTree = null;
			}
			try {
				drawTree( theString );
				updateExpressions();
			} catch (RuntimeException exc) {
// this is where all the Exceptions land
				errors.setText( "Oops. "+exc.getMessage() );
			}
		} else if (e.getSource() == eval) {
			try {
				double value = expTree.evaluate();
				answer.setText( ""+value );
			} catch (RuntimeException exc) {
				errors.setText( "Oops. "+exc.getMessage() );
			}
		} else {
			errors.setText("Error: Unexpected GUI Error CLOSE WINDOW!!!");
		}
// this call is required to get the GUI to validate any new stuff that
// has happened to the components (mainly the tree drawing)
		this.revalidate();
		repaint();
	}
	
/**
 * Pretty self-expanatory private method.
 */
	private void clearLabels() {
		preOrderExp.setText("");
		inOrderExp.setText("");
		postOrderExp.setText("");
		levelOrderExp.setText("");
		answer.setText("");
		errors.setText("");
	}
	
/*
 * This private method grabs the various order iterators 
 * from the Expression tree that was created.
 * If they are not available, then they are just left blank.
 * In this case, it follows a format of one space
 * between each token.
 * The results are presented in the appropriate label areas.
 */
	private void updateExpressions() {
		try {
			Iterator<String> preOrderIt = expTree.preOrderIterator();
			String preOrder = preOrderIt.next();
			while (preOrderIt.hasNext()) {
				preOrder += (" "+preOrderIt.next() );
			}
			preOrderExp.setText( preOrder );
		} catch (RuntimeException e) { } 
		try {
			Iterator<String> inOrderIt = expTree.inOrderIterator();
			String inOrder = inOrderIt.next();
			while (inOrderIt.hasNext()) {
				inOrder += (" "+inOrderIt.next() );
			}
			inOrderExp.setText( inOrder );
		} catch (RuntimeException e) { }
		try {
			Iterator<String> postOrderIt = expTree.postOrderIterator();
			String postOrder = postOrderIt.next();
			while (postOrderIt.hasNext()) {
				postOrder += (" "+postOrderIt.next() );
	
	}
			postOrderExp.setText( postOrder );
		} catch (RuntimeException e) { }
		try {
			Iterator<String> levelOrderIt = expTree.levelOrderIterator();
			String levelOrder = levelOrderIt.next();
			while (levelOrderIt.hasNext()) {
				levelOrder += (" "+levelOrderIt.next() );
			}
			levelOrderExp.setText( levelOrder );
		} catch (RuntimeException e) { }
	}
			
	
/**
 * Uses the ExpressionTree constructor to generate the tree,
 * then uses the DrawableBTree to generate the drawing,
 * and places it into the TreeArea, trying to fit it nicely
 * to the space provided in the TreeArea.
 */
	private void drawTree( String theString ) {
		expTree = new ExpressionTree( theString );
		Dimension d = new Dimension( treeArea.getWidth(), treeArea.getHeight() );
		treeDrawing = new DrawableBTree<String>( expTree, d );
		treeArea.add( treeDrawing );
	}

/**
 * The main starting point for the entire calculator
 * @param args Args passed to the program which are ignored.
 */
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		JFrame frame = new JFrame( "CSC115 Calculator" );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		frame.setContentPane( calc );
		frame.pack();
		frame.setVisible( true );
	}
} 
