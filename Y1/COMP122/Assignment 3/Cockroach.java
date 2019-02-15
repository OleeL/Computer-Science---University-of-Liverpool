public abstract class Cockroach{
	//attributes
	private String name;
    private int[] highest_floors = new int[2000];
    private int[] stepsList      = new int[2000];
    private int iteration        = 0;             //increments for the floors setting
    private int steps            = 0;             //the number of steps taken.
    private int floor            = 1;			  //the floor they're currently on
    private int highest_floor    = 1;			  //the highest floor they've been on

	//methods
	public Cockroach(String n)
    {
		this.name = n;
    }

    //this will need to be used by the subclasses as they take steps differently
    public abstract void takeStep();

    //this will update the highest floor. I can call this every time the steps chaange.
    protected void updateHighestFloor()
    {
        if (this.floor > this.highest_floor)
            this.highest_floor = this.floor;
    }

    //saves the score of the highest floor. this is called on every experiment of the
    //highest floor in every 100 steps.
    public void save_score()
    {
    	this.highest_floors[this.iteration++] = this.highest_floor;
    }

    //resets the cockroach's attributes for the next experiment
    public void restart()
    {
        this.highest_floor = 1;
        this.floor         = 1;
		this.steps         = 0;
    }

    //sets the floor that they're on
    protected void setFloor(int f)
    {
        if (f < 1)
            this.floor = 1; 
        else if (f > 102)
            this.floor = 102;
        else
            this.floor = f;
    }

    //returns the number of attempts the cockroach takes to get the floor 102
    public int[] toTop()
    {
        for (int i = 0; i < 2000; i++){
            while (this.floor < 102){ //while the cockroach hasn't reached top floor, keep walking
                this.takeStep();
                this.step();
            }
            this.stepsList[i] = this.steps;
            this.restart();
        }
        return this.stepsList;
    }

    //takes a step
    private void step()
    {
    	this.steps++;
    }

    //rolls the dice
    protected int take_chance(int n1, int n2)
    {
        return n1 + (int)(n2 * Math.random());
    }

    //returns the cockroach's name
    public String getName()
    {
        return this.name;
    }

    //returns the floor they're on
    public int getFloor()
    {
    	return this.floor;
    }

    //returns the highest floor list
    public int[] getHighestFloors()
    {
        return this.highest_floors;
    }

}