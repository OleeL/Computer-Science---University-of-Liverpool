FUNCTION Cockroach_Bella()
	SUPER("Bella")

FUNCTION takeStep()
	IF (!SELF.viewed_86_floor && SELF.getFloor() == 86)
		SELF.viewed_86_floor <- true
	ELSE
		chance = SELF.take_chance(1,6)
		IF (chance > 0 && chance < 4)
			SELF.viewed_86_floor <- false
			SELF.checkPassed86(-1)
		ELSE IF (chance == 4)
			SELF.viewed_86_floor <- false
			SELF.checkPassed86(2)
		ELSE IF (chance == 5)
			SELF.viewed_86_floor <- false
			SELF.checkPassed86(3)
	SELF.updateHighestFloor()

//this method checks if bella passes floor 86
FUNCTION checkPassed86(a)
	//f = floor
	//a = floor amount to change
	f <- SELF.getFloor()
	IF ((f < 86 && f+a > 86)||(f > 86 && f+a < 86))
		SELF.setFloor(86)
	ELSE
		SELF.setFloor(f+a)

