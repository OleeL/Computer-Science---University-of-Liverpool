/*
	Name: Oliver Legg
	Student ID: 201244658
	Departmental username: u7ol2
	University email address; O.Legg@student.liverpool.ac.uk
*/

import java.util.*;
import java.io.*;


// Implement a linked list from the object A2Node
class A2List {

	private static Scanner keyboardInput = new Scanner (System.in);
	public static A2Node head, tail; // head and tail of the linked list
	private static final int MaxInitCount = 10;
	private static final int MaxReqCount = 100;
	public static int initCount;
	public static int reqCount;

	public static int[] reqData = new int[MaxReqCount]; // store the requests, accessible to all methods


	// DO NOT change the main method
	public static void main(String[] args) throws Exception {
		A2Node curr;
		int tmp=-1;
		int[] initData = new int[MaxInitCount];

		initCount = 0;
		reqCount = 0;
		head = null;
		tail = null;

		try {
//			System.out.println();
//			System.out.print("Enter the initial number of files in the cabinet (1-" + MaxInitCount + "): ");
			initCount = keyboardInput.nextInt();
			if (initCount > MaxInitCount || initCount <= 0)
				System.exit(0);
//			System.out.print("Enter the initial file IDs in the cabinet (" + initCount + " different +ve integers): ");
			for (int i=0; i<initCount; i++)
				initData[i] = keyboardInput.nextInt();				
//			System.out.println();
//			System.out.print("Enter the number of file requests (1=" + MaxReqCount + "): ");
			reqCount = keyboardInput.nextInt();
			if (reqCount > MaxReqCount || reqCount <= 0)
				System.exit(0);
//			System.out.print("Enter the request file IDs (" + reqCount + " different +ve integers): ");
			for (int i=0; i<reqCount; i++)
				reqData[i] = keyboardInput.nextInt();				
		}
		catch (Exception e) {
			keyboardInput.next();
			System.exit(0);
		}


		
		try {
			System.out.println("appendIfMiss...");
			// create a list with the input data
			// call appendIfMiss()
			for (int i=initCount-1; i>=0; i--) {
				insertNodeHead(new A2Node(initData[i]));
			}
			appendIfMiss();
		}
		catch (Exception e) {
			System.out.println("appendIfMiss exception! " + e);
		}

		try {
			System.out.println("moveToFront...");
			// empty the previous list and restart with the input data
			// then call moveToFront()
			emptyList();
			for (int i=initCount-1; i>=0; i--) {
				insertNodeHead(new A2Node(initData[i]));
			}
			moveToFront();
		}
		catch (Exception e) {
			System.out.println("moveToFront exception!");
		}

		try {
			System.out.println("freqCount...");
			// empty the previous list and restart with the input data
			// then call freqCount()
			emptyList();
			for (int i=initCount-1; i>=0; i--) {
				insertNodeHead(new A2Node(initData[i]));
			}
			freqCount();
		}
		catch (Exception e) {
			System.out.println("freqCount exception!");
		}		
	}
	
	// append to end of list when miss
	static void appendIfMiss() {
		A2Node curr;                          //the current node
		int hits = 0;                         //counts the number of hits
		int comparisons = 0;                  //counts the number of comparisons
		boolean inList = false;               //variable which changes depending on whether the value is in the list or not
		for (int i = 0; i < reqCount; i++){   //loops through the requests
			curr = head;                      //sets the current to the head of the linked list to start the iteration 
			while (curr != null) { 			  //begins to iterate through the list until the node is empty.
				comparisons++;				  //the comparisons are increased as the next line is where the data is compared
				if (reqData[i] == curr.data){ //if the request data is the same as the data in the list then it's a hit
					inList = true;
					hits++;
					break;					  //stops using this requested number as we've found what we wanted from the linked list.
				}
				curr = curr.next; 			  //if the data is did not match the data in the list then check the next node.
			}
			if (!inList){							//if the request is not in the list then add the request to the list at the end 
				tail.next = new A2Node(reqData[i]); //adds the node
				tail = tail.next;                   //sets a new tail
			}
			inList = false;
			System.out.print(comparisons+ " ");
			comparisons = 0;
		}
		System.out.println();
		System.out.println(hits+" h");
		printList();
	}

	// move the file requested to the beginning of the list
	static void moveToFront() {
		A2Node before;                        //node before the current node
		A2Node curr;                          //the current node
		int hits = 0;
		int comparisons = 0;
		boolean inList = false;
		for (int i = 0; i < reqCount; i++){   //loops through the requests
			curr = head;
			before = curr;                    //initialises the before it's called
			while (curr != null) {            
				comparisons++;
				if (reqData[i] == curr.data){
					inList = true;
					hits++;
					if (curr == head) 		 //if the current is already the head, you don't need to move it. So break
						break;
					before.next = curr.next; //swaps the previous next to the current next. (moves past the current)
					curr.next = head;        //makes the current's next the head
					head = curr;             //makes the head the current node
					break;
				}
				before = curr;               //sets the before var to the node before the current
				curr = curr.next; 			 //here the current updates and before has the old node.
			}
			if (!inList)      							
				insertNodeHead(new A2Node(reqData[i])); //adds the node onto the head of the link list 
			inList = false;
			System.out.print(comparisons+ " ");
			comparisons = 0;
		}
		System.out.println();
		System.out.println(hits+" h");
		printList();
	}
	
	// move the file requested so that order is by non-increasing frequency
	static void freqCount() {
		A2Node curr;   //the current node selected
		int hits = 0;
		int comparisons = 0;
		boolean inList = false;
		for (int i = 0; i < reqCount; i++){ 
			curr = head;
			while (curr != null) {
				comparisons++;
				if (reqData[i] == curr.data){
					curr.freq++;       //increments the node's frequency as hit has been
					hits++;
					inList = true;
					curr = head;
					sortByFrequency(); //sorts the list by frequency 
					break;             //once the if statement is done, just break out the loop and finish
				}
			curr = curr.next;
			}
			if (!inList){
				tail.next = new A2Node(reqData[i]); //add the data to the tail of the list
				tail = tail.next;					//redefines the new tail of the list
			}
			sortByFrequency(); //sorts the list by frequency 
			inList = false;
			System.out.print(comparisons+ " ");
			comparisons = 0;
		}
		System.out.println();
		System.out.println(hits+" h");
		printList();
	}

	//this sorts by frequency in the link list.
	//this uses the bubble sort algorithm
	static void sortByFrequency(){
		int tempF;                             //temporary frequency for swapping the values
		int tempV;	                           //temporary value for swapping frequencies
		A2Node curr = head;
		while (curr.next != null){             //the loops stop when the current node gets to the end of the list
			if (curr.freq < (curr.next).freq){ //sorting the list into freq descending
				tempF = curr.freq; 			   //saving the frequency values
				tempV = curr.data;			   //saving the data values
				curr.freq = (curr.next).freq;  //swapping the freq values
				curr.data = (curr.next).data;  //swapping the data values
				(curr.next).freq = tempF; 	   //swapping the freq values
				(curr.next).data = tempV;      //swapping the data values
				curr = head;                   //restarts the loop back to the head
			}
			curr = curr.next;                  //sorts the next item in the list
		}
	}

	static void insertNodeHead(A2Node newNode) {

		newNode.next = head;
		if (head == null)
			tail = newNode;
		head = newNode;
	}

	// DO NOT change this method
	// delete the node at the head of the linked list
	static A2Node deleteHead() {
		A2Node curr;

		curr = head;
		if (curr != null) {
			head = head.next;
			if (head == null)
				tail = null;
		}
		return curr;
	}

	// DO NOT change this method
	// print the content of the list in two orders:
	// from head to tail
	static void printList() {
		A2Node curr;

		System.out.print("List: ");
		curr = head;
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
		System.out.println();
	}

	
	// DO NOT change this method
	static void emptyList() {
		
		while (head != null)
			deleteHead();
	}

}
