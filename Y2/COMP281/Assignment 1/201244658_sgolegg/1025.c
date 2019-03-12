/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1025
* RunID: 24396
* Result: Accepted
*/
#include <stdio.h>
 

// Returns the largest common factor (GCD)
int largest_common_factor(int num1, int num2)
{
    int tmp;
    while(num1) 
    { 
        tmp = num1;
        num1 = num2 % num1;
        num2 = tmp;
    }       
    return num2;
}

// Returns the smallest common multiple (LCM)
int smallest_common_multiple(int num1, int num2)
{
    return num1 / largest_common_factor(num1, num2) * num2;
}

// The program takes 2 positive integers and returns their largest common factor and smallest common multiple
int main()
{  
    int num1, num2;
    scanf("%d %d", &num1, &num2);
    printf("%d %d", largest_common_factor(num1, num2), smallest_common_multiple(num1, num2) );
    return 0;
}