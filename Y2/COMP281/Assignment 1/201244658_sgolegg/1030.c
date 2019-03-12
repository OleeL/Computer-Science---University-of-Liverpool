/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1030
* RunID: 24279
* Result: Accepted
*/

#include <stdio.h>

// Gets the k'th decimal point from n
// e.g n = 3.1415 k = 4 answer = 5
int getDigit(double n, int k){
    for (int i = 0; i <= k-2; i++)
    {
        n = n * 10;
        n = n - ((int) n);
    }
    return (int) (n * 10);
 }

// Takes 2 doubles and 1 integer on user input. This then divides the first 
// double by the second and returns the k'th decimal point from (num1/num2)  
int main(void) {
    double number1, number2;
    int digit;
    scanf("%lf %lf %u", &number1, &number2, &digit);
    double answer = (number1 / number2);
    printf("%d", getDigit(answer, digit));
}