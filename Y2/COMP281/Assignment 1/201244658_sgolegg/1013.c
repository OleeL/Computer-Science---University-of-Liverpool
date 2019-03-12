/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1013
* RunID: 24000
* Result: Accepted
*/

#include <stdio.h>

int input(int score);
void output(int scoreBoundaries[]);

int main(void)
{

    // scoreBoundaries [ 0 = high, 1 = medium, 2 = low ]
    int score;
    int scoreBoundaries[3] = {0, 0, 0};
    scanf("%d", &score);
    do
    {
        scoreBoundaries[input(score)]++;
        scanf("%d", &score);
    }while(score != 0);
    output(scoreBoundaries);
    
    return 0;
}

// returns the correct index to increment
int input(int score)
{       
    if (score >= 85)
        return 0;
    else 
        if (score >= 60 && score <= 84)
            return 1;
        else
            return 2;
}

// takes the score boundary frequencies and outputs them
void output(int scoreBoundaries[])
{
    printf(">=85:%d\n", scoreBoundaries[0]);
    printf("60-84:%d\n", scoreBoundaries[1]);
    printf("<60:%d", scoreBoundaries[2]);
}
