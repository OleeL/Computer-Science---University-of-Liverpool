//Name       = Oliver Legg
//Student ID = 201244658
//Email      = O.Legg@student.liverpool.ac.uk

public class Doors
{
	public static void main(String[] args)
	{
		int num_of_doors = 0;
		boolean testing = false; //used for checking which doors open when testing
		if (args.length <= 0) //if user doesnt enter args quit
		{
			System.out.println("\nOops, not enough arguments!");
			System.out.println("Usage: java Doors N (String:['g','p','s'])");
			System.exit(0);
		}

		if (args.length == 2){ //if the user enters 2 args do the extra question
			for (int i = 0; i < 3; i++)
			{			
				if ((Character.toLowerCase(args[1].charAt(i)) !=  "g".charAt(0)) && (Character.toLowerCase(args[1].charAt(i)) != "p".charAt(0)) && (Character.toLowerCase(args[1].charAt(i)) != "s".charAt(0)))
				{
					System.out.println("\nInvalid arg: "+Character.toLowerCase(args[1].charAt(i)));
					System.out.println("Usage: java Doors N (String:['g','p','s'])");
					System.exit(0);
				}
			}
		}
		try 
		{
			num_of_doors = Integer.parseInt(args[0]); //makes arg 1 an integer
		} 
		catch (NumberFormatException e) //if it's not an integer tell the user
		{
			System.out.println("\nOops, enter an integer!");
			System.out.println("Usage: java Doors N (String:['g','p','s'])");
			System.exit(0);
		}
		if (num_of_doors < 1 || num_of_doors > 1000000) //if the user's arg 1 input is below 1 and above 1000000
		{
			System.out.println("\nN must be between 1 and 1000000 !");
			System.out.println("Usage: java Doors N (String:['g','p','s'])");
			System.exit(0);
		}
		if (args.length > 2) //if the user inputs too many arguments tip the user and exit.
		{
			System.out.println("\nOops, too many arguments!");
			System.out.println("Usage: java Doors N (String:['g','p','s'])");
			System.exit(0);
		}
		String sequence = ""; //defining
		boolean forget = (args.length == 1); //if 2 args entered (forget means to forget the doors[] after each turn)
		players[] p = new players[3]; //creating the players list
		p[0] = new ginny(num_of_doors, forget); //creating the players
		p[1] = new petra(num_of_doors, forget);
		p[2] = new sven(num_of_doors,  forget);
		if (!forget)
			sequence = args[1].toLowerCase(); //converts the second arg to lowercase
		if (forget)
		{	
			for (int i = 0; i < p.length; i++)
			{
				System.out.println(p[i].getName());
				p[i].run();  //runs the abstract method (flips the doors)
				if (testing) //if testing is enabled,
					System.out.println(p[i].getList()); //output specifically what doors are open
				System.out.println(p[i].getDoorsOpen()+" doors open"); 
				System.out.println();
			}
		}
		else
		{
			int n = 0;
			for (int v = 0; v < sequence.length(); v++) //loop for the number of characters in the string of the second arg.
			{
				if (sequence.charAt(v) == "g".charAt(0))      //if the character is equal to g then it's ginny
					n = 0; //saves the number (index of the player)
				else if (sequence.charAt(v) == "p".charAt(0)) //if the character is equal to g then it's petra
					n = 1;
				else if (sequence.charAt(v) == "s".charAt(0)) //if the character is equal to g then it's sven
					n = 2;
				System.out.println(p[n].getName());			  //outputs the name of the characters 
				p[n].run();  //runs the program the the player
				if (testing) //if testing is enabled,
					System.out.println(p[n].getList());  //output what doors are open
				System.out.println(p[n].getDoorsOpen()+" doors open");
				System.out.println();
			}
		}
	}
}