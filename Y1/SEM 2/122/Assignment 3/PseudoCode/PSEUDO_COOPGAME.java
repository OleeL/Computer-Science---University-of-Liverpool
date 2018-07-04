FUNCTION main(args)
	n <- 100
	trials <- 2000
	IF (NOT args.isAllInteger)
		OUTPUT("\tn <- (int) number of vertices")
		OUTPUT("\n\tUsage: java CoopGame n [trials]\n")
		OUTPUT("\ttrials <- (int) number of trials (optional, defaults to 2000)")
	IF (args.length < 1)
		OUTPUT("\n\tUsage: java CoopGame n [trials]\n")
		OUTPUT("\tn <- (int) number of vertices")
		OUTPUT("\ttrials <- (int) number of trials (optional, defaults to 2000)")
	ELSE IF (args.length == 1)
		n <- toInt(args[0])
	ELSE IF (args.length == 2)
		n <- toInt(args[0])
		trials <- toInt(args[1])
	IF (n < 1 || n > 5000 || args.length > 2)
		OUTPUT("\n\tOops, check your parameter(s). The first parameter specIFies n,")
		OUTPUT("\tthe number of vertices (at least 3). ")
		OUTPUT("\tAn optional second parameter specIFies the number of trials to")
		OUTPUT("\tperFORm (between 1 and 5000).")
	cycle c1 <- NEW cycle(n, 1)
	cycle c2 <- NEW cycle(n, 2)
	path  p1 <- NEW path(n, 1)
	path  p2 <- NEW path(n, 2)
	cycle_results1 <- NEW [trials]
	cycle_results2 <- NEW [trials]
	path_results1  <- NEW [trials]
	path_results2  <- NEW [trials]
	FOR i in 0, trials
		cycle_results1[i] <- c1.run()
		cycle_results2[i] <- c2.run()
		path_results1[i]  <- p1.run()
		path_results2[i]  <- p2.run()
	OUTPUT("Cycle of size "+n+" ("+trials+" trials) with one starting -1 value: "+Average(cycle_results1))
	OUTPUT("Cycle of size "+n+" ("+trials+" trials) with two starting -1 value: "+Average(cycle_results2))
	OUTPUT()
	OUTPUT("Path of size "+n+" ("+trials+" trials) with one starting -1 value: "+Average(path_results1))
	OUTPUT("Path of size "+n+" ("+trials+" trials) with two starting -1 value: "+Average(path_results2))
    
FUNCTION Average(list)
	sum <- 0
	FOR (i to list)
		sum <- sum + i
	RETURN sum / list.length
