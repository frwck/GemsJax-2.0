package org.gemsjax.client.communication.exception;

import org.gemsjax.client.communication.WebSocket;

public class WebSocketConnectionException extends Exception{
	
	public enum Reason
	{
		/**
		 * The connection has not yet been established.
		 * That means, that sending and receiving is not possible so far.<br />
		 * WebSocket API readystate = 0
		 */
		
		CONNECTING,
		/**
		 * The connection is going through the closing handshake.
		 * That means, that sending and receiving is no longer possible, because the connection is in the final closing phase.
		 * The connection will be released briefly and the next reached state is the {@link #CLOSED} state. 
		 */
		CLOSING,
		/**
		 * There is no connection to with the WebSocket, because the connection is closed correctly via the closing handshake.
		 * So its not possible to send or receive data via the {@link WebSocket}
		 */
		CLOSED
	}
	
	
	
	private Reason reason;
	
	
	/**
	 * Creates a new {@link WebSocketConnectionException} by handling the 
	 * following states, which are specified in the WebSocket API  (http://dev.w3.org/html5/websockets/): 
	 * <ul>
  		<li>const unsigned short CONNECTING = 0;	</li>
  		<li>const unsigned short OPEN = 1;		</li>
  		<li>const unsigned short CLOSING = 2;	</li>
  		<li>const unsigned short CLOSED = 3;	</li>
  	   </ul>
  		where the states mean:
  		<ul>
  		<li>CONNECTING (numeric value 0) The connection has not yet been established. </li>
		<li>OPEN (numeric value 1) The WebSocket connection is established and communication is possible.</li>
		<li>CLOSING (numeric value 2) The connection is going through the closing handshake. </li>
		<li>CLOSED (numeric value 3) The connection has been closed or could not be opened.<li>
		</ul>
	 * @param readyState
	 */
	public WebSocketConnectionException( int readyState)
	{
		switch (readyState)
		{
			case 0: reason =Reason.CONNECTING; break;
			case 2: reason = Reason.CLOSING; break;
			case 3: reason = Reason.CLOSED; break;
		}
	}
	
	
	public Reason getReason()
	{
		return reason;
	}

}
