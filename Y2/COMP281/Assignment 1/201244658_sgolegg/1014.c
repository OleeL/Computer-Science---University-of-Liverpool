/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1014
* RunID: 23324
* Result: Accepted
*/

#include <stdio.h>
#define M_PI 3.14

float area(float num);
float circumference(float num);

int main(void)
{
    float input1, input2, range1, range2;
    float a_sum = 0, c_sum = 0;

    // takes input as range
    scanf("%f", &input1);
    scanf("%f", &input2);

    // radius1 must be the smaller number in order for the range to make sense
    if (input1 <= input2){
        range1 = input1;
        range2 = input2;
    }
    else
    {
        range1 = input2;
        range2 = input1;
    }

    // adding up the range of areas seperate to the circumference of circle range.
    for (int i = range1; i <= range2; i ++)
    {
        a_sum += area(i);
        c_sum += circumference(i);
    }

    // outputting the sums
    printf("%.3f\n", a_sum);
    printf("%.3f", c_sum); 
    return 0;
}

// calculates the area of a circle
float area(float num)
{
    return M_PI*(num*num);
}

// calculates the circumference of a circle
float circumference(float num)
{
    return 2*M_PI*num;
}