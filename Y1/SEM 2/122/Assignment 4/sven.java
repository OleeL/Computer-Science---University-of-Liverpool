public class sven extends players
{
	public sven(int number_of_doors, boolean forget_doors)
	{
		//passes the name, the number of doors +1 and whether it's doing the extra question.
		super("Sven", number_of_doors+1, forget_doors);
	}
	
	public void run()
	{
		if (forget_doors)
		{
			for (int i = 1; i < number_of_doors; i++){
				for (int v = i+1; v < number_of_doors; v++)
				{
					//checking to see if the numbers are perfect squares
					if (isSquared(i) && isSquared(v))
					{
						//if the two numbers summed are greater than the list size then i and v can be flipped.
						if ((i + v) < number_of_doors){
							if (doors[i]) 
								doors[i] = false;
							else
								doors[i] = true;
							if (doors[(i + v)]) 
								doors[(i + v)] = false;
							else
								doors[(i + v)] = true;
						}
					}
				}
			}
		}
		else //does everything above but it changes doors_global[] not doors[].
		{
			for (int i = 1; i < number_of_doors; i++){
				for (int v = i+1; v < number_of_doors; v++)
				{
					//checking to see if the numbers are perfect squares
					if (isSquared(i) && isSquared(v))
					{
						if ((i + v) < number_of_doors){
							if (doors_global[i]) 
								doors_global[i] = false;
							else
								doors_global[i] = true;
							if (doors_global[(i + v)]) 
								doors_global[(i + v)] = false;
							else
								doors_global[(i + v)] = true;
						}
					}
				}
			}
		}
	}

	private boolean isSquared(int n)
	{
		//the square root of a number is a decimal number if it's not a perfect square
		//this checks to see if the numbers are perfect squares
		double n2 = Math.sqrt(n);
		if (n2 == (int) n2)
			return true;
		else 
			return false;
	}
}