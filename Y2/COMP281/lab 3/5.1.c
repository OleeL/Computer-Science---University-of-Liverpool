#include <stdio.h>
int main(void)
{
	int num =0;
    while( num <= 100)
    {
		printf("%d\n", num);
		if (num==2)
		{
			break;
		}
		num++;
	}
    printf("Out of while-loop");
    return 0;
}