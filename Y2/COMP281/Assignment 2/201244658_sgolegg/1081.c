/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1081
* RunID: 37112
* Result: Accepted
*/

#include<stdio.h>
#include<stdlib.h> // Required for malloc

#define ALIVE 'X'
#define DEAD '.'

int get_index(int row, int column, int columns);
int neighbour_count(char *board, int rows, int columns, int x, int y);
void step(char *board, int rows, int columns, int steps);
void print_board(char *board, int rows, int columns);

int main(void)
{
    
    int rows, columns, steps;
    char *board;

    // Taking in the inputs of the rows, columns and steps   
    scanf("%d %d %d", &rows, &columns, &steps);

    // The board rows and columns designate all of the cells.
    board = (char *) malloc(rows * columns * sizeof(char));

    // Takes in the input of the board
    for (int row = 0; row < rows; row++)
        scanf("%s", &board[get_index(row, 0, columns)]);

    // Begins the program
    step(board, rows, columns, steps);

    // When the steps are done, it's time to print the board.
    print_board(board, rows, columns);

    // Freeing the malloc for the board as it's no longer used. 
    // I do this to prevent memory leak
    free(board);
    
    return 0;
}

// Gets the malloc array index from 2d array to 1d form.
int get_index(int row, int column, int columns)
{
    return (row * columns + column);
}

// Outputs the state of the game of life:
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

// Returns the number of neighbors next to a given cell on the board
int neighbour_count(char *board, int rows, int columns, int x, int y)
{
    int count = 0;
    for (int row = y-1; row <= y+1; row++)
        for (int col = x-1; col <= x+1; col++)
            if ((!(col == x && row == y)) && ((col >= 0 && col < columns) 
                                                && (row >= 0 && row < rows)))
                if (board[get_index(row, col, columns)] == ALIVE)
                    count++;
    return count;
}

// Begins the part of the program which changes cells on the 
// board on each and every step according to the game rules
void step(char *board, int rows, int columns, int steps)
{
    // board_next is what the board will be next step.
    char *board_next = (char *) malloc(rows * columns * sizeof(char));
    // runs for the amount steps passed in by the user.
    while (steps > 0 )
    {
        steps--;
        // Runs over elements of the game board and checks if cell needs change.
        for (int row = 0; row < rows; row++)
        {
            for (int column = 0; column < columns; column++)
            {
                int neighbours = neighbour_count(board, rows, columns, column,
                                                                        row);
                char state = board[get_index(row, column, columns)];
                char change_state = state;
                
                // Checks if the cell is alive.
                if (state == DEAD)
                {
                    // Any dead cell with exactly three live neighbours becomes
                    // a live cell, as if by reproduction.
                    if (neighbours == 3) 
                        change_state = ALIVE;
                }
                else
                {
                    /*
                    Any live cell with fewer than two live neighbours dies, 
                    as if caused by underpopulation.

                    Any live cell with two or three live neighbours 
                    lives on to the next generation.

                    Any live cell with more than three live neighbours dies, 
                    as if by overpopulation.
                    */
                    if (neighbours <= 1)
                        change_state = DEAD;
                    if (neighbours >= 4)
                        change_state = DEAD;
                }
                board_next[get_index(row, column, columns)] = change_state;
            }
        }

        // Board_next is complete. Board is now become board_next 
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++)
                board[get_index(row, column, columns)] = board_next[get_index(
                                                    row, column, columns)];
    }
    
    // Freeing the malloc for board_next as it is no 
    // longer required. I do this to prevent memory leak
    free(board_next);
}