/** Assignment:	5, part 1
    Author:	Derek Roberts
    Student ID:	V00698880
    Date:	November 23, 2010
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
    Input:		Varies by class, all called from outside this file.
    Output:		Varies by class, but some have a return.
    Purpose:	To prepare squishy UVic hippies for naval combat.
*/

import java.util.*;

public class Player {
	//Variable list:
	//	gamesWon	- int: games won, to be initialized in Player class
	//	name		- str: the player's name, init'd in Player class
	//	searchSpace	- int: the size of the board, init'd in Player class
	private int gamesWon; 
	private String name; 
	private int searchSpace;
	
	//Purpose:	To set the player's name, the board size and set
	//			gamesWon to its initial value of zero.
	//Input:	The player's name (string) and the board's size (int).
	//Output:	None.
	//Variable list:
	//	gamesWon	- int: games won, set initially here to zero
	//	name		- str: the player's name (from parameter)
	//	searchSpace	- int: takes value of maxSize (parameter)
	public Player(String player, int maxSize) {
		gamesWon = 0; name = player; searchSpace = maxSize; 
	}
	
	//Purpose:	Calling this method increments gamesWon.
	//Input:	None.
	//Output:	None.
	//Variable list:
	//	gamesWon	- int: private, incremented when method called 
	public void wonAGame() { 
		gamesWon++; 
	}
	
	//Purpose:	To prompt a user for coordinates on the board.  Rows or
	//			columns are expected.  The user will be stuck in a do-
	//			while loop until an acceptable number is given.  Then
	//			that integer is returned as moveIndex (int).
	//Input:	The string "row" or "column" by parameter as well as an
	//			integer via Scanner.  That integer is set to moveIndex.
	//Output:	System.out.print text and moveIndex (int) is returned.
	//Variable list:
	//	prompt		- str: passed by parameter
	//	moveIndex	- int: set via input (Scanner) and returned
	//	searchSpace	- int: private, used to reference board size
	public int move(String prompt) { 
		int moveIndex;
		Scanner input = new Scanner(System.in);
		do { 
			System.out.print("Please enter a " + prompt);
			System.out.print(" between 0 and " + (searchSpace - 1) + ": "); 
			moveIndex = input.nextInt(); 
		} while (moveIndex >= searchSpace); 
		return moveIndex; 
	}
	
	//Purpose:	To return the player's name when called.
	//Input:	None.
	//Output:	The player's name is returned.
	//Variable list:
	//	prompt		- str: private, used to reference the player's name
	public String getName() { 
		return name; 
	}
	
	//Purpose:	To return the number of games the player has won.
	//Input:	None.
	//Output:	The number of games won is returned.
	//Variable list:
	//	gamesWon	- int: private, used to reference games won
	public int getGamesWon() { 
		return gamesWon; 
	} 
}
