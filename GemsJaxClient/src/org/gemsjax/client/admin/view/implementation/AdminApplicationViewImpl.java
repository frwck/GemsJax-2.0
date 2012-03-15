package org.gemsjax.client.admin.view.implementation;


import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.Footer;
import org.gemsjax.client.admin.adminui.Header;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.ShortInfoNotification;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.view.AdminUIView;
import org.gemsjax.client.admin.view.NotificationRequestShortInfoView;
import org.gemsjax.client.admin.view.QuickSearchView;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.communication.message.notification.CollaborationRequestNotification;
import org.gemsjax.shared.communication.message.notification.ExperimentRequestNotification;
import org.gemsjax.shared.communication.message.notification.FriendshipRequestNotification;
import org.gemsjax.shared.communication.message.notification.LiveNotificationMessage;
import org.gemsjax.shared.communication.message.notification.LiveQuickNotificationMessage;
import org.gemsjax.shared.communication.message.notification.Notification;
import org.gemsjax.shared.communication.message.notification.QuickNotification;
import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.Request;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This is the base class for the GUI. 
 * @author Hannes Dorfmann
 *
 */
public class AdminApplicationViewImpl implements AdminUIView, QuickSearchView, NotificationRequestShortInfoView{
	
	/**
	 * The width of the "visible" content. This is the width of the {@link Header}, {@link Footer} and the {@link TabEnviroment}. <br /><br />
	 * <b> Also the Notification width will be calculated according this value.
	 * <u> If you change this value from percent to another unity, like px, you also have to change the implementation of the private method {@link TipNotification} calculateWidthAndPosition()</u></b>
	 * 
	 */
	public static final String contentWidth="90%";
	
	
	
	private VLayout uiLayout;
	private UserLanguage language;
	private Header header;
	private TabEnviroment tabEnviroment;
	
	
	public AdminApplicationViewImpl(UserLanguage language)
	{
		this.language = language;
		createUI();
		
	}
	
	
	private void createUI()
	{
		// Set the 
		uiLayout = new VLayout();
		uiLayout.setWidth100();
		uiLayout.setHeight100();
		uiLayout.setMargin(0);
		
		header = new Header(language);
		
		// Header
		Canvas spacerHeaderLeft = new Canvas();
		Canvas spacerHeaderRight = new Canvas();
	
		HLayout headerCenteringLayout = new HLayout();
		headerCenteringLayout.setWidth100();
		headerCenteringLayout.setHeight(Header.HEIGHT);
		spacerHeaderLeft.setWidth("*");
		spacerHeaderRight.setWidth("*");
		
		headerCenteringLayout.addMember(spacerHeaderLeft);
		headerCenteringLayout.addMember(header);
		headerCenteringLayout.addMember(spacerHeaderRight);
		
		
		
		// TabEnviroment
		tabEnviroment = TabEnviroment.getInstance();
	
		Canvas spacerTabLeft = new Canvas();
		Canvas spacerTabRight = new Canvas();
	
		HLayout tabCenteringLayout = new HLayout();
		tabCenteringLayout.setWidth100();
		tabCenteringLayout.setHeight("*");
		spacerTabLeft.setWidth("*");
		spacerTabRight.setWidth("*");
		
		tabCenteringLayout.addMember(spacerTabLeft);
		tabCenteringLayout.addMember(tabEnviroment);
		tabCenteringLayout.addMember(spacerTabRight);
		
		
		
		
		// Add parts
		uiLayout.addMember(headerCenteringLayout);
		uiLayout.addMember(tabCenteringLayout);
		uiLayout.addMember(Footer.getInstance());
		
		uiLayout.draw();
		uiLayout.hide();
		
	}
	


	@Override
	public HasClickHandlers getUserMenuExperiments() {
		return header.getDashBoardMenuItem();
	}


	@Override
	public HasClickHandlers getUserMenuMetaModels() {
		return header.getMetaModelsItem();
	}


	@Override
	public HasClickHandlers getUserMenuSettings() {
		return header.getSettingsItem();
	}


	@Override
	public HasClickHandlers getUserMenuLogout() {
		return header.getLogoutItem();
	}


	@Override
	public HasClickHandlers getUserMenuNotificationRequestCenter() {
		return header.getNotificationsMenuItem();
	}


	@Override
	public void show() {
		uiLayout.show();
	}


	@Override
	public void hide() {
		uiLayout.hide();
	}


	@Override
	public void addQuickSearchHandler(QuickSearchHanlder h) {
		header.getSearchField().getQuickSearchHandlers().add(h);
	}


	@Override
	public void removeQuickSearchHandler(QuickSearchHanlder h) {

		header.getSearchField().getQuickSearchHandlers().remove(h);
	}


	@Override
	public void setUnreadNotificationRequest(long unread) {
		header.setNotificationRequestCount(unread);
		
	}


	@Override
	public void showShortNotification(LiveNotificationMessage msg) {
		
		Notification n = msg.getNotification();
		String text = "You have received a new notification";
		if (n instanceof QuickNotification)
		switch (((QuickNotification) n).getType()){
			case FRIENDSHIP_CANCELED: text = language.NotificationQuickFriendshipMessage()+" \""+((QuickNotification)n).getOptionalMessage()+"\"";
			case COLLABORATEABLE_DELETED: text = (language.NotificationQuickCollaborationDeletedMessagePart1()+" \""+((QuickNotification)n).getOptionalMessage()+"\""+language.NotificationQuickCollaborationDeletedMessagePart2());
			case EXPERIMENT_DELETED: text = (language.NotificationQuickExperimentDeletedMessagePart1()+" \""+((QuickNotification)n).getOptionalMessage()+"\""+language.NotificationQuickExperimentDeletedMessagePart2());
		}
		
		else
		if (n instanceof ExperimentRequestNotification)
		{
			boolean accept = ((ExperimentRequestNotification) n).isAccepted();
			text = (((ExperimentRequestNotification) n).getDisplayName()+" "+(accept?language.NotificationAccepted():language.NotificationRejected())+ language.NotificationExperimentInvitation()+"\""+((ExperimentRequestNotification)n).getExperimentName()+"\"");
		}
		else
		if (n instanceof CollaborationRequestNotification)
		{
			boolean accept = ((CollaborationRequestNotification) n).isAccepted();
			text=(((CollaborationRequestNotification) n).getDisplayName()+" "+(accept?language.NotificationAccepted():language.NotificationRejected())+ language.NotificationCollaborationInvitation()+"\""+((CollaborationRequestNotification)n).getCollaborationName()+"\"");
		}
		if (n instanceof FriendshipRequestNotification)
		{
			boolean accept = ((FriendshipRequestNotification) n).isAccepted();
			text = (((FriendshipRequestNotification) n).getDisplayName()+" "+(accept?language.NotificationAccepted():language.NotificationRejected())+ language.NotificationFriendshipInvitation());
		}

		
		NotificationManager.getInstance().showShortInfoNotification(new ShortInfoNotification(text));
		
	}


	@Override
	public void showShortRequestNotification(LiveRequestMessage msg) {
		
		String text = "You have received a new request";

		Request r = msg.getRequest();
		
		
		if (r instanceof AdminExperimentRequest)
			text = (language.RequestExperimentInvitedMessage()+" \""+((AdminExperimentRequest)r).getExperimentName()+"\"");
		
		else
		if (r instanceof CollaborationRequest)
			text = (language.RequestCollaborationInvitedMessage()+" \""+((CollaborationRequest)r).getName()+"\"");
		
		else
		if(r instanceof FriendshipRequest)
			text = (r.getRequesterDisplayName()+" "+language.RequestFriendshipInvitedMessage());
		
		NotificationManager.getInstance().showShortInfoNotification(new ShortInfoNotification(text));
	}



}
