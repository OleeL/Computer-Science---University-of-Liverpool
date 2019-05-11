/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1084
* RunID: 38321
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

void step(char *board, int t, node_t ** head, int rows, int columns);
void print_board(char *board, int rows, int columns);
int get_index(int row, int column, int columns);

int main(void)
{
    int number_of_timesteps, arrival_time, row_index, rows, columns;
    
    // Memory allocation of the linked list 
    // (creating the head) and assigning it as the head
    node_t * head = malloc(sizeof(node_t)); 
    
    // Creating the current variable and setting that to the head
    // this line is just to make it compatible with the next section
    node_t * current = head;              
    current->next = head;    
   
    // Taking in the inputs of the rows, columns and steps   
    scanf("%d %d %d", &rows, &columns, &number_of_timesteps);

    // Takes the input of the user and stops taking input when an input 
    // is missing (it's equal to 2 because it's expecting 2 inputs.)
    while (scanf("%d %d", &arrival_time, &row_index) == 2)
    {
        current = current->next;
        current->arrival_time = arrival_time;
        current->row_index = row_index;
        current->next = malloc(sizeof(node_t));
    }
    free(current->next);
    current->next = NULL;

    // The game board with memory allocation. 
    char *board = (char *) malloc(rows * columns * sizeof(char));

    // Creates a clear board of dots.
    for (int row = 0; row < rows; row++)
        for (int column = 0; column < columns; column++)
            board[get_index(row, column, columns)] = '.';

    /*
    This for loop runs for the number of time steps which are in the game
    We dont pass the actual array into this function, as this would be time 
    consuming. We pass the address so we don't have to return anything or
    pass large amounts of data.
    */
    for (int t = 0; t < number_of_timesteps; t++)
        step(board, t, &head, rows, columns);

    // The final step is to print the board to finish the program
    print_board(board, rows, columns);
    
    // Freeing the malloc so there is no memory leak.
    free(board);
    
    return 0;
}

// Makes one step and changes the board according to the rules.
// Takes the pointer of the 2d array highway board 
// t = time. Passing t = 0 would add all vehicles in arrival_time 0 to column 0
// 'Head' takes the linked list which stores all of the user inputs
void step(char *board, int t, node_t ** head, int rows, int columns)
{
    // Makes the last column empty
    for (int row = rows-1; row >= 0; row--)
        board[get_index(row, columns-1, columns)] = (char) '.'; 

    //moves every column element further down by 1 for each row.
    for (int column = columns-1; column >= 1; column--)
    {
        for (int row = rows-1; row >= 0; row--)
        {
            // Switch the further columns with the time 
            // step before and make the old coordinates empty
            board[get_index(row, column, columns)] = board[get_index(row, 
                                                            column-1, columns)];
            board[get_index(row, column-1, columns)] = '.';
        }
    }
    /*
    This code below is for putting the new vehicles onto the highway on the
    0th column.
    If the time step passed into the step function is equal to the arrival
    Time on a node in the linked list,
    It looks at that node's arrival time and adds it to the board.
    The element of the linked list is also deleted once it has been used to 
    save time.
    */
    
    node_t * current = (*head);
    node_t * temp_node = (*head);
    node_t * del_node = NULL;

    // The while loop searches through every element of the linked list.
    while (current != NULL)
    {
        if (current->arrival_time == t)
        {
            //Equivalent to board[row_index][0] = '1';
            board[get_index(current->row_index, 0, columns)] = '1';
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
void print_board(char *board, int rows, int columns)
{
    for (int row = 0; row < rows; row++)
    {
        for (int column = 0; column < columns; column++)
        {
            printf("%c", board[get_index(row, column, columns)]);
        }
        printf("\n");
    }
}

// Gets the malloc array index from 2d array to 1d form.
int get_index(int row, int column, int columns)
{
    // This forumlae is for the pointers to point at the correct elements in a 
    // 2 dimensional array
    return (row * columns + column);
}
