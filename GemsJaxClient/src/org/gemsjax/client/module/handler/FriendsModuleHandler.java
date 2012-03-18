package org.gemsjax.client.module.handler;

import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestAnswerMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestMessage;
import org.gemsjax.shared.communication.message.search.UserResult;


/**
 * A handler for the {@link FriendsModule}
 * @author Hannes Dorfmann
 *
 */
public interface FriendsModuleHandler {
	
	/**
	 * Called, if anything with the friends has been updated
	 */
	public void onFriendsUpdate();
	
	/**
	 * Called, if {@link FriendsModule#doInitGetAllFriends()} has got a answer from the server.
	 * @param successful is <b>true</b>, if everything was sucessful or <b>false</b> if an error has occurred
	 */
	public void onInitGetFriendsResponse(boolean successful);
	
	
	/**
	 * Called if a new friendship has been established.
	 * It would be nice, if the client displays a notification
	 */
	public void onNewFriendAdded(Friend f);

	/**
	 * Called if a successful answer from {@link FriendsModule#cancelFriendship(String, java.util.Set)} has been received
	 * @param exFriend
	 */
	public void onCancelFriendshipsSuccessful(Friend exFriend);

	/**
	 * Called if the {@link NewFriendshipRequestMessage} has returned a {@link NewFriendshipRequestAnswerMessage} from server as response
	 * @param referenceId
	 */
	public void onNewFriendshipRequestSuccessful(UserResult userWhoHasReceivedRequest);
	
	/**
	 * Called, if a friendship could not be canceled
	 * @param friendToUnfriend
	 * @param error
	 */
	public void onCancelFriendshipError(Friend friendToUnfriend, FriendError error);
	
	/**
	 * Called, if a new friendship request has not been accepted by the server
	 * @param user
	 * @param errror
	 */
	public void onNewFriendshipRequestError(UserResult user, FriendError errror);
	
}
