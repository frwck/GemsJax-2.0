package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.event.ManageFriendshipEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles the User interactions for friendshipmanagement like make new friendship 
 * requests or to cancel existing friendships (unfriend)
 * @author Hannes Dorfmann
 *
 */
public interface ManageFriendshipHandler  extends EventHandler{
	
	/**
	 * Called, if the user wants to be befriended with somebody
	 * @param event
	 */
	public void onNewFriendshipRequired(ManageFriendshipEvent event);
	
	/**
	 * Called, if the user wants no longer be befriended with a friend
	 * @param event
	 */
	public void onUnfriendRequired(ManageFriendshipEvent event);

}
