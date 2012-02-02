package org.gemsjax.client.admin.view.implementation;

import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.tabs.TwoColumnLayout;
import org.gemsjax.client.admin.view.GlobalSearchResultView;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.VerticalBigMenuButtonBar;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.UserResult;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.layout.VStack;

public class GlobalSearchResultViewImpl extends LoadingTab implements GlobalSearchResultView{

	
	private VStack contentRight; 
	private BigMenuButton userButton, friendsButton, metaModelButton, modelButton, experimentButton;
	private VerticalBigMenuButtonBar bigMenuButtonBar;
	
	private TwoColumnLayout layout;
	
	public GlobalSearchResultViewImpl(String title, UserLanguage lang) {
		super(title, lang);
		
		bigMenuButtonBar = new VerticalBigMenuButtonBar(200,10,60);
		bigMenuButtonBar.setMargin(5);
		
		
		
		generateMenuButtons(lang);
		bigMenuButtonBar.addMember(userButton);
		bigMenuButtonBar.addMember(friendsButton);
		bigMenuButtonBar.addMember(metaModelButton);
		bigMenuButtonBar.addMember(modelButton);
		bigMenuButtonBar.addMember(experimentButton);
		
		
		contentRight = new VStack();
		contentRight.setWidth("*");
		
		
		layout = new TwoColumnLayout();
		layout.setLeftColumn(bigMenuButtonBar, false);
		layout.setRightColumn(contentRight, false);
		this.setContent(layout);
		
	}
	
	private void generateMenuButtons(UserLanguage lang)
	{
		userButton = new BigMenuButton(lang.GlobalSearchUsersMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showUserResults();
			}
		});
		
		
		friendsButton = new BigMenuButton(lang.GlobalSearchFriendsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showFriendResults();
			}
		});
		
		
		metaModelButton = new BigMenuButton(lang.GlobalSearchMetaModelsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showMetaModelResults();
			}
		});
		
		
		modelButton = new BigMenuButton(lang.GlobalSearchModelsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showModelResults();
			}
			
			
		});
		
		
		experimentButton = new BigMenuButton(lang.GlobalSearchExperimentsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showExperimentResults();
			}
		});
	}
	
	
	private void showUserResults()
	{
		
	}

	
	private void showFriendResults()
	{
		
	}
	
	private void showMetaModelResults()
	{
		
	}

	private void showModelResults()
	{
		
	}
	
	
	private void showExperimentResults()
	{
		
	}

	@Override
	public void setUserResult(Set<UserResult> userResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFriendResult(Set<Friend> friendResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPublicMetaModelResult(Set<CollaborationResult> metaModels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUsersMetaModelResult(Set<CollaborationResult> metaModels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setExperimentResult(Set<ExperimentResult> experimentResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPublicModelresult(Set<CollaborationResult> models) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUsersModelResult(Set<CollaborationResult> models) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HasClickHandlers getSearchButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSearchString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showUnexpectedErrorMessage(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showErrorMessage(SearchError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
