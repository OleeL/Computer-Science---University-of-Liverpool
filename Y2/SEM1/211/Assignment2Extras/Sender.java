/*************************************
 * Filename:  Sender.java
 * Names: 
 *              Oliver Legg
 *              Victor Andres Del Milagro Hidalgo Rivas
 * 
 * Student-IDs:
 *              201244658
 *              201233880
 * 
 * Date: 1/11/2018
 *************************************/
import java.util.Random;

// I created an ArrayList data structure to put the packets in.
// It doesn't say that I can't import other libraries in the requirements so I have.
// I have tested the ArrayList in the linux farms and it works.
import java.util.ArrayList;

public class Sender extends NetworkHost

{
    /*
     * Predefined Constant (static member variables):
     *
     *   int MAXDATASIZE : the maximum size of the Message data and
     *                     Packet payload
     *
     *
     * Predefined Member Methods:
     *
     *  void startTimer(double increment):
     *       Starts a timer, which will expire in
     *       "increment" time units, causing the interrupt handler to be
     *       called.  You should only call this in the Sender class.
     *  void stopTimer():
     *       Stops the timer. You should only call this in the Sender class.
     *  void udtSend(Packet p)
     *       Sends the packet "p" into the network to arrive at other host
     *  void deliverData(String dataSent)
     *       Passes "dataSent" up to application layer. Only call this in the 
     *       Receiver class.
     *  double getTime()
     *       Returns the current time of the simulator.  Might be useful for
     *       debugging.
     *  String getReceivedData()
     *       Returns a String with all data delivered to receiving process.
     *       Might be useful for debugging. You should only call this in the
     *       Sender class.
     *  void printEventList()
     *       Prints the current event list to stdout.  Might be useful for
     *       debugging, but probably not.
     *
     *
     *  Predefined Classes:
     *
     *  Message: Used to encapsulate the message coming from application layer
     *    Constructor:
     *      Message(String inputData): 
     *          creates a new Message containing "inputData"
     *    Methods:
     *      boolean setData(String inputData):
     *          sets an existing Message's data to "inputData"
     *          returns true on success, false otherwise
     *      String getData():
     *          returns the data contained in the message
     *  Packet: Used to encapsulate a packet
     *    Constructors:
     *      Packet (Packet p):
     *          creates a new Packet, which is a copy of "p"
     *      Packet (int seq, int ack, int check, String newPayload)
     *          creates a new Packet with a sequence field of "seq", an
     *          ack field of "ack", a checksum field of "check", and a
     *          payload of "newPayload"
     *      Packet (int seq, int ack, int check)
     *          chreate a new Packet with a sequence field of "seq", an
     *          ack field of "ack", a checksum field of "check", and
     *          an empty payload
     *    Methods:
     *      boolean setSeqnum(int n)
     *          sets the Packet's sequence field to "n"
     *          returns true on success, false otherwise
     *      boolean setAcknum(int n)
     *          sets the Packet's ack field to "n"
     *          returns true on success, false otherwise
     *      boolean setChecksum(int n)
     *          sets the Packet's checksum to "n"
     *          returns true on success, false otherwise
     *      boolean setPayload(String newPayload)
     *          sets the Packet's payload to "newPayload"
     *          returns true on success, false otherwise
     *      int getSeqnum()
     *          returns the contents of the Packet's sequence field
     *      int getAcknum()
     *          returns the contents of the Packet's ack field
     *      int getChecksum()
     *          returns the checksum of the Packet
     *      String getPayload()
     *          returns the Packet's payload
     * 
     */

    // Add any necessary class variables here. They can hold
    // state information for the sender. 
    private int acknum;                         // Acknowledgement Number
    private int seqnum;                         // Sequence Number
    private double t;                           // Time
    private int windowSize;                     // The size of the window
    private Packet[] window;                    // The window data structure
    private boolean[] windowSent;               // A list to see the messages sent
    private ArrayList<Packet> messagesToBeSent; // A list of messages that cannot be sent yet

    // Also add any necessary methods (e.g. checksum of a String)

    // Checksum a given string
    private final int checksum(String text)
    {
        int sum = 0;
        for (int i = 0; i < text.length(); i++)
            sum += Character.getNumericValue(text.charAt(i));
        return sum;
    }

    // Gets the number of bytes in a string
    private final int getBytes(String text)
    {
        return text.length();
    }

    // Runs if the packet is corrupt or lost
    private final void resendMessage(Packet p)
    {
        startTimer(t);
        udtSend(p);
    }

    //checks if the window is full
    private final boolean windowIsFull()
    {
        for (int i = 0; i < windowSize; i++)
        {
            if (window[i] == null)
                return false;
        }
        return true;
    }

    //Shifts all the index values of packets in the window down by 1
    private final void windowShift()
    {
        for (int i = 0; i < windowSize-1; i++)
        {
            if (windowSent[i] == true){
                window[i]     = window[i+1];
                windowSent[i] = windowSent[i+1];
            }
        }
        fillWindow();
    }

    // pops the front of the queue in messagesToBeSent and resends the message
    private final void fillWindow()
    {
        for (int i = 0; i < windowSize-1; i++){
            if (messagesToBeSent.size() != 0 && window[i] == null)
            {
                window[i]     = messagesToBeSent.remove(0);
                resendMessage(window[i]);
                windowSent[i] = false;
            }
        }
    }


    // This is the constructor.  Don't touch!
    public Sender(int entityName,
                       EventList events,
                       double pLoss,
                       double pCorrupt,
                       int trace,
                       Random random)
    {
        super(entityName, events, pLoss, pCorrupt, trace, random);
    }

    // This routine will be called whenever the application layer at the sender
    // has a message to  send.  The job of your protocol is to insure that
    // the data in such a message is delivered in-order, and correctly, to
    // the receiving application layer.
    protected void Output(Message message)
    {

        // The acknowledgement number is equal to the number of bytes in the paylaod
        acknum = getBytes(message.getData());
        seqnum++;
        // If the window is not full start the timer and add the message to the window
        if (!windowIsFull())
        {
            for (int i = 0; i < windowSize; i++){
                if (window[i] == null){
                    // Sending the packet and sending it to receiver and saving the packet temporarily.
                    startTimer(t);
                    window[i] = new Packet(seqnum, acknum, checksum(message.getData()), message.getData());
                    windowSent[i] = false;
                    udtSend(window[i]);
                    break;
                }
            }
        }
    }
    
    // This routine will be called whenever a packet sent from the receiver
    // (i.e. as a result of udtSend() being done by a receiver procedure)
    // arrives at the sender.  "packet" is the (possibly corrupted) packet
    // sent from the receiver.
    protected void Input(Packet packet)
    {
        // The timer stops because I have recieved something from the receiver
        stopTimer();

        // Checking if the message was acknowedged if it was then increase
        // the acknum and seqnum. If it wasn't then try to send the message again
        for (int i = 0; i < windowSize; i++){
            if (window[i] != null){
                if (window[i].getAcknum() == packet.getAcknum() && window[i].getSeqnum() == packet.getSeqnum()){
                    // Accepted
                    windowSent[i] = true;
                    windowShift();
                    break;        
                }
                else
                {
                    // Message that was sent was corrupt
                    resendMessage(window[i]);
                    break;
                }
            }
        }
    }
    
    // This routine will be called when the senders's timer expires (thus 
    // generating a timer interrupt). You'll probably want to use this routine 
    // to control the retransmission of packets. See startTimer() and 
    // stopTimer(), above, for how the timer is started and stopped. 
    protected void TimerInterrupt()
    {
        for (int i = 0; i < windowSize; i++)
        {
            if (!windowSent[i] && window[i] != null)
                resendMessage(window[i]);
        }
    }
    
    // This routine will be called once, before any of your other sender-side 
    // routines are called. It should be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of the sender).

    protected void Init()
    {
        acknum = 0;                                   // Acknowledgement number
        seqnum = 0;                                   // Sequence number
        t = 40;                                       // Time
        windowSize = 8;                               // Size
        window = new Packet[windowSize];              // Window
        windowSent = new boolean[windowSize];         // Messages sent
        messagesToBeSent = new ArrayList<Packet>();   // A list of messages that cannot be sent yet
    }

}