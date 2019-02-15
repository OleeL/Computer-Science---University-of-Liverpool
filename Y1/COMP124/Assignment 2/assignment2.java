/*

Name       = Oliver Legg
Student ID = 201244658
Email      = O.Legg@student.liverpool.ac.uk

*/

class assignment2 
{
	//Crafter A = 1 pot        every 5 mins
	//Crafter B = 1 pot        every 6 mins
	//Packer  A = 1 pot packed every 4 mins
	public static synchronized void main(String[] args)
	{
		packer m = new packer("Macca", 400); //macca the packer
		crafter h = new crafter("Harry", 500, m); //harry the potter
		h.start(); //starts the harry thread
		crafter b = new crafter("Beatrix", 600, m); //beatrix the crafter
		b.start(); //starts the beatrix thread
		m.start(); //starts the macca thread
	}
}

//the crafter is the class that creates the pots and puts it on the shelf.
class crafter extends Thread
{
	String name;							//the name of the potter
	private packer m;						//for the passing packer
	private volatile boolean alive = true;  //this turns to false when the crafter stops
	private volatile int pots_made = 0;     //counter for the number of pots made
	private volatile int n;					//crafter thread count
	private static   int count = 1; 		//crafter thread count
	private          int time;				//crafter time variable

	//takes input name, time and packer 
	crafter(String name, int time, packer m){
		this.name = name;
		this.time = time;
		this.n = count++; //increments the ID
		this.m = m;
		System.out.println("Potter "+n+" ("+name+") has started");
	}

	//this runs the main program for the potter
	public void run()
	{
		while (alive){ //this loop finishes when the potter has made 10 pots
			try{Thread.sleep(time);} //sleeps for the given time
			catch(InterruptedException e) {}
			System.out.println(name+" has made a pot");
			while (alive){ //loops until the shelf is not full
				if (!m.isFull()){
					m.put(true);// puts the pot in the shelf
					pots_made++;
					break;
				}
			}
			if (this.pots_made >= 10)//if the potter has made 10 pots, kill the crafter
				this.alive = false;
			System.out.println(name+" has put his pot on the shelf");
		}
	}
}

//the packer is the class that packs the pots from the shelf and also holds the shelf.
class packer extends Thread
{	
	private int time;
	private volatile String name;
	private volatile int shelf       = 0;    //The number of pots in the shelf
	private volatile int maxSize     = 5;    //max number of pots allowed on the shelf.
	private volatile int pots_packed = 0;    //pots packed is how many pots the packer has packed.
	private volatile boolean ready   = true; //if the packer is not busy - true
	private volatile boolean alive   = true;

	packer(String name, int time){
		this.name  		 = name;
		this.ready 		 = ready;
		this.alive 		 = alive;
		this.pots_packed = pots_packed;
		this.time 		 = time;
	}

	//this runs the whole program for the packer and checks if conditions meet.
	public void run() 
	{
		boolean hasOutputtedReady = false; //this is so that a statement only outputs when it needs to.
		boolean hasOutputtedEmpty = false; //this is so that a statement only outputs when it needs to.
		System.out.println("The packer ("+(this.name)+") has started");
		while(alive)
		{
			this.isDone();
			if (!hasOutputtedReady) //if the packer is ready, output the following once
			{
				System.out.println(this.name+" is ready to pack");
				hasOutputtedReady = true;
			}
			if (!hasOutputtedEmpty){ //this information only outputs once
				System.out.println("Shelf is empty. Waiting to remove . . .");
				hasOutputtedEmpty = true;
			}
			if (this.shelf > 0) //if the shelf is not empty, run the following.
			{
				this.isDone(); //if the packer has packed all 20, close the program.
				this.pack(); // pack the pot
				try{Thread.sleep(this.time);}catch(InterruptedException e) {}
				hasOutputtedReady = false;
			}
		}
	}

	//puts the pots in the shelf
	public synchronized void put(Boolean pot)
	{
		this.shelf++;
		System.out.println("Inserting pot. There are now "+this.shelf+" pots on the shelf");
	}

	//takes the pot off the shelf.
	private synchronized void pack()
	{
		this.shelf--;
		System.out.println("Removing pot. There are now "+this.shelf+" pots on the shelf");
		this.pots_packed++;
	}

	//checks if the shelf is full
	public synchronized boolean isFull()
	{
	    return (this.shelf >= maxSize);
	}

	//checks if the packer is done.
	public synchronized void isDone()
	{
		if (this.pots_packed >= 20){
		    System.out.println("Done . . . 20 pots packed");
		    System.exit(1);
		}
	}
}
