public abstract class graph
{
	private boolean[] coop; //contains all of the -1's and 1's
	private int steps;      //the number of steps taken in an iteration
	private boolean hasTwo; //whether the user wants to have two minus 1's in an iteration

	public graph(int number, boolean hasTwo)
	{
		this.steps = 0;					 //number of steps default set to 0
		this.hasTwo = hasTwo;			 
		this.coop = new boolean[number]; //number of vertices set to user input
	}

	public abstract int run();			 //meant to be used by path and cycle subclasses
	protected abstract void reset(); 	 //meant to be used by path and cycle subclasses

	//compares sets the 2 to whatever they're meant to be set to in an iteration
	protected void oneStep(int i1, int i2)
	{
		//if two -1's then they become cooperative
		if (!this.coop[i1] && !this.coop[i2])
		{
			this.coop[i1] = true;
			this.coop[i2] = true;
		}
		//if one is non cooperative, then they're not cooperative
		else if ((!this.coop[i1] && this.coop[i2]) || (!this.coop[i2] && this.coop[i1]))
		{
			this.coop[i1] = false;
			this.coop[i2] = false;
		}
		this.setSteps(this.getSteps()+1);
	}

	//checks whether the all of the vertices are cooperative
	protected boolean isComplete()
	{
		for (int i = 0; i < this.getCoopLength(); i++)
		{
			if (!this.coop[i])
				return false;
		}
		return true;
	}

	//sets an index in the coop function
	protected void setCoop(int index, boolean bool)
	{
		this.coop[index] = bool;
	}

	//sets the steps to a certain number given
	protected void setSteps(int n)
	{
		this.steps = n;
	}

	//returns the steps
	protected int getSteps()
	{
		return this.steps;
	}

	//gets the cooplength (number of vertices)
	protected int getCoopLength()
	{
		return this.coop.length;
	}

	//returns whether the user wanted two 1's at the start.
	protected boolean getHasTwo()
	{
		return this.hasTwo;
	}
}