package org.gemsjax.client.admin.view.implementation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.SearchField;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.tabs.TwoColumnLayout;
import org.gemsjax.client.admin.view.GlobalSearchResultView;
import org.gemsjax.client.admin.view.handlers.FriendshipHandler;
import org.gemsjax.client.admin.view.handlers.ShowCollaborationHandler;
import org.gemsjax.client.admin.view.handlers.ShowExperimentHandler;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.client.admin.widgets.VerticalBigMenuButtonBar;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.UserResult;
import org.gemsjax.shared.user.UserOnlineState;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class GlobalSearchResultViewImpl extends LoadingTab implements GlobalSearchResultView{

	private static final int ENTRY_HEIGHT = 40;
	
	
	private final String NO_PROFILE_PICTURE_URL="/images/NoProfilePicture.jpg";
	private final int PROFILE_PICTURE_WIDTH=40;
	private final int PROFILE_PICTURE_HEIGHT=40;
	
	
	private final String OFFLINE_PICTURE = "/images/offline.png";
	private final String ONLINE_PICTURE = "/images/online.png";
	
	private final int ONLINE_OFFLINE_WIDTH=10;
	private final int ONLINE_OFFLINE_HEIGHT=10;
	
	
	private Set<FriendshipHandler> friendHandlers;
	private Set<ShowCollaborationHandler> collaborationHandlers;
	private Set<ShowExperimentHandler> experimentHandlers;
	
	
	
	private VStack contentRight; 
	private VStack resultContainer;
	private Canvas currentDisplayedResult;
	private BigMenuButton userButton, friendsButton, metaModelButton, modelButton, experimentButton;
	private VerticalBigMenuButtonBar bigMenuButtonBar;
	private TwoColumnLayout layout;
	private SearchField searchField;
	
	private VStack userStack, friendStack, metaStack, modelStack, experimentStack;
	
	public GlobalSearchResultViewImpl(String title, UserLanguage lang) {
		super(title, lang);
		
		
		friendHandlers = new LinkedHashSet<FriendshipHandler>();
		collaborationHandlers = new LinkedHashSet<ShowCollaborationHandler>();
		experimentHandlers = new LinkedHashSet<ShowExperimentHandler>();
		
		
		bigMenuButtonBar = new VerticalBigMenuButtonBar(200,10,60);
		bigMenuButtonBar.setMargin(5);
		
		
		generateMenuButtons(lang);
		bigMenuButtonBar.addMember(userButton);
		bigMenuButtonBar.addMember(friendsButton);
		bigMenuButtonBar.addMember(metaModelButton);
		bigMenuButtonBar.addMember(modelButton);
		bigMenuButtonBar.addMember(experimentButton);
		
		
		
		
		userStack = new VStack();
		userStack.addMember(new Label(language.GlobalSearchNoResult()));
		
		friendStack = new VStack();
		friendStack.addMember(new Label(language.GlobalSearchNoResult()));
		
		metaStack = new VStack();
		metaStack.addMember(new Label(language.GlobalSearchNoResult()));
		
		modelStack = new VStack();
		modelStack.addMember(new Label(language.GlobalSearchNoResult()));
		
		experimentStack = new VStack();
		experimentStack.addMember(new Label(language.GlobalSearchNoResult()));
		
		
		
		HStack helpSearchStack = new HStack();
		helpSearchStack.setWidth100();
		Label helpSearchLabel = new Label();
		helpSearchLabel.setWidth("80%");
		
		helpSearchStack.addMember(helpSearchLabel);
		
		contentRight = new VStack();
		contentRight.setWidth("*");
		
		searchField = new SearchField();
		searchField.setResetAfterFireSearch(false);
		searchField.setValue(title);
		DynamicForm form = new DynamicForm();
		form.setWidth("20%");
		form.setAlign(Alignment.RIGHT);
		form.setFields(searchField);
		searchField.setAlign(Alignment.RIGHT);
		
		
		helpSearchStack.addMember(form);
		
		contentRight.addMember(helpSearchStack);

		
		
		resultContainer = new VStack();
		resultContainer.setWidth100();
		
		contentRight.addMember(resultContainer);
	
		
		
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
				userButton.setActive(true);
			}
		});
		
		
		friendsButton = new BigMenuButton(lang.GlobalSearchFriendsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showFriendResults();
				friendsButton.setActive(true);
			}
		});
		
		
		metaModelButton = new BigMenuButton(lang.GlobalSearchMetaModelsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showMetaModelResults();
				metaModelButton.setActive(true);
			}
		});
		
		
		modelButton = new BigMenuButton(lang.GlobalSearchModelsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showModelResults();
				modelButton.setActive(true);
			}
			
			
		});
		
		
		experimentButton = new BigMenuButton(lang.GlobalSearchExperimentsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showExperimentResults();
				experimentButton.setActive(true);
			}
		});
	}
	

	private void showResult(Canvas resultGUI)
	{
		if(currentDisplayedResult!=null)
			resultContainer.removeMember(currentDisplayedResult);
		
		resultContainer.addMember(resultGUI);
		
		currentDisplayedResult = resultGUI;
	}
	
	private void showUserResults()
	{
		showResult(userStack);
	}

	
	private void showFriendResults()
	{
		showResult(friendStack);
	}
	
	private void showMetaModelResults()
	{
		showResult(metaStack);
	}

	private void showModelResults()
	{
		showResult(modelStack);
	}
	
	
	private void showExperimentResults()
	{
		showResult(experimentStack);
	}

	@Override
	public void setUserResult(Set<UserResult> userResults) {
		
		
		userStack.removeMembers(userStack.getMembers());
		
		userStack.addMember(new Title(language.GlobalSearchUsersMenuTitle()));
		
		if (userResults==null || userResults.isEmpty())
			userStack.addMember(new Label(language.GlobalSearchNoResult()));
		else
		{
			for (UserResult u: userResults)
				userStack.addMember(new UserResultEntry(u));
		}
		
		
	}

	@Override
	public void setFriendResult(Set<Friend> friendResults) {
		
		friendStack.removeMembers(friendStack.getMembers());
		
		
		friendStack.addMember(new Title(language.GlobalSearchFriendsMenuTitle()));
		
		if (friendResults==null || friendResults.isEmpty())
			friendStack.addMember(new Label(language.GlobalSearchNoResult()));
		else
		{
			for (Friend u: friendResults)
				friendStack.addMember(new FriendResultEntry(u));
		}
		
	}

	

	@Override
	public void setExperimentResult(Set<ExperimentResult> experimentResults) {
		
		experimentStack.removeMembers(experimentStack.getMembers());
		
		experimentStack.addMember(new Title(language.GlobalSearchExperimentsMenuTitle()));
		
		if (experimentResults==null || experimentResults.isEmpty())
			experimentStack.addMember(new Label(language.GlobalSearchNoResult()));
		else
		{
			for (ExperimentResult e: experimentResults)
				experimentStack.addMember(new ExperimentResultEntry(e));
		}
	}

	
	@Override
	public void showContent()
	{
		super.showContent();
		showUserResults();
		userButton.setActive(true);
	}

	@Override
	public void showUnexpectedErrorMessage(Throwable t) {
		Window.alert(language.GlobalSearchUnexpectedErrorMessage());
	}

	@Override
	public void showErrorMessage(SearchError error) {
		
		String msg="Error. Please retry";
		
		switch (error)
		{
			case AUTHENTICATION: msg = language.GlobalSearchErrorNotAuthenticated(); break;
			case DATABASE: msg = language.GlobalSearchErrorDatabase(); break;
			case PARSING: msg = language.GlobalSearchErrorParsing(); break;
		}
		
		
		Window.alert(msg);
	}

	@Override
	public void close() {
		TabEnviroment.getInstance().removeTab(this);
	}
	
	
	private void fireFriendShipRequest(UserResult user)
	{
		for (FriendshipHandler h: friendHandlers)
			h.onNewFriendshipRequired(user);
	}
	
	
	private void fireUnfriend(Friend friendId)
	{
		for (FriendshipHandler h: friendHandlers)
			h.onUnfriendRequired(friendId);
	}
	
	
	private void fireShowCollaboration(int colId)
	{
		for (ShowCollaborationHandler h: collaborationHandlers)
			h.onShowCollaborationRequired(colId);
	}
	
	
	private void fireShowExperiment(int experimentId)
	{
		for (ShowExperimentHandler h: experimentHandlers)
			h.onShowExperimentRequired(experimentId);
	}
	
	
	
	@Override
	public void addQuickSearchHandler(QuickSearchHanlder h) {
		searchField.getQuickSearchHandlers().add(h);
	}

	@Override
	public void removeQuickSearchHandler(QuickSearchHanlder h) {
		searchField.getQuickSearchHandlers().remove(h);
	}

	@Override
	public void addFriendshipHandler(FriendshipHandler h) {
		friendHandlers.add(h);
	}

	@Override
	public void removeFriendshipHandler(FriendshipHandler h) {
		friendHandlers.remove(h);
		
	}

	@Override
	public void addShowCollaborateableHandler(ShowCollaborationHandler h) {
		collaborationHandlers.add(h);
	}

	@Override
	public void removeShowCollaborateableHandler(ShowCollaborationHandler h) {
		collaborationHandlers.remove(h);
	}

	@Override
	public void addShowExperimentHandler(ShowExperimentHandler h) {
		experimentHandlers.add(h);
	}

	@Override
	public void removeShowExperimentHandler(ShowExperimentHandler h) {
		experimentHandlers.remove(h);
	}

	@Override
	public void setMetaModelResult(Set<CollaborationResult> userMetaModels,
			Set<CollaborationResult> publicMetaModels) {
		
		metaStack = new VStack();
		
		metaStack.addMember(new Title(language.GlobalSearchMyMetaModelsTitle()));
		
		if (userMetaModels==null || userMetaModels.isEmpty())
			metaStack.addMember(new Label(language.GlobalSearchNoResult()));
		else
		{
			for (CollaborationResult r: userMetaModels)
				metaStack.addMember(new CollaborationResultEntry(r));
		}
		
		
		
		
		
		metaStack.addMember(new Title(language.GlobalSearchPublicMetaModelsTitle()));
		
		if (publicMetaModels==null || publicMetaModels.isEmpty())
			metaStack.addMember(new Label(language.GlobalSearchNoResult()));
		else
		{
			for (CollaborationResult r: publicMetaModels)
				metaStack.addMember(new CollaborationResultEntry(r));
		}
		
		
		
	}

	@Override
	public void setModelResult(Set<CollaborationResult> userModel,
			Set<CollaborationResult> publicModels) {
		
		
		modelStack = new VStack();
		
		modelStack.addMember(new Title(language.GlobalSearchMyModelsTitle()));
		
		if (userModel==null || userModel.isEmpty())
			modelStack.addMember(new Label(language.GlobalSearchNoResult()));
		else
		{
			for (CollaborationResult r: userModel)
				modelStack.addMember(new CollaborationResultEntry(r));
		}
		
		
		
		
		modelStack.addMember(new Title(language.GlobalSearchPublicModelsTitle()));
		
		if (publicModels==null || publicModels.isEmpty())
			modelStack.addMember(new Label(language.GlobalSearchNoResult()));
		else
		{
			for (CollaborationResult r: publicModels)
				modelStack.addMember(new CollaborationResultEntry(r));
		}
		
	}
	
	
	private class UserResultEntry extends HStack{
		
		public UserResultEntry(final UserResult user)
		{
			
			// generate
			this.setWidth100();
			this.setMargin(20);
			this.setHeight(ENTRY_HEIGHT);
			Img profile = new Img(user.getProfilePicture()==null?NO_PROFILE_PICTURE_URL:user.getProfilePicture());
			profile.setWidth(PROFILE_PICTURE_WIDTH);
			profile.setHeight(PROFILE_PICTURE_HEIGHT);
			
			profile.setAlign(Alignment.LEFT);
			profile.setValign(VerticalAlignment.CENTER);
			
			
			Label username = new Label(user.getUsername());
			Label displayName = new Label(user.getDisplayName());
			
			username.setBaseStyle("GloabalSearchResultLabel");
			displayName.setBaseStyle("GloabalSearchResultLabel");
			username.setWidth("30%");
			displayName.setWidth("*");
			
			this.addMember(profile);
			this.addMember(displayName);
			this.addMember(username);
			
			Button friendship = new Button(language.GlobalSearchNewFriendshipRequest());
			friendship.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					fireFriendShipRequest(user);
				}
			});
			
			friendship.setWidth("20%");
			this.addMember(friendship);
		}
		
	}
	
	
	private class FriendResultEntry extends HStack{
		
		public FriendResultEntry(final Friend friend)
		{
			
			// generate
			this.setWidth100();
			this.setMargin(20);
			this.setHeight(ENTRY_HEIGHT);
			
			Img profile = new Img(friend.getProfilePicture()==null?NO_PROFILE_PICTURE_URL:friend.getProfilePicture());
			profile.setWidth(PROFILE_PICTURE_WIDTH);
			profile.setHeight(PROFILE_PICTURE_HEIGHT);
			profile.setAlign(Alignment.LEFT);
			profile.setValign(VerticalAlignment.CENTER);
			
			
			Img online = new Img(friend.getOnlineState()==UserOnlineState.ONLINE?ONLINE_PICTURE:OFFLINE_PICTURE);
			online.setWidth(ONLINE_OFFLINE_WIDTH);
			online.setHeight(ONLINE_OFFLINE_HEIGHT);
			online.setAlign(Alignment.LEFT);
			online.setValign(VerticalAlignment.CENTER);
			
			Label dispName = new Label(friend.getDisplayName());
			dispName.setWidth("*");
			dispName.setBaseStyle("GloabalSearchResultLabel");
			
			this.addMember(online);
			this.addMember(profile);
			this.addMember(dispName);
			
			
			
			
			Button friendship = new Button(language.GlobalSearchUnfriend());
			friendship.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					fireUnfriend(friend);
				}
			});
			
			friendship.setWidth("20%");
			this.addMember(friendship);
		}
		
	}
	
	
	
	private class CollaborationResultEntry extends HStack{
		
		public CollaborationResultEntry(final CollaborationResult col)
		{
			// generate
			this.setWidth100();
			this.setMargin(20);
			this.setHeight(ENTRY_HEIGHT);
			
			Label name = new Label(col.getName());
			Label owner = new Label(col.getOwnerName());
			
			name.setWidth("40");
			name.setBaseStyle("GloabalSearchResultLabel");
			
			owner.setWidth("40");
			owner.setBaseStyle("GloabalSearchResultLabel");
			
			
			this.addMember(name);
			this.addMember(owner);
			
			Button show = new Button(language.GlobalSearchShow());
			show.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					fireShowCollaboration(col.getId());
				}
			});
			
			show.setWidth("20%");
			
			this.addMember(show);
		}
		
	}
	
	
	private class ExperimentResultEntry extends HStack{
		
		public ExperimentResultEntry(final ExperimentResult col)
		{
			// generate
			this.setWidth100();
			this.setMargin(20);
			this.setHeight(ENTRY_HEIGHT);
			
			
			Label name = new Label(col.getName());
			name.setBaseStyle("GloabalSearchResultLabel");
			name.setWidth("40%");
			
			Label owner = new Label(col.getOwnerName());
			owner.setBaseStyle("GloabalSearchResultLabel");
			owner.setWidth("40%");
			
			this.addMember(name);
			this.addMember(owner);
			
			Button show = new Button(language.GlobalSearchShow());
			show.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					fireShowExperiment(col.getId());
				}
			});
			
			show.setWidth("20%");
			this.addMember(show);
		}
		
	}

	
}
