/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1015
* RunID: 24201
* Result: Accepted
*/
#include <stdio.h>
#include <stdlib.h>


// Creating a linked list
typedef struct node {
    int number;
    struct node * next;
} node_t;

// Outputs the entire list
void print_list(node_t * head) {
    node_t * current = head;
    while (current != NULL) {
        printf("%c", current->number);
        current = current->next;
    }
}

int main(void)
{
    int number;
    node_t * head = malloc(sizeof(node_t));
    node_t * current = head;
    current->next = head; // this line is just to make it compatible with the next section

    // takes the input of the user and creates the next link in the list
    while (scanf("%d", &number) != EOF)
    {
        current = current->next;
        current->number = number;
        current->next = malloc(sizeof(node_t));
        current->next->next = NULL;
    }
    current->next = NULL;
    print_list(head);
    return 0;
}
