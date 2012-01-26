package org.gemsjax.server.communication.channel.handler;

import java.util.Set;

import org.gemsjax.server.communication.channel.FriendsLiveChannel;
import org.gemsjax.server.module.OnlineUser;

/**
 * The handler for the {@link FriendsLiveChannel}
 * @author Hannes Dorfmann
 *
 */
public interface FriendsChannelHandler {
	
	public abstract void onGetAllFriends(OnlineUser requester, String referenceId);
	
	public abstract void onCancelFriendships(OnlineUser requester, Set<Integer> friendIds, String referenceId);
	
	public abstract void onNewFriendshipRequest(OnlineUser requester, int friendId, String referenceId);
	

}
