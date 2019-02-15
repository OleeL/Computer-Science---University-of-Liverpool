public class path extends graph
{
	public path(int number, boolean hasTwo)
	{
		super(number, hasTwo); //passes this into the super class
	}

	//runs the experiment once
	public int run()
	{
		this.reset(); //resets the values
		while (true){ //while the experiment is not complete
			if (this.isComplete()) //stops the loops if the vertices are done
				return this.getSteps();
			int randomNum = (int)((this.getCoopLength()-1) * Math.random()); //finds a random vertex
			this.oneStep(randomNum, randomNum + 1 );
		}
	}

	//resets the values for the next experiment
	protected void reset()
	{
		for (int i = 1; i < this.getCoopLength(); i++)
			this.setCoop(i, true);
		this.setCoop(0, false);
		if (this.getHasTwo())
			this.setCoop(this.getCoopLength()-1, false);
		this.setSteps(0);
	}
}