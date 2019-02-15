#include <stdio.h>
int main(void)
{
    int x=5;

    printf("%d\n", x++);
    /* 5 is displayed then, x is increased to 6. */

    printf("%d\n", ++x);
    /* Initially, x = 6. It is increased to 7 then, it is displayed. */

    return 0;
}
