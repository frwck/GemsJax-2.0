package org.gemsjax.client.admin.view;

import java.util.Set;

import org.gemsjax.shared.communication.message.friend.Friend;

/**
 * The view, which let the user manage his friends
 * @author Hannes Dorfmann
 *
 */
public interface FriendsManagementView {
	
	
	public void show();
	
	public void setAllFriends(Set<Friend> friends);
	
	
	
	public void hide();

}
