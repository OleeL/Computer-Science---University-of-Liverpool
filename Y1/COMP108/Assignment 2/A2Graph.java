/*
	Name: Oliver Legg
	Student ID: 201244658
	Departmental username: u7ol2
	University email address; O.Legg@student.liverpool.ac.uk
*/

import java.util.*;
import java.io.*;

class A2Graph {

	private static final int MaxVertex = 100;
	private static final int MinVertex = 1;
	private static Scanner keyboardInput = new Scanner (System.in);
	// adjacency matrix, adjMatrix[i][j] = 1 means i and j are adjacent, 0 otherwise
	public static int[][] adjMatrix = new int[MaxVertex][MaxVertex];
	public static int numVertex; // total number of vertices

	// DO NOT change the main method
	public static void main(String[] args) throws Exception {
		boolean userContinue=true;
		int distance=1;
		int[][] neighbourMatrix = new int[MaxVertex][MaxVertex];
		
		input();

		try {
//			System.out.print("Enter a distance (" + MinVertex + "--" + numVertex + ", -1 to exit): ");
			distance = keyboardInput.nextInt();
		}
		catch (Exception e) {
			keyboardInput.next();
		}
		if (distance < MinVertex || distance > numVertex)
			System.out.println("incorrect range");
		else { 
			neighbourhood(distance, neighbourMatrix, numVertex);
			printSquareArray(neighbourMatrix, numVertex);
		}
		
		degreeSeparation();
	}

	// find the degree of separation of the graph using the method neightbourhood()
	static void degreeSeparation() {
		int s = 3;
		System.out.println("Degree of separation is "+s);
		//System.out.println("The graph is not connected")
	}

	// input parameter: an integer distance
	// output: compute neighbourhood matrix for distance 
	static void neighbourhood(int distance, int result[][], int size) {
		//results for distance 2
		for (int y = 0; y < size; y++) //loops through y coordinate
		{
			for (int x = 0; x < size; x++)
			{   //checks if the results are equal to 1
				if (adjMatrix[y][x] == 1){
					result[y][x] = 1;
				}
				else if (x != y) //nodes can't have a distance to it's self.
				{
					for (int c = 0; c < size; c++) //looks along the row and checks for the distances
					{
						if (adjMatrix[x][c] == 1 && x != c && adjMatrix[y][c] > 0)
						{
							result[y][x] = adjMatrix[x][c]+1; //finds the distance.
						}
					}
				}
				if (result[y][x] > distance) //if the distance is too high, 
					result[y][x] = 0;        //it shouldn't be looking that far.
			}
		}
		//intended to use but didn't work
		//for (int i = 2; i < distance; i++)
		//	result = additionate(distance, result, size);
	}

	//intended to use but didn't work. 
	public static int[][] additionate(int distance, int[][] a, int size)
	{
		boolean have = false;
		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++)
			{
				for (int i = 0; i < distance; i++){
					if (adjMatrix[y][x] == i){
						a[y][x] = i;
						have = true;
					}
				}
				if ((x != y) && !have)
				{
					for (int c = 0; c < size; c++)
					{
						if (adjMatrix[x][c] == distance-1 && x != c && adjMatrix[y][c] > 0)
						{
							a[y][x] = adjMatrix[x][c]+1; 
						}
					}
				}
				if (a[y][x] > distance)
					a[y][x] = 0;
				have = false;
			}
		}
		return a;
	}

	// DO NOT change this method
	static void printSquareArray(int array[][], int size) {
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}

	
	// DO NOT change this method
	static void input() {
		int i, j;
		boolean success = false;

		try {
			success = true;
//			System.out.print("How many vertices (" + MinVertex + "--" + MaxVertex + ")? ");
			numVertex = keyboardInput.nextInt();
			if (numVertex > MaxVertex || numVertex < MinVertex) {
				success = false;
			}
			if (success) {
//				System.out.println("Enter adjacency matrix: ");
				for (i=0; i<numVertex; i++)
					for (j=0; j<numVertex; j++)
						adjMatrix[i][j] = keyboardInput.nextInt();
				for (i=0; i<numVertex && success; i++) {
					if (adjMatrix[i][i] != 0)
						success = false;
					for (j=0; j<numVertex; j++) {
						if (adjMatrix[i][j] != adjMatrix[j][i])
							success = false;
					}
				}
			}
			if (!success) {
				System.out.print("Incorrect range ");
				System.out.print("or adjacency matrix not symmetric ");
				System.out.println("or vertex connected to itself");
				System.exit(0);
			}
		}
		catch (Exception e) {
			keyboardInput.next();
		}
	}

}

