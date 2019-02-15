
public class ThreeDiceScorer extends ThreeDice //ThreeDiceScorer is a subclass of ThreeDice
{ 
/*    I made this private because the game class doesn't need the points
    You need to ask for the score, not the points. The getScore does the
    Calulation*/
    private int points; 

    public ThreeDiceScorer(int s1, int s2, int s3) 
    {
        super(s1,s2,s3);   //inherits from ThreeDiceScorer 
        points = s1+s2+s3; //adds the dices together to get the base points
    }

    public int getScore()
    {
        if (threeSame())
            return points + 60; //sumofdie+60 points if all 3 die are the same
        if (runOfThree())
            return points + 40; //sumofdie+40 points if die goes 1,2,3 or 5,4,6 (if its a run of 3)
        if (pair())
            return points + 20; //sumofdie+20 points if 2 die are the same
        return points; //if there is no pattern, just return the sum of die
    }
}