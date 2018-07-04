import static java.lang.Math.max; //imports math for the MAX function further down

public abstract class StandardAccount //abstract meaning that the class is a superclass and cannot be instantiated
{       
	protected int     user_DAYTIMEMINS;        //users parameters    [integer] 
	protected int     user_NIGHTTIMEMINS;      //users parameters    [integer]   
	protected int     user_MBPERMONTH;         //users parameters    [integer]
	protected String  account_type;            //bronze/silver/gold  [String]
	protected double  package_cost;            //per month           [pound]
	protected double  daytime_phone_cost;      //per minute          [pence]
	protected double  ew_phone_cost;           //per minute          [pence]
	protected int     number_of_tv_channels;   //                    [integer]
	protected int     broadband_data;          //megabytes per month [integer]
	protected double  broadband_data_interest; //amount over limit   [pence]
	protected boolean free_spotify;            //£0 spotify on S+G   [boolean]
	protected boolean music_on_demand;         //music on demand S+G [boolean]

	//constructor function
	public StandardAccount(int dtmin, int ntmin, int mbmonth)
	{
        user_DAYTIMEMINS   = dtmin;
        user_NIGHTTIMEMINS = ntmin;
        user_MBPERMONTH    = mbmonth;
	}

	/*	
	prints out the cost of overything and what's included
	Below I used the pound sign unicode because printing with it like this: £
	would not work. Therefore I had to create a variable that would use it there
	In the lines of code below, I also used the String.format("%.2f",x) this
	makes the float output a number as 1.00 instead of 1.0. This is useful when
	displaying money

	backslash u means unicode
	backslash t tabs across. helpful to read.
	backslash n creates a new line
	*/

	public void summary()
	{   
		String pound = "\u00a3";//ascii encoding for broken '£' sign
		System.out.println("Account Summary for "+account_type+" Account:");
		System.out.println("\tPackage Cost: "+pound+String.format("%.2f",package_cost));
		System.out.println("\tCost of daytime calls: "+pound+String.format("%.2f",daytime_phone_cost)+"/min");
		System.out.println("\tCost of evening and weekend calls: "+pound+String.format("%.2f",ew_phone_cost)+"/min");
		System.out.println("\tNumber of Channels: "+number_of_tv_channels);
		System.out.println("\tBroadband Included: "+broadband_data+"Mb");
		System.out.println("\tBroadband Cost (above included limit): "+pound+String.format("%.2f",broadband_data_interest));
		System.out.println("\tTotal daytime calls cost: "+pound+String.format("%.2f",(daytime_phone_cost*user_DAYTIMEMINS)));
		System.out.println("\tTotal evening calls cost: "+pound+String.format("%.2f",(ew_phone_cost*user_NIGHTTIMEMINS)));
		System.out.println("\tTotal (extra) broadband cost: "+pound+String.format("%.2f",getBroadbandCost()));
		System.out.println("\tTotal cost: "+pound+String.format("%.2f",getTotalCost()));
		System.out.println("\tSpotify Account provided: "+free_spotify);
		System.out.println("\tMusic on Demand provided: "+music_on_demand+"\n");
	}

	//broadband_data, broadband_data_interest, user_MBPERMONTH
	protected double getBroadbandCost() 
	{
		return broadband_data_interest*(max(0, user_MBPERMONTH-broadband_data)); //max syntax used here from math library.
	}

	//gets the total cost
	protected double getTotalCost() 
	{
		return (daytime_phone_cost*user_DAYTIMEMINS)+(ew_phone_cost*user_NIGHTTIMEMINS)+getBroadbandCost()+package_cost;
	}
}