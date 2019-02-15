#include <stdio.h>

int main(void)
{
	// declare variables before using them
	int a, b, c, answer;

	// read a integer number as input and store it in a
	scanf("%i", &a);

	// read a integer number as input and store it in b
	scanf("%i", &b);

	// read a integer number as input and store it in c
	scanf("%i", &c);

	// compute the solution and store it in answer
	answer = a + b + c;

	// print out the solution as a integer number
	printf("%i\n", answer);

	return 0;
}