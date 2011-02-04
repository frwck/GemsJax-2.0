package org.gemsjax.client.adminui;

import org.apache.tools.ant.NoBannerLogger;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HStack;






/**
 * The UserBox is the GUI Component that displays the Main Menu, User Menu, Notifications Menu, search field, Account-Settings Menu 
 *<br />
 *Its a Singleton
 * @author Hannes Dorfmann
 *
 */
public class UserBox extends HStack{

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
	

	
	/**
	 * Singleton instance
	 */
	private static UserBox instance;
	
	
	private UserBox()
	{
		super();
		
		// notificationItem
		
		Img borderLeft = new Img("/images/userbox_left_border.png");
		Img borderRight = new Img("/images/userbox_right_border.png");
		
		borderRight.setWidth(7);
		borderRight.setHeight(37);
		
		borderLeft.setWidth(7);
		borderLeft.setHeight(37);
		
		
		UserBoxItem notificationItem = new UserBoxItem("<a href=\"#\">notifications</a>");
		
		this.addMember(borderLeft);
		this.addMember(new UserBoxItem("<a href=\"#\">welcome</a>"));
		this.addMember(new UserBoxItemSeparator());
		this.addMember(new UserBoxItem("<a href=\"#\">metamodels</a>"));
		this.addMember(new UserBoxItemSeparator());
		this.addMember(new UserBoxItem("<a href=\"#\">experiments</a>"));
		this.addMember(new UserBoxItemSeparator());
		this.addMember(notificationItem);
		this.addMember(new NotificationCountLabel());
		this.addMember(new UserBoxItemSeparator());
		this.addMember(new UserBoxItem("<a href=\"#\">settings</a>"));
		this.addMember(new UserBoxItemSeparator());
		this.addMember(new UserBoxItem("<a href=\"#\">logout</a>"));
		this.addMember(borderRight);
		
		
		// Styling

		this.setMargin(3);
		this.setMembersMargin(0);
		this.setHeight(37);
		this.setAlign(Alignment.RIGHT);
		
	}
	
	
	public static UserBox getInstance()
	{
		if (instance == null)
			instance = new UserBox();
		
		
		return instance;
	}
	
	
	
	
}
