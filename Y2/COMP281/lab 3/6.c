#include <stdio.h>
int main(void)
{
	int sum=0;
	for( int i = 0; i <= 10; i++ )
	{
		sum = sum + i;
		if( i==5 ){
			goto addition;
		}
	}
	addition:
	printf( "%d\n", sum );
    return 0;
}