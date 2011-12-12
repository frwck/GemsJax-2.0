package org.gemsjax.shared;

/**
 * This class contains constants (grouped in subclasses) that were used for the xml communication between server and client and vice versa 
 * @author Hannes Dorfmann
 *
 */
public class CommunicationConstants {
	
	/**
	 * Constants used by the logout tag 
	 * @author Hannes Dorfmann
	 *
	 */
	public class Logout{
		/**
		 * Is used by the server, to told the client that the reason for the logout is, that another client
		 * has been authenticated and connected (for the same User)
		 */
		public static final int REASON_SERVER_OTHER_CONNECTION = 1;
		
		/**
		 * 
		 */
		public static final int REASON_CLIENT_USER_LOGOUT = 2;
	}
	

}
