//Name: Oliver Legg
//Student ID: 201244658
//University Email Address: O.Legg@student.liverpool.ac.uk

#include <stdio.h>
#include <stdlib.h>
int main(void)
{
	int grades[6]; //holds the grades of the user input
	//this is looped through and is 6 char[] long (because 6 modules)
	//each char[] is 31 bytes as it is 31 chars.
	const char get[6][31] = { "Enter the mark for module 1: ",
		"Enter the mark for module 2: ",
		"Enter the mark for module 3: ",
		"Enter the mark for module 4: ",
		"Enter the mark for module 5: ",
		"Enter the mark for module 6: " };
	char format[] = "%d"; // format string for the scanf function and makes the string an int.
	char passchars[] = "\nNumber of passes = %d";
	char failchars[] = "\nNumber of fails  = %d";
	int nopasses = 0;
	int nofails = 0;
	int ac1 = 0; //ac1 is the accumulator 1. This increases an index of 31 every time to get a different module print output
	int ac2 = 0; //ac2 is the accumulator 2. This increases by an index of 4 as 4 is the number of bytes a number is worth.

	_asm {
		lp:
			lea eax, get
			add eax, ac1
			push eax;         // puts the register in the stack (get[0] address goes on the stack)
			call printf;      // calls printf which is a premade function that prints whatever is on the top of the stack
			add esp, 4;       // address is worth 4 bytes. This line gets rid of everything you just wrote on the stack.

			lea ebx, grades;  //address to this storage is loaded in the eax
			add ebx, ac2
			push ebx
			lea eax, format
			push eax
			call scanf;		  //gets the users input
			add esp, 8

			cmp[ebx], 0;      //compares the number with 0. The square brackets gets the value FROM the address.
			jl lp;            //jump if the input is lesser than 0
			cmp[ebx], 100
			jg lp;            //jump if the input is greater than 100

			//increments all of the values up, so that on the next loop, it will display a new module
			//and the user input will go in a different index of the array
			add ac1, 31
			add ac2, 4
			cmp ac2, 24
			jne lp

			mov ac1, 0

		// Checks if you pass or fail
		passOrFail:
			cmp ac1, 24
			je finish
			lea ebx, grades
			add ebx, ac1
			add ac1, 4
			cmp[ebx], 40
			jge pass;
			cmp[ebx], 40
			jl fail;

		//runs if you pass a module. This just increments the number of passes varialbe
		pass:
			add nopasses, 1
			jmp passOrFail

		//runs if you fail a module. This just increments the number of fails varialbe
		fail :
			add nofails, 1
			jmp passOrFail

		//this is the finishing part of the program where it outputs the users results.
		finish :
			//prints the number of passes
			lea ebx, nopasses
			push[ebx]
			lea eax, passchars
			push eax
			call printf
			add esp, 8

			//prints the number of fails
			lea ebx, nofails
			push[ebx]
			lea eax, failchars
			push eax
			call printf
			add esp, 8

			//creates an input so that the program doesn't just close at the end
			//the final program will not be able to use breakpoints.
			lea eax, format
			push eax
			call scanf
			add esp, 4
	}
	return 0;
}
