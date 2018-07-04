FUNCTION graph(number, label)
	SELF.steps <- 0
	SELF.label <- label
	SELF.coop <- NEW [number]

FUNCTION ABSTRACT run()
FUNCTION ABSTRACT reset()

FUNCTION oneStep(i1, i2)
	IF (NOT SELF.coop[i1] && NOT SELF.coop[i2])
		SELF.coop[i1] <- TRUE
		SELF.coop[i2] <- TRUE
	ELSE IF ((NOT SELF.coop[i1] && SELF.coop[i2]) || (NOT SELF.coop[i2] && SELF.coop[i1]))
		SELF.coop[i1] <- FALSE
		SELF.coop[i2] <- FALSE
	SELF.setSteps(SELF.getSteps()+1)

FUNCTION isComplete()
	FOR (i in 0, SELF.coop.length)
		IF (NOT SELF.coop[i])
			RETURN FALSE
	RETURN TRUE

FUNCTION setCoop(index, bool)
	SELF.coop[index] <- bool

FUNCTION setSteps(n)
	SELF.steps <- n

FUNCTION getSteps()
	RETURN SELF.steps

FUNCTION getCoopLength()
	RETURN SELF.coop.length

FUNCTION getLabel()
	RETURN SELF.label