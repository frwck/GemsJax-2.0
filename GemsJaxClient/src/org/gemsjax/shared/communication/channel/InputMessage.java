package org.gemsjax.shared.communication.channel;

import org.gemsjax.shared.communication.CommunicationConnection;

/**
 * A {@link InputMessage} is a message that is delivered by the underlying {@link CommunicationConnection} to
 * the registered {@link InputChannel}s.
 * 
 * @author hannes
 *
 */
public class InputMessage {
	
	
	/**
	 * The HTTP response status code.<br />
	 * For example: 200, 404, 500, 300, ... <br />
	 * 
	 */
	private int statusCode;
	
	/**
	 * The response message text, received from the server.  
	 * Is only set, if {@link ResponseStatus#VALID}
	 */
	private String message;
	
	
	
	/**
	 * 
	 * @param statusCode The HTTP status code
	 * @param message The received message as string (must be parsed by a parser)
	 * @see ResponseStatus
	 */
	public InputMessage(int statusCode, String message)
	{
		this.statusCode = statusCode;
		this.message = message;
		
	}
	
	
	
	/**
	
	 * Get the HTTP response status code.<br />
	 * For example: 200, 404, 500, 300, ... <br />
	 * @return the HTTP status code
	 */
	public int getStatusCode() {
		return statusCode;
	}


	/**
	 * Get the response message text, received from the server.  
	 * @return the received message 
	 */
	public String getText() {
		return message;
	}


}
