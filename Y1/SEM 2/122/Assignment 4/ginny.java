public class ginny extends players
{
	public ginny(int number_of_doors, boolean forget_doors)
	{
		//passes the name, the number of doors +1 and whether it's doing the extra question.
		super("Ginny", number_of_doors+1, forget_doors); 
	}
	
	public void run()
	{
		if (forget_doors)
		{	
			int[] gcdlist = new int[number_of_doors]; //holds all of the possible GCD values within it's range
			for (int i = 0; i < number_of_doors; i++){ //runs over every possibility
				gcdlist[i] = gcd(number_of_doors-1, i);
			}
		    for (int i = 0; i < number_of_doors; i++){ 
		    	if (number_of_doors == 2) //if there are only 2 doors then they're all going to be true
					if (doors[i]) //if it's true then flip it to false
						doors[i] = false;
					else
						doors[i] = true;
		    	else if (gcdlist[i] == 1) //if it's a 1 then it's true
		    		if (doors[i]) //if it's true the flip it to false
						doors[i] = false;
					else
						doors[i] = true;
		    }
		}
		else //everything above this is the same except it's done with different doors list.
		{
			int[] gcdlist = new int[number_of_doors];
			for (int i = 0; i < number_of_doors; i++){
				gcdlist[i] = gcd(number_of_doors-1, i);
			}
		    for (int i = 0; i < number_of_doors; i++){
		    	if (number_of_doors == 2)
					if (doors_global[i]) 
						doors_global[i] = false;
					else
						doors_global[i] = true;
		    	else if (gcdlist[i] == 1)
    				if (doors_global[i]) 
						doors_global[i] = false;
					else
						doors_global[i] = true;
		    }
		}
	}

	//returns the greatest common divistor
	//e.g. GCD(120, 560) = 40
	private int gcd(int a, int b)
	{
		if (a == 0 || b == 0) //if they're both == 0 then it's 0.
			return 0;
		int ans = 1;
        for(int i = 1; (i <= a && i <= b); ++i) //loops from 1 to a or b (whichever is lower) 
        {
            if(a % i==0 && b % i==0) //if they're divisible then thats the GCD so far.
            	ans = i;
        }
        return ans;
	}
}