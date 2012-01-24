package org.gemsjax.shared.communication.message.friend;

import org.gemsjax.shared.communication.CommunicationConstants;
import org.gemsjax.shared.communication.message.Message;

/**
 * This {@link Message} is sent from Server to client as the response on a {@link NewFriendshipRequestMessage}
 * @author Hannes Dorfmann
 *
 */
public class NewFriendshipRequestAnswerMessage extends FriendMessage{
	
	public enum FriendshipRequestAnswerStatus
	{
		/**
		 * The Friendship request was handled successfull
		 */
		OK,
		/**
		 * The Friendship request has failed, because the {@link NewFriendshipRequestMessage} could not be parsed correctly
		 */
		FAIL_PARSING,
		
		/**
		 * an unexpected database error has occurred
		 */
		FAIL_DATABASE,
		
		/**
		 * The user that has sent the {@link NewFriendshipRequestMessage} is not authenticated
		 */
		FAIL_AUTHENTICATION,
		
		/**
		 * A similar request has already been sent to the server and has not answered yet
		 */
		FAIL_ALREADY_REQUESTED,
		
		/**
		 * The request has failed, because the sender is already a friend of the request receiver
		 */
		FAIL_ALREADY_FRIENDS;
		
		/**
		 * Converts a {@link CommunicationConstants.FriendshipRequestAnswer} constant to the 
		 * corresponding  {@link FriendshipRequestAnswerStatus} enum 
		 * @param constant
		 * @return
		 */
		public static FriendshipRequestAnswerStatus fromConstant(String constant)
		{
			
			if (constant.equals(CommunicationConstants.FriendshipRequestAnswer.OK))
				return OK;
			
			if (constant.equals(CommunicationConstants.FriendshipRequestAnswer.FAIL_ALREADY_FRIENDS))
				return FAIL_ALREADY_FRIENDS;
			
			if (constant.equals(CommunicationConstants.FriendshipRequestAnswer.FAIL_ALREADY_REQUESTED))
				return FAIL_ALREADY_REQUESTED;
			
			if (constant.equals(CommunicationConstants.FriendshipRequestAnswer.FAIL_AUTHENTICATION))
				return FAIL_AUTHENTICATION;
			
			if (constant.equals(CommunicationConstants.FriendshipRequestAnswer.FAIL_DATABASE))
				return FAIL_DATABASE;
			
			if (constant.equals(CommunicationConstants.FriendshipRequestAnswer.FAIL_PARSING))
				return FAIL_PARSING;
			
			return null;
		}
	}
	
	
	public static final String TAG = "new";
	public static final String ATTRIBUTE_STATE="status";
	public static final String ATTRIBUTE_RECEIVER_ID = "receiver-id";
	public static final String ATTRIBUTE_RECEIVER_DISPLAY_NAME="dispName";
	
	private FriendshipRequestAnswerStatus status;
	private int id;
	private String dispName;
	
	public NewFriendshipRequestAnswerMessage(FriendshipRequestAnswerStatus status, int receiverUserId, String receiverDisplayName)
	{
		this.dispName = receiverDisplayName;
		this.id = receiverUserId;
		this.status = status;
	}
	
	
	private String stateToConstant()
	{
		switch  (status)
		{
		case OK: return CommunicationConstants.FriendshipRequestAnswer.OK;
		case FAIL_ALREADY_FRIENDS: return CommunicationConstants.FriendshipRequestAnswer.FAIL_ALREADY_FRIENDS;
		case FAIL_ALREADY_REQUESTED: return CommunicationConstants.FriendshipRequestAnswer.FAIL_ALREADY_REQUESTED;
		case FAIL_AUTHENTICATION: return CommunicationConstants.FriendshipRequestAnswer.FAIL_AUTHENTICATION;
		case FAIL_DATABASE : return CommunicationConstants.FriendshipRequestAnswer.FAIL_DATABASE;
		case FAIL_PARSING: return CommunicationConstants.FriendshipRequestAnswer.FAIL_PARSING;
		}
		
		return null;
	}
	
	
	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+"><"+TAG+" "+ATTRIBUTE_STATE+"=\""+stateToConstant()+"\" "+ATTRIBUTE_RECEIVER_ID+"=\""+id+"\" "+ATTRIBUTE_RECEIVER_DISPLAY_NAME+"=\""+dispName+"\" /></"+TAG+"></"+FriendMessage.TAG+">";
	}

}
