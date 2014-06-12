/*
* Calc.java by Derek Roberts (v00698880)
*
* Performs post fix calculations of the form +, -, / or x (aka *)
*/
public class Calc{
	
	/**
	* Makes a single pass through a stack, calculating and replacing sets 
	* of one operator (+,-,/,x) above two operands (#s) with their result.
	* The calc calls itself recursively until only the total remains.
	*
	* If an invalid stack is passed calc will empty the stack.  An already
	* empty stack will be returned as-is, ready for rejecting.
	*/
	public static LLStack process( LLStack stackIn ){
		LLStack operator	= new LLStack<String>();
		LLStack operand	= new LLStack<String>();
		int tally = 1000000000;
		int stackBefore = stackIn.size();
		while( stackIn.size() > 0 ){
			try{
				if( isOperator( stackIn ) ){
					operator.push( stackIn.pop() );
					tally = 0;
				} else{
					operand.push( stackIn.pop() );
					tally++;
				}
				if( operator.size() > 0 && operand.size() > 1 && tally == 2 ){
					int		i1	= Integer.parseInt( (String)operand.pop() );
					String	s1	= (String)operator.pop();
					int 		i2	= Integer.parseInt( (String)operand.pop() );
					operand.push( Integer.toString( calc( i1, s1, i2 ) ));					
				}
			} catch( StackEmptyException e){
				System.out.println( e );
			} catch( NumberFormatException e2){
				System.out.println( e2 );
			}
		}
		stackIn = restack( operand, operator );
		int stackAfter = stackIn.size();
		
		if( stackBefore == stackAfter && stackAfter > 1 )
			stackIn.makeEmpty();
		
		if( stackIn.size() > 1 )
			stackIn = process( stackIn );

		return stackIn;
	}
	
	/**
	* Returns true only for a stack with an operator (+-/) at the top.
	*/
	public static boolean isOperator( LLStack stackIn ){
		try{
			String sChk = (String)stackIn.peek();
			if( sChk.equals("+") || sChk.equals("-") || sChk.equals("/") || sChk.equals("x") )
				return true;
			else
				return false;
		} catch( StackEmptyException e){
			System.out.println( e );
			return false;
		}
	}
	
	/**
	* Restacks/reassmbled operator and operand stacks back together.
	* Uses the reverse method.
	*/
	public static LLStack restack( LLStack stackTo, LLStack stackFrom ){
		stackTo = reverse( stackTo );
		while( stackFrom.size() > 0 ){
			try{
				stackTo.push( stackFrom.pop() );
			} catch( StackEmptyException e){
				System.out.println( e );
			}
		}
		return stackTo;
	}
	
	/**
	* Reverses a stack arround backwards.  Used with restack method.
	*/
	public static LLStack reverse( LLStack stackIn ){
		LLStack stackOut = new LLStack<String>();
		while( stackIn.size() > 0 ){
			try{
				stackOut.push( stackIn.pop() );
			} catch( StackEmptyException e){
				System.out.println( e );
			}
		}
		return stackOut;
	}
	
	/**
	* Makes simple calculations of the form integer-operator-integer.
	*/
	public static int calc( int operand1, String operator, int operand2 ){
		if( operator.equals("+") ){
			return operand1+operand2;
		} else if( operator.equals("-") ){
			return operand1-operand2;
		} else if( operator.equals("/") ){
			return operand1/operand2;
		} else{
			return operand1*operand2;
		}
	}
	
	/**
	* Prints out what's left in a stack, because I screw up a lot and this helps.
	* Troubleshooting method, no longer used in final version of assignment.
	*/
	public static void dump( LLStack stackIn ){
		while( stackIn.size() > 0){
			try{
				System.out.print( stackIn.pop() +" " );
			} catch( StackEmptyException e){
				System.out.println( e );
			}
		}
		System.out.println( "end of dump." );
	}
	
	/**
	* Main!
	*/
	public static void main( String[] args){
		LLStack sCalc = new LLStack<String>();
		
		//Before adding command line support I entered the numbers in
		//directly using push.  That's still an option, as per the next bit.
		if ( args.length > 0 ){
			int countArgs = args.length;
			for( int i = 0; i < countArgs; i++ ){
				sCalc.push( args[i] );
				System.out.print( args[i] +" " );
			}
		} else {
			sCalc.push( "4" );
			sCalc.push( "5" );
			sCalc.push( "+" );
			sCalc.push( "6" );
			sCalc.push( "3" );
			sCalc.push( "/" );
			sCalc.push( "x" );
		}
		
		sCalc = process( sCalc );
		
		try{
			System.out.println( "= "+ sCalc.pop() );
		} catch(StackEmptyException e){
			System.out.println( "= Invalid Expression" );
		}
	}
}