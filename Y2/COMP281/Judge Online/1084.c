/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1084
* RunID: 30540
* Result: Accepted
*/

//References
//https://www.learn-c.org/en/Linked_lists

#include <stdio.h>
#include <stdlib.h> // Required for malloc

// Creating a construct of a linked list
typedef struct node {
    int arrival_time;
    int row_index;
    struct node * next;
} node_t;

void step(char *board, int t, node_t ** head);
void print_board(char *board);

int number_of_rows, number_of_columns;

int main(void)
{
    int number_of_timesteps, arrival_time, row_index;

    node_t * head = malloc(sizeof(node_t)); // Memory allocation of the linked list (creating the head) and assigning it as the head
    node_t * current = head;                // Creating the current variable and setting that to the head
    current->next = head;                   // this line is just to make it compatible with the next section
   
    // Taking in the inputs of the rows, columns and steps   
    scanf("%d %d %d", &number_of_rows, &number_of_columns, &number_of_timesteps);

    // takes the input of the user and stops taking input when an input is missing (it's equal to 2 because it's expecting 2 inputs.)
    while (scanf("%d %d", &arrival_time, &row_index) == 2)
    {
        current = current->next;
        current->arrival_time = arrival_time;
        current->row_index = row_index;
        current->next = malloc(sizeof(node_t));
    }
    free(current->next);
    current->next = NULL;

    char board[number_of_rows][number_of_columns];

    // Creates a clear board of dots.
    for (int row = 0; row < number_of_rows; row++)
    {
        for (int column = 0; column < number_of_columns; column++)
        {
            board[row][column] = '.';
        }
    }

    // This for loop runs for the number of time steps which are in the game
    // We dont pass the actual array into this function, as this would be time consuming.
    // We pass the address so we don't have to return anything or pass large amounts of data.
    for (int t = 0; t < number_of_timesteps; t++)
    {
        step(&board[0][0], t, &head);
    }
    print_board(&board[0][0]);
    return 0;
}

// Makes one step and changes the board according to the rules.
// Takes the pointer of the 2d array highway board 
// t = time. Passing t = 0 would add all vehicles in arrival_time 0 to column 0
// 'Head' takes the linked list which stores all of the user inputs
void step(char *board, int t, node_t ** head)
{
    // Makes the last column empty
    for (int row = number_of_rows; row >= 0; row--)
        *(board + row * number_of_columns + number_of_columns-1) = '.';

    //moves every column element further down by 1 for each row.
    for (int column = number_of_columns-1; column >= 1; column--)
    {
        for (int row = number_of_rows-1; row >= 0; row--)
        {
            // This forumlae is for the pointers to point at the correct elements in a 2 dimensional array
            *(board + row * number_of_columns + column) = *(board + row * number_of_columns + column-1);
            *(board + row * number_of_columns + column-1) = '.';
        }
    }
    
    // This code below is for putting the new vehicles onto the highway on the 0th column.
    // If the time step passed into the step function is equal to the arrival time on a node in the linked list,
    // It looks at that node's arrival time and adds it to the board.
    // The element of the linked list is also deleted once it has been used to save time.
      
    node_t * current = (*head);
    node_t * temp_node = (*head);
    node_t * del_node = NULL;

    // The while loop searches through every element of the linked list.
    while (current != NULL)
    {
        if (current->arrival_time == t)
        {
            *(board + current->row_index * number_of_columns) = '1'; //Equivalent to board[row_index][0] = '1';
            if (current == (*head))
            {
                del_node = (*head);
                (*head) = current->next;
            }
            else
            {
                temp_node->next = current->next;
                del_node = current;
            }
        }
        temp_node = current;
        current = current->next;
        if (del_node != NULL)
        { 
            free(del_node);
            del_node = NULL;
        }
    }
}

// Outputs the whole board
void print_board(char *board)
{
    for (int row = 0; row < number_of_rows; row++)
    {
        for (int column = 0; column < number_of_columns; column++)
        {
            printf("%c", *(board + row * number_of_columns + column));
        }
        printf("\n");
    }
}