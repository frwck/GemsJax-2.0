package org.gemsjax.client.admin.view;

import java.util.Set;

import org.gemsjax.shared.communication.message.friend.Friend;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface FriendsOnlineView {
	
	/**
	 * Set the count of friends that are currently online
	 */
	public void setOnlineCount();
	
	
	public HasClickHandlers getOnlineFriendsListButton();
	
	
	public void setOnlineFriends(Set<Friend> onlineFriends);
	
	public void setOfflineFriends(Set<Friend> offlineFriends);
	

}
