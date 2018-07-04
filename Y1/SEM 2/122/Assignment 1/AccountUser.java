import java.util.Scanner; //for the user input
public class AccountUser
{
/*	keeping the inputs seperate from the main function helps me 
	to understand the flow and structure of the program*/
	public static int[] inputs() 
	{
		Scanner scanner = new Scanner(System.in);
		int[] input = new int[3]; //3 inputs so the list is set to the length of 3

/*		I have the next 3 inputs in while loops for data validation
		If the user enters a valid input, the user breaks from the loop
		and moves to the next question, otherwise they will be stuck answering the same question*/
		while (true)
		{
			System.out.print("Please enter the number of daytime minutes used per month: ");
			input[0] = scanner.nextInt();
			if (input[0] >= 0) //if the user enters a number greater than or equal to 0, then the input is valid
				break;
			else
				System.out.println("Number has to be greater than or equal to 0");
		}
		
		while (true)
		{
			System.out.print("Please enter the number of evening/weekend minutes used per month: ");
			input[1] = scanner.nextInt();
			if (input[1] >= 0)//if the user enters a number greater than or equal to 0, then the input is valid
				break;
			else
				System.out.println("Number has to be greater than or equal to 0");
		}
		
		while (true)
		{
			System.out.print("Please enter the number of Megabytes used per month: ");
			input[2] = scanner.nextInt();
			if (input[2] >= 0)//if the user enters a number greater than or equal to 0, then the input is valid
				break;
			else
				System.out.println("Number has to be greater than or equal to 0");
		}
		System.out.println();
		return input; //returns the input
	}

	public static void main(String[] args)
	{
		int[] input          = new int[3]; //3 inputs so the list is set to the length of 3
		input                = inputs(); //calls the function that was mentioned above
/*		instantiates the classes and puts the user input into their parameters so the accounts
		know what to calculate*/
		BronzeAccount bronze = new BronzeAccount(input[0],input[1], input[2]);
		SilverAccount silver = new SilverAccount(input[0],input[1], input[2]);
		GoldAccount   gold   = new GoldAccount(input[0],input[1], input[2]);
		bronze.summary(); //prints the output of bronze
		silver.summary(); //prints the output of silver
		gold.summary();   //prints the output of gold
		double[] cost        = new double[3];
		double tmp;

		//puts the total cost of each account into the cost list
		cost[0] = bronze.getTotalCost();
		cost[1] = silver.getTotalCost();
		cost[2] = gold.getTotalCost();

		//orders the list from cheapest to most expensive
		if (cost[1] < cost[0]) 
		{
		   tmp = cost[1];
		   cost[1] = cost[0];
		   cost[0] = tmp;
		}

		if (cost[2] < cost[1])
		{
		   tmp = cost[2];
		   cost[2] = cost[1];
		   cost[1] = tmp;
		}

		if (cost[1] < cost[0])
		{
		   tmp = cost[1];
		   cost[1] = cost[0];
		   cost[0] = tmp;
		}
		//if all 3 accounts are the same cost
		if (cost[0] == cost[2]){ 
			System.out.println("All account types are equally as cheap. However, we recommend that you use the "+gold.account_type+" account.");
			System.out.println("This is because you get the most TV channels, a free Spotify account and music on demand.");
			System.out.println("You also have the ability to have unlimited calls and have less interest on going over broadband interest.");
		} 
		else
		{
			if (cost[0] == cost[1]){//if 2 cheapest accounts are the same cost
				if ((cost[0] == bronze.getTotalCost() && cost[1] == silver.getTotalCost() ) || (cost[0] == silver.getTotalCost() && cost[1] == bronze.getTotalCost())){ 
					System.out.println("Bronze account and Silver Account types are equally as cheap. However, we recommend that you use the "+silver.account_type+" account.");
					System.out.println("This is because you get more TV channels and a free Spotify account.");
					System.out.println("You will also get more minutes than the bronze account and you're charged less interest on going over broadband limit.");}
				else{
					if ((cost[0] == bronze.getTotalCost() && cost[1] == gold.getTotalCost() ) || (cost[0] == gold.getTotalCost() && cost[1] == bronze.getTotalCost())){
						System.out.println("Bronze and Gold account types are equally as cheap. However, we recommend that you use the "+gold.account_type+" account.");
						System.out.println("This is because you get the most TV channels, a free Spotify account and music on demand.");
						System.out.println("You also have the ability to have unlimited calls and you're charged less interest on going over broadband limit.");}
					else{
						if ((cost[0] == silver.getTotalCost() && cost[1] == gold.getTotalCost() ) || (cost[0] == gold.getTotalCost() && cost[1] == silver.getTotalCost())){
							System.out.println("Gold and Silver account types are equally as cheap. However, we recommend that you use the "+gold.account_type+" account.");
							System.out.println("This is because you get the most TV channels, a free Spotify account and music on demand.");
							System.out.println("You also have the ability to have unlimited calls and you're charged less interest on going over broadband limit.");
						}
					}
				}
			}
			else
			{
				if (cost[0] == bronze.getTotalCost()) //if the bronze account is the cheapest cost
					System.out.println(bronze.account_type+" Account is cheapest cost.");
				else
				{
					if (cost[0] == silver.getTotalCost()) //if the silver account is the cheapest cost
						System.out.println(silver.account_type+" Account is cheapest cost.");
					else //if none of the other conditions meet gold must be the cheapest cost
						System.out.println(gold.account_type+" Account is cheapest cost.");
				}
			}
		}
	}
}