#include <stdio.h>
int main(void)
{
    int salary, grade, basis;
    printf("Enter basic salary and grade: ");
    scanf("%d%d", &basis, &grade);
    salary = basis + ((grade==1)?200:((grade==2)?300:500));
    printf("Total salary is %d.\n", salary);
    return 0;
}