public class StackEmptyException extends Exception
{
	public StackEmptyException(String msg)
	{
		super(msg);
	}
}

/**
 * Used with try-catch, to avoid crashing
 * 
 * try{
 * 		s.makeEmpty();
 * 		s.pop();
 * }
 * catch (StackEmptyException e){
 * }
