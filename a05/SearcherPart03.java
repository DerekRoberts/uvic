/** Assignment:	5, part 3
    Author:	Derek Roberts
    Student ID:	V00698880
    Date:	December 1, 2010
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
    Input:		The users will enter board coordinates for their ships
				at first, but then for shooting the enemy.  
    Output:		A mockup of battleship.  There will be feedback for hits
				and misses as well as an end to the game.
    Purpose:	To digest and conquor!
*/

import java.util.*;			//For Scanner
import java.lang.Math.*;	//For Math.abs = absolute value function

public class SearcherPart03 {
  public static final int BOARD_DIMENSION = 10;
  
  //Item numbers, and lengths will never change, but hit points will
  private static final int[]    menuLst    ={ 0, 1, 2, 3, 4, 5 };
  private static final int[]    menuLng    ={ 1, 1, 2, 3, 4, 5 };
  private static       int[]    menuHitPts ={ 1, 1, 2, 3, 4, 5 };

  //Here's the menu followed by the old boat names
  private static final String[] menuNew ={ "Jalapeño", "Chipotle",
							"Nacho", "Taco", "Burrito", "Quesadilla" };
  private static final String[] menuOld ={ "Dingy", "Dingy", "Sailboat",
							"Yacht", "BC Ferry", "Cruise Ship" };
							
  //I'll need to have scanner ready too
  private static Scanner scIn = new Scanner( System.in );

  //Purpose: To set location on the board for a boat (unitially, dingy).
  //Input:   The Player's name and their own player's board.
  //         The move method in the Player class will prompt the user
  //         board locations.
  //Output:	 Some printing to the screen.
  public static void boatSetter( Player p, char[][] game )
  {
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

	boatMarker( p, game );
	switchPlayers();
  }
  
  public static void boatMarker( Player pNum, char[][] board )
  {  
	//Initialize Scanner stdin
	Scanner inScan = new Scanner( System.in );
	
	//do-while runs as long as any boats/snacks need to be set up.
	//menuLst[i] set to -1 once positioned on board
	do {
		//Print out the delicious menu of spicy violence
		System.out.println( "\n  #. Item       \tLength \t\tFormerly " );
		for( int i = 0; i <=5; i++ )
			if( menuLst[i] >= 0 )
				System.out.println( "  "+menuLst[i]+". " +menuNew[i]+ "\t\t" +menuLng[i]+ "\t\t" +menuOld[i]);
		
		System.out.print( "\nMenu Selection: " );
		int foodNum = inScan.nextInt();
		
		//While loop checks if foodNum is within range and matches an 
		//available option.  If not it insults you and asks again.
		while( foodNum < 0 || foodNum > 5 || menuLst[foodNum] < 0 ){
			System.out.println( "\nAhm not getting yeh a kids' menu, Scherlock.   ...yar." );
			System.out.print( "\nMenu Selection: " );
			foodNum = inScan.nextInt();
			System.out.print( "\n" );
		} 
		
		//If statement puts an "s" at the end of "square" if length > 1
		System.out.print( "\n" +menuNew[foodNum]+ ", length = " +menuLng[foodNum]+ " square" );
		if( menuLng[foodNum] == 1 )
			System.out.println( ".\n" );
		else
			System.out.println( "s.\n" );
	
		printBoardSetup( pNum.getName(), board );

		//first and second set of coordinates to set on board
		//single length items only use firstCoord
		int[] firstCoord  = new int[2];
		int[] secondCoord = new int[2];
	
		//This do-while takes care of snacks/boats with length = 1
		while( menuLng[foodNum] == 1 && menuLst[foodNum] >= 0 ){
			System.out.print( "Pick the single set of coordinates for your "+ menuNew[foodNum]+ ".\n\n" );
			firstCoord[0] = pNum.move( "row" );
			firstCoord[1] = pNum.move( "column" );		
			if( board[firstCoord[0]][firstCoord[1]] == "+".charAt(0) ){
				board[firstCoord[0]][firstCoord[1]] = menuNew[foodNum].charAt(0);
				menuLst[foodNum] = -1;
				printBoardSetup( pNum.getName(), board);
				break;
			}
			System.out.println( "\nI t'ink you oughta try that 'un again.\n" );
		}
		
		//Here's a list of requirements for start and end coordinates
		//Are they in a straight line?  Vertically or horizontally?
		boolean checkInLine = false;
		boolean checkVert   = false;
		boolean checkHoriz  = false;
		//Are they the required length appart?
		boolean checkLength = false;
		//Are all spaces requested free?  This one starts true.
		boolean checkSpaces = true;
//		//If all checks are satisfied and then I accept them?
//		boolean checkAccept = false;
		
		//This do-while takes care of snacks/boats with length > 1
		while( menuLng[foodNum] > 1 && menuLst[foodNum] >= 0 ){
			System.out.print( "Pick the first set of coordinates for your "+ menuNew[foodNum]+ ".\n\n" );
			firstCoord[0]  = pNum.move( "row" );
			firstCoord[1]  = pNum.move( "column" );
			System.out.print( "\nNow pick something " +( menuLng[foodNum]-1 )+ " up or down.\n\n" );
			secondCoord[0] = pNum.move( "row" );
			secondCoord[1] = pNum.move( "column" );
			
			//Checks vertical and horizontal direction, only one is
			//allowed to be true for checkInLine to be true
			if( firstCoord[0] == secondCoord[0] )
				checkHoriz = true;
			if( firstCoord[1] == secondCoord[1] )
				checkVert  = true;
				
			if( checkHoriz == true && checkVert == false ){
				checkInLine = true;
			}
			else if( checkHoriz == false && checkInLine == true ){
				checkInLine = true;
			}
			else{
				checkInLine = false;
			}
								
			//Checks to see if they are the correct length appart and if
			//all spaces are available for placement
			if( firstCoord[0] == Math.abs( secondCoord[0] - menuLng[foodNum] + 1 )){	//horizontal
				checkLength = true;
				for( int i = 0; i < ( menuLng[foodNum] - 1 ); i++ ){
					if(( firstCoord[0] + 1 ) != '+' )
						checkSpaces = false;
				}
				if( checkSpaces = true ){
					menuLst[foodNum] = -1;
					for( int i = 0; i < menuLng[foodNum]; i++ )
						board[( firstCoord[0] + i )][firstCoord[1]] = menuNew[foodNum].charAt(0);
				}
			}
			if( firstCoord[1] == Math.abs( secondCoord[1] - menuLng[foodNum] + 1 )){	//vertical
				checkLength = true;
				for( int i = 0; i < ( menuLng[foodNum] - 1 ); i++ ){
					if(( firstCoord[0] + 1 ) != '+' )
						checkSpaces = false;
				}
				if( checkSpaces = true ){
					menuLst[foodNum] = -1;
					for( int i = 0; i < menuLng[foodNum]; i++ )
						board[firstCoord[0]][( firstCoord[1] + i )] = menuNew[foodNum].charAt(0);
				}
			}
			
			
			
			if( checkLength && checkSpaces && menuLst[foodNum] < 0 ){
				System.out.println( "Woo hoo!" );
				printBoardSetup( pNum.getName(), board);
				break;
			}
			else
				System.out.println( "\nRejected, graaaah!  ...Let's be trin' that again.\n" );
		}
		
	} while ( menuLst[0] >= 0 || menuLst[1] >= 0 || menuLst[2] >= 0 || menuLst[3] >= 0 || menuLst[4] >= 0 ||menuLst[5] >= 0 );	
	System.out.println( "Yaaarrr!  Tis a glorious fleet of indigestion!\n\n" );
	
	//Reset values after successful completion
	menuLst[0] = 0;
	menuLst[1] = 1;
	menuLst[2] = 2;
	menuLst[3] = 3;
	menuLst[4] = 4;
	menuLst[5] = 5;

  }

  public static void switchPlayers()
  {
	  System.out.println( "\nFetch the o'er player, would ye?\n");
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

  //Modified not to show enemy's boats/lunch
  public static void printBoard(String playerName, char[][] game) {
    for (int r=0; r<game.length; r++) {
      for (int c=0; c<game[r].length; c++)
		if( game[r][c] == 'X' || game[r][c] == '_' || game[r][c] == '+' )
			System.out.print( game[r][c] + "  ");
		else
			System.out.print( "+  " );
      System.out.println();
    }
    System.out.println("That's the board for " + playerName +'\n' + '\n');
  }

  //This is the original printBoard method, which is still used to show
  //all boat locations characters during setup
  public static void printBoardSetup(String playerName, char[][] game) {
    for (int r=0; r<game.length; r++) {
      for (int c=0; c<game[r].length; c++)
		System.out.print( game[r][c] + "  ");
      System.out.println();
    }
    System.out.println("That's the board for " + playerName +'\n' + '\n');
  }

  public static boolean takeATurn( Player p, char[][] b ) {
	printBoard(p.getName(), b);
    System.out.println("Ok " + p.getName() + " here we go!");
    int theRow = p.move("row");
    int theColumn = p.move("column");
    if (theRow < 0 || theColumn < 0) {
		//Text changed to Game Over, because same escape used for win
        System.out.println( "Game Over!" );
        return true;
    }
    
    //I stopped using P and A to show shots, because all boats are
    //marked using single capital letters.  Instead it's _ or X.
    //
    //If the shot hits a '+' or an old '_'then it's a miss
    //then it's a hit!  I used underscore '_; for miss
    if( b[theRow][theColumn] == '+' || b[theRow][theColumn] == 'X' ){
		b[theRow][theColumn] = '_';
		printBoard(p.getName(), b);
		System.out.println( "\nMiss!\n" );
	}
	else{
		//I'm using "X"s to show hits
		//Hitting an old "X" will show a hit again, which is sorta right
		b[theRow][theColumn] = 'X';
		System.out.println( "\nHit!\n" );
	}
	//I added my switchPlayers method to hide each player's board
	printBoard(p.getName(), b);
	if( boatCheck( p, b ) == false )
		switchPlayers();
	
	//If all boats/snacks have been eaten the game will end
	return boatCheck( p, b );
  }
  
  //This method counts spots remaining with targets in them and returns
  //boolean true if the whole fleet has been eaten
  public static boolean boatCheck( Player p, char[][] board ){
	  //Health checkers for each snack in the fleet
	  int aliveJ = 0;
	  int aliveC = 0;
	  int aliveN = 0;
	  int aliveT = 0;
	  int aliveB = 0;
	  int aliveQ = 0;

	  for( int i = 0; i < BOARD_DIMENSION; i++ ){
		  for( int j = 0; j < BOARD_DIMENSION; j++ ){
			  if( board[i][j] == 'J' )
				aliveJ += 1;
			  if( board[i][j] == 'C' )
				aliveC += 1;
			  if( board[i][j] == 'N' )
				aliveN += 1;
			  if( board[i][j] == 'T' )
				aliveT += 1;
			  if( board[i][j] == 'B' )
				aliveB += 1;
			  if( board[i][j] == 'Q' )
				aliveQ += 1;
		  }
	  }
	  System.out.println( "Here's what remains for the eatin':" );
	  System.out.println( "Jalapeño hits left   = " +aliveJ+ "\tTaco hits left       = " +aliveT);
	  System.out.println( "Chipotle hits left   = " +aliveC+ "\tBurrito hits left    = " +aliveB );
	  System.out.println( "Nacho hits left      = " +aliveN+ "\tQuesadilla hits left = " +aliveQ+ "\n");
	  
	  if(( aliveJ + aliveC + aliveN + aliveT + aliveB + aliveQ ) == 0 ){
		  System.out.println( "\nOpponent:\"YaaaaAAAaaR!  You ate my lunch!\"" );
		  System.out.println( p.getName() + " is stuffed full of victory!" );
		  return true;
	  }
	  else
		return false; // Fixed!  Please see lines 180-182				//Verify
  }
  
  public static void main(String[] args) {
    char [][] player1_board = new char[BOARD_DIMENSION][BOARD_DIMENSION];
    char [][] player2_board = new char[BOARD_DIMENSION][BOARD_DIMENSION];     
    Player p1 = new Player("Pat", BOARD_DIMENSION);
    Player p2 = new Player("Atilla", BOARD_DIMENSION);   
    initBoard(player1_board);
    initBoard(player2_board);  
    boolean done = false;
 
	//Launch my boatSetter method
    boatSetter( p1, player1_board );
    boatSetter( p2, player2_board );

    //I like this if statement better than the old do{}while(!done),
    //because it now lets p1 quit without waiting for p2's turn.
    //
    //Also, this was a cool way to make an infinite loop on purpose!
    for( ; ; )
      if( takeATurn(p1, player2_board) == true || takeATurn(p2, player1_board) == true)
		break;
   }
}
//ascii art copied and modified from http://www.ludd.luth.se/~vk/q/g99/pirate_ship.html.gz
