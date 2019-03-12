/*
* Student ID: 201244658
* Student Name: Oliver Legg
* Email: O.Legg@student.liverpool.ac.uk
*
* User: sgolegg
*
* Problem ID: 1081
* RunID: 30450
* Result: Accepted
*/

#include <stdio.h>

int rows, columns, steps;

void print_board(char board[rows][columns]);
int neighbour_count(int x, int y, char board[rows][columns]);
void step(char board[rows][columns]);


int main(void)
{

    // Taking in the inputs of the rows, columns and steps   
    scanf("%d %d %d", &rows, &columns, &steps);

    // The board rows and columns designate all of the cells.
    char board[rows][columns];

    // Takes in the input of the board
    for (int row = 0; row < rows; row++)
        scanf("%s", &board[row]);
    
    step(board);
    return 0;
}

// Outputs the state of the game of life:
void print_board(char board[rows][columns])
{
    for (int row = 0; row < rows; row++)
    {
        for (int column = 0; column < columns; column++)
        {
            printf("%c", board[row][column]);
        }
        printf("\n");
    }
}

// Returns the number of neighbors next to a given cell on the board
int neighbour_count(int x, int y, char board[rows][columns])
{
    int count = 0;
    for (int row = y-1; row <= y+1; row++)
        for (int col = x-1; col <= x+1; col++)
            if ((!(col == x && row == y)) && ((col >= 0 && col < columns) && (row >= 0 && row < rows)))
                if ((char) board[row][col] == (char) 'X')
                    count++;
    return count;
}

// Begins the part of the program which changes cells on the board on each and every step according to the game rules
void step(char board[rows][columns])
{
    // board_next is what the board will be next step.
    char board_next[rows][columns];

    // runs for the amount steps passed in by the user.
    while (steps > 0 )
    {
        steps--;

        // Running over element of the game board and checking if the cell needs to change.
        for (int row = 0; row < rows; row++)
        {
            for (int column = 0; column < columns; column++)
            {
                int neighbours = neighbour_count(column, row, board);
                char state = board[row][column];
                char change_state = state;
                
                // Checks if the cell is alive.
                if (state == '.')
                {
                    // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                    if (neighbours == 3) 
                        change_state = 'X';
                }
                else
                {
                    // Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
                    // Any live cell with two or three live neighbours lives on to the next generation.
                    // Any live cell with more than three live neighbours dies, as if by overpopulation.
                    if (neighbours <= 1)
                        change_state = '.';
                    if (neighbours >= 4)
                        change_state = '.';
                }
                board_next[row][column] = change_state;
            }
        }

        // Board_next is complete. Board is now become board_next 
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++)
                board[row][column] = board_next[row][column];
    }

    // When the steps are done, it's time to print the board.
    print_board(board);
}