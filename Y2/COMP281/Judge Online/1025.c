/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1025
* RunID: 
* Result: Accepted
*/
#include <stdio.h>

unsigned int largest_common_factor(unsigned int a, unsigned int b);
unsigned int smallest_common_multiple(unsigned int a, unsigned int b);

int main(void) {
    unsigned int num1, num2;
    printf("Enter two positive integers:\n");
    scanf("%u %u", &num1, &num2);
    printf("%u %u", largest_common_factor(num1, num2), smallest_common_multiple(num1, num2));
    return 0;
}

unsigned int largest_common_factor( unsigned int a, unsigned int b )
{
    unsigned int answer = 0;
    for(unsigned int i = 1; i <= a && i <= b; i++)
    {
        if( a % i == 0 && b % i == 0)
            answer = i;
    }
    return answer;
}

unsigned int smallest_common_multiple(unsigned int a, unsigned int b) {
    return (a*b)/largest_common_factor(a, b);  
}