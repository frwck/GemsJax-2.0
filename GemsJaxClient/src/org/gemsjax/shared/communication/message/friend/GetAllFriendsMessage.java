package org.gemsjax.shared.communication.message.friend;

/**
 * Require a list with all friends, that means, after {@link GetAllFriendsMessage} has been sent to the server,
 * a {@link AllFriendsAnswerMessage} is sent from server to the client
 * @author Hannes Dorfmann
 *
 */
public class GetAllFriendsMessage extends ReferenceableFriendMessage {

	public static final String TAG = "get-all-friends";
	
	public GetAllFriendsMessage(String referenceId)
	{
		super(referenceId);
	}
	

	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+" "+ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+"/><"+FriendMessage.TAG+">";
	}

}
