#include <stdio.h>

int main(void) {
	int i, j;
	char name[10] = "matrix";
	i = j = 0;

	for ( ; i < 3; ++i) {
		for ( ; j < 4; ++j)
			printf("(%d, %d)\t", i, j);
		printf("\n");
	}

	printf("This is a %dx%d %s.\n", i, j, name);

	return 0;
}