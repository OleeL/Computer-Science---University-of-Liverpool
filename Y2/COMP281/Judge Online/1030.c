/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1030
* RunID: 
* Result: Accepted
*/

#include <stdio.h>

int getDigit(double n,int k){
    for (int i = 0; i <= k-2; i++)
    {
        n = n * 10;
        n = n - ((int) n);
    }
    return (int) (n * 10);
 }

int main(void) {
    double number1, number2;
    int digit;
    scanf("%lf %lf %u", &number1, &number2, &digit);
    double answer = (number1 / number2);
    printf("%d", getDigit(answer, digit));
}