package org.gemsjax.client.module;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.gemsjax.client.communication.channel.FriendsLiveChannel;
import org.gemsjax.client.communication.channel.handler.FriendsLiveChannelHandler;
import org.gemsjax.client.module.handler.FriendsModuleHandler;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.user.UserOnlineState;

/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class FriendsModule implements FriendsLiveChannelHandler{
	
	/**
	 * Used by {@link FriendsModule#getFriendByDisplayName(String)}
	 * @author Hannes Dorfmann
	 *
	 */
	public class FriendDisplayNameResults
	{

		private String searchString;
		private Set<Friend> result;
		
		
		public FriendDisplayNameResults(String searchString, Set<Friend> result)
		{
			this.searchString = searchString;
			this.result = result;
		}
		
		
		public String getSerachString()
		{
			return searchString;
		}
		
		
		public Set<Friend> getResult()
		{
			return result;
		}
	}
	
	
	private FriendsLiveChannel channel;
	private Set<FriendsModuleHandler> handlers;
	
	public Map<Integer, Friend> friends;
	
	
	public FriendsModule(FriendsLiveChannel c)
	{
		this.channel = c;
		this.handlers = new LinkedHashSet<FriendsModuleHandler>();
		friends = new LinkedHashMap<Integer, Friend>();
		channel.addFriendsChannelHandler(this);
	}
	
	
	public void addFriendsModuleHandler(FriendsModuleHandler h)
	{
		handlers.add(h);
	}

	
	public void removeFriendsModuleHandler(FriendsModuleHandler h)
	{
		handlers.remove(h);
	}
	
	
	
	private void fireUpdate()
	{
		for (FriendsModuleHandler h : handlers)
			h.onFriendsUpdate();
	}
	
	
	private void fireAuthenticationError()
	{
		for (FriendsModuleHandler h : handlers)
			h.onAuthenticationError(); 
	}
	
	
	private void fireUnexpectedError()
	{
		for (FriendsModuleHandler h : handlers)
			h.onUnexpectedError(); 
	}
	

	@Override
	public void onFriendUpdate(Friend friend) {
		friends.put(friend.getId(), friend);
	}


	@Override
	public void onAllFriendsReceived(Set<Friend> fr) {
		
		for (Friend f: fr)
			friends.put(f.getId(), f);
		
		fireUpdate();
	}


	@Override
	public void onAutenticationError() {
		fireAuthenticationError();
	}


	@Override
	public void onUnexpectedError(Throwable t) {
		fireUnexpectedError();
	}


	@Override
	public void onFriendshipCanceled(Set<Integer> exFriendIds) {
		for (int id: exFriendIds)
			friends.remove(id);
	}
	
	
	
	/**
	 * Get the number of friends that are currently online
	 * @return
	 */
	public int getOnlineCount()
	{
		int online =0;
		for (Friend f: friends.values())
			if (f.getOnlineState()==UserOnlineState.ONLINE)
				online ++;
		
		
		
		return online;
				
	}
	
	
	/**
	 * Get a Set of {@link Friend}s that are online right now
	 * @return
	 */
	public Set<Friend> getOnlineFriends()
	{
		Set<Friend> online = new LinkedHashSet<Friend>();
		
		for (Friend f: friends.values())
			if (f.getOnlineState()==UserOnlineState.ONLINE)
				online.add(f);
		
		return online;
	}
	
	
	
	/**
	 * Get a Set of {@link Friend}s that are offline right now
	 * @return
	 */
	public Set<Friend> getOfflineFriends()
	{
		Set<Friend> offline = new LinkedHashSet<Friend>();
		
		for (Friend f: friends.values())
			if (f.getOnlineState()==UserOnlineState.OFFLINE)
				offline.add(f);
		
		return offline;
	}
	
	
	/**
	 * Get all friends
	 * @return
	 */
	public Collection<Friend> getAllFriends()
	{
		return friends.values();
	}
	
	
	
	public Friend getFriendById(int id)
	{
		return friends.get(id);
	}
	
	/**
	 * Get all {@link Friend}s that contain the dispNameToSearch in there display name property
	 * @param dispNameToSearch
	 * @return
	 */
	public FriendDisplayNameResults getFriendByDisplayName(String dispNameToSearch)
	{
		Set<Friend> fr = new LinkedHashSet<Friend>();
		
		for (Friend f: friends.values())
			if (f.getDisplayName().contains(dispNameToSearch))
				fr.add(f);
		
		return new FriendDisplayNameResults(dispNameToSearch, fr);
	}
	
	
	

}
