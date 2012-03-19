package org.gemsjax.client.admin.view;

import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;

public interface ManageFriendsView {
	
	public void showAlreadyBefriendedMessage(Friend friend);
	
	public void showYetBefriended(Friend friend);
	
	
	public void showUnexpectedError(Throwable t);
	
	public void showFriendshipRequestError(String userDisplayName, FriendError error);
	
	public void showFriendshipRequestSuccessful(String userDisplayName);
	
	public void showCancelFriendshipSuccessful(Friend exFriend);
	
	public void showCancelFriendshipError(Friend friendToCancel, FriendError error);
	
	
}
