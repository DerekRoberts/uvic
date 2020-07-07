import java.util.regex.*;
import java.util.Iterator;
import java.util.Stack;
import java.util.EmptyStackException;

/**
 * ExpressionTree.java
 * Created as a solution for the CSC115 Calculator Assignment
 * @author Bette Bultena
 * @version 1.0.2
 */
 
/**
 * Class ExpressionTree is a binary tree that
 * holds basic Arithmetic expressions.
 */
public class ExpressionTree extends BinaryTree<String>{
  
// the array of tokens for this tree
	private String[] inputExp;
  
/**
 * Create an Expression tree from a string of 
 * infix elements.
 * It does not check for complete accuracy, and will
 * try its best.
 * The accuracy of the expression tree will be 
 * determined during the evaluation of the expression.
 * @param input The string to be parsed into the tree.
 * @throws ExpressionTreeException if there is a parsing error.
 */
	public ExpressionTree( String input ) {
		super();
		parseInput( input );
		try{
			root = populate(0,inputExp.length-1);
		} catch( ExpressionTreeException e){
			System.out.println( "Populate fail.  Unable to evaluate expression." );
		}
	}

/**
* Given an opening bracket return the location of it's matching closing bracket.
* Nested sets of brackets are handled by calling endBracket recursively.
* @param start The index of the first koen in the subArray.  (yes, I copied that :p)
* @param end The indexof the last token in the subArray.  (this too...)
* @return The location in enputExp of the closing bracket.
*/
	int endBracket( int start, int end ){
		if( !inputExp[end].equals( ")" ) )
			return end;
		for( int i=(end-1) ; i>=start ; i-- ){
			if( inputExp[i].equals( ")" ) ){
				i = endBracket( start, i );
			} else if( inputExp[i].equals( "(" ) ){
				return i;
			}
		}
		// If a matching set of brackets is not found a negative number is returned
		// which will cause the populate method to end early.
		return -1;
	}
/**
 * Recursively creates a tree out of an inFix expression from the subArray of inputExp
 * @param start The index of the first token in the subArray.
 * @param end The index of the last token in the subArray.
 * @return The root node of the subTree that was created with this section of the array.
 * @throws ExpressionTreeException if we cannot create the subTree.
 */
	private TreeNode<String> populate( int start, int end )  throws ExpressionTreeException{
		
		// If the beginning and end of the range open and close a set of brackets,
		// then disregard them by shrinking the range by one on each side.
		// This combined with endBracket method are how I'm handling parenthesis.
		if( inputExp[end].equals(")") && inputExp[start].equals("(") ){
			end--;
			start++;
		}
		
		// If a character is an operator then make that operator the root and split the range
		// into the left and right children.  The operators are each searched for individually in
		// reverse order (+,-,*,/,^) before moing on to the next operator.  The exception to this
		// search is that if any brackets are found the endBracket method is used to jump to
		// the close of that set of brackets.  Eventually brackets will be placed into child nodes
		// on their own and then will have their outside brackets omitted.  Multiple levels of
		// brackets are handled recursively by the endBracket method.
		
		for( int i=end ; i>=start ; i--){
			if( inputExp[i].equals("+") )
				return new TreeNode<String>( inputExp[i], populate ( start, i-1 ), populate ( i+1, end) );
			else if( inputExp[i].equals( ")" ) )
				i = endBracket( start, i );
		}
		
		for( int i=end ; i>=start ; i-- ){
			if( inputExp[i].equals("-") )
				return new TreeNode<String>( inputExp[i], populate ( start, i-1 ), populate ( i+1, end) );
			else if( inputExp[i].equals( ")" ) )
				i = endBracket( start, i );
		}

		for( int i=end ; i>=start ; i-- ){
			if( inputExp[i].equals("*") )
				return new TreeNode<String>( inputExp[i], populate ( start, i-1 ), populate ( i+1, end) );
			else if( inputExp[i].equals( ")" ) )
				i = endBracket( start, i );
		}
		
		for( int i=end ; i>=start ; i-- ){
			if( inputExp[i].equals("/") )
				return new TreeNode<String>( inputExp[i], populate ( start, i-1 ), populate ( i+1, end) );
			else if( inputExp[i].equals( ")" ) )
				i = endBracket( start, i );
		}
		
		for( int i=end ; i>=start ; i-- ){
			if( inputExp[i].equals("^") )
				return new TreeNode<String>( inputExp[i], populate ( start, i-1 ), populate ( i+1, end) );
			else if( inputExp[i].equals( ")" ) )
				i = endBracket( start, i );
		}
		
		// Hopefully we're now left with only one expression.  If that's the case, return it!
		// If processing is complete and the range is not one (end=start), then there is an
		// error with the data.  It will then throw an ExpressionTreeException.
		if( end == start ){
			return new TreeNode<String>( inputExp[start] );
		} else{
			throw new ExpressionTreeException ( "Unable to evaluate expression." );
		}
	}

/**
 * Evaluates this ExpressionTree.
 * @return The resulting value.
 * @throws ExpressionTreeException if the expression is not valid
 *				or the expression cannot be reduced to a single double.
 */

	public double evaluate() throws ExpressionTreeException{
		Stack<Double> s = new Stack<Double>();
		double check, soln, num1, num2;
		String token;
		Iterator<String> it = postOrderIterator();
		while (it.hasNext()) {
			token = it.next();
			try {
				check = Double.parseDouble(token);
				s.push( new Double( check ) );
			} catch (NumberFormatException e) {
				try {
					num2 = s.pop().doubleValue();
					num1 = s.pop().doubleValue();
				} catch (EmptyStackException e2) {
					throw new ExpressionTreeException("unable to evaluate expression");
				}
				s.push( new Double( evalExp( num1, num2, token.charAt(0))));
			}
		}
		try {
			soln = s.pop().doubleValue();
		} catch( EmptyStackException e ) {
			throw new ExpressionTreeException( "unable to evaluate expression");
		}
		if (!s.isEmpty()) {
			throw new ExpressionTreeException( "evaluation stack not empty");
		}
		return soln;
	}
   
/**
 * This private method actually does the arithmetic.
 * @param num1 The left hand operand.
 * @param num2 The right operand.
 * @param op The operator.
 * @return The result of operand operator operand.
 * @throws ExpressionTreeException if operand is not valid.
 */
	private double evalExp( double num1, double num2, char op ) {
		double result;
		switch (op) {
			case '*':
				result = num1 * num2;
				break;
			case '/':
				result = num1 / num2;
				break;
			case '+':
				result = num1 + num2;
				break;
			case '-':
				result = num1 - num2;
				break;
			case '^':
				result = Math.pow( num1, num2 );
				break;
			default:
				throw new ExpressionTreeException("unable to evaluate expression");
		}
		return result;
	}

/**
 * A private method that checks to see if a character is an arithmetic operator or not.
 * @return True if the character is an operator, false otherwise.
 */
	private boolean isOperator( char c ) {
		switch(c) {
			case '*':
			case '/':
			case '+':
			case '-':
			case '^':
			return true;
		}
		return false;
	}
/**
 * Same as above only takes a String argument.
 * @return True if the string is a single operator, false otherwise.
 */
	private boolean isOperator( String s ) {
		if (s.length() > 1) {
			return false;
		}
		return isOperator( s.charAt(0));
	}
	
/**
 * Populates the inputExp array by dividing the String into expression "tokens".
 * In the constructor, you want to call this method first.
 * Tokens are divided into operators and substrings.
 * White space is removed.
 * Ideally, the working string is an arithmetic expression, but this parsing method doesn't care.
 *
 * What it does in more detail:
 * Creates a temporary array big enough to handle all the "tokens"
 * Compiles a regular expression that looks for any operator characters. 
 *			this is done by the Pattern object called opPattern.
 *				(since a lot of these are special characters in java,
 *				there is a lot of backslashes here)
 * Then for each bit that is separated by an operator, we extract the bits that are separated by white space
 * 		(done by the Pattern object called "spacePattern" 
 * Every token is placed as a String object into the array.
 * Because we do not know off-hand, how many tokens there are, the array is over-estimated.
 * Once completed, we know the exact number and create a new array to hold the tokens.
 */
	private void parseInput( String s ) {
		String bit, op;
		String[] littleBits;	
		String[] tmpArray = new String[ s.length() ];
		Pattern spacePattern = Pattern.compile( "[\\s]+" );
		Pattern opPattern = Pattern.compile( "[\\*\\^\\+\\-/()]" );
// matcher will look for operators in the string.
		Matcher matcher = opPattern.matcher( s );
		int lastIndex = 0;
		int expIndex = 0;
		while (matcher.find()) { // goes through the whole string in order, picking out the operators
			op = matcher.group(); 
// bit is the substring between the last operator and this one we just found.
			bit = s.substring( lastIndex, matcher.start() );
			bit = bit.trim(); // trims the white space from the beginning and end.
			if (!bit.equals("")) {
// separate the substrings from the spaces and make each a separate token.
				littleBits = spacePattern.split( bit );
				for (int i=0; i<littleBits.length; i++) {
					tmpArray[expIndex++] = littleBits[i];
				}
			}
			lastIndex = matcher.start();
			if (op.charAt(0) == '-') {
// If it's a negative sign, then we will ignore it as an operator.
// A minus sign follows another operator, unless it's the first number.
				if (expIndex > 0) {
					if (isOperator( tmpArray[expIndex-1] )) {
						continue;
					}
				} else {
					continue;
				}
			} // end if
// add the operator...
			tmpArray[expIndex++] = op;
			lastIndex = matcher.end();
		} // end while loop
// if there is any junk left after the last operator we need to parse that, too.
		if (lastIndex < s.length()) {
			bit = s.substring(lastIndex, s.length());
			bit = bit.trim();
			if (!(bit.equals(""))) {
				littleBits = spacePattern.split( bit );
				for (int i=0; i<littleBits.length; i++) {
					tmpArray[expIndex++] = littleBits[i];
				}
			}
		}
// we now know the exact number of tokens and can put them into the inputExp array.
		inputExp = new String[expIndex];
		for (int i=0; i<expIndex; i++) {
			inputExp[i] = tmpArray[i];
		}
	}
  
/**
 * Unit tester.
 * Often modified to what it is I'm testing
 */
	public static void main( String[] args ) { 	
		String in = "6+((2+3)*(4-2))+8*2";						//was "6+((2+3)*(4-2))"
		ExpressionTree tree = new ExpressionTree( in );
		Iterator<String> it = tree.postOrderIterator();
		System.out.println("PostOrder:");
		while (it.hasNext()) {
			System.out.print(it.next()+ " ");
		}
		System.out.println();
		Iterator<String> it2 = tree.inOrderIterator();
		System.out.println("InOrder:");
		while (it2.hasNext()) {
			System.out.print(it2.next() + " ");
		}
		System.out.println();
		try{
			System.out.println("And the answer is "+ tree.evaluate());
		} catch( ExpressionTreeException e){
			System.out.println( "Error!" );
		}
		DrawableBTree t = new DrawableBTree<String>( tree);
		t.showFrame();
	}
}


