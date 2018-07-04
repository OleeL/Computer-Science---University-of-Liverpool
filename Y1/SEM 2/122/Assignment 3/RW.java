public class RW
{
	public static void main(String[] args)
	{
		Cockroach[] cockroaches = new Cockroach[2];
		Cockroach_Don Don = new Cockroach_Don();
		Cockroach_Bella Bella = new Cockroach_Bella();
		cockroaches[0] = Don;
		cockroaches[1] = Bella;
		int[] cooccurancesList = new int[2000];
		int cooccurances = 1;

		//Experiments for the highest floor achieved in 100 steps
		for (int i = 0; i < 2000; i++){
			for (int v = 0; v < 100; v++){
				for (int q = 0; q < cockroaches.length; q++)
					cockroaches[q].takeStep();
			}
			for (int q = 0; q < cockroaches.length; q++){
				cockroaches[q].save_score();
				cockroaches[q].restart();
			}
		}

		//Experiments for a windy day for Don
		int fallen_don = 0; //the number of Don cockroaches that have fallen
        for (int i = 0; i < 2000; i++){
            while (Don.getFloor() < 102){ //while the cockroach hasn't reached top floor, keep walking
                Don.takeStep();
                if (Don.getFloor() == 52)
                {
                	fallen_don++;
                	break;
                }
            }
            Don.restart();
        }

        //Experiments for a windy day for Bella
        int fallen_bella = 0; //the number of Bella cockroaches that have fallen
        for (int i = 0; i < 2000; i++){
            while (Bella.getFloor() < 102){ //while the cockroach hasn't reached top floor, keep walking
                Bella.takeStep();
                if (Bella.getFloor() == 52)
                {
                	fallen_bella++;
                	break;
                }
            }
            Bella.restart();
        }

        //calculating the averages
        double fallen_don_avg   = (double) fallen_don / 2000;
        double fallen_bella_avg = (double) fallen_bella / 2000;

		//printing the results and the name for the cockroach
		//results are: average for highest floors achieved
		//average for how long it to for the cockroach to get to the top
		for (int q = 0; q < cockroaches.length; q++){
			System.out.println("----------");
			System.out.println(cockroaches[q].getName());
			System.out.println("----------");
			System.out.println("2000 experiments, walking 100 steps, the maximum height achieved has average value: "+Average(cockroaches[q].getHighestFloors()));
			System.out.println("2000 experiments, the steps it took to reach the top has average value: "+Average(cockroaches[q].toTop()));
			System.out.println();
		}
		for (int i = 0; i < 2000; i++){
			cockroaches[0].restart();
			cockroaches[1].restart();
			for (int v = 0; v < 2000; v++){
				cockroaches[0].takeStep();
				cockroaches[1].takeStep();
				if (cockroaches[0].getFloor() == cockroaches[1].getFloor())
					cooccurances++;
			}
			cooccurancesList[i] = cooccurances;
			cooccurances = 1;
		}
		System.out.println("----------");
		System.out.println("2000 experiments the number of times Don and Bella share the same floor has average value: "+Average(cooccurancesList));
		System.out.println("----------");
		System.out.println();
		System.out.println("----------"); //windy day data
		System.out.println("2000 experiments for a windy day (Don): "+fallen_don_avg+" fell on average. That's "+fallen_don+" fallen in total");
		System.out.println("2000 experiments for a windy day (Bella): "+fallen_bella_avg+" fell on average. That's "+fallen_bella+" fallen in total");
		System.out.println("----------");
	}

	//takes an int list and returns the average of it in the form of a double.
	public static double Average(int[] l) {
	  	double avg = 0;
		for (int i = 0; i < l.length; i++) 
		    avg += (double)l[i];
		return avg / l.length;
	}
}