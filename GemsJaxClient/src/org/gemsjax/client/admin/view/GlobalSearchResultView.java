package org.gemsjax.client.admin.view;

import java.util.Set;

import org.gemsjax.client.admin.widgets.BigMenuButton.BigMenuButtonChangedEventHandler;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.UserResult;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface GlobalSearchResultView {

	public void showUserResult(Set<UserResult> userResults);
	
	public void showFriendResult(Set<UserResult> friendResults);
	
	public void showCollaborationResult(Set<CollaborationResult> collaborationResults);
	
	public void showExperimentResult(Set<ExperimentResult> experimentResults);
	
	public HasClickHandlers getShowUserResultButton();
	
	public HasClickHandlers getShowCollaborationResultButton();
	
	public HasClickHandlers getShowExperimentResultButton();
	
	public void addBigMenuButtonChangedEventHandler(BigMenuButtonChangedEventHandler h);
	
	public void removeBigMenuButtonChangedEventHandler(BigMenuButtonChangedEventHandler h);
	
}
