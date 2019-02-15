#include <stdio.h>
int main(void)
{
    int a, b, c;
    printf("Enter three integers: ");
    scanf("%d%d%d", &a, &b, &c);
    if( a > b )
    {
        if( a > c )
        {
            printf("%i is the largest.\n", a);
        }
        else
        {
            printf("%i is the largest.\n", c);
        }
    }
    else
    {
        if( b > c )
        {
            printf("%i is the largest.\n", b);
        }
        else
        {
            printf("%i is the largest.\n", c);
        }
    }
    return 0;
}
