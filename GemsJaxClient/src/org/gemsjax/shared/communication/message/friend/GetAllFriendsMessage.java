package org.gemsjax.shared.communication.message.friend;

/**
 * Require a list with all friends, that means, after {@link GetAllFriendsMessage} has been sent to the server,
 * a {@link AddFriendsAnswerMessage} is sent from server to the client
 * @author Hannes Dorfmann
 *
 */
public class GetAllFriendsMessage extends FriendMessage {

	public static final String TAG = "get-all-friends";

	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+"><"+TAG+"/><"+FriendMessage.TAG+">";
	}

}
