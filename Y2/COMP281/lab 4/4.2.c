#include <stdio.h>
int main(void)
{
	int counter = 10;
	while (counter >= 0)
	{
		if (counter == 7)
		{
			counter--;
			continue;
		}
		printf("%d\n", counter);
		counter--;
	}
    return 0;
}