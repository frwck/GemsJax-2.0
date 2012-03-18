package org.gemsjax.client.module;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.gemsjax.client.communication.channel.FriendsLiveChannel;
import org.gemsjax.client.communication.channel.handler.FriendsLiveChannelHandler;
import org.gemsjax.client.module.handler.FriendsModuleHandler;
import org.gemsjax.shared.communication.message.friend.CancelFriendshipMessage;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.GetAllFriendsMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestMessage;
import org.gemsjax.shared.communication.message.search.UserResult;
import org.gemsjax.shared.user.UserOnlineState;

import com.google.gwt.user.client.Random;

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
	private String initGetAllReferenceId;

	
	public Map<Integer, Friend> friends;
	private Map<String, UserResult> pendingFriendshipRequests;
	private Map<String, Friend> pendingCancelFriendshipRequests;
	
	private long refIdCounter = 0;
	
	
	public FriendsModule(FriendsLiveChannel c)
	{
		this.channel = c;
		this.handlers = new LinkedHashSet<FriendsModuleHandler>();
		friends = new LinkedHashMap<Integer, Friend>();
		channel.addFriendsChannelHandler(this);
		pendingFriendshipRequests = new TreeMap<String, UserResult>();
		pendingCancelFriendshipRequests = new TreeMap<String, Friend>();

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
	
	
	

	@Override
	public void onFriendUpdate(Friend friend) {
		friends.put(friend.getId(), friend);
	}


	@Override
	public void onAllFriendsReceived(String refId, Set<Friend> fr) {
		
		
		for (Friend f: fr)
			friends.put(f.getId(), f);
		

		if (refId.equals(initGetAllReferenceId))
			for(FriendsModuleHandler h: handlers)
				h.onInitGetFriendsResponse(true);
		else
		fireUpdate();
	}



	@Override
	public void onFriendshipCanceled(Set<Integer> exFriendIds) {
		for (int id: exFriendIds)
			friends.remove(id);
		
		fireUpdate();
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
	
	
	public void doInitGetAllFriends() throws IOException
	{
		initGetAllReferenceId ="init-get-all-"+Random.nextInt();
		channel.send(new GetAllFriendsMessage(initGetAllReferenceId));
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
	
	
	/**
	 * Send a request to the server, that you want to get the list with all friends
	 * @throws IOException
	 */
	public void requestAllFriends(String referenceId) throws IOException
	{
		
		channel.send(new GetAllFriendsMessage(referenceId) );
	}
	
	
	/**
	 * Cancel the friendship to the specified friends
	 * @param friend
	 * @throws IOException
	 */
	public void cancelFriendship(Friend friend) throws IOException
	{
		refIdCounter++;
		String referenceId = "CF"+refIdCounter;
		pendingCancelFriendshipRequests.put(referenceId, friend);
		Set<Integer> ids = new LinkedHashSet<Integer>();
		ids.add(friend.getId());
		channel.send(new CancelFriendshipMessage(referenceId, ids));
	}
	
	public void makeNewFriendshipRequest(UserResult user) throws IOException{
		refIdCounter++;
		String referenceId = "CF"+refIdCounter;
		pendingFriendshipRequests.put(referenceId, user);
		channel.send(new NewFriendshipRequestMessage(referenceId, user.getUserId()));
	}


	@Override
	public void onCancelFriendshipAnswer(String referenceId, Set<Integer> exFriendIds) {
		
		Friend friend = pendingCancelFriendshipRequests.get(referenceId);
		
		for (int id: exFriendIds)
			friends.remove(id);
		
		for (FriendsModuleHandler h : handlers){
			h.onFriendsUpdate();
			h.onCancelFriendshipsSuccessful(friend);
		}
	}


	@Override
	public void onNewFriendAdded(Friend newFriend) {
		friends.put(newFriend.getId(), newFriend);
		
		for (FriendsModuleHandler h : handlers)
		{
			h.onFriendsUpdate();
			h.onNewFriendAdded(newFriend);
		}
		
	}


	@Override
	public void onError(String referenceId, FriendError error, String additionalInfo) {
	

		if (referenceId.equals(initGetAllReferenceId))
			for(FriendsModuleHandler h: handlers)
				h.onInitGetFriendsResponse(false);
		else
		{
			Friend friend = pendingCancelFriendshipRequests.get(referenceId);
			UserResult user = pendingFriendshipRequests.get(referenceId);
			
			if (friend!=null){
				for (FriendsModuleHandler h : handlers)
					h.onCancelFriendshipError(friend, error);
				pendingCancelFriendshipRequests.remove(referenceId);
			}
			else
			if (user != null){
				for (FriendsModuleHandler h : handlers)
					h.onNewFriendshipRequestError(user, error);	
				pendingFriendshipRequests.remove(referenceId);
			}
		}	
	}


	@Override
	public void onNewFriendshipRequestAnswer(String referenceId) {
		
		for (FriendsModuleHandler h: handlers)
		{
			UserResult user = pendingFriendshipRequests.get(referenceId);
			h.onFriendsUpdate();
			h.onNewFriendshipRequestSuccessful(user);
			pendingFriendshipRequests.remove(referenceId);
		}
		
	}
	
	
	

}
