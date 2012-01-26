package org.gemsjax.client.module.handler;

import org.gemsjax.client.module.FriendsModule;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestAnswerMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestMessage;


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
	 * Called if a new friendship has been established.
	 * It would be nice, if the client displays a notification
	 */
	public void onNewFriendAdded(Friend f);
	
	/**
	 * Called if an error Answer has been received from the server for the given
	 * reference id
	 */
	public void onErrorAnswer(String referenceId, FriendError error, String additionalInfo);
	
	/**
	 * Called if a successful answer from {@link FriendsModule#cancelFriendship(String, java.util.Set)} has been received
	 * @param referenceId
	 */
	public void onFriendshipsSuccessfullCanceled(String referenceId);

	/**
	 * Called if the {@link NewFriendshipRequestMessage} has returned a {@link NewFriendshipRequestAnswerMessage} from server as response
	 * @param referenceId
	 */
	public void onNewFriendshipSuccessfull(String referenceId);
	
}
