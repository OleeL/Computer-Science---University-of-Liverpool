public class Average {
	/*    	
	Takes a list of numbers and returns the average summing the list and dividing by
    	the length
 	*/
    public static double ret(int[] l)
    {
    	double sum = 0;
    	for (int i=0; i < l.length; i++)
    		sum = sum + l[i];
    	return (sum/l.length);
    }

    public static void main(String[] args){
		int[] unfairdice = new int[] {2,3,4,5,6,6}; //the unfair dice
		int[] pwdScores  = new int[6*6*6]; //points with dice total (pwd)
		int[] pudScores  = new int[6*6*6]; //points with unfair dice total (pud)
		int index = 0;

		//tries every possibility of the dice 1,6 and puts it into the parameters of 3 dice scorer
		for (int x = 1; x < 7; x++){
			for (int y = 1; y < 7; y++){
				for (int z = 1; z < 7; z++){
					ThreeDiceScorer pwd = new ThreeDiceScorer(x, y, z); //points with dice (pud)
					pwdScores[index] = pwd.getScore();
					index+= 1;
				}
			}
		}
		//Again, tries every possibility of the dice 1,6 and puts it into the parameters of 3 dice scorer
		System.out.print("Average with 3, fair, 6 sided die: ");
		System.out.println(ret(pwdScores)); //outputs the average of the normal dice
		index = 0;
		for (int x = 1; x < 7; x++){
			for (int y = 1; y < 7; y++){
				for (int z = 0; z < 6; z++){ //instead of numbers for the unfair dice, it uses the indexes of the unfair dice list
					ThreeDiceScorer pud = new ThreeDiceScorer(x, y, unfairdice[z]); //points with dice (pud)
					pudScores[index] = pud.getScore();
					index+= 1;
				}
			}
		}
		// prints the average of the unfair dice
		System.out.print("Average with 2, fair, 6 sided die and 1 unfair dice being: {2,3,4,5,6,6}: ");
		System.out.println(ret(pudScores));
	}
}