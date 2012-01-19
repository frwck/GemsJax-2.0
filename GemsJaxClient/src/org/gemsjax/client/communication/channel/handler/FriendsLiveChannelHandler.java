package org.gemsjax.client.communication.channel.handler;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.shared.communication.message.friend.Friend;

/**
 * A handler for {@link FriendsLiveChannel}
 * @author Hannes Dorfmann
 *
 */
public interface FriendsLiveChannelHandler {

	/**
	 * Called, if a update about a {@link Friend} is received from the server
	 * @param friend
	 */
	public void onFriendUpdate(Friend friend);
	
	/**
	 * Called, if the answer about the "get all friends" request has been received
	 * @param friends
	 */
	public void onAllFriendsReceived(Set<Friend> friends);
	
	/**
	 * Called, if the quering friends was not allowed by the server, because the user is not authenticated correctly
	 */
	public void onAutenticationError();
	
	/**
	 * Called, if a parse error has occurred on server or client side or the server has a database error or a {@link IOException}
	 * was thrown while sending/receiving data from server
	 * @param t
	 */
	public void onUnexpectedError(Throwable t);
	
	/**
	 * Called, if friendships were canceled
	 * @param exFriendId
	 */
	public void onFriendshipCanceled(Set<Integer> exFriendIds);
	
}
