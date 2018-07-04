FUNCTION path(number, label)
	super(number, label)


FUNCTION run()
	SELF.reset()
	WHILE (TRUE)
		IF (SELF.isComplete())
			RETURN SELF.getSteps()
		randomNum = RANDOM(0, getCoopLength()-1)
		SELF.oneStep(randomNum, randomNum + 1 )

FUNCTION reset()
	FOR i in 1, SELF.getCoopLength
		SELF.setCoop(i, TRUE)
	SELF.setCoop(0, FALSE)
	IF (SELF.getLabel() == 2)
		SELF.setCoop(SELF.getCoopLength()-1, FALSE)
	SELF.setSteps(0)
