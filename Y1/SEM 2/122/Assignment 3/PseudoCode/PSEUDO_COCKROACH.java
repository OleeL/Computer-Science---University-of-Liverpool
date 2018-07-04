
//methods
FUNCTION Cockroach(n)
	SELF.name = n

FUNCTION ABSTRACT takeStep()

FUNCTION updateHighestFloor()
    IF (SELF.floor > SELF.highest_floor)
        SELF.highest_floor = SELF.floor

//saves the score
FUNCTION save_score()
	SELF.highest_floors[SELF.iteration++] = SELF.highest_floor


//resets the cockroach's attributes for the next experiment
FUNCTION restart()
    SELF.highest_floor = 1
    SELF.floor         = 1
	SELF.steps         = 0


//sets the floor that they're on
FUNCTION setFloor(f)
    IF (f < 1)
        SELF.floor = 1 
    ELSE IF (f > 102)
        SELF.floor = 102
    ELSE
        SELF.floor = f

//returns the number of attempts the cockroach takes to get the floor 102
FUNCTION toTop()
    FOR i in 0, 1999
        WHILE (SELF.floor < 102)
            SELF.takeStep()
            SELF.step()
        SELF.stepsList[i] = SELF.steps
        SELF.restart()
    RETURN SELF.stepsList


//takes a step
FUNCTION step()
	SELF.steps++


//rolls the dice
FUNCTION take_chance(n1, n2)
    RETURN n1 + ((n2 * MATH.random())


//returns the cockroach's name
FUNCTION getName()
    RETURN SELF.name


//returns the floor they're on
FUNCTION getFloor()
	RETURN SELF.floor


//returns the highest floor list
FUNCTION getHighestFloors()
    RETURN SELF.highest_floors


