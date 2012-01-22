package org.gemsjax.server.communication.channel.handler;

import java.util.Set;

import org.gemsjax.server.communication.OnlineUser;

public interface FriendsChannelHandler {
	
	public abstract void onGetAllFriends(OnlineUser requester);
	
	public abstract void onCancelFriendships(OnlineUser requester, Set<Integer> friendIds);

}
