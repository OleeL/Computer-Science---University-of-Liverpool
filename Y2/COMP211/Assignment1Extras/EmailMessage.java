/*************************************
 * Filename:  EmailMessage.java
 * Names: Oliver Legg
          Victor Andres Del Milagro Hidalgo Rivas
 * Student ID: 201244658
               201233880
 * Date: 19/10/2018
 Extras
 *************************************/

import java.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.*;

public class EmailMessage {
	/* SMTP-sender of the message (in this case, contents of From-header. */
	public String Sender;

	/* SMTP-recipient, or contents of To-header. */
	public String Recipient;

	/* Target MX-host */
	public String DestHost;
	private InetAddress DestAddr;
    public int DestHostPort;

	/* The headers and the body of the message. */
	public String Headers;
	public String Body;
	public static String mime = "";
	
	/* To make it look nicer */
	private static final String CRLF = "\r\n";

	/*
	 * Create the message object by inserting the required headers from RFC 822
	 * (From, To, Date).
	 */
	public EmailMessage(String from, String to, String cc, String subject, String text, String localServer, int localServerPort) throws UnknownHostException {
		String[] cc2 = cc.replaceAll(" ", "").split(","); // get the CarbonCopy
		
		Sender = from.trim(); // get rid of white spaces.
		Recipient = to.trim();
		Headers = "From: " + Sender + CRLF;
		Headers += "To: " + Recipient + CRLF;
		if (cc2[0].length() > 0){ // check if the user has ever input a cc.
			Headers += "CC: ";
			for (int i = 0; i < cc2.length; i++){ // Separate email addresses appropriately and add them to the headers.
				Headers += cc2[i];
				if (i+1 < cc2.length)
					Headers += ", ";
			}
			Headers += CRLF;
		}
		if (mime.length() > 0) // Add the http to the headers if it has been downloaded.
			Headers += mime;
		Headers += "Subject: " + subject.trim() + CRLF;

		/*
		 * A close approximation of the required format. Unfortunately only GMT.
		 */
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss 'GMT'");
		String dateString = format.format(new Date());
		Headers += "Date: " + dateString + CRLF;

		/*
		 * Get message. We must escape the message to make sure that there are
		 * no single periods on a line. This would mess up sending the mail.
		 */
		Body = escapeMessage(text);

		/*
		 * Take the name of the local mailserver and map it into an InetAddress
		 */
		DestHost = localServer;
		try {
			DestAddr = InetAddress.getByName(DestHost);
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + DestHost);
			System.out.println(e);
			throw e;
		}
        DestHostPort = localServerPort;
	}

	/*
	 * Check whether the message is valid. In other words, check that both
	 * sender and recipient contain only one @-sign.
	 */
	public boolean isValid() {
		int fromat = Sender.indexOf('@');
		int toat = Recipient.indexOf('@');

		if (fromat < 1 || (Sender.length() - fromat) <= 1) {
			System.out.println("Sender address is invalid");
			return false;
		}
		if (toat < 1 || (Recipient.length() - toat) <= 1) {
			System.out.println("Recipient address is invalid");
			return false;
		}
		if (fromat != Sender.lastIndexOf('@')) {
			System.out.println("Sender address is invalid");
			return false;
		}
		if (toat != Recipient.lastIndexOf('@')) {
			System.out.println("Recipient address is invalid");
			return false;
		}
		return true;
	}

	/* For printing the message. */
	public String toString() {
		String res;

		res = "Sender: " + Sender + '\n';
		res += "Recipient: " + Recipient + '\n';
		res += "MX-host: " + DestHost + ", address: " + DestAddr + '\n';
		res += "Message:" + '\n';
		res += Headers + CRLF;
		res += Body;

		return res;
	}

	/*
	 * Escape the message by doubling all periods at the beginning of a line.
	 */
	private String escapeMessage(String body) {
		String escapedBody = "";
		String token;
		StringTokenizer parser = new StringTokenizer(body, "\n", true);

		while (parser.hasMoreTokens()) {
			token = parser.nextToken();
			if (token.startsWith(".")) {
				token = "." + token;
			}
			escapedBody += token;
		}
		return escapedBody;
	}
}
