#include <stdio.h>
int main(void)
{
    int a = 10, b = 20;
    if( a > 5 && b > 10 )
    {
        printf("Both conditions are satisfied.\n");
    }
    if( a > 15 || b > 15 )
    {
        printf("Anyone condition is compulsory true.\n");
    }
    return 0;
}