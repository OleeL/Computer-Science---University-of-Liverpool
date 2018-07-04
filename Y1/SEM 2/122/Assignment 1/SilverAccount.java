public class SilverAccount extends StandardAccount
{
	public SilverAccount(int dtmin, int ntmin, int mbmonth)
	{
		//giving the attributes of the StandardAccount values.
		//I don't have to call the variable data types because they're already defined
		super(dtmin, ntmin, mbmonth);//inherits from the standard account
		account_type = "Silver";
		package_cost = 46.00;
		daytime_phone_cost = 0.12;
		ew_phone_cost = 0.00;
		number_of_tv_channels = 130;
		broadband_data = 1000;
		broadband_data_interest = 0.01;
		free_spotify = true;
		music_on_demand = false;
	}
}