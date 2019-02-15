/*
 * Student Name: Oliver Legg
 * Student ID:   201244658
 */

package ac.liv.csc.comp201;

import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.model.IMachineController;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;
import ac.liv.csc.comp201.control.*;

public class MachineController extends Thread implements IMachineController {
	
	private boolean running = true;				// A state of the program whether it's running or not
	private IMachine machine;                   // To use the machine interface across all methods 
	private static final String version="1.22"; // The version of the program
	private static final String[] COINS_NAME = {"ab","ac","ba","bc","bd","ef"}; // The coin codes share the same index as the coin numbers
	private static final int[] COINS_NUM = {1,5,10,20,50,100}; // The worth of the coins in pence. They share the same indexes as the coins code names
	private boolean availableDrink = true;     // Whether drinks are available to make because of running out of stock
	private boolean makingDrink = false;       // The state of the program. This state is making drink the drink
	private boolean addingIngredients = false; // Sub-state of the makingDrink for adding the ingredients
	private boolean pouring = false;           // Sub-state of the makingDrink for pouring the water
	private boolean gotCup = false;            // Makes sure a cup is actually placed.
	private float previousTemp = 0;            // The previous temperature of the temperature last taken. Useful to see if temperature is increasing
	private String[] input = new String[4];    // The code input of the program
	private String temporaryMessage = "";      // The temporary message of the program. This resets whenever a button is pressed
	private Drink drink;                       // Defines the drink 
	private Cup cup;                           // Defines the cup 
	
	// Runs once when you start the machine
	public void startController(IMachine machine) {
		System.out.println("Version: "+version);
		this.machine = machine;	// Machine that is being controlled
		machine.getKeyPad().setCaption(0,"0");
		machine.getKeyPad().setCaption(1,"1");
		machine.getKeyPad().setCaption(2,"2");		
		machine.getKeyPad().setCaption(3,"3");
		machine.getKeyPad().setCaption(4,"4");		
		machine.getKeyPad().setCaption(5,"5");
		machine.getKeyPad().setCaption(6,"6");
		machine.getKeyPad().setCaption(7,"7");
		machine.getKeyPad().setCaption(8,"8");
		machine.getKeyPad().setCaption(9,"Reject");
		display();
		super.start();	
	}
	
	public MachineController() {
					
	}
	
	// Loops continuously. I use this for button presses and inserting coins
	private synchronized void runStateMachine() {
		
		// If there are no drinks available then lock the coin handler 
		if (!drinks_all_availability()) {
			machine.getCoinHandler().lockCoinHandler();	
			machine.getDisplay().setTextString("<html>NO DRINKS AVAILABLE<br>PLEASE FILL UP HOPPERS</html>");
			availableDrink = false;
		}
		else {
			machine.getCoinHandler().unlockCoinHandler();
			availableDrink = true;
		}
		
		cup=machine.getCup(); // Gets the cup
		
		// Gets the key and coin codes buttons when they're pressed
		int keyCode=machine.getKeyPad().getNextKeyCode();
		String coinCode=machine.getCoinHandler().getCoinKeyCode();
		
		// If a coin is pressed then increase the balance
		if (coinCode!=null) {
			temporaryMessage = "";
			for (int i = 0; i < COINS_NAME.length; i++) {
				if (coinCode == COINS_NAME[i]) {
					machine.setBalance(machine.getBalance() + COINS_NUM[i]);
					break;
				}
			}			
			display();
		}
		
		// If a key is pressed then clear the display 
		if (keyCode != -1) {
			temporaryMessage = "";
		}
		
		// When a button is pressed
		switch (keyCode) {
			case 0 :
				code_addInput("0");
				display();
				break;
			case 1 :
				code_addInput("1");
				display();
				break;
			case 2 :
				code_addInput("2");
				display();
				break;
			case 3 :
				code_addInput("3");
				display();
				break;
			case 4 :
				code_addInput("4");
				display();
				break;
			case 5 :
				code_addInput("5");
				display();
				break;
			case 6 :
				code_addInput("6");
				display();
				break;
			case 7 :
				code_addInput("7");
				display();
				break;
			case 8 :
				code_addInput("8");
				display();
				break;
			case 9 : 
				rejectCoins();
				display();
				break;
		}	
	}
	
	// Handles the display to output information to the screen.
	public void display() {
		if (availableDrink) {
			String displaying_text = "";
			String br = "<br/>"; // Breaks the line
			String c = code_get();
			displaying_text += "<html>";
			displaying_text += "Code: ";
			displaying_text += "<u>"+c+"</u>";
			for (int i = c.length(); i < input.length; i++) displaying_text += "_";
			displaying_text += br + "Balance: " + Drink.penceToPound(machine.getBalance());
			if (drink != null) // If the customer has selected a drink, display information about it.
			{
				displaying_text += br + drink.getName();
				displaying_text += br + "Cost: " + Drink.penceToPound(drink.getCost());
				if (makingDrink)
				{
					displaying_text += br + "Making Drink...";
				}
				else
				{
					if (machine.getBalance() < drink.getCost()) 
					{
						displaying_text += br + "Insufficient funds";
					}
				}
			}
			displaying_text += br + temporaryMessage;
			displaying_text += "</html>";
			machine.getDisplay().setTextString(displaying_text);
		}
	}
	
	// Adds a number to the input code. The function also validates the code to make sure that
	// the numbers entered so far are safe.
	public void code_addInput(String n) {
		String code = "";
		if (!makingDrink) {
			if (availableDrink) {
				// Adds the new number to the end of the list.
				for (int i = 0; i < input.length; i++) {
					if (input[i] == null) {
						input[i] = n;
						break;
					}
				}
				// Puts the number into the code variable
				for (int i = 0; i < input.length; i++) {
					if (input[i] != null) {
						code += input[i];
					}
					else {
						break;
					}
				}
				// Checks if the drink entered is valid so far. If it's not then tell the user.
				if (Drink.drink_valid_input(code)) {
					if (Drink.drink_valid_code(code)) {
						code_enter();
					}
				}
				else { // If the drink entered wasn't valid just clear everything.
					drink = null;
					temporaryMessage = "Invalid code";
					code_clear();
					display();								
				}
			}
		}
	}
	
	// Sets all indexes of the input to null
	public void code_clear() {
		for (int i = 0; i < input.length; i++) {
			input[i] = null;
		}
	}
	
	// Creates the drink by entering the code 
	public void code_enter() {
		if (!makingDrink && !pouring && gotCup)
			gotCup = false;			

		drink = new Drink(code_get());
		if (!drinks_availability(drink.getCode())) {
			drink = null;
			temporaryMessage = "ERROR: Missing ingredients";
		}
		code_clear();
		display();
	}
	
	// Returns the code as a String
	public String code_get() {
		String c = "";
		for (int i = 0; i < input.length; i++) {
			if (input[i] == null)
				break;
			c += (String) input[i];
		}
		return c;
	}
	
	// Runs the machine
	public void run() {
		//int counter=1;
		while (running) {
			//machine.getDisplay().setTextString("Running drink machine controller "+counter);
			//counter++;
			try 
			{
				Thread.sleep(10); // Set this delay time to lower rate if you want to increase the rate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runStateMachine();
			updateController();
		}		
	}
	
	// Handles pouring the drinks and checks on the temperature
	// This also loops every step.
	public void updateController() {
		
		// Making the drink
		if (drink != null) {
			if (machine.getBalance() >= drink.getCost() && !makingDrink) {
				makingDrink = true;
				machine.setBalance(machine.getBalance()-drink.getCost());
				display();
			}
			
			// Retrieves cup as the correct size
			if (makingDrink && !gotCup) {
				if (drink.getSize() == "Medium")
					machine.vendCup(Cup.MEDIUM_CUP);
				else if (drink.getSize() == "Large")
					machine.vendCup(Cup.LARGE_CUP);
				else // It must be a small cup
					machine.vendCup(Cup.SMALL_CUP);
				gotCup = true;
			}
		
			// If drink is being made then run the following
			if (makingDrink) {
				// If the water temperature is lesser than the drink should be then turn on the heater
				if ((machine.getWaterHeater().getTemperatureDegreesC() <= drink.getTemperature()) && (!machine.getWaterHeater().getHeaterOnStatus())) {
					if (!addingIngredients)
						machine.getWaterHeater().setHeaterOn();
				}
				
				// If the water temperature is greater than the drink should be then turn off heater and start pouring
				if (machine.getWaterHeater().getTemperatureDegreesC() >= drink.getTemperature()) {
					machine.getWaterHeater().setHeaterOff();
					previousTemp = machine.getWaterHeater().getTemperatureDegreesC();
					if(!pouring && !addingIngredients)
						pouring = true;
				}
				
				// If the machine is pouring water
				if (pouring) {
					if (cup.getWaterLevelLitres() < drink.getWater()) {
						if (machine.getWaterHeater().getTemperatureDegreesC() < 79) {
							machine.getWaterHeater().setHeaterOn();
							previousTemp = machine.getWaterHeater().getTemperatureDegreesC();
						}
						else {
							machine.getWaterHeater().setHeaterOff();
							previousTemp = machine.getWaterHeater().getTemperatureDegreesC();
						}
						machine.getWaterHeater().setHotWaterTap(true);	
						temporaryMessage = "Adding hot water...";
					}
					else // The cup is full
					{
						addingIngredients = true;
						pouring = false;
						machine.getWaterHeater().setHotWaterTap(false);
					}
				}
					
				// If the cup is full of water then start adding ingredients
				if (addingIngredients) {
					// Processing is the number of processes done if this step
					// This variable increases when a certain ingredient is still being added
					// This section will proceed when there aren't any processes
					int processing = 0;
					temporaryMessage = "Adding powders...";

					// The drink is chocolate
					if (drink.getDType() == "Chocolate") {							
						if ((int) cup.getChocolateGrams() <= drink.getRecipe()) {
							machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
							processing++;
						}
						else {
							machine.getHoppers().setHopperOff(Hoppers.CHOCOLATE);
						}
					}
					// If the drink is coffee then dispense coffee until the correct amount is dispensed
					else if (drink.getDType() == "Coffee") {
						if (cup.getCoffeeGrams() <= drink.getRecipe()) {
							machine.getHoppers().setHopperOn(Hoppers.COFFEE);					
							processing++;
						}
						else {
							machine.getHoppers().setHopperOff(Hoppers.COFFEE);	
						}
						// If the drink is white coffee then dispense milk
						if (drink.getType() == "White") {
							if (cup.getMilkGrams() <= drink.getMilkPowder()) {
								machine.getHoppers().setHopperOn(Hoppers.MILK);
								processing++;
							}
							else {								
								machine.getHoppers().setHopperOff(Hoppers.MILK);
							}
						}
						// If the drink needs sugar then add sugar 
						if (drink.hasSugar()) {
							if (cup.getSugarGrams() <= drink.getSugarAmount()) {
								machine.getHoppers().setHopperOn(Hoppers.SUGAR);								
								processing++;
							}
							else {
								machine.getHoppers().setHopperOff(Hoppers.SUGAR);
							}
						}
					}
					// If there was no processing done this step then done
					if (processing == 0) {
						// Useful for debugging and seeing amounts of ingredients in the cup and drink temperature.
						/*
						System.out.println("Drink made:\t"+drink.getName());
						System.out.println("Drink made at:\t" + cup.getTemperatureInC()+ " degrees C");
						System.out.println("Coffee in cup:\t"+cup.getCoffeeGrams());
						System.out.println("Choc in cup:\t"+cup.getChocolateGrams());
						System.out.println("Milk in cup:\t"+cup.getMilkGrams());
						System.out.println("Sugar in cup:\t"+cup.getSugarGrams());
						System.out.println();
						*/
						drink = null;
						pouring = false;
						makingDrink = false;
						addingIngredients = false;
						temporaryMessage = "Drink finished";
					}
					display();
				}
			}
		}
		
		// Dealing with hot water temperatures keeping it between 70 and 80 degrees
		if (!makingDrink || addingIngredients) {
			// If the machine is not making the drink hover between 70 to 80 degrees C
			if ((machine.getWaterHeater().getTemperatureDegreesC() >= (float) 80) && (machine.getWaterHeater().getHeaterOnStatus())) {
				machine.getWaterHeater().setHeaterOff();
				previousTemp = machine.getWaterHeater().getTemperatureDegreesC();
			}
			else if ((machine.getWaterHeater().getTemperatureDegreesC() <= (float) 70) && (!machine.getWaterHeater().getHeaterOnStatus())) {
				machine.getWaterHeater().setHeaterOn();
				previousTemp = machine.getWaterHeater().getTemperatureDegreesC();
			}			
		}
		// If the previous temperature on the last step is greater than the current step and the heater is off then the heat is rising for no reason.
		if ((previousTemp < machine.getWaterHeater().getTemperatureDegreesC()) && (!machine.getWaterHeater().getHeaterOnStatus())) {
			System.out.println("Error: heater off but temp was rising");
			temporaryMessage = "Error: heater off but temp was rising - Shutting down machine";
			machine.shutMachineDown();
			stopController();
		}
		previousTemp = machine.getWaterHeater().getTemperatureDegreesC();
	}
	
	// Stops the controller from running
	public void stopController() {
		running=false;
	}

	// returns the balance in coins to the user.
	private void rejectCoins() {
		int i = COINS_NAME.length-1;
		temporaryMessage = Drink.penceToPound(machine.getBalance()) + " returned";
		while (true) {
			if (i < 0) {
				temporaryMessage = "ERROR: Not enough change in hopper to return";
				display();
				break;
			}
			if (machine.getCoinHandler().getCoinHopperLevel(COINS_NAME[i]) > 0) {
				if (machine.getBalance() - COINS_NUM[i] >= 0) {
					machine.getCoinHandler().dispenseCoin(COINS_NAME[i]);
					machine.setBalance(machine.getBalance() - COINS_NUM[i]);
				}
				else {
					i--;
				}
			}
			else {
				i--;
			}
			if (machine.getBalance() == 0) {
				break;				
			}
		}
	}
	
	// Tells you if there is at least one drink can be made.
	// This is done by checking the drinks with the cheapest main ingredient
	// If a white coffee can be made then so can a black coffee. 
	// If a black coffee can be made, that doesn't mean a white coffee can.
	private boolean drinks_all_availability() {
		// If there is at least 1 drink available return true
		if (drinks_availability("101"))	
			return true;
		if (drinks_availability("300"))
			return true;
		return false;
	}
	
	// Passes a drink code and the function checks if the machine has enough ingredients to process the drink.
	private boolean drinks_availability(String drink_code) {
		Drink drink_model = new Drink(drink_code); // The drink code
		if (machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR) < drink_model.getSugarAmount() && drink_model.hasSugar())
			return false;
		if (machine.getHoppers().getHopperLevelsGrams(Hoppers.CHOCOLATE) < drink_model.getRecipe() && drink_model.getDType() == "Chocolate")
			return false;
		if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE) < drink_model.getRecipe() && drink_model.getDType() == "Coffee")
			return false;
		if (machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK) < drink_model.getRecipe() && drink_model.getDType() == "Coffee" && drink_model.getType() == "White")
			return false;
		return true;
	}
}
