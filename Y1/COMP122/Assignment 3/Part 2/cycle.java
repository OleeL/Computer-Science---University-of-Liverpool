public class cycle extends graph
{
	public cycle(int number, boolean hasTwo)
	{
		super(number, hasTwo); //passes this information to the superclass
	}

	//runs the experiment once
	public int run()
	{
		this.reset(); //resets the values
		while (true){ //while the experiment is not complete
			if (this.isComplete()) //stops the loops if the vertices are done
				return this.getSteps(); 
			int randomNum = (int)((this.getCoopLength()) * Math.random()); //finds a random vertex
			int randomNum2 = (randomNum+1) % this.getCoopLength(); //allows the numbers to flip around the graph. (like a circle)
			this.oneStep(randomNum, randomNum2);
		}
	}

	//resets all the attributes for the next experiment
	protected void reset()
	{
		for (int i = 0; i < this.getCoopLength(); i++)
			this.setCoop(i,true);
		if (this.getHasTwo())
			this.setCoop((int)Math.floor(this.getCoopLength()/2)-1, false);
		this.setCoop((int)this.getCoopLength()-1, false);
		this.setSteps(0);
	}
}