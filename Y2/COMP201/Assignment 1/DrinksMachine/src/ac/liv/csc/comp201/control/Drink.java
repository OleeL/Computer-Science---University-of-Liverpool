/*
 * Student Name: Oliver Legg
 * Student ID:   201244658
 */

package ac.liv.csc.comp201.control;
import ac.liv.csc.comp201.simulate.Cup;

public class Drink {
	
	private static final String[] SIZES = {"", "5", "6"}; // The only sizes of the drinks (empty string is small)
	private static final String[] DRINKS = {"101", "102", "201", "202", "300"}; // All possible drinks
	private static final int SUGAR_AMOUNT = 5;	    // Grams of sugar
	private static final int MILK_POWDER = 3;       // Grams of milk powder
	private static final int COFFEE_POWDER = 2;     // Grams of coffee powder
	private static final int CHOCOLATE_POWDER = 28; // Grams of chocolate powder
	private float temperature = (float) 95.9;       // The temperature of the drink
	private double volume = Cup.SMALL;              // Litres of water (for a small)
	private boolean sugar = false;    		        // If the drink even has sugar or not
	private int cost;    					        // The cost of the drink
	private String dType;					        // The drink type e.g.
	private String type;					        // Type of drink. i.e. Black, white, hot chocolate etc.
	private String size = "Small";			        // The size of the cup small, medium or, large. Default = small
	private String code;					        // The drink's code (input on keypad on machine)
	private String name;                            // The drink's name
	
	public Drink(String code) {
		this.code = code;
		int length = code.length();
		int index = 0;
		if (length == 4) {
			// Increases the index of the chars 
			index++;
			/* (X)XXX If the drinks code has 4 chars then the user wants
			 to choose the size of the drinks */
			if (code.charAt(0) == "5".charAt(0)) {
				size = "Medium";
				volume = Cup.MEDIUM;
				cost += 20;
			}
			else if (code.charAt(0) == "6".charAt(0)) {
				size = "Large";
				volume = Cup.LARGE;
				cost += 25;
			}
		}
		
		// (X)XX The type of drink e.g. coffee black, white, hot chocolate.
		if (code.charAt(0+index) == "1".charAt(0)) {
			dType = "Coffee";
			type = "Black";
			cost += 120;
		}
		else if (code.charAt(0+index) == "2".charAt(0)) {
			dType = "Coffee";
			type = "White";
			cost += 120;
		}
		else if (code.charAt(0+index) == "3".charAt(0)) {
			dType = "Chocolate";
			type = "";
			temperature = (float) 90;
			cost += 110;
		}
		
		// XX(X) Whether the drink has sugar or not
		if (code.charAt(2+index) == "2".charAt(0)) {
			sugar = true;
			cost += 10;
		}
		
		// Creates the name of the drink
		name = size + " " + type + " " + dType;
		if (hasSugar())
			name += " -  /w Sugar";		
		
	}
	
	// Returns the drink code
	public String getCode() {
		return code;
	}
	
	// Returns the cost of the drink
	public int getCost() {
		return cost;
	}
	
	// Returns the cost of the drink "Small", "Medium", "Large"
	public String getSize() {
		return size;
	}
	
	// Gets the drink type "Coffee", "Hot Chocolate"
	public String getDType() {
		return dType;
	}
	
	// Gets the type of drink "White", "Black"
	public String getType() {
		return type;
	}
	
	// Gets the recipe of the drink type. If DType is coffee then it returns coffee powder
	// If the DType is chocolate then it returns the chocolate powder. This scales by the cup size
	// If there was an error on instantiation input then just return 0.
	public double getRecipe() {
		if (dType == "Coffee")
			return COFFEE_POWDER * (volume / Cup.SMALL);
		else if (dType == "Chocolate")
			return CHOCOLATE_POWDER * (volume / Cup.SMALL);
		else
			return 0;
	}
	
	// Returns whether the drink has sugar or not
	public boolean hasSugar() {
		return sugar;
	}
	
	// Returns how much sugar is in the drink (scales by the cup size)
	public double getSugarAmount() {
		return SUGAR_AMOUNT * (volume / Cup.SMALL);
	}
	
	// Gets the name of the drink. 
	public String getName() {
		return name;
	}
	
	// Gets the temperature in degrees celcius.
	public float getTemperature() {
		return temperature;
	}
	
	// Gets the amount of milk powder. This is only for white coffees
	public double getMilkPowder() {
		if (dType == "Coffee" && type == "White")
			return MILK_POWDER * (volume / Cup.SMALL);
		else
			return 0;
	}
	
	// Gets the water in L
	public double getWater() {
		return volume;
	}

	// Converts the balance from pence to pound in string format
	public static String penceToPound(int number) {
		double ptp = (double) number / 100;
		return "£"+String.format("%.2f",ptp);
	}
	
	// Checks if the input or part of the code that has been inputed is valid
	public static boolean drink_valid_input(String code) {	
		String str;
		for (int i = 0; i < SIZES.length; i++) {
			for (int v = 0; v < DRINKS.length; v++) {
				str = (String) SIZES[i] + (String) DRINKS[v];
				if (code.equals(str.substring(0, Math.min(code.length(), str.length())))) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Checks if the whole code inputed is valid
	// You can use this to make sure your code is valid before you instantiate an object of this class
	public static boolean drink_valid_code(String code) {
		for (int i = 0; i < SIZES.length; i++) {
			for (int v = 0; v < DRINKS.length; v++) {
				if (code.equals(SIZES[i] + DRINKS[v])) {
					return true;
				}
			}
		}
		return false;
	}
}
