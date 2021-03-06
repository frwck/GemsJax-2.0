package org.gemsjax.shared.communication;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.CommunicationError;
import org.gemsjax.shared.communication.message.collaborateablefile.NewCollaborateableFileMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.gemsjax.shared.user.User;
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
		
		public static final String FAIL_EXPERIMENT_DISPLAYED_NAME_IN_USE = "fail_exp_disp_name";
		
		public static final String FAIL_EXPERIMENT_VERIFICATION_CODE = "fail_exp_verification";
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
		 * The communication constant mapped to {@link NewCollaborateableFileMessage.CollaborateableType#METAMODEL}
		 */
		public static final String TYPE_METAMODEL ="MetaModel";
		

		/**
		 * The communication constant mapped to {@link NewCollaborateableFileMessage.CollaborateableType#MODEL}
		 */
		public static final String TYPE_MODEL = "Model";

	}
	
	public class CollaborateableError{
		private CollaborateableError(){}
		
		public static final String OK = "ok";

		public static final String FAIL_AUTHENTICATION="fail_authentication";
		
		public static final String FAIL_TYPE="fail_type";
		public static final String FAIL_NAME="fail_name";
		public static final String FAIL_INVALID_COLLABORATOR="invalid_colaborator";
		public static final String FAIL_INVALID_ADMIN="invalid_admin";
	}
	
	
	
	public class RequestError{
		private RequestError(){}
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.request.RequestError#PARSING}
		 */
		public static final String PARSING="fail_parsing";
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.request.RequestError#DATABASE}
		 */
		public static final String DATABASE="fail_database";
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.request.RequestError#AUTHENTICATION}
		 */
		public static final String AUTHENTICATION="fail_authentication";
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.request.RequestError#REQUEST_NOT_FOUND}
		 */
		public static final String REQUEST_NOT_FOUND="fail_id";
		
		public static final String PERMISSION_DENIED="fail_denied";
		
		public static final String ALREADY_BEFRIENDED="fail_befriended";
		
		public static final String ALREADY_IN_COLLABORATION="fail_collaboration";
		
		public static final String ALREADY_EXPERIMENT_ADMIN="fail_admin";
		
		public static final String ALREADY_REQUESTED="alerady_requested";
		
	}
	
	
	
	public class NotificationError{
		private NotificationError(){}
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.notification.NotificationError#PARSING}
		 */
		public static final String PARSING="fail_parsing";
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.notification.NotificationError#DATABASE}
		 */
		public static final String DATABASE="fail_database";
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.notification.NotificationError#AUTHENTICATION}
		 */
		public static final String AUTHENTICATION="fail_authentication";
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.notification.NotificationError#NOT_FOUND}
		 */
		public static final String NOT_FOUND = "not_found";
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.notification.NotificationError#PERMISSION_DENIED}
		 */
		public static final String PERMISSION_DENIED="permission";
		
		
	}
	
	/**
	 * This class contains the constants for the  {@link CollaborateableFileError}
	 * @author Hannes Dorfmann
	 *
	 */
	public class CollaborateableFileError{
		private CollaborateableFileError(){}
		
		public static final String NAME_MISSING = "name";
		public static final String AUTHENTICATION = "authentication";
		public static final String PERMISSION_DENIED ="permission";
		public static final String PARSING = "parsing";
		public static final String DATABASE = "database";
		public static final String NOT_FOUND = "not_found";
	}
	
	
	/**
	 * This are the constants for {@link Collaborateable.Permission}s.
	 * @author Hannes Dorfmann
	 *
	 */
	public class CollaborateablePermission{
		
		private CollaborateablePermission(){}
		/**
		 * No public access granted. That means, that only the owner and the collaborative working {@link User} (invited) have access to this {@link Collaborateable}
		 */
		public static final int PRIVATE = 0;
		/**
		 * Every {@link User} can access this with reading permission. Changing this {@link Collaborateable} is not possible except the owner {@link User}
		 * and the other collaborative working {@link User} (invited).
		 */
		public static final int READ_ONLY = 1;
		
		/**
		 * This means, that every user can make a copy of this {@link Collaborateable}
		 */
		public static final int COPYABLE = 2;
	}
	
	
	
	public class ExperimentError{
		private ExperimentError(){};
		
		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.experiment.ExperimentError#AUTHENTICATION}
		 */
		public static final String AUTEHNTICATION = "authentication";

		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.experiment.ExperimentError#PARSE}
		 */
		public static final String PARSE ="parse";

		/**
		 * Mapped to {@link org.gemsjax.shared.communication.message.experiment.ExperimentError#DATABASE}
		 */
		public static final String DATABASE ="database";
		
		
	}
	
	

}
