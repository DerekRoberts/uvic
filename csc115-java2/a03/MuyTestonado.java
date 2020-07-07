public class MuyTestonado{
	public static void main( String[] args){
		System.out.println( "Muy Testonado" );
		IntList l1 = new IntList( 12 );
		l1.fill();
		System.out.println( "l1.find(...) = " + l1.find( 9, false ) );

	}
}
