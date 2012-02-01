package org.gemsjax.shared.communication;

import org.gemsjax.shared.communication.message.CommunicationError;
import org.gemsjax.shared.communication.message.collaboration.NewCollaborateableMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.gemsjax.shared.user.UserOnlineState;

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
	 * This are the constants for {@link UserOnlineState}
	 * @author Hannes Dorfmann
	 *
	 */
	public class OnlineState{
		/**
		 * mapped to {@link UserOnlineState#ONLINE}
		 */
		public static final String ONLINE="online";
		/**
		 * mapped to {@link UserOnlineState#OFFLINE}
		 */
		public static final String OFFLINE="offline";
	}
	
	
	
	/**
	 * Used by {@link CommunicationError}
	 * @author Hannes Dorfmann
	 *
	 */
	public class Error{
		
		private Error(){}
		
		/**
		 * To indicate that a parser could not parse the message.
		 * Mapped to {@link CommunicationError.ErrorType#PARSE}
		 */
		public static final int PARSE = 3;
		
		/**
		 * Mapped to {@link CommunicationError.ErrorType#DATABASE}
		 */
		public static final int DATABASE = 4;
		
		/**
		 * Mapped to {@link CommunicationError.ErrorType#AUTHENTICATION}
		 */
		public static final int AUTHENTICATION=5;
		
	}
	
	
	/**
	 * Constants that are used durring a registration
	 * @author Hannes Dorfmann
	 *
	 */
	public class Registration{
		private Registration(){}
		
		/**
		 * The registration was successful.
		 * Mapped to {@link RegistrationAnswerMessage.RegistrationAnswerStatus#OK}.
		 */
		public static final String OK = "ok";
		
		/**
	{
		 * Mapped to {@link RegistrationAnswerMessage.RegistrationAnswerStatus#FAIL_USERNAME}.
		 */
		public static final String FAIL_USERNAME = "fail_username";
		
		/**
		 * Mapped to {@link RegistrationAnswerMessage.RegistrationAnswerStatus#FAIL_EMAIL}. 
		 */
		public static final String FAIL_EMAIL = "fail_email";
		
		/**
		 * Mapped to {@link RegistrationAnswerMessage.RegistrationAnswerStatus#FAIL_INVALID_USERNAME}
		 */
		public static final String FAIL_INVALID_USERNAME = "fail_invalid_username";

		/**
		 * Mapped to {@link RegistrationAnswerMessage.RegistrationAnswerStatus#FAIL_INVALID_EMAIL}
		 */
		public static final String FAIL_INVALID_EMAIL = "fail_invalid_email";
	}
	
	/**
	 * The constants for the {@link NewFriendshipRequestAnswerMessage.FriendshipRequestAnswerStatus} enum
	 * @author Hannes Dorfmann
	 *
	 */
	public class FriendError {
		private FriendError(){}
		
		
		/**
		 * Mapped to {@link NewFriendshipRequestAnswerMessage.FriendshipRequestAnswerStatus#PARSING}
		 */
		public static final String PARSING="fail_parsing";
		
		/**
		 * Mapped to {@link NewFriendshipRequestAnswerMessage.FriendshipRequestAnswerStatus#DATABASE}
		 */
		public static final String DATABASE="fail_database";
		
		/**
		 * Mapped to {@link NewFriendshipRequestAnswerMessage.FriendshipRequestAnswerStatus#AUTHENTICATION}
		 */
		public static final String AUTHENTICATION="fail_authentication";
		/**
		 * Mapped to {@link NewFriendshipRequestAnswerMessage.FriendshipRequestAnswerStatus#ALREADY_REQUESTED}
		 */
	
		public static final String ALREADY_REQUESTED="fail_requested";
		
		/**
		 * Mapped to {@link NewFriendshipRequestAnswerMessage.FriendshipRequestAnswerStatus#ALREADY_FRIENDS}
		 */
		public static final String ALREADY_FRIENDS="fail_friends";
		
		/**
		 * Mapped to {@link NewFriendshipRequestAnswerMessage.FriendshipRequestAnswerStatus#FRIEND_ID}
		 */
		public static final String FRIEND_ID="fail_friend_id";
	}
	
	
	
	public class SearchError{
		private SearchError(){}
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.search.SearchError#PARSING}
		 */
		public static final String PARSING="fail_parsing";
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.search.SearchError#DATABASE}
		 */
		public static final String DATABASE="fail_database";
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.search.SearchError#AUTHENTICATION}
		 */
		public static final String AUTHENTICATION="fail_authentication";
		}
	
	
	public class CollaborateableType{
		private CollaborateableType(){}
		
		/**
		 * The communication constant mapped to {@link NewCollaborateableMessage.CollaborateableType#METAMODEL}
		 */
		public static final String TYPE_METAMODEL ="MetaModel";
		

		/**
		 * The communication constant mapped to {@link NewCollaborateableMessage.CollaborateableType#MODEL}
		 */
		public static final String TYPE_MODEL = "Model";

	}
	
	public class CollaborateableError{
		public CollaborateableError(){}
		
		public static final String OK = "ok";

		public static final String FAIL_AUTHENTICATION="fail_authentication";
		
		public static final String FAIL_TYPE="fail_type";
		public static final String FAIL_NAME="fail_name";
		public static final String FAIL_INVALID_COLLABORATOR="invalid_colaborator";
		public static final String FAIL_INVALID_ADMIN="invalid_admin";
	}

}
