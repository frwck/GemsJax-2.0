package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.SearchChannel;
import org.gemsjax.client.communication.channel.handler.SearchChannelHandler;
import org.gemsjax.client.module.handler.GlobalSearchModuleHandler;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.GlobalSearchMessage;
import org.gemsjax.shared.communication.message.search.GlobalSearchResultSet;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.UserResult;
import org.gemsjax.shared.user.RegisteredUser;

public class GlobalSearchModule implements SearchChannelHandler {
	
	private SearchChannel channel;
	private FriendsModule friendModule;
	private RegisteredUser autenticatedUser;
	private Set<GlobalSearchModuleHandler> handlers;
	private int lastRefIdCounter;
	
	public GlobalSearchModule(RegisteredUser currentlyAuthenticatedUser, SearchChannel channel, FriendsModule friendModule)
	{
		handlers = new LinkedHashSet<GlobalSearchModuleHandler>();
		this.channel = channel;
		this.friendModule =friendModule;
		this.autenticatedUser = currentlyAuthenticatedUser;
		lastRefIdCounter = 0;
	}
	
	public void addGlobalSearchModuleHandler(GlobalSearchModuleHandler h)
	{
		handlers.add(h);
	}
	
	public void removeGlobalSearchModuleHandler(GlobalSearchModuleHandler h)
	{
		handlers.remove(h);
	}


	@Override
	public void onSearchResultReceived(String referenceId,
			Set<UserResult> userResults,
			Set<CollaborationResult> collaborationResults,
			Set<ExperimentResult> experimentResults) {
	
		Set<UserResult> users = new LinkedHashSet<UserResult>();
		Set<Friend> friends = new LinkedHashSet<Friend>();
		
		Set<CollaborationResult> publicMetaModels = new LinkedHashSet<CollaborationResult>();
		Set<CollaborationResult> publicModels = new LinkedHashSet<CollaborationResult>();
		
		Set<CollaborationResult> usersMetaModels = new LinkedHashSet<CollaborationResult>();
		Set<CollaborationResult> usersModels = new LinkedHashSet<CollaborationResult>();
		
		
		// Filter friends from other users
		for (UserResult u: userResults)
		{
			Friend f = friendModule.getFriendById(u.getUserId());
			
			if (f!=null) // then u is a Friend
				friends.add(f);
			
			else
			users.add(u);
		}
		
		
		// Filter public from owned or collaborated
		for (CollaborationResult c : collaborationResults)
		{
			switch (c.getType())
			{
				case METAMODEL:
								if (c.isCollaborator())
									usersMetaModels.add(c);
								else
									publicMetaModels.add(c);
								break;
								
				case MODEL:
								if (c.isCollaborator())
									usersModels.add(c);
								else
									publicModels.add(c);
								break;
			}
		}
		
		
		
		
		// fire Result ready
		GlobalSearchResultSet result = new GlobalSearchResultSet(users, friends, publicMetaModels, publicModels, usersMetaModels, usersModels, experimentResults);
		
		for (GlobalSearchModuleHandler h : handlers)
			h.onSearchResultReady(result);
	}


	@Override
	public void onSearchResultError(String referenceId, SearchError error) {
		
		for (GlobalSearchModuleHandler h : handlers)
			h.onSearchResultErrorResponse(error);

	}


	@Override
	public void onUnexpectedError(String referenceId, Throwable t) {
		for (GlobalSearchModuleHandler h : handlers)
			h.onUnexpectedSearchError(t);

	}
	
	
	public String doNewSearch(String searchString) throws IOException
	{
		lastRefIdCounter++;
		
		String refId = this.toString()+"-"+lastRefIdCounter;
		
		channel.send(new GlobalSearchMessage(refId, searchString));
		
		return refId;
		
	}

}
