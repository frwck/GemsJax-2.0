package org.gemsjax.client.admin.view;

import java.util.Set;

import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.UserResult;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface GlobalSearchResultView {
	
	
/*
	public void showUserResult(Set<UserResult> userResults);
	
	public void showFriendResult(Set<UserResult> friendResults);
	
	public void showMetaModels(Set<CollaborationResult> metaModels);
	
	public void showExperimentResult(Set<ExperimentResult> experimentResults);
	
	public void showModels(Set<CollaborationResult> models);
	
	public HasClickHandlers getShowUserResultButton();
	
	public HasClickHandlers getShowModelsResultButton();
	
	public HasClickHandlers getShowMetaModelsResultButton();
	
	public HasClickHandlers getShowExperimentResultButton();
	*/
	
	
	public void setUserResult(Set<UserResult> userResults);
	
	public void setFriendResult(Set<Friend> friendResults);
	
	public void setPublicMetaModelResult(Set<CollaborationResult> metaModels);
	
	public void setUsersMetaModelResult(Set<CollaborationResult> metaModels);
	
	public void setExperimentResult(Set<ExperimentResult> experimentResults);
	
	public void setPublicModelresult(Set<CollaborationResult> models);
	
	public void setUsersModelResult(Set<CollaborationResult> models);
	
	public HasClickHandlers getSearchButton();
	
	public String getSearchString();
	
	
	public void showLoading();
	
	public void showContent();
	
	
	public void showUnexpectedErrorMessage(Throwable t);
	
	public void showErrorMessage(SearchError error);
	
	/**
	 * Closes the view. that means, the view is no longer visible on the screen
	 */
	public void close();
	
	/*
	public void addBigMenuButtonChangedEventHandler(BigMenuButtonChangedEventHandler h);
	
	public void removeBigMenuButtonChangedEventHandler(BigMenuButtonChangedEventHandler h);
	*/
	
}
