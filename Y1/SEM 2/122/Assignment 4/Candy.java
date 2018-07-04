public class Candy
{
	public static int sales      = 0; //doesn't make the sales part of the object but apart of all of the objects
	public static double revenue = 0; //revenue is part of all of the objects too, thus these 2 attributes are static.
	private String name;              
	private String prefix;			 
	private String suffix;			  
	private double cost;
	private int initial_stock; //the max the vendor stocks up to of the candy
	private int stock;		   //the current stock of the candy

	public Candy( 
		String prefix,
		String suffix,
		double cost, 
		int initial_stock)
	{
		this.prefix        = prefix;
		this.suffix        = suffix;
		this.name          = prefix + " " + suffix; //the name is created from appending the prefix and the suffix
		this.cost          = cost;
		this.stock         = initial_stock;
		this.initial_stock = initial_stock;
	}

	public String getName()
	{
		return name;
	}

	public double getCost()
	{
		return cost;
	}

	public int getStock()
	{
		return stock;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public String getSuffix()
	{
		return suffix;
	}

	//decreases the stock and increases the revenue
	public void consume()
	{
		if (stock > 0){
			stock--;
			sales++;
			revenue = revenue + cost;
		}
	}

	public static int getSales()
	{
		return sales;
	}

	public static double getRevenue()
	{
		return revenue;
	}

	//sets the stock back to 0
	public void reset_stock()
	{
		stock = initial_stock;
	}

	//sets the revenue to 0.
	public static void reset_revenue()
	{
		revenue = 0;
	}
}