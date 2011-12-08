package org.gemsjax.client.communication.exception;

import org.gemsjax.client.communication.WebSocketCommunicationConnection;

/**
 * This exception is thrown, when an error occures while sending data via the {@link WebSocketCommunicationConnection} 
 * @author Hannes Dorfmann
 *
 */
public class WebSocketSendException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7440035781981424666L;
	private String toSend;
	
	
	public WebSocketSendException(String toSend)
	{
		super();
		this.toSend = toSend;
	}
	
	/**
	 * Get the message / data, which should be sent but couln't and raise this exception
	 * @return
	 */
	public String getToSend()
	{
		return toSend;
	}

}
