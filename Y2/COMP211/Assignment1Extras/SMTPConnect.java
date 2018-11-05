/*************************************
 * Filename:  SMTPConnect.java
 * Names: Oliver Legg
          Victor Andres Del Milagro Hidalgo Rivas
 * Student-IDs: 201244658
                201233880
 * Date: 19/10/2018
 Extras
 *************************************/

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Open an SMTP connection to mailserver and send one mail.
 *
 */
public class SMTPConnect {

	/* The socket to the server */
	private Socket connection;

	/* Streams for reading from and writing to socket */
	private BufferedReader fromServer;
	private DataOutputStream toServer;
	private static final String CRLF = "\r\n";
	
	/* Are we connected? Used in close() to determine what to do. */
	private boolean isConnected = false;

	/* Create an SMTPConnect object. Create the socket and the 
	   associated streams. Initialise SMTP connection. */
	public SMTPConnect(EmailMessage mailmessage) throws IOException {
	
		// Open a TCP client socket with hostname and portnumber specified in
		// mailmessage.DestHost and  mailmessage.DestHostPort, respectively.
		System.out.println("Connecting to: "+mailmessage.DestHost +":"+ Integer.toString(mailmessage.DestHostPort));
		connection = new Socket(mailmessage.DestHost, mailmessage.DestHostPort);
	
		// attach the BufferedReader fromServer to read from the socket and
		// the DataOutputStream toServer to write to the socket
		fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		toServer = new DataOutputStream(connection.getOutputStream());

		/* Read one line from server and check that the reply code is 220.
		   If not, throw an IOException. */
		String response = fromServer.readLine();
		//System.out.println(response);
		if (!response.startsWith("220")) {
			throw new IOException("220 reply not received from server.");
		}

		/* SMTP handshake. We need the name of the local machine.
		   Send the appropriate SMTP handshake command. */
		String localhost = InetAddress.getLocalHost().getHostName();
		sendCommand( "HELO Handshake " + localhost + CRLF, 250 );
		isConnected = true;
    	}

    /* Send message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send(EmailMessage mailmessage) throws IOException {
		/* Send all the necessary commands to send a message. Call
	   	   sendCommand() to do the dirty work. Do _not_ catch the
	   	   exception thrown from sendCommand(). */
		String response;
		sendCommand("MAIL FROM: <"+mailmessage.Sender+">"+CRLF, 250);
		sendCommand("RCPT TO: <"+mailmessage.Recipient+">"+CRLF, 250);		
		sendCommand("DATA"+CRLF, 354);
		System.out.println(mailmessage.Headers+CRLF);
		System.out.println(mailmessage.Body+CRLF);
		sendCommand(mailmessage.Headers+CRLF+mailmessage.Body+CRLF+"."+CRLF, 250);
	}

    /* Close SMTP connection. First, terminate on SMTP level, then
       close the socket. */
	public void close() {
		isConnected = false;
		try {
		    sendCommand("QUIT"+CRLF, 221);
		    connection.close();
		} catch (IOException e) {
		    System.out.println("Unable to close connection: " + e);
		    isConnected = true;
		}
    }

    /* Send an SMTP command to the server. Check that the reply code is
       what is is supposed to be according to RFC 821. */
    private void sendCommand(String command, int rc) throws IOException {
		/* Write command to server and read reply from server. */
		//System.out.print(command); //used for testing to see what commands are being put through
		toServer.writeBytes(command);
		String response = fromServer.readLine();

		/* Check that the server's reply code is the same as the parameter
	   	   rc. If not, throw an IOException. */
		//System.out.println("The response should be: "+Integer.toString(rc)); //used for testing and to see what response should be seen
		//System.out.println("The response was: "+response+"\n");              //used for testing and to see what response was actually given
		if (!response.startsWith(Integer.toString(rc))) {
			throw new IOException(Integer.toString(rc)+" reply not received from server.");
		}
    }

    //Destructor. Closes the connection if something bad happens.
    protected void finalize() throws Throwable {
		if(isConnected) {
			close();
		}
		super.finalize();
    }
} 
