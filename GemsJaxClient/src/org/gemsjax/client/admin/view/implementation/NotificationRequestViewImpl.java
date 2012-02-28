package org.gemsjax.client.admin.view.implementation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.SearchField;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.tabs.TwoColumnLayout;
import org.gemsjax.client.admin.view.GlobalSearchResultView;
import org.gemsjax.client.admin.view.NotificationRequestView;
import org.gemsjax.client.admin.view.handlers.FriendshipHandler;
import org.gemsjax.client.admin.view.handlers.ShowCollaborationHandler;
import org.gemsjax.client.admin.view.handlers.ShowExperimentHandler;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.client.admin.widgets.VerticalBigMenuButtonBar;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.notification.Notification;
import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.Request;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.UserResult;
import org.gemsjax.shared.user.UserOnlineState;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class NotificationRequestViewImpl extends LoadingTab implements NotificationRequestView{

	
	private Set<AnswerRequestHandler> requestHandlers;
	private Set<ChangeNotificationHandler> notificationHandlers;
	 
	private VStack rightViewContainer;
	private Canvas currentDisplayedView;
	private BigMenuButton notificationButton, friendshipButton, collaborationButton, experimentButton;
	private VerticalBigMenuButtonBar bigMenuButtonBar;
	private TwoColumnLayout layout;
	private SearchField searchField;
	private UserLanguage language;
	
	private VStack notificationStack, friendStack, collaborationStack, experimentStack;
	
	public NotificationRequestViewImpl(String title, UserLanguage lang) {
		super(title, lang);
		this.language = lang;
		
		
		requestHandlers = new LinkedHashSet<AnswerRequestHandler>();
		notificationHandlers = new LinkedHashSet<ChangeNotificationHandler>();
	
		
		bigMenuButtonBar = new VerticalBigMenuButtonBar(200,10,60);
		bigMenuButtonBar.setMargin(5);
		
		
		generateMenuButtons(lang);
		bigMenuButtonBar.addMember(notificationButton);
		bigMenuButtonBar.addMember(friendshipButton);
		bigMenuButtonBar.addMember(collaborationButton);
		bigMenuButtonBar.addMember(experimentButton);
		
		
		
		rightViewContainer = new VStack();
		rightViewContainer.setWidth100();
		
		
		layout = new TwoColumnLayout();
		layout.setLeftColumn(bigMenuButtonBar, false);
		layout.setRightColumn(rightViewContainer, false);
		this.setContent(layout);
		
	}
	
	private void generateMenuButtons(UserLanguage lang)
	{
		notificationButton = new BigMenuButton(lang.NotificationTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showNotifications();
				notificationButton.setActive(true);
			}
		});
		
		
		friendshipButton = new BigMenuButton(lang.RequestFriendshipTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showFriendshipRequests();
				friendshipButton.setActive(true);
			}
		});
		
		
		experimentButton = new BigMenuButton(lang.GlobalSearchExperimentsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showExperimentRequests();
				experimentButton.setActive(true);
			}
		});
		
		
		collaborationButton= new BigMenuButton(lang.GlobalSearchExperimentsMenuTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showCollaborationRequests();
				collaborationButton.setActive(true);
			}
		});
		
	}
	

	private void showView(Canvas view)
	{
		if(currentDisplayedView!=null)
			rightViewContainer.removeMember(currentDisplayedView);
		
		rightViewContainer.addMember(view);
		
		currentDisplayedView = view;
	}
	
	private void showNotifications()
	{
		showView(notificationStack);
	}

	
	private void showFriendshipRequests()
	{
		showView(friendStack);
	}
	
	private void showExperimentRequests()
	{
		showView(experimentStack);
	}

	private void showCollaborationRequests()
	{
		showView(collaborationStack);
	}
	
	
	private void doAccept(Request r){
		
	}
	
	
	private void doReject(Request r){
		
	}
	
	
	
	private class RequestListEntry extends HStack{
		
		private Request request;
		
		
		public HStack createHeader(){
			HStack header = new HStack();
			header.setWidth100();
			header.setMargin(20);
			header.setBackgroundColor("#CFCFCF");
			
			Label date = new Label(language.RequestDateLabel());
			date.setBaseStyle("RequestListHeaderEntry");
			date.setWidth("20%");
			
			
			Label from = new Label(language.RequestFromLabel());
			from.setBaseStyle("RequestListHeaderEntry");
			date.setWidth("20%");
			
			Label msg = new Label(language.RequestMessageLabel());
			msg.setBaseStyle("RequestListHeaderEntry");
			msg.setWidth("30%");
			
			Label options = new Label(language.RequestOptionsLabel());
			options.setBaseStyle("RequestListHeaderEntry");
			options.setWidth("30%");
			

			header.addMember(date);
			header.addMember(msg);
			header.addMember(from);
			header.addMember(options);
			
			return header;
		}
		
		
		public RequestListEntry(){
			
		}
		
		
		
		public RequestListEntry(Request r)
		{
			this.request = r;
			
			
			// generate
			this.setWidth100();
			this.setMargin(20);
			
			Label date = new Label(DateUtil.format(request.getDate()));
			date.setBaseStyle("RequestListEntry");
			date.setWidth("20%");
			
			Label from = new Label(request.getRequesterDisplayName());
			from.setBaseStyle("RequestListEntry");
			from.setWidth("20%");
			
			Label msg = new Label(language.RequestExperimentInvitedMessage());
			msg.setBaseStyle("RequestListEntry");
			msg.setWidth("30%");
			
			if (r instanceof AdminExperimentRequest)
				msg.setContents(language.RequestExperimentInvitedMessage()+" \""+((AdminExperimentRequest)r).getExperimentName()+"\"");
			
			else
			if (r instanceof CollaborationRequest)
				msg.setContents(language.RequestCollaborationInvitedMessage()+" \""+((CollaborationRequest)r).getName()+"\"");
			
			else
			if(r instanceof FriendshipRequest)
				msg.setContents(r.getRequesterDisplayName()+"("+r.getRequesterUsername()+") "+language.RequestFriendshipInvitedMessage());
			
			
			
			
			Button accept = new Button(language.RequestAccept());
			accept.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					doAccept(request);
				}
			});
			
			accept.setWidth("10%");
			
			
			
			Button reject = new Button(language.RequestAccept());
			accept.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					doReject(request);
				}
			});
			
			reject.setWidth("10%");
				
			this.addMember(date);
			this.addMember(msg);
			this.addMember(from);
			this.addMember(accept);
			this.addMember(reject);
			
		}
		
	}



	@Override
	public void addAnswerRequestHandler(AnswerRequestHandler h) {
		requestHandlers.add(h);
	}

	@Override
	public void removeAnswerRequestHandler(AnswerRequestHandler h) {
		requestHandlers.remove(h);
	}

	@Override
	public void addChangeNotificationHandler(ChangeNotificationHandler h) {
		notificationHandlers.add(h);
	}

	@Override
	public void removeChangeNotificationHandler(ChangeNotificationHandler h) {
		notificationHandlers.remove(h);
	}

	@Override
	public void setNotifications(Set<Notification> notifications) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFriendshipRequests(Set<FriendshipRequest> requests) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAdministrateExperimentRequests(
			Set<AdminExperimentRequest> requests) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCollaborationRequests(Set<CollaborationRequest> requests) {
		// TODO Auto-generated method stub
		
	}

	
}
