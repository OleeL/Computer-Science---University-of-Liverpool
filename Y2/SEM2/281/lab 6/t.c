#include <stdio.h>

void foo(int i) {
	i = 12;
}

int main(void)
{
	int i = 1;

	foo(i);
	
	printf("%d\n", i);

	return 0;
}