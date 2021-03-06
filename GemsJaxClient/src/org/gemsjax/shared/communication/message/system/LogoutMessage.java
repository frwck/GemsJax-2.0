package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.CommunicationConstants;



/**
 * Used to send a Logout Message to the client, to tell the client, that he was logged out by the server
 * @author Hannes Dorfmann
 *
 */
public class LogoutMessage extends SystemMessage{

	public enum LogoutReason
	{
		/**
		 * Is used by the server, to told the client that the reason for the logout is, that another client
		 * has been authenticated and connected (for the same User).
		 * This is mapped with the constant {@link CommunicationConstants.Logout#REASON_SERVER_OTHER_CONNECTION}
		 */
		SERVER_OTHER_CONNECTION,
		
		/**
		 * The user has clicked on his client the logout button and want to be logged out right now.
		 * This is mapped with the constant {@link CommunicationConstants.Logout#REASON_CLIENT_USER_LOGOUT}
		 */
		CLIENT_USER_LOGOUT
	}
	
	/**
	 * The {@link LogoutMessage} is embarked in this tag
	 */
	public static final String TAG = "logout";
	public static final String ATTRIBUTE_REASON="reason";
	
	private LogoutReason reason;
	
	public LogoutMessage(LogoutReason reason)
	{
		this.reason = reason;
	}

	
	private Integer reasonToInt()
	{
		switch(reason)
		{
			case SERVER_OTHER_CONNECTION: return CommunicationConstants.Logout.REASON_SERVER_OTHER_CONNECTION;
			case CLIENT_USER_LOGOUT: return CommunicationConstants.Logout.REASON_CLIENT_USER_LOGOUT;
		}
		
		return null;
	}
	
	/**
	 * Converts a int (see {@link CommunicationConstants.Logout} to a {@link LogoutReason} enum
	 * @param reasonCode
	 * @return
	 */
	public static LogoutReason intToLogoutReason(int reasonCode)
	{
		switch(reasonCode)
		{
			case CommunicationConstants.Logout.REASON_CLIENT_USER_LOGOUT: return LogoutReason.CLIENT_USER_LOGOUT;
			case CommunicationConstants.Logout.REASON_SERVER_OTHER_CONNECTION: return LogoutReason.SERVER_OTHER_CONNECTION;
		}
		
		return null;
	}
	
	
	public LogoutReason getLogoutReason()
	{
		return reason;
	}
	

	@Override
	public String toXml() {
		
		Integer r = reasonToInt();
		
		return "<"+SystemMessage.TAG+"><"+TAG+" "+ATTRIBUTE_REASON+"=\""+r+"\" /></"+SystemMessage.TAG+">";
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof LogoutMessage) ) return false;
		
		final LogoutMessage that = (LogoutMessage) other;
		
		
		if (this.reason == that.reason)
			return true;
		
		return false;
	}
		
	@Override
	public int hashCode() {
		if (reason!=null)
			return reason.hashCode();
		else
			return super.hashCode();
	}


}
