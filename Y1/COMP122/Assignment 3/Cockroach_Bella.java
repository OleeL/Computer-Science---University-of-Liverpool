public class Cockroach_Bella extends Cockroach
{
	private boolean viewed_86_floor = false;

	//passes the cockroaches name into the superclass
	public Cockroach_Bella()
	{
		super("Bella");
	}

	//this is run every time bella takes a step
	public void takeStep()
	{
		/*		
		if bella has not viewed the 6th floor on the previous turn
		and shes already on the 86th turn, then do nothing this turn
		and make sure bella doesn't see it for the next turn
		*/
		if (!this.viewed_86_floor && this.getFloor() == 86)
			this.viewed_86_floor = true;
		else{
			int chance = this.take_chance(1,6);
			if (chance > 0 && chance < 4){ //1 in 2 chance bella will fall 1 floor
				this.viewed_86_floor = false;
				this.checkPassed86(-1);}
			else if (chance == 4){ //1 in 6 chance bella will go up 2 floors
				this.viewed_86_floor = false;
				this.checkPassed86(2);}
			else if (chance == 5){ //1 in 6 chance bella will go up 3 floors
				this.viewed_86_floor = false;
				this.checkPassed86(3);}}
		this.updateHighestFloor();
	}
	
	//this method checks if bella passes floor 86
	private void checkPassed86(int a)
	{
		//f = floor
		//a = floor amount to change
		int f = this.getFloor();
		if ((f < 86 && f+a > 86)||(f > 86 && f+a < 86))
			this.setFloor(86);
		else
			this.setFloor(f+a);
	}
}
