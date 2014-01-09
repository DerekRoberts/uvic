/** Assignment:	1, part 2
    Author:	Derek Roberts
    Student ID:	V00698880
    Class:	CSC 110
    Section:	A02
    Tutorial:	B06
    Purpose:	This program creates methods to draw totem pole representations
    		of a raven, a fish and a human.  The main method uses a for loop
    		to call those methods and draw the human totem once, followed by
    		four repetitions of the fish and the raven.
    Note:	Why on earth does the raven have nipples?!?
*/

public class a01pt02
{
	public static void main (String[] args)
	{
		for (int counter = 1 ; counter <= 5 ; counter++)
		{
			
			if (counter == 1)
			{
				Human();	// Draws a human totem
			}
			else
			{
				Fish();		// Draws a fish totem
				Raven();	// Draws a raven totem
			}			
		}
			AwesomeBatBonus();

	}
	
	// The Raven method draws a raven-themed totem-chunk
	// Output = block of text art, Console input = none
	
	public static void Raven ()
	{
		System.out.println(":        .-\"\"-.        :");
		System.out.println(":        : ';':        :");
		System.out.println(":`.     `'.  .''     .':");
		System.out.println(" : `=._ `- '' -' _.-'.:");
		System.out.println("  :      .    .      :");
		System.out.println("   `.._`.      .'_..'");
		System.out.println("     `-.:      :");
		System.out.println("        :      :");
		System.out.println("        `:.__.:'");
		System.out.println("");

	}
	
	// The Fish method draws a delicious looking fish-themed totem-chunk
	// Output = block of text art, Console input = none

	public static void Fish ()
	{
		System.out.println("          _,--/");
		System.out.println("       .-'---./_    __");
		System.out.println("      /o        \"-.' /");
		System.out.println("      \\        _.-' _\\");
		System.out.println("       \"\\|--''");
		System.out.println("");
	}
	
	// The Human method draws a human-themed totem-chunk
	// Output = block of text art, Console input = none
	
	public static void Human ()
	{
		System.out.println("       \\\\\\|||///");
		System.out.println("       =========");
		System.out.println("       | o   o |");
		System.out.println("        \\  w  /");
		System.out.println("         |   |");
		System.out.println("    (    )   (    )");
		System.out.println("     \\//|*   *|\\\\/");
		System.out.println("      \\/(  *  )\\/");
		System.out.println("         =====");
		System.out.println("         (\\|/)");
		System.out.println("         || ||");
		System.out.println("       .-'| |---.");
		System.out.println("      '---' ----'");
		System.out.println("");
	}

	// The AwesomeBatBonus method draws Batman
	// Output = Action and Adventure, Console input = none
	
	public static void AwesomeBatBonus ()
	{
		System.out.println("Thoroughly           Tb.          Tb.                                ");              
		System.out.println("Awesome              :$$b.        $$$b.                              ");
		System.out.println("Bonus ->             :$$$$b.      :$$$$b.                            ");
		System.out.println("                     :$$$$$$b     :$$$$$$b                           ");
		System.out.println("                      $$$$$$$b     $$$$$$$b                          ");
		System.out.println("                      $$$$$$$$b    :$$$$$$$b                         ");
		System.out.println("                      :$$$$$$$$b---^$$$$$$$$b                        ");
		System.out.println("                      :$$$$$$$$$b        \"\"\"Tb                       ");
		System.out.println("                       $$$$$$$$$$b    __...__`.                      ");
		System.out.println("                       $$$$$$$$$$$b.g$$$$$$$$$pb                     ");
		System.out.println("                       $$$$$$$$$$$$$$$$$$$$$$$$$b                    ");
		System.out.println("                       $$$$$$$$$$$$$$$$$$$$$$$$$$b                   ");
		System.out.println("                       :$$$$$$$$$$$$$$$$$$$$$$$$$$:                  ");
		System.out.println("                       :$$$$$$$$$$$$$\"T$$$$$$$$$$P:                  ");
		System.out.println("                       :$$$$$$$$$$$$$b  \"\"T$$$$P' :                  ");
		System.out.println("                       :$$$$$$$$$$$$$$b._.g$$$$$p.db                 ");
		System.out.println("                       :$$$$$$$$$$$$$$$$$$$$$$$$$$$$:                ");
		System.out.println("                       :$$$$$$$$\"\"\"\"\"T$$$$$$$$$$$$P\":                ");
		System.out.println("                       :$$$$$$$$       \"\"\"\"T$$$P\"'  :                ");
		System.out.println("                       :$$$$$$$$    .'       `\"     :                ");
		System.out.println("                       $$$$$$$$:   /                :                ");
		System.out.println("                       $$$$$$$$:           .----,   :                ");
		System.out.println("                       $$$$$$$$:         ,\"          :               ");
		System.out.println("                       $$$$$$$$$p.                   |               ");
		System.out.println("                      :$$$$$$$$$$$$p.                :               ");
		System.out.println("                      :$$$$$$$$$$$$$$$p.            .'               ");
		System.out.println("                      :$$$$$$$$$$$$$$$$$$p...___..-\"                 ");
		System.out.println("                      $$$$$$$$$$$$$$$$$$$$$$$$$:                     ");
		System.out.println("   .db.          bug  $$$$$$$$$$$$$$$$$$$$$$$$$$                     ");
		System.out.println("  d$$$$bp.            $$$$$$$$$$$$$$$$$$$$$$$$$$:                    ");
		System.out.println(" d$$$$$$$$$$pp..__..gg$$$$$$$$$$$$$$$$$$$$$$$$$$$                    ");
		System.out.println("d$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$p._            .gp. ");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$p._.ggp._.d$$$$b");
	}
	
}

// Batman stolen from http://www.chris.com/ascii/index.php?art=comics%2Fbatman

