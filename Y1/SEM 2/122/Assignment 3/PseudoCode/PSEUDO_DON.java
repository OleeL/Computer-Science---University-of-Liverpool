
FUNCTION Cockroach_Don()
	SUPER("Don")

FUNCTION takeStep()
	//0.1% chance of flying into the centre of the strailwell and falling to gnd floor
	IF (SELF.take_chance(1,1000) == 1)
		SELF.setFloor(1)
	ELSE 
		chance <- SELF.take_chance(1,6)
		IF (chance == 1 || chance == 2)
			SELF.setFloor(SELF.getFloor() - 1)
		ELSEIF (chance > 2 && chance < 6)
			SELF.setFloor(SELF.getFloor() + 1)
		ELSE
			SELF.setFloor(SELF.getFloor() + SELF.take_chance(1,6))
	SELF.updateHighestFloor()

