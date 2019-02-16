/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1015
* RunID: 22456
* Result: Accepted
*/

int main(void)
{
    float a, b, r1, r2;
    float a_sum, c_sum = 0;

    // takes input as range
    scanf("%f", &a);
    scanf("%f", &b);

    // r1 must be the smaller number in order for the range to make sense
    if (a <= b){
        r1 = a;
        r2 = b;
    }
    else
    {
        r1 = b;
        r2 = a;
    }

    // adding up the range of areas seperate to the circumferance of circle range.
    for (int i = r1; i <= r2; i ++)
    {
        a_sum += area(i);
        c_sum += circumferance(i);
    }

    // outputting the sums
    printf("%.3f\n", a_sum);
    printf("%.3f", c_sum); 
    return 0;
}