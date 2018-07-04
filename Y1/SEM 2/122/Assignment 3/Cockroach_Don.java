public class Cockroach_Don extends Cockroach
{
	//passes the cockroaches name into the superclass
	public Cockroach_Don()
	{
		super("Don");
	}
	public void takeStep()
	{
		//0.1% chance of flying into the centre of the strailwell and falling to gnd floor
		if (this.take_chance(1,1000) == 1)
			this.setFloor(1);
		else{ 
			int chance = this.take_chance(1,6);
			if (chance == 1 || chance == 2) //1 in 3 chance the cockroach will fall down
				this.setFloor(this.getFloor() - 1);
			else if (chance > 2 && chance < 6) //1 in 2 chance the cockroach will move up
				this.setFloor(this.getFloor() + 1);
			else //1 in 6 chance the cockroach can move up the amount corresponding to the next die roll
				this.setFloor(this.getFloor() + this.take_chance(1,6));}
		this.updateHighestFloor(); //checks if the cockroach has reached a new highest floor.
	}
}
