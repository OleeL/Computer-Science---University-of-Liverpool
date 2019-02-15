public class petra extends players
{
	public petra(int number_of_doors, boolean forget_doors)
	{
		//passes the name, the number of doors +1 and whether it's doing the extra question.
		super("Petra", number_of_doors+1, forget_doors);
	}
	
	public void run()
	{
		int[] flippers; //the ints you need to flip
		if (forget_doors){ 
			for (int i = 0; i < number_of_doors; i++){ //loops for the number of doors that are there.
				if (i < 2) //if i < 2 then it's going to be false
					doors[i] = false;
				else{
					if (isPrime(i)){ //if it's a prime number then the the times tables of i must be flipped.
						flippers = multOf(i, (number_of_doors-1)); //times tables of i.
						for (int v = 0; v < flippers.length; v++){ //loops through the list of times tables.
							if (doors[flippers[v]])  //if it's true make it false
								doors[flippers[v]] = false; 
							else 
								doors[flippers[v]] = true;
						}
					}
				}
			}
		}
		else //does everything above but it changes doors_global[] not doors[].
		{
			for (int i = 0; i < number_of_doors; i++){
				if (i < 2)
					doors_global[i] = false;
				else{
					if (isPrime(i)){
						flippers = multOf(i, (number_of_doors-1));
						for (int v = 0; v < flippers.length; v++){
							if (doors_global[flippers[v]]) 
								doors_global[flippers[v]] = false;
							else
								doors_global[flippers[v]] = true;
						}
					}
				}
			}
		}
	}

	//if the number given is a prime number return true
	private boolean isPrime(int n){
		if (n==2 || n ==3) //if the number is 2 or 3 then it's prime
		{
			return true;
		}
		else
		{
			for (int i = 2; i <= Math.ceil(Math.sqrt(n)); i++){ //loop from 2 to the sqrt of n.
				if ((n % i) == 0){ //if it's divisible by i then it's not a prime
					return false;
				}
			}
			return true;
		}
	}

	//returns a list of times tables from 0 to n
	private int[] multOf(int m, int limit)
	{
		int newLimit = (int) Math.ceil(limit/m);
		int[] list = new int[newLimit+1];
		for (int i = 0; i <= (newLimit); i++){ //loops from 0 to limit
			list[i] = i * m;
		}
		return list;
	}
}