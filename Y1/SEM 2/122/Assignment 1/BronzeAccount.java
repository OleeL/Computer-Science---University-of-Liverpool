public class BronzeAccount extends StandardAccount
{
	public BronzeAccount(int dtmin, int ntmin, int mbmonth)
	{
		//giving the attributes of the StandardAccount values.
		//I don't have to call the variable data types because they're already defined
		super(dtmin, ntmin, mbmonth); //inherits from the standard account
		account_type = "Bronze";
		package_cost = 36.00;
		daytime_phone_cost = 0.12;
		ew_phone_cost = 0.05;
		number_of_tv_channels = 60;
		broadband_data = 500;
		broadband_data_interest = 0.02;
		free_spotify = false;
		music_on_demand = false;
	}
}