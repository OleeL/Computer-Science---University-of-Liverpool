public class CoopGame{
	public static void main(String[] args)
	{
		int n = 100;
		int trials = 2000;

		//input error handling
		try {
			if (args.length < 1){
				System.out.println("\n\tUsage: java CoopGame n [trials]\n");
				System.out.println("\tn = (int) number of vertices");
				System.out.println("\ttrials = (int) number of trials (optional, defaults to 2000)");
				System.exit(1);}
			else if (args.length == 1)
				n = Integer.parseInt(args[0]); //turns string input to int 
			else if (args.length == 2){
				n = Integer.parseInt(args[0]); //turns string input to int 
				trials = Integer.parseInt(args[1]);} //turns string input to int
			if (n < 1 || n > 5000 || args.length > 2)
			{
				System.out.println("\n\tOops, check your parameter(s). The first parameter specifies n,");
				System.out.println("\tthe number of vertices (at least 3). ");
				System.out.println("\tAn optional second parameter specifies the number of trials to");
				System.out.println("\tperform (between 1 and 5000).");
				System.exit(1);
			}
		}
		catch (NumberFormatException e)
		{
			System.out.println("\n\tUsage: java CoopGame n [trials]\n");
			System.out.println("\tn = (int) number of vertices");
			System.out.println("\ttrials = (int) number of trials (optional, defaults to 2000)");
			System.exit(1);
		}

		//defining experiments 
		cycle c1 = new cycle(n, false); //false because one -1 in graph
		cycle c2 = new cycle(n, true);  //true  because two -1 in graph
		path  p1 = new path(n, false);
		path  p2 = new path(n, true);
		int[] cycle_results1 = new int[trials];
		int[] cycle_results2 = new int[trials];
		int[] path_results1  = new int[trials];
		int[] path_results2  = new int[trials];

		//begins experiments
		for (int i = 0; i < trials; i++){
			cycle_results1[i] = c1.run();
			cycle_results2[i] = c2.run();
			path_results1[i]  = p1.run();
			path_results2[i]  = p2.run();
		}

		//outputting data 
		System.out.println("Cycle of size "+n+" ("+trials+" trials) with one starting -1 value: "+Average(cycle_results1));
		System.out.println("Cycle of size "+n+" ("+trials+" trials) with two starting -1 value: "+Average(cycle_results2));
		System.out.println();
		System.out.println("Path of size "+n+" ("+trials+" trials) with one starting -1 value: "+Average(path_results1));
		System.out.println("Path of size "+n+" ("+trials+" trials) with two starting -1 value: "+Average(path_results2));
	}

	//computes the average
	public static double Average(int[] list)
	{
		int sum = 0;
		for (int i : list)
			sum+=i;
		return (double) sum/list.length;
	}
}