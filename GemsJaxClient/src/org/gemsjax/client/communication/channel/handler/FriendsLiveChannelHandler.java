package org.gemsjax.client.communication.channel.handler;

import java.io.IOException;
import java.util.Set;

import org.gemsjax.shared.communication.message.friend.CancelFriendshipMessage;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.FriendErrorAnswerMessage;

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
	public void onAllFriendsReceived(String referenceId, Set<Friend> friends);
	
	/**
	 * Called, if a friendship request has been accepted and the user is now befriended
	 * @param newFriend
	 */
	public void onNewFriendAdded(Friend newFriend);
	
	/*
	 * Called, if a parse error has occurred on server or client side or the server has a database error or a {@link IOException}
	 * was thrown while sending/receiving data from server
	 * @param t
	 *
	public void onUnexpectedError(Throwable t);
	*/
	
	/**
	 * Called if a {@link FriendErrorAnswerMessage} has been received
	 * @param error
	 * @param aditionalInfo
	 */
	public void onError(String referenceId, FriendError error, String aditionalInfo);
	
	/**
	 * Called, if friendships were canceled
	 * @param exFriendId
	 */
	public void onFriendshipCanceled(Set<Integer> exFriendIds);
	
	/**
	 * Called if the server response on a {@link CancelFriendshipMessage} was received
	 * @param exFriendIds
	 */
	public void onCancelFriendshipAnswer(String referenceId, Set<Integer> exFriendIds);
	
	
	public void onNewFriendshipRequestAnswer(String referenceId, Friend friend);
}
