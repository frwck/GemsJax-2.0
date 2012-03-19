package org.gemsjax.client.admin.view.handlers;

import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.search.UserResult;

/**
 * This Handler can be used by any View to make instant friendship request or to unfriend a friend
 * @author Hannes Dorfmann
 *
 */
public interface FriendshipHandler {
	
	/**
	 * Unfriend
	 * @param friendId
	 */
	public void onUnfriendRequired(Friend user);
	
	/**
	 * The user wants to make a new friendship request
	 * @param userId
	 */
	public void onNewFriendshipRequired(UserResult user);

}
