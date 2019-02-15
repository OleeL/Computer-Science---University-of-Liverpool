#include <stdio.h>

int main(void)
{
    int *pointer_start, *pointer;
    pointer = pointer_start;
    &pointer = -1;
    while (&pointer != 0)
    {
        scanf("%d", pointer);
        printf("grade = %d \n", *pointer);
        printf("start pointer = %d \n", pointer_start);
        printf("pointer = %d \n\n", pointer);
        pointer++;
    }



    for (int *i = pointer_start; i <= pointer; i++)
    {
        printf("%d ", *i);
    }
    return 0;
}
