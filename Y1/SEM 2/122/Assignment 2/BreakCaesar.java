import java.lang.Math;
public class BreakCaesar
{
	//this known frequency is for letters in the english language.
	//I used these numbers for the calculation chi-squared score.
	//at index 0 is the freqency for letter 'a'. index 1 = 'b', index 2 = 'c' etc.
	public static double[] knownFreq = {0.0855, 0.0160, 0.0316, 0.0387, 0.1210,
	0.0218, 0.0209, 0.0496, 0.0733, 0.0022,
	0.0081, 0.0421, 0.0253, 0.0717, 0.0747,
	0.0207, 0.0010, 0.0633, 0.0673, 0.0894,
	0.0268, 0.0106, 0.0183, 0.0019, 0.0172,
	0.0011};

/*	I keep the letter cases separate so that when the values shift,
	all of the cases down shift to lowercase or uppercase.*/
	public static char[] alphabetlower = new char[26];
	public static char[] alphabetupper = new char[26];
        
    //the main handles the user input and prints all of the decryted data
	public static void main(String[] args)
	{
		//if the user doesnt pass any args, exit the program
		if (args.length < 1){
			System.out.print("Oops, you haven't given enough parameters!    Usage: java BreakCaesar \"string\"");
			System.exit(1);
		}

		//putting the letters in the alphabet
		int index = 0;
        for (char c = 'a'; c <= 'z'; c++){
            alphabetlower[index] = c;
			alphabetupper[index++] = Character.toUpperCase(c);}

		//printing the encryped input and the decrypted output.
		System.out.println("==============================");
		System.out.println(args[0]);
		System.out.println("==============================");
		System.out.println(decrypt(args[0]));
	}

	//shift text shifts the characters in the message in a certain number of times
	public static String shifttext(int shift_num, String text)
	{
		String finalstring = "";
		boolean character_in_alphabet = false;
		char c;
		for (int i = 0; i<text.length(); i++){
			c = text.charAt(i); //gets the character at an index of the string.
			for (int v = 0; v<26;v++){
				/*checking to see if the character is lower or uppercase. If it's upper,
				then use the upper alphabet to shift, otherwise use the lower alphabet */
				if (c == alphabetlower[v] || c == alphabetupper[v])
				{
					character_in_alphabet = true;
					if (Character.isUpperCase(c))
						finalstring = finalstring + alphabetupper[Math.floorMod((v+shift_num),26)];
					else 
						finalstring = finalstring + alphabetlower[Math.floorMod((v+shift_num),26)];
					break;
				}
			} 

			//if the character is not in the alphabet at all, then just keep it as is.
			if (!character_in_alphabet)
				finalstring = finalstring + c;
			character_in_alphabet = false;
		}
		return finalstring;
	}

	//gets the value of the string. This applies the chi-squared score
	public static double getStringValue(String text)
	{
		double finalnumber = 0;
		double power = 0;
		char c;
		//you have to make sure that you apply the calculation to every letter in the string
		//each letters score should um up.
		for (int i = 0; i<text.length(); i++){
			for (int v = 0; v<26; v++){
				c = text.charAt(i);
				if (c == alphabetupper[v] || c == alphabetlower[v])
				{
					// sum += (f - english)^2  /  english
					power = Math.pow((frequency(text,c)-knownFreq[v]),2); 
					finalnumber += (power / (knownFreq[v])); 
				}
			}
		}
		return finalnumber;
		
	}
	
	//the frequency is the number of times a character appears in a string
	public static int frequency(String text, char letter)
	{
		int count = 0;
		for (int i=0; i<text.length(); i++){
			if (text.charAt(i) == letter)
				count+=1;
		}
		return count;
	}

	//decrypt makes sure that every possible shift is applied into the shifttext function
	//this function also returns the value that is most simmilar to enlglish
	public static String decrypt(String text)
	{
		double value = getStringValue(text);
		String newtext = text;
		for (int i = 0; i<26; i++){ //loops 26 times because 26 letters are in the alphabet
			if (getStringValue(shifttext(i,text)) < value){ //the lowest value is cosest to english
				value = getStringValue(shifttext(i,text));
				newtext = shifttext(i,text);}
		}
		return newtext;
	}
}