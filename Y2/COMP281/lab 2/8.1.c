#include <stdio.h>
int main(void)
{
    int x = 56, y = 65;
    if ( --x > 50 && ++y > 50)
    {
        printf("x=%d, y=%d\n", x, y );
    }
    return 0;
}