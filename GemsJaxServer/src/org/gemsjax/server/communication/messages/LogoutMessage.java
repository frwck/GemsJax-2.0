package org.gemsjax.server.communication.messages;

import org.gemsjax.shared.CommunicationConstants;

/**
 * Used to send a Logout Message to the client, to tell the client, that he was logged out by the server
 * @author Hannes Dorfmann
 *
 */
public class LogoutMessage implements Message{

	public enum LogoutReason
	{
		/**
		 * Is used by the server, to told the client that the reason for the logout is, that another client
		 * has been authenticated and connected (for the same User).
		 * This is mapped with the constant {@link CommunicationConstants.Logout#REASON_OTHER_CONNECTION}
		 */
		OTHER_CONNECTION
	}
	
	private LogoutReason reason;
	
	public LogoutMessage(LogoutReason reason)
	{
		this.reason = reason;
	}

	
	private Integer reasonToIn()
	{
		switch(reason)
		{
			case OTHER_CONNECTION: return CommunicationConstants.Logout.REASON_OTHER_CONNECTION;
		}
		
		return null;
	}

	@Override
	public String toXml() {
		
		Integer r = reasonToIn();
		
		return "<logout reason=\""+r+"\" />";
	}
	
}
