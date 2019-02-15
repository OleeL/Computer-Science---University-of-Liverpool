#include <stdio.h>
int main(void)
{
    int var;
    printf("Enter a Number: ");
    scanf("%d", &var);
    if( var % 2 == 0 )
    {
        printf("You entered an Even Number.\n");
    }
    else
    {
        printf("You entered an Odd Number.\n");
    }
    return 0;
}