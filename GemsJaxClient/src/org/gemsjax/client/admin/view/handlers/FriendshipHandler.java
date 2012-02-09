package org.gemsjax.client.admin.view.handlers;

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
	public void onUnfriendRequired(int friendId);
	
	/**
	 * The user wants to make a new friendship request
	 * @param userId
	 */
	public void onNewFriendshipRequired(int userId);

}
