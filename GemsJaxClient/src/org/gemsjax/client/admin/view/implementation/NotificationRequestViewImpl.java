package org.gemsjax.client.admin.view.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.tabs.TwoColumnLayout;
import org.gemsjax.client.admin.view.NotificationRequestView;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.VerticalBigMenuButtonBar;
import org.gemsjax.shared.communication.message.notification.CollaborationRequestNotification;
import org.gemsjax.shared.communication.message.notification.ExperimentRequestNotification;
import org.gemsjax.shared.communication.message.notification.FriendshipRequestNotification;
import org.gemsjax.shared.communication.message.notification.Notification;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.notification.QuickNotification;
import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.Request;
import org.gemsjax.shared.communication.message.request.RequestError;

import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
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
	private UserLanguage language;
	
	private VStack errorStack;
	private Button retryLoadingButton;
	
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
		
		notificationStack = new VStack();
		friendStack = new VStack();
		collaborationStack = new VStack();
		experimentStack = new VStack();
		
		notificationStack.setAnimateMembers(true);
		friendStack.setAnimateMembers(true);
		collaborationStack.setAnimateMembers(true);
		experimentStack.setAnimateMembers(true);
		
		
		errorStack = new VStack();
		errorStack.addMember(new Label(language.NotificationErrorLoading()));
		retryLoadingButton = new Button(language.NotificationErrorRestartLoading());
		errorStack.addMember(retryLoadingButton);
		
		layout = new TwoColumnLayout();
		layout.setLeftColumn(bigMenuButtonBar, false);
		layout.setRightColumn(rightViewContainer, false);
		this.setContent(layout);
		
		notificationButton.setActive(true);
		showNotifications();
		
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
		
		
		experimentButton = new BigMenuButton(lang.RequestExperimentTitle(), new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showExperimentRequests();
				experimentButton.setActive(true);
			}
		});
		
		
		collaborationButton= new BigMenuButton(lang.RequestCollaborationTitle(), new ClickHandler() {
			
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
	
	
	private void doAccept(RequestListEntry r){
		
		VStack p = (VStack)r.getParent();
		p.setAnimateMembers(true);
		
		for (AnswerRequestHandler h: requestHandlers)
			h.onRequestAnswer(r.request, true);
		
		p.removeMember(r);
	}
	
	
	private void doReject(RequestListEntry r){
		VStack p = (VStack)r.getParent();
		p.setAnimateMembers(true);
		
		for (AnswerRequestHandler h: requestHandlers)
			h.onRequestAnswer(r.request, false);
		
		p.removeMember(r);
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
		notificationStack.clear();
		
		
		List<Notification> sorted = new ArrayList<Notification>(notifications);
		
		Collections.sort(sorted, new Comparator<Notification>() {

			@Override
			public int compare(Notification o1,
					Notification o2) {
				return o1.getDate().compareTo(o2.getDate()) * -1;
			}
		});
		
		
		notificationStack.addMember(new NotificationListEntry().createHeader());
		
		int i =1;
		for (Notification n : sorted){	
			notificationStack.addMember(new NotificationListEntry(n),i);
			i++;
		}
	}

	@Override
	public void setFriendshipRequests(Set<FriendshipRequest> requests) {
		friendStack.clear();
		
		
		List<FriendshipRequest> sortedRequests = new ArrayList<FriendshipRequest>(requests);
		
		Collections.sort(sortedRequests, new Comparator<FriendshipRequest>() {

			@Override
			public int compare(FriendshipRequest o1,
					FriendshipRequest o2) {
				return o1.getDate().compareTo(o2.getDate()) * -1;
			}
		});
		
		friendStack.addMember(new RequestListEntry().createHeader());
		
		int i =1;
		for (Request r : requests){	
			friendStack.addMember(new RequestListEntry(r),i);
			i++;
		}
	}

	@Override
	public void setAdministrateExperimentRequests(Set<AdminExperimentRequest> requests) {
		
		List<AdminExperimentRequest> sortedRequests = new ArrayList<AdminExperimentRequest>(requests);
		
		Collections.sort(sortedRequests, new Comparator<AdminExperimentRequest>() {

			@Override
			public int compare(AdminExperimentRequest o1,
					AdminExperimentRequest o2) {
				return o1.getDate().compareTo(o2.getDate()) * -1;
			}
		});
		
		// TODO any optimization would be great (not delete all and rebuild all)
		experimentStack.clear();
		
		experimentStack.addMember(new RequestListEntry().createHeader());
		
		int i =1;
		for (Request r : sortedRequests){	
			experimentStack.addMember(new RequestListEntry(r),i);
			i++;
		}
	}

	@Override
	public void setCollaborationRequests(Set<CollaborationRequest> requests) {
		collaborationStack.clear();
		
		

		List<CollaborationRequest> sortedRequests = new ArrayList<CollaborationRequest>(requests);
		
		Collections.sort(sortedRequests, new Comparator<CollaborationRequest>() {

			@Override
			public int compare(CollaborationRequest o1,
					CollaborationRequest o2) {
				return o1.getDate().compareTo(o2.getDate()) * -1;
			}
		});
		
		
		collaborationStack.addMember(new RequestListEntry().createHeader());
		
		int i =1;
		for (Request r : sortedRequests){	
			collaborationStack.addMember(new RequestListEntry(r),i);
			i++;
		}
	}
	
	
	
	private void doMarkAsRead(NotificationListEntry n)
	{
		
		for (ChangeNotificationHandler h: notificationHandlers)
			h.onNotificationAsRead(n.notification);
		
		notificationStack.removeMember(n);
		
	}
	
	
	private void doDeleteNotification(NotificationListEntry n){
		
		for (ChangeNotificationHandler h: notificationHandlers)
			h.onDeleteNotification(n.notification);
		
		notificationStack.removeMember(n);
	}
	
	
	
	private class RequestListEntry extends HStack {
		
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
					doAccept(RequestListEntry.this);
				}
			});
			
			accept.setWidth("10%");
			
			
			
			Button reject = new Button(language.RequestAccept());
			reject.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					doReject(RequestListEntry.this);
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

	
	
	
	
	private class NotificationListEntry extends HStack {
		
		private Notification notification;
		
		
		public HStack createHeader(){
			HStack header = new HStack();
			header.setWidth100();
			header.setMargin(20);
			header.setBackgroundColor("#CFCFCF");
			
			Label date = new Label(language.NotificationDateLabel());
			date.setBaseStyle("NotificationListHeaderEntry");
			date.setWidth("10%");
			
			
			Label from = new Label(language.NotificationTopicLabel());
			from.setBaseStyle("NotificationListHeaderEntry");
			date.setWidth("60%");
			
			Label msg = new Label(language.NotificationMarkAsRead());
			msg.setBaseStyle("NotificationListHeaderEntry");
			msg.setWidth("15%");
			
			Label options = new Label(language.NotificationDelete());
			options.setBaseStyle("NotificationListHeaderEntry");
			options.setWidth("15%");
			

			header.addMember(date);
			header.addMember(msg);
			header.addMember(from);
			header.addMember(options);
			
			return header;
		}
		
		
		public NotificationListEntry(){
			
		}
		
		
		
		public NotificationListEntry(Notification n)
		{
			this.notification = n;
			
			setAsRead(n.isRead());
			
			
			// generate
			this.setWidth100();
			this.setMargin(20);
			
			Label date = new Label(DateUtil.format(notification.getDate()));
			date.setBaseStyle("RequestListEntry");
			date.setWidth("10%");
		
			
			Label msg = new Label(language.RequestExperimentInvitedMessage());
			msg.setBaseStyle("RequestListEntry");
			msg.setWidth("60%");
			
			if (n instanceof QuickNotification){
			
				switch (((QuickNotification) n).getType()){
					case FRIENDSHIP_CANCELED: msg.setContents(language.NotificationQuickFriendshipMessage()+" \""+((QuickNotification)n).getOptionalMessage()+"\"");
					case COLLABORATEABLE_DELETED: msg.setContents(language.NotificationQuickCollaborationDeletedMessagePart1()+" \""+((QuickNotification)n).getOptionalMessage()+"\""+language.NotificationQuickCollaborationDeletedMessagePart2());
					case EXPERIMENT_DELETED: msg.setContents(language.NotificationQuickExperimentDeletedMessagePart1()+" \""+((QuickNotification)n).getOptionalMessage()+"\""+language.NotificationQuickExperimentDeletedMessagePart2());
					
				}
			}
			else
			if (n instanceof ExperimentRequestNotification)
			{
				boolean accept = ((ExperimentRequestNotification) n).isAccepted();
				msg.setContents(((ExperimentRequestNotification) n).getDisplayName()+"("+((ExperimentRequestNotification) n).getUsername()+") "+(accept?language.NotificationAccepted():language.NotificationRejected())+ language.NotificationExperimentInvitation()+"\""+((ExperimentRequestNotification)n).getExperimentName()+"\"");
			}
			else
			if (n instanceof CollaborationRequestNotification)
			{
				boolean accept = ((CollaborationRequestNotification) n).isAccepted();
				msg.setContents(((CollaborationRequestNotification) n).getDisplayName()+"("+((CollaborationRequestNotification) n).getUsername()+") "+(accept?language.NotificationAccepted():language.NotificationRejected())+ language.NotificationCollaborationInvitation()+"\""+((CollaborationRequestNotification)n).getCollaborationName()+"\"");
			}
			if (n instanceof FriendshipRequestNotification)
			{
				boolean accept = ((FriendshipRequestNotification) n).isAccepted();
				msg.setContents(((FriendshipRequestNotification) n).getDisplayName()+"("+((FriendshipRequestNotification) n).getUsername()+") "+(accept?language.NotificationAccepted():language.NotificationRejected())+ language.NotificationFriendshipInvitation());
			}
			
			
			
			
			Button asReadButton = new Button(language.NotificationMarkAsRead());
			asReadButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					doMarkAsRead(NotificationListEntry.this);
				}
			});
			
			asReadButton.setWidth("15%");
			
			
			
			Button deleteNotification = new Button(language.RequestAccept());
			deleteNotification.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					doDeleteNotification(NotificationListEntry.this);
				}
			});
			
			deleteNotification.setWidth("15%");
				
			this.addMember(date);
			this.addMember(msg);
			this.addMember(asReadButton);
			this.addMember(deleteNotification);
			
		}
		
		
		
		public void setAsRead(boolean read)
		{
			if (read)
				this.setBackgroundColor("white");
			else
				this.setBackgroundColor("#E6E6E6");
		}
		
	}





	@Override
	public void showRequestError(Request r, RequestError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showNotificationError(Notification n, NotificationError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showIt(boolean show) {
		TabEnviroment te = TabEnviroment.getInstance();
		if (te.containsTab(this))
			te.setSelectedTab(this);
		else
			te.addTab(this);
	}

	@Override
	public void showInitializeError() {
		showErrorContent(errorStack);
	}

	@Override
	public HasClickHandlers getReInitializeButton() {
		return retryLoadingButton;
	}

	@Override
	public void setNotificationAsRead(Notification n, boolean read) {
		int index = findNotificationIndexBy(n);
		if (index >=0){
			NotificationListEntry e = (NotificationListEntry) notificationStack.getMember(index);
			e.setAsRead(read);
		}
		
	}

	@Override
	public void addNotification(Notification n) {
		// Find the correct index position
		
		int index = -1;
		Canvas [] members = notificationStack.getMembers();
		// Assumption: the members are already sorted, so we start on the top and go to the bottom to find the correct position
		
		for (int i =0; i<members.length; i++)
		{
			if (members[i] instanceof NotificationListEntry)
			{
				Date d = ((NotificationListEntry)members[i]).notification.getDate();
				
				if (n.getDate().compareTo(d)>0){ // notification belongs over the current index
					index = i;
					break;
				}
			}
		}
		if (index>=0)
			notificationStack.addMember(new NotificationListEntry(n),index);
		else	// This should never be the case
			notificationStack.addMember(new NotificationListEntry(n));
		
	}

	
	
	private int findNotificationIndexBy(Notification n){
		int i =0;
		for (Canvas c: notificationStack.getMembers()){
			if (c instanceof NotificationListEntry)
				if (((NotificationListEntry) c).notification.equals(n))
					return i;
			i++;
		}
		
		return -1;
	}

	
	@Override
	public void addRequest(Request r) {
		if (r instanceof FriendshipRequest)
			addFriendshipRequest((FriendshipRequest) r);
		else
		if (r instanceof AdminExperimentRequest)
			addExperimentRequest((AdminExperimentRequest) r);
		else
		if (r instanceof CollaborationRequest)
			addCollaborationRequest((CollaborationRequest) r);
	}
	
	
	private void addFriendshipRequest(FriendshipRequest r)
	{
		int index = -1;
		Canvas [] members = friendStack.getMembers();
		// Assumption: the members are already sorted, so we start on the top and go to the bottom to find the correct position
		
		for (int i =0; i<members.length; i++)
		{
			if (members[i] instanceof RequestListEntry)
			{
				Date d = ((RequestListEntry)members[i]).request.getDate();
				
				if (r.getDate().compareTo(d)>0){ // notification belongs over the current index
					index = i;
					break;
				}
			}
		}
		if (index>=0)
			friendStack.addMember(new RequestListEntry(r),index);
		else	// This should never be the case
			friendStack.addMember(new RequestListEntry(r));
		
		
	}
	
	
	private void addExperimentRequest(AdminExperimentRequest r)
	{
		int index = -1;
		Canvas [] members = experimentStack.getMembers();
		// Assumption: the members are already sorted, so we start on the top and go to the bottom to find the correct position
		
		for (int i =0; i<members.length; i++)
		{
			if (members[i] instanceof RequestListEntry)
			{
				Date d = ((RequestListEntry)members[i]).request.getDate();
				
				if (r.getDate().compareTo(d)>0){ // notification belongs over the current index
					index = i;
					break;
				}
			}
		}
		if (index>=0)
			experimentStack.addMember(new RequestListEntry(r),index);
		else	// This should never be the case
			experimentStack.addMember(new RequestListEntry(r));
		
		
	}
	
	
	private void addCollaborationRequest(CollaborationRequest r)
	{
		int index = -1;
		Canvas [] members = collaborationStack.getMembers();
		// Assumption: the members are already sorted, so we start on the top and go to the bottom to find the correct position
		
		for (int i =0; i<members.length; i++)
		{
			if (members[i] instanceof RequestListEntry)
			{
				Date d = ((RequestListEntry)members[i]).request.getDate();
				
				if (r.getDate().compareTo(d)>0){ // notification belongs over the current index
					index = i;
					break;
				}
			}
		}
		if (index>=0)
			collaborationStack.addMember(new RequestListEntry(r),index);
		else	// This should never be the case
			collaborationStack.addMember(new RequestListEntry(r));
		
		
	}

	@Override
	public void setCount(int unreadNotifications, int friendshipRequests,
			int experimentRequests, int collaborationRequests) {
		
		notificationButton.setText(language.NotificationTitle() +(unreadNotifications>0?"("+unreadNotifications+")":""));
		friendshipButton.setText(language.RequestFriendshipTitle() +(friendshipRequests>0?"("+friendshipRequests+")":""));
		experimentButton.setText(language.RequestExperimentTitle() +(experimentRequests>0?"("+experimentRequests+")":""));
		collaborationButton.setText(language.RequestCollaborationTitle() +(collaborationRequests>0?"("+collaborationRequests+")":""));
		
	}
}
