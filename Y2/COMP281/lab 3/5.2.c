#include <stdio.h>
int main(void)
{
	for ( int var = 100; var >= 10; var -- )
	{
		printf("%d\n", var);
		if ( var == 99 )
		{
			break;
		}
	}
    printf("Out of for-loop");
    return 0;
}