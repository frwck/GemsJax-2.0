package org.gemsjax.client.admin.adminui;

import org.gemsjax.client.admin.UserLanguage;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.layout.HStack;






/**
 * The UserBox is the GUI Component that displays the Main Menu, User Menu, Notifications Menu, search field, Account-Settings Menu 
 *<br />
 * @author Hannes Dorfmann
 *
 */
public class UserBox extends HStack {

	/**
	 * A Item which is simply a GUI Component (a {@link Label}) and can contain html and text
	 * and has the CSS style "userbox-item" (defined in /css/GemsJax.css).
	 * @author Hannes Dorfmann
	 *
	 */
	private class UserBoxItem extends Label
	{
		/**
		 * Create a new UserBoxItem with the CSS Style userbox-item (defined in /css/GemsJax.css) and do some other styling things like Centering, Padding
		 */
		public UserBoxItem()
		{
			super();
			this.setStyleName("userbox-item");
			this.setAlign(Alignment.CENTER);
			this.setValign(VerticalAlignment.CENTER);
			
		}
		
		
		/**
		 * Create a new UserBoxItem with the CSS Style userbox-item (defined in /css/GemsJax.css) and do some other styling things like Centering
		 */
		public UserBoxItem(String contents)
		{
			this();
			this.setContents(contents);
			this.setText(contents);
		}
		
		/**
		 * Set the displayable text
		 * @param text
		 */
		public void setText(String text)
		{
			this.setContents("<a href=\"#\">"+text+"</a>");
		}
		
		
		
	}
	
	
	/**
	 * This is a Simple GUI Object (a Image) that displays a Separator, which is set between each {@link UserBoxItem}
	 * @author Hannes Dorfmann
	 *
	 */
	private class UserBoxItemSeparator extends Img
	{
		public UserBoxItemSeparator()
		{
			super("/images/userbox_separator.png");
			this.setWidth(2);
			this.setHeight(37);
		}
	}
	
	
	/**
	 * This is a special {@link UserBoxItem} which displays the unread notifications
	 * @author Hannes Dorfmann
	 *
	 */
	
	private class NotificationCountLabel extends UserBoxItem{
		
		
		public NotificationCountLabel()
		{
			this.setAlign(Alignment.LEFT);
			setCount(212);
			
			
		}
		
		public void setCount(int count)
		{
			String content = "<table cellspacing=\"0\" cellpadding=\"0\" class=\"userbox-notifications-unread\"><tr>" +
			"<td class=\"userbox-notifications-unread-border-left\"></td>" +
			"<td class=\"userbox-notificatoins-unread-count\">"+count+"</td>" +
			"<td class=\"userbox-notifications-unread-border-right\"></td>" +
			"</tr></table>";
			this.setContents(content);
			this.setWidth((""+count).length()*9+10+10);
		}
	}
	

	
	
	private UserBoxItem notificationsItem;
	private UserBoxItem metaModelsItem;
	private UserBoxItem experimentsItem;
	private UserBoxItem settingsItem;
	private UserBoxItem logoutItem;
	private UserBoxItem dashBoardItem;
	private UserLanguage language;
	
	public UserBox(UserLanguage language)
	{
		super();
		this.language = language;
		// notificationItem
		
		Img borderLeft = new Img("/images/userbox_left_border.png");
		Img borderRight = new Img("/images/userbox_right_border.png");
		
		borderRight.setWidth(7);
		borderRight.setHeight(37);
		
		borderLeft.setWidth(7);
		borderLeft.setHeight(37);
		
		
		notificationsItem = new UserBoxItem(language.NotificationsMenuItem());
		metaModelsItem= new UserBoxItem(language.MetaModelsMenuItem());
		experimentsItem = new UserBoxItem(language.ExperimentsMenuItem());
		settingsItem = new UserBoxItem(language.SettingsMenuItem());
		logoutItem = new UserBoxItem(language.Logout());
		dashBoardItem = new UserBoxItem("<a href=\"#\">welcome</a>");
		
		this.addMember(borderLeft);
		//TODO display username
		this.addMember(dashBoardItem);
		
		this.addMember(new UserBoxItemSeparator());
		this.addMember(metaModelsItem);
		this.addMember(new UserBoxItemSeparator());
		this.addMember(experimentsItem);
		this.addMember(new UserBoxItemSeparator());
		this.addMember(notificationsItem);
		this.addMember(new NotificationCountLabel());
		this.addMember(new UserBoxItemSeparator());
		this.addMember(settingsItem);
		this.addMember(new UserBoxItemSeparator());
		this.addMember(logoutItem);
		this.addMember(borderRight);
		
		
		// Styling

		this.setMargin(3);
		this.setMembersMargin(0);
		this.setHeight(37);
		this.setAlign(Alignment.RIGHT);
		
	}
	
	
	
	public HasClickHandlers getDashBoardMenuItem()
	{
		return dashBoardItem;
	}
	
	
	public HasClickHandlers getNotificationsMenuItem()
	{
		return notificationsItem;
	}
	
	
	public HasClickHandlers getMetaModelsItem()
	{
		return metaModelsItem;
	}
	
	
	public HasClickHandlers getExpetimetsItem()
	{
		return experimentsItem;
	}
	
	public HasClickHandlers getSettingsItem()
	{
		return settingsItem;
	}
	
	
	public HasClickHandlers getLogoutItem()
	{
		return logoutItem;
	}
	
	
	
}
