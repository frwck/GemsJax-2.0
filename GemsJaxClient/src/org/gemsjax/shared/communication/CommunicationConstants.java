package org.gemsjax.shared.communication;

import org.gemsjax.shared.communication.message.UnexpectedErrorMessage;

/**
 * This class contains constants (grouped in subclasses) that were used for the xml communication between server and client and vice versa 
 * @author Hannes Dorfmann
 *
 */
public class CommunicationConstants {
	
	private CommunicationConstants(){}
	
	/**
	 * Constants used by the logout tag 
	 * @author Hannes Dorfmann
	 *
	 */
	public class Logout{
		
		private Logout(){}
		/**
		 * Is used by the server, to told the client that the reason for the logout is, that another client
		 * has been authenticated and connected (for the same User)
		 */
		public static final int REASON_SERVER_OTHER_CONNECTION = 1;
		
		/**
		 * Is used by the client, to tell the server, that the user has clicked on a Logout button and will do a noraml logout
		 */
		public static final int REASON_CLIENT_USER_LOGOUT = 2;
	}
	
	
	/**
	 * Constants got the login tag
	 * @author Hannes Dorfmann
	 *
	 */
	public class Login{
		
		private Login(){}
		
		/**
		 * Used to tell, that the login was successful
		 */
		public static final String OK = "ok";
		
		/**
		 * Used to tell, that the login has failed, because username or password is wrong
		 */
		public static final String FAIL = "fail";
		
	}
	
	
	/**
	 * Used by {@link UnexpectedErrorMessage}
	 * @author Hannes Dorfmann
	 *
	 */
	public class UnexpectedError{
		
		private UnexpectedError(){}
		
		/**
		 * To indicate that a parser could not parse the message.
		 * Mapped to {@link UnexpectedErrorMessage.ErrorType}
		 */
		public static final int PARSE = 1;
		
		/**
		 * 
		 */
		public static final int DATABASE = 2;
		
	}

}
