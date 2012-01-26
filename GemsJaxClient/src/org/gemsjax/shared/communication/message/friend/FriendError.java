package org.gemsjax.shared.communication.message.friend;

import org.gemsjax.shared.communication.CommunicationConstants;

public enum FriendError {
	

	/**
	 * The Friendship request has failed, because the {@link ReferenceableFriendMessage} could not be parsed correctly
	 */
	PARSING,
	
	/**
	 * an unexpected database error has occurred
	 */
	DATABASE,
	
	/**
	 * The user that has sent the {@link ReferenceableFriendMessage} is not authenticated
	 */
	AUTHENTICATION,
	
	/**
	 * A similar request has already been sent to the server and has not answered yet
	 */
	ALREADY_REQUESTED,
	
	/**
	 * The request has failed, because the sender is already a friend of the request receiver
	 */
	ALREADY_FRIENDS,
	
	/**
	 * The request has failed, because the id of the friend is not valid
	 */
	FRIEND_ID;
	
	/**
	 * Converts a {@link CommunicationConstants.FriendError} constant to the 
	 * corresponding  {@link FriendshipRequestAnswerStatus} enum 
	 * @param constant
	 * @return
	 */
	public static FriendError fromConstant(String constant)
	{
	
		if (constant.equals(CommunicationConstants.FriendError.ALREADY_FRIENDS))
			return ALREADY_FRIENDS;
		
		if (constant.equals(CommunicationConstants.FriendError.ALREADY_REQUESTED))
			return ALREADY_REQUESTED;
		
		if (constant.equals(CommunicationConstants.FriendError.AUTHENTICATION))
			return AUTHENTICATION;
		
		if (constant.equals(CommunicationConstants.FriendError.DATABASE))
			return DATABASE;
		
		if (constant.equals(CommunicationConstants.FriendError.PARSING))
			return PARSING;
		
		if (constant.equals(CommunicationConstants.FriendError.FRIEND_ID))
			return FRIEND_ID;
		
		return null;
	}
	
	
	
	/**
	 * Converts this {@link FriendError} to the corresponding {@link CommunicationConstants.FriendError} constant.
	 * This is used by the {@link FriendErrorAnswerMessage}
	 * @return
	 */
	public String toConstant()
	{
		switch  (this)
		{
		case ALREADY_FRIENDS: return CommunicationConstants.FriendError.ALREADY_FRIENDS;
		case ALREADY_REQUESTED: return CommunicationConstants.FriendError.ALREADY_REQUESTED;
		case AUTHENTICATION: return CommunicationConstants.FriendError.AUTHENTICATION;
		case DATABASE : return CommunicationConstants.FriendError.DATABASE;
		case PARSING: return CommunicationConstants.FriendError.PARSING;
		case FRIEND_ID: return CommunicationConstants.FriendError.FRIEND_ID;
		}
		
		return null;
	}

}
