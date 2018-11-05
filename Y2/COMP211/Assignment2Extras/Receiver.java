/*************************************
 * Filename:  Receiver.java
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

public class Receiver extends NetworkHost

{
    /*
     * Predefined Constants (static member variables):
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
     *  Message: Used to encapsulate a message coming from application layer
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
    // state information for the receiver. 

    ArrayList<Packet> sequenceList; // Used as a stack to hold all of the packets
    int sequence; // Used as an index to see what order packets are coming.

    // Also add any necessary methods (e.g. checksum of a String)
    private final int checksum(String text)
    {
        int sum = 0;
        for (int i = 0; i < text.length(); i++)
            sum += Character.getNumericValue(text.charAt(i));
        return sum;
    }

    // Acknowledge the amount of bytes that were sent.
    private final int getBytes(String text)
    {
        return text.length();
    }

    // Delivers the packets in order.
    private final void deliverInOrder()
    {
        boolean hasChanged = false;
        while (sequenceList.size() != 0){
            for (int i = 0; i < sequenceList.size(); i++)
            {
                if (sequenceList.get(i).getSeqnum() == sequence)
                {
                    deliverData(sequenceList.get(i).getPayload());
                    sequenceList.remove(i);
                    sequence++;
                    hasChanged = true;
                    break;
                }
            }
            if (!hasChanged)
                break;
            hasChanged = false;
        }
    }

    // This is the constructor.  Don't touch!
    public Receiver(int entityName,
                       EventList events,
                       double pLoss,
                       double pCorrupt,
                       int trace,
                       Random random)
    {
        super(entityName, events, pLoss, pCorrupt, trace, random);
    }

    // This routine will be called whenever a packet from the sender
    // (i.e. as a result of a udtSend() being done by a Sender procedure)
    // arrives at the receiver. Argument "packet" is the (possibly corrupted)
    // packet sent from the sender.
    protected void Input(Packet packet)
    {
        // If the checksum passes then deliver the data
        // Seqnum, acknum, checksum, payload
        if (packet.getChecksum() == checksum(packet.getPayload())){
            sequenceList.add(packet);
            deliverInOrder();
            udtSend(new Packet(packet.getSeqnum(), getBytes(packet.getPayload()), 0));
        }
        else{
            udtSend(new Packet(packet.getSeqnum(), -1, 0));
        }
    }


    
    // This routine will be called once, before any of your other receiver-side
    // routines are called. It should be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of the receiver).
    protected void Init()
    {
        // Initialising the sequence list.
        sequenceList = new ArrayList<Packet>();
        
        // Sequence starts at 1.
        sequence = 1; 
    }

}
