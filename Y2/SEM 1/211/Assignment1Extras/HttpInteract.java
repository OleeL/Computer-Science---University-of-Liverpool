/*************************************
 * Filename:  HttpInteract.java
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
 * Class for downloading one object from http server.
 *
 */
public class HttpInteract {
	private String host;
	private String path;
	private String requestMessage;
	private String mime = "";
		
	private static final int HTTP_PORT = 80;
	private static final String CRLF = "\r\n";
	private static final int BUF_SIZE = 4096; 
	private static final int MAX_OBJECT_SIZE = 102400;

 	/* Create a HttpInteract object. */
	public HttpInteract(String url) {
		
		/* Split "URL" into "host name" and "path name", and
		 * set host and path class variables. 
		 * if the URL is only a host name, use "/" as path 
		 */		
		constructPath(url);
		
		/* Construct requestMessage, add a header line so that
		 * server closes connection after one response. */		

		System.out.println("requestMessage = "+requestMessage);
		return;
	}
	
	
	/* Send Http request, parse response and return requested object 
	 * as a String (if no errors), 
	 * otherwise return meaningful error message. 
	 * Don't catch Exceptions. EmailClient will handle them. */		
	public String send() throws IOException {
		
		/* buffer to read object in 4kB chunks */
		char[] buf = new char[BUF_SIZE];
	
		/* Maximum size of object is 100kB, which should be enough for most objects. 
		 * Change constant if you need more. */		
		char[] body = new char[MAX_OBJECT_SIZE];
		
		String statusLine="";	// status line
		int status;		// status code
		String headers="";	// headers
		int bodyLength=-1;	// length of body
				
		String[] tmp;
		
		/* The socket to the server */
		Socket connection;
		
		/* Streams for reading from and writing to socket */
		BufferedReader fromServer;
		DataOutputStream toServer;
		
		System.out.println("Connecting server: " + host+CRLF);
		
		while (true){
			/* Connect to http server on port 80.
			 * Assign input and output streams to connection. */
			connection = new Socket(host, 80);
			fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			toServer = new DataOutputStream(connection.getOutputStream());
			
			System.out.println("Send request:\n" + requestMessage);


			/* Send requestMessage to http server */
			toServer.writeBytes(requestMessage);
			
			/* Read the status line from response message */
			statusLine = fromServer.readLine();
			statusLine = getCode(statusLine);
			System.out.println("Status Line: "+statusLine+CRLF);
			
			/* Extract status code from status line. If status code is not 200,
			 * close connection and return an error message. 
			 * Do NOT throw an exception */		
			//String response = fromServer.readLine();
			//System.out.println(response);
			if (statusLine.startsWith("301") || statusLine.startsWith("302"))
			{
				System.out.println("Redirecting...");
				String temp = "not null";
				while (true)
				{	
					temp = fromServer.readLine();
					if (temp.startsWith("Location:"))
						break;
				}
				connection.close();
				constructPath(removehttps(getCode(temp)));
			}		
			else if (!statusLine.startsWith("200"))
			{
				connection.close();
				return "Status code was not 200";
			}
			else
			{
				break;			
			}			
		}
		
		
		/* Read header lines from response message, convert to a string, 
 		 * and assign to "headers" variable. 
		 * Recall that an empty line indicates end of headers.
		 * Extract length  from "Content-Length:" (or "Content-length:") 
		 * header line, if present, and assign to "bodyLength" variable. 
		*/
		// requires about 10 lines of code
		
		headers = fromServer.readLine();
		System.out.println("Headers:"+CRLF);
		String temp;
		int contentLength = 0;
		while (true){
			temp = fromServer.readLine();
			headers += temp+CRLF;
			if (temp.startsWith("Content-Length:")){
				contentLength = Integer.parseInt(temp.replaceAll("[^0-9]", ""));
				mime += temp + CRLF;}
			else if (temp.startsWith("Content-Disposition:"))
				mime += temp + CRLF;
			else if (temp.startsWith("Content-Type:")){
				mime += temp + CRLF;
				break;} // if header line is not present, do not assign anything.
		}
		String[] pathSplit = path.split("/");
		mime += "Files included: "+pathSplit[(pathSplit.length-1)]+CRLF;
		
		fromServer.readLine();
		/* If object is larger than MAX_OBJECT_SIZE, close the connection and 
		 * return meaningful message. */
		if ((contentLength*32) > MAX_OBJECT_SIZE ){
			connection.close();
			return "ERROR: message too large to open";
		}
		System.out.println(headers);
		//}
					    
		/* Read the body in chunks of BUF_SIZE using buf[] and copy the chunk
		 * into body[]. Stop when either we have
		 * read Content-Length bytes or when the connection is
		 * closed (when there is no Content-Length in the response). 
		 * Use one of the read() methods of BufferedReader here, NOT readLine().
		 * Make sure not to read more than MAX_OBJECT_SIZE characters.
		 */				
		int bytesRead = 0;
		while (bytesRead < contentLength) {
			int response = fromServer.read(buf, 0, BUF_SIZE);
			if (response == bodyLength)
				break;
			for (int i = 0; ((i + bytesRead) < MAX_OBJECT_SIZE) && (i < response); i++)
				body[bytesRead + i] = buf[i]; 
			bytesRead += response;
		}

		/* At this points body[] should hold to body of the downloaded object and 
		 * bytesRead should hold the number of bytes read from the BufferedReader
		 */
		
		/* Close connection and return object as String. */
		System.out.println("Done reading file. Closing connection.");
		connection.close();
		return(new String(body, 0, bytesRead));
	}
	
	public String getMime()
	{
		return mime;
	}
	
	private void constructPath(String url)
	{
		URL url2;
		try{
			url2 = new URL("http://"+url);
			host = url2.getHost();
			path = url2.getPath();
		}
		catch(MalformedURLException ex){
			System.out.println("Error forming path");
		}
		requestMessage= "GET "+path+ " HTTP/1.1"+CRLF+"Host: "+host+CRLF+CRLF;
		
		System.out.println("URL = "+url);
		System.out.println("host = "+host);
		System.out.println("path = "+path);	
	}
	
	private static String getCode(String code)
	{
		if (code.charAt(0) ==  " ".charAt(0)) 
			return code.substring(1);
		else
			return ""+getCode(code.substring(1));
	}
	
	private static String removehttps(String code)
	{
		return code.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","");
	}
}