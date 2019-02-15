FUNCTION main(args)
	Cockroach[] cockroaches <- NEW Cockroach[2]
	Cockroach_Don Don <- NEW Cockroach_Don()
	Cockroach_Bella Bella <- NEW Cockroach_Bella()
	cockroaches[0] <- Don
	cockroaches[1] <- Bella
	cooccurancesList <- NEW int[2000]
	cooccurances <- 1

	//Cockroach_Bella Bella <- NEW Cockroach_Bella()
	FOR i in 0, 1999
		FOR v in 0,99:
			FOR q in 0, cockroaches.length
				cockroaches[q].takeStep()
		
		FOR q in 0, cockroaches.length
			cockroaches[q].save_score()
			cockroaches[q].restart()
		
	

	FOR q in 0, cockroaches.length
		OUTPUT("----------")
		OUTPUT(cockroaches[q].getName())
		OUTPUT("----------")
		OUTPUT("2000 experiments, walking 100 steps, the maximum height achieved has average value: "+AVERAGE(cockroaches[q].getHighestFloors()))
		OUTPUT("2000 experiments, the steps it took to reach the top has average value: "+AVERAGE(cockroaches[q].toTop()))
		OUTPUT()
	
	FOR i in 0, 1999
		cockroaches[0].restart()
		cockroaches[1].restart()
		FOR 0, 1999
			cockroaches[0].takeStep()
			cockroaches[1].takeStep()
			if (cockroaches[0].getFloor() == cockroaches[1].getFloor())
				cooccurances++
		
		cooccurancesList[i] <- cooccurances
		cooccurances <- 1
	
	OUTPUT("----------")
	OUTPUT("2000 experiments the number of times Don and Bella share the same floor has average value: "+AVERAGE(cooccurancesList))
	OUTPUT("----------")


FUNCTION AVERAGE(l):
  	avg <- 0
	FOR (int i <- 0 i < l.length
	    avg <- avg + l[i]
	RETURN avg / l.length
