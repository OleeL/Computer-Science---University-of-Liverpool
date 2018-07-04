import java.util.Scanner; //used for inputs
import java.lang.Math; //used to create random integers

public class Game
{
	/*	
	Returns a list of 6 values.
	The 6 values represents the 3 dice
	rolls for the 2 players.
	l[0],l[1],l[2] are the numbers for player 1
	l[3],l[4],l[5] are the numbers for player 2
	*/
    public static int[] roll(){
		int[] l = new int[6];
		for (int i=0; i<6; i++)
			l[i] = (1 + (int)(6 * Math.random()));
		return l;}

	//adds up all of the rounds for the final output
	public static int sumOfList(int[] l){
		int sum = 0;
		for (int i=0; i<l.length; i++)
			sum += l[i];
		return sum;}


    public static void main(String[] args)
    {
		int rounds, roundspassed = 1, totalWins1 = 0, totalWins2 = 0; //defining the integers here for later use
        Scanner sc = new Scanner(System.in); //creating the input
        int[] r = new int[6]; //r represents 'random' as the random values will be kept there
		/*
		r[0],r[1],r[2] for player 1
		r[3],r[4],r[5] for player 2

		 I have the next input in a while loop for data validation
		If the user enters a valid input, the user breaks from the loop
		and moves to the game, otherwise they will be stuck answering the same question */
        while (true)
		{
			System.out.print("Input the number of rounds to play (min 0): ");
			rounds = sc.nextInt();
			if (rounds > -1)
				break;
			else
				System.out.println("Please enter a positive integer or 0");
		}
		//list of integers that hold the players scores. These update every round
		int[] player1scores = new int[rounds];
		int[] player2scores = new int[rounds];
		//list of three dice scorers. these update every round like the playerscores list
        ThreeDiceScorer[] tdsList1 = new ThreeDiceScorer[rounds];
	    ThreeDiceScorer[] tdsList2 = new ThreeDiceScorer[rounds];
	    //if the rounds are equal to 0 then quit the game
        if (rounds != 0)
        {
			while (rounds >= roundspassed) //if the rounds have complete then finish
			{
				r = roll(); //r represents 'random' as the random values will be kept there from the roll() function
				tdsList1[roundspassed-1] = new ThreeDiceScorer(r[0],r[1],r[2]);//instantiates the 3 dice scorer for player 1
				tdsList2[roundspassed-1] = new ThreeDiceScorer(r[3],r[4],r[5]);//instantiates the 3 dice scorer for player 2

				//OUTPUTS THE PLAYER ROUNDS, DICE ROLLS AND SCORE
				System.out.print("\nRound: "+roundspassed+"    Results:    ");
				System.out.print("Player 1: "+tdsList1[roundspassed-1].getDie1()+" "+tdsList1[roundspassed-1].getDie2()+" "+tdsList1[roundspassed-1].getDie3()+"  Score: "+tdsList1[roundspassed-1].getScore());
				System.out.print("    Player 2: "+tdsList2[roundspassed-1].getDie1()+" "+tdsList2[roundspassed-1].getDie2()+" "+tdsList2[roundspassed-1].getDie3()+"  Score: "+tdsList2[roundspassed-1].getScore());

				//OUTPUTS WHO HAS WON THE ROUND
				if (tdsList1[roundspassed-1].getScore() > tdsList2[roundspassed-1].getScore())
					System.out.print("     Round winner is player 1.");
				else if (tdsList1[roundspassed-1].getScore() == tdsList2[roundspassed-1].getScore())
					System.out.print("     Round is tied!");
				else System.out.print("     Round winner is player 2.");
				roundspassed++;
			}

			//Gets all the player's scores into a list
			for (int i = 0; i < rounds; i++){
				player1scores[i] = tdsList1[i].getScore();
				player2scores[i] = tdsList2[i].getScore();}

			//counts the total wins and losses for each plyer
			//if it's a draw then don't increment either
			for (int i = 0; i < rounds; i++){
				if (player1scores[i] > player2scores[i])
					totalWins1 += 1;
				else if (player1scores[i] == player2scores[i])
					;
				else totalWins2 += 1;}

			//outputs who has won the game, the average points per round for the players and the players scores.
			System.out.println("\n\nTotal wins: \n     Player 1: "+totalWins1+"     Player 2: "+totalWins2);
			System.out.println("Total points: \n     Player 1: "+sumOfList(player1scores)+"     Player 2: "+sumOfList(player2scores));
	        System.out.print("Average points per rounds:\n     Player 1: "+Average.ret(player1scores)); 
	        System.out.println("     Player 2: "+Average.ret(player2scores)); 

	        //outputs the winner and the loser. if it's a tie, output then output that it's a tie
	        if (totalWins1 > totalWins2)
				System.out.println("Overall points winner is player 1.");
			else if (totalWins1 == totalWins2)
				System.out.println("Overall points outcome is tied!");
			else
				System.out.println("\nOverall points winner is player 2.");
		}
		else
		{
			System.exit(0); //exits the game
		}
	}
}