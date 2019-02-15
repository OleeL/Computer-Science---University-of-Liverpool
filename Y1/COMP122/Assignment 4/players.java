public abstract class players
{
	protected int number_of_doors; 
	protected boolean forget_doors; 
	protected boolean[] doors;
	protected static boolean[] doors_global; //this list is shared by all of the players (only when passing 2 args)
	private String name;
	
	public players(String name, int number_of_doors, boolean forget_doors)
	{
		this.name            = name;
		this.number_of_doors = number_of_doors;
		this.forget_doors    = forget_doors;
		this.doors           = new boolean[number_of_doors]; //creates the list to the size of the number of doors
		this.doors_global    = new boolean[number_of_doors]; 

		//sets all of the doors to closed.
		for (int i = 0; i < doors.length; i++)
		{
			doors[i]        = false; 
			doors_global[i] = false;
		}
	}
	
	//if you pass true then you get the forgotton doors list
	public int getDoorsOpen()
	{
		int count = 0;
		for (int i = 0; i < number_of_doors; i++)
		{
			if (forget_doors)
			{
				if (doors[i])
					count++; //this only has to count up the open doors and return the nunmber
			}
			else
			{
				if (doors_global[i])
					count++;
			}
		}
		return count;
	}
	
	//if you pass true then forget the doors
	public abstract void run();
	
	//returns the name
	public String getName()
	{
		return name;
	}

	//returns the doors like this "00001010100100"
	public String getList()
	{
		String output = "";
		for (int i = 0; i < number_of_doors; i++)
		{
			if (forget_doors)
			{
				if (doors[i])
					output = output + "1";
				else
					output = output + "0";
			}
			else
			{
				if (doors_global[i])
					output = output + "1";
				else
					output = output + "0";
			}
		}
		return output;
	}
}