package org.gemsjax.client.admin.view;

import java.util.Set;

import org.gemsjax.client.admin.view.handlers.FriendshipHandler;
import org.gemsjax.client.admin.view.handlers.ShowCollaborationHandler;
import org.gemsjax.client.admin.view.handlers.ShowExperimentHandler;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.UserResult;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface GlobalSearchResultView extends QuickSearchView{
	
	public void addFriendshipHandler(FriendshipHandler h);
	public void removeFriendshipHandler(FriendshipHandler h);
	
	public void addShowCollaborateableHandler(ShowCollaborationHandler h);
	public void removeShowCollaborateableHandler(ShowCollaborationHandler h);
	
	public void addShowExperimentHandler(ShowExperimentHandler h);
	public void removeShowExperimentHandler(ShowExperimentHandler h);
	
	
	
	public void setUserResult(Set<UserResult> userResults);
	
	public void setFriendResult(Set<Friend> friendResults);
	
	
	public void setMetaModelResult(Set<CollaborationResult> userMetaModels, Set<CollaborationResult> publicMetaModels);
	
	public void setExperimentResult(Set<ExperimentResult> experimentResults);
	
	public void setModelResult(Set<CollaborationResult> userModel, Set<CollaborationResult> publicModels);
	
	
	public void setTitle(String title);

	public void showLoading();
	
	public void showContent();
	
	
	public void showUnexpectedErrorMessage(Throwable t);
	
	public void showErrorMessage(SearchError error);
	
	/**
	 * Closes the view. that means, the view is no longer visible on the screen
	 */
	public void close();
	
	
	
}
