import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
import java.io.*;
public class TFIDF 
{
/*	this returns a String[] of all the words in the given document.
	it's faster to add everything into an array list in the below
	function than it is to use another function to calculate how many 
	words are in the given text document. String[] require a set length
	and arraylists dont. Therefore i put everything into an array list
	and convert it into a String[] at the end.*/
	public static String[] list_words_in_doc(String doc)
	{
		String[] list_word = new String[0]; //if the file doesnt exist, the func should return an empty String[]
		ArrayList<String> strlist = new ArrayList<String>();
		try{ //if the file doesn't exist, the error should be handled
			String line;
			FileReader file = new FileReader(doc);
			BufferedReader buff = new BufferedReader(file);
			line = buff.readLine();
			String[] words;
			while (line != null){
				//splits up all of the words. so that words words with '.' or ',' aren't considered part of the word
				words = line.toLowerCase().replaceAll("[^a-zA-Z]", " ").toLowerCase().split(" "); 
				line = buff.readLine();
				for (int i = 0; i < words.length; i++){
					if (words[i].equals(null) || words[i].equals(""));
					else strlist.add(words[i]);}} //adding everything into a list
			String[] strarray = strlist.toArray(new String[strlist.size()]); //converting everything into a string[]
			return strarray;}
		catch(IOException ex){ return list_word; }
	}

	/*returns the number of times a given word in the document appears.
	it's faster to get this from the string[] that was made at the start
	of the proram instead of grabbing it from the document it's self. */
	
	public static double word_appears_in_doc(String[] l, String t)
	{
		int counter = 0;
		for (int i = 0; i < l.length; i++)
			if (l[i].equals(t))
				counter++;
		return counter;
	}

	/*returns True of False whether t is in a document or not. Again,
	it's faster to get this from the String[] that was made at the start
	of the proram instead of grabbing it from the document it's self. */
	public static boolean word_in_list_bool(String[] list, String t)
	{
		for (int i = 0; i < list.length; i++){
			if (list[i].equals(t))
				return true;}
		return false;
	}

	//Calculate term frequency
	public static double tf(String t, String[] list)
	{
		return word_appears_in_doc(list, t)/list.length;
	}

	//calculates the Inverse document frequency
	public static double idf(String t, ArrayList<String[]> list)
	{
		double tc = 0; //TC is the term count. Term count is the number of documents that contain the term
		for (int x = 0; x < list.size(); x++){ //lists over every file
			if (word_in_list_bool(list.get(x), t))
				tc++;}
		return Math.log10((double)list.size() / (double)tc);
	}

	//calculates the term frequency * inverse document frequency
	public static double tf_idf(String[] file, String t, ArrayList<String[]> list)
	{
		if (list.size() > 1) 
			return (tf(t, file)*idf(t, list));
		else //if the user enters only one text file, only apply the tf calculation
			return tf(t, file);
	}

	//main part of the program takes the arguments of the txt file names and locations when the program executes.
	public static void main(String[] args)
	{
		if (args.length < 1){ //if the user enters too many arguments then, the user should be presented with a helpful error message
			System.out.print("Oops, you haven't given enough parameters! Usage: java TFIDF \"filename.txt\" ...");
			System.exit(1);}
		if (args.length == 1) //making my output identical to the sample output
			System.out.println("\nMax TFIDF value for this file.\n");
		else
			System.out.println("\nMax TFIDF value for each file.\n");
		//This is where all of the inverse term frequency data is being held.
		ArrayList<String[]> list = new ArrayList<String[]> ();
		HashMap<String, Double> tfidflist = new HashMap<String, Double> (); 
		for (int i = 0; i < args.length; i++){
			list.add(list_words_in_doc(args[i]));}
		//for loop that runs for the number of txt files entered to the program.
		for (int i = 0; i < args.length; i++){ 
			for (int v = 0; v < list.get(i).length; v++){
				if (!tfidflist.containsValue(list.get(i)[v]))
					tfidflist.put(list.get(i)[v], tf_idf(list.get(i), list.get(i)[v], list));}}
		//for loop that runs for the number of txt files entered to the program.
		for (int i = 0; i < args.length; i++){ 
			double highest_value = 0.0; //This is defined for the next section
			String highest_word = "no data"; //this data will also appear if no important words are found.
			System.out.println("==========");
			System.out.println(args[i]);
			System.out.println("==========");
			for (int v = 0; v < list.get(i).length; v++){ //gets the word with the highest value
				if (tfidflist.get(list.get(i)[v]) >= highest_value ){
					highest_value = tfidflist.get(list.get(i)[v]);
					highest_word = list.get(i)[v];}}
			//prints the word and score of the highest value
			System.out.println(highest_word+ "  "+highest_value+"\n"); 
		}
	}
}