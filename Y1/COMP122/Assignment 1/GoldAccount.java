public class GoldAccount extends StandardAccount
{
	public GoldAccount(int dtmin, int ntmin, int mbmonth)
	{
		//giving the attributes of the StandardAccount values.
		//I don't have to call the variable data types because they're already defined
		super(dtmin, ntmin, mbmonth); //inherits from the standard account
		account_type = "Gold";
		package_cost = 61.00;
		daytime_phone_cost = 0.0;
		ew_phone_cost = 0.0;
		number_of_tv_channels = 230;
		broadband_data = 1520;
		broadband_data_interest = 0.01;
		free_spotify = true;
		music_on_demand = true;
	}
}