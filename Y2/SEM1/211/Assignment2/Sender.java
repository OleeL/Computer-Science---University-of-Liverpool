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
     *  Useful links
     * https://cgi.csc.liv.ac.uk/~gairing/COMP211/
     * https://cgi.csc.liv.ac.uk/~gairing/COMP211/LN/COMP211_Topic3_Transport.pdf
     * https://cgi.csc.liv.ac.uk/~gairing/COMP211/A2/
     * 
     */

    // Add any necessary class variables here. They can hold
    // state information for the sender. 
    private int seqnum;
    private int acknum;
    private Packet tmp_packet;
    private double t;

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

    // Runs if the 
    private final void resendMessage()
    {
        startTimer(t);
        udtSend(tmp_packet);
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
        // Starts the timer.
        startTimer(t);

        // Sending the packet and sending it to receiver and saving the packet temporarily.
        tmp_packet = new Packet(seqnum, acknum, checksum(message.getData()), message.getData());
        udtSend(tmp_packet);

        // Saving the message to a temp message variable
        acknum = getBytes(message.getData());
    }
    
    // This routine will be called whenever a packet sent from the receiver
    // (i.e. as a result of udtSend() being done by a receiver procedure)
    // arrives at the sender.  "packet" is the (possibly corrupted) packet
    // sent from the receiver.
    protected void Input(Packet packet)
    {
        // The timer stops because I have recieved something from the reciever
        stopTimer();

        // Checking if the message was acknowedged if it was then increase
        // the ack and seqnum. If it wasn't then try to send the message again
        if (acknum == packet.getAcknum()){
            
            // Accepted
            acknum = packet.getAcknum();
            seqnum += packet.getAcknum();
        }
        else
        {
            // Message that was sent was corrupt
            resendMessage();
        }

    }
    
    // This routine will be called when the senders's timer expires (thus 
    // generating a timer interrupt). You'll probably want to use this routine 
    // to control the retransmission of packets. See startTimer() and 
    // stopTimer(), above, for how the timer is started and stopped. 
    protected void TimerInterrupt()
    {
        resendMessage();
    }
    
    // This routine will be called once, before any of your other sender-side 
    // routines are called. It should be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of the sender).

    protected void Init()
    {
        seqnum = 0;
        acknum = 0;
        t = 40;
    }

}