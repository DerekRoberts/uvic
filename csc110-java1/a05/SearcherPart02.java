/** Assignment:	5, part 2
    Author:	Derek Roberts
    Student ID:	V00698880
    Date:	November 28, 2010
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
    Input:		The users will enter board coordinates for their ships
				at first, but then for shooting the enemy.  
    Output:		A mockup of battleship.  There will be feedback for hits
				and misses as well as an end to the game.
    Purpose:	To share the joys of piratehood with assignment markers!
*/

import java.util.*;

public class SearcherPart02 {
  public static final int BOARD_DIMENSION = 10;
  
  //Menu item numbers, lengths and names will never change
  private static final int[]    menuLst ={ 0, 1, 2, 3, 4, 5 };
  private static final int[]    menuLng ={ 1, 1, 2, 3, 4, 5 };
  private static final String[] menuNew ={ "Jalapeño", "Chipotle",
							"Nacho", "Taco", "Burrito", "Quesadilla" };
  private static final String[] menuOld ={ "Dingy", "Dingy", "Sailboat",
							"Yacht", "BC Ferry", "Cruise Ship" };
  private static Scanner scIn = new Scanner( System.in );


  //Purpose: To set location on the board for a boat (unitially, dingy).
  //Input:   The Player's name and their own player's board.
  //         The move method in the Player class will prompt the user
  //         board locations.
  //Output:	 Some printing to the screen.
  public static void boatSetter( Player p, char[][] game )
  {
	//Initialize Scanner stdin
	Scanner inScan = new Scanner( System.in );

	System.out.println( "| +-----.____" );
	System.out.println( "| | (   )    `-------.____" );
	System.out.println( "| |  `. \\         (   )  |" );
	System.out.println( "| |    `. \\.----.' .'    |" );
	System.out.println( "| |      \\/      \\'      |  SPiCY MEXiCAN FOOD" );
	System.out.println( "| |      | ()  () |      |     PiRATE BATTLESHiP!" );
	System.out.println( "| |      .\\  '`  /.      |" );
	System.out.println( "| |    .' .|VVVV|. \\     |" );
	System.out.println( "| |  .' .' `.__.' \\ `.   |" );
	System.out.println( "| |(___)            \\ `. |    where even lunch" );
	System.out.println( "| +-----.____       (___)|       be a battle" );
	System.out.println( "|            `------.____|" );
	System.out.print( "\nyArrr " + p.getName() + ", I be Señor Scurvy.  Place yer order!\n" );
	System.out.println( "\n  #. Item       \tLength \t\tFormerly " );

	//Print out the delicious menu of spicy violence
	for( int i = 0; i <=5; i++ )
		System.out.println( "  "+menuLst[i]+". " +menuNew[i]+ "\t\t" +menuLng[i]+ "\t\t" +menuOld[i]);

	System.out.print( "\nMenu Selection: " );
	
	int foodNum = inScan.nextInt();
	if (foodNum == 0)
		System.out.println( "\nJalapeño!  'Tis a fine selection.\n" );
	else
	{
		System.out.println( "\nThat be all wrong, ye rancid sea weasel!  I pick jalapeño fer ya.\n" );
		foodNum = 0;
	}
	
	boatMarker( p, 0, game );
	switchPlayers();
  }
  
  public static void boatMarker( Player pNum, int foodNum, char[][] board )
  {
	int theRow = 0;
	int theColumn = 0;
	String foodName = "";

	//If statement puts an "s" at the end of "square" if length > 1
	System.out.print( menuNew[foodNum]+ ", length = " +menuLng[foodNum]+ " square" );
	if( menuLng[foodNum] == 1 )
		System.out.println( ".\n" );
	else
		System.out.println( "s.\n" );
	
	printBoard( pNum.getName(), board );
	
	for( int i = 0; i < menuLng[foodNum]; i++ )
	{
		theRow    = pNum.move( "row" );
		theColumn = pNum.move( "column" );
		if( board[theRow][theColumn] == "+".charAt(0) ){
			board[theRow][theColumn] = menuNew[foodNum].charAt(0);
			printBoard( pNum.getName(), board );
		}
		else{
			System.out.println( "Ye be trying me patience...  <grr>" );
			i--;
		}
	}	
  }

  public static void switchPlayers()
  {
	  System.out.println( "Fetch the o'er player, would ye?\n");
	  System.out.println( "Enter anything t' clear ye board an' continue..." );
	  scIn.next();
	  //This pads the screen to hide the other player's info
	  //At least on my machine you can't scroll that many lines back
	  for( int i = 0; i < 1000; i++ )
		System.out.println();
  }

  //Unchanged
  public static void initBoard(char[][] game) {
    for (int r=0; r<game.length; r++)
      for (int c=0; c<game[r].length; c++)
        game[r][c] = '+';
   }

  public static void printBoard(String playerName, char[][] game) {
    for (int r=0; r<game.length; r++) {
      for (int c=0; c<game[r].length; c++)
        System.out.print( game[r][c] + "  ");
      System.out.println();
    }
    System.out.println("That's the board for " + playerName +'\n' + '\n');
  }

  public static boolean takeATurn(Player p, char[][] b, char[][] atBoats) {
    System.out.println("Ok " + p.getName() + " here we go!");
    int theRow = p.move("row");
    int theColumn = p.move("column");
    if (theRow < 0 || theColumn < 0) {
        System.out.println("Exiting Game Before Completion . . . ");
        return true;
    }
    
    //If the shot does not hit a '+' (empty) or an old shot (w/ getName)
    //Then the only boat in this game (a05, pt02) has been hit!
    if( atBoats[theRow][theColumn] != '+' && atBoats[theRow][theColumn] != p.getName().charAt(0)){
		b[theRow][theColumn] = 'X';
		printBoard(p.getName(), b);
		System.out.println( "\nOpponent: \"Yaaar!  You ate my Jalapeño!\"" );
		System.out.println( "\nYou Win!" );
		return true;
	}
	else{
		b[theRow][theColumn] = p.getName().charAt(0);
		//I added my switchPlayers method to hide each player's board
	    printBoard(p.getName(), b);
		switchPlayers();
	    return false; // Fixed!  Please see lines 180-182
	}
  }
  public static void main(String[] args) {
    char [][] player1_board = new char[BOARD_DIMENSION][BOARD_DIMENSION];
    char [][] player2_board = new char[BOARD_DIMENSION][BOARD_DIMENSION];     
    Player p1 = new Player("Pat", BOARD_DIMENSION);
    Player p2 = new Player("Atilla", BOARD_DIMENSION);   
    initBoard(player1_board);
    initBoard(player2_board);  
    boolean done = false;
    
    //I'm using a second set of boards to keep boat locations a secret
    char [][] player1_boats = new char[BOARD_DIMENSION][BOARD_DIMENSION];
    char [][] player2_boats = new char[BOARD_DIMENSION][BOARD_DIMENSION];  
    initBoard( player1_boats );
    initBoard( player2_boats );
 
	//Launch my boatSetter method
    boatSetter( p1, player1_boats );
    boatSetter( p2, player2_boats );

    //I like this if statement better than the old do{}while(!done),
    //because it now lets p1 quit without waiting for p2's turn.
    //
    //Also, this was a cool way to make an infinite loop on purpose!
    for( ; ; )
      if( takeATurn(p1, player2_board, player2_boats) == true || takeATurn(p2, player1_board, player1_boats) == true)
		break;
   }
}
//ascii art copied and modified from http://www.ludd.luth.se/~vk/q/g99/pirate_ship.html.gz
