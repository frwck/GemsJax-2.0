package org.gemsjax.client.admin.notification;

import org.gemsjax.client.admin.notification.NotificationEvent.NotificationEventType;
import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VStack;

public class TipNotification extends Notification implements ResizeHandler{
	
	/**
	 * if the {@link NotificationPosition} is set to {@link NotificationPosition#BOTTOM_CENTERED}, {@link NotificationPosition#BOTTOM_LEFT} or 
	 * {@link NotificationPosition#BOTTOM_RIGHT} this space will be added from the bottom of the browser-window to the bottom of this notification
	 */
	private static final int bottomPositionSpacer = 100;
	
	
	
	
	public TipNotification(String title, String text, int timeToHide,  NotificationPosition position)
	{
		super();
		this.setStyleName("tipNotification");
		
		this.setMembersMargin(5);
		
		
		
		setText(text);
		setTitle(title);
		setPosition(position);
		
		Window.addResizeHandler(this);
		
		if (title!=null && !title.isEmpty()){
			Label titleLabel = new Label("<h1>"+title+"</h1>");
			titleLabel.setHeight(38);
			
			this.addMember(titleLabel);
		}
		
		if (text!=null && !text.equals(""))
		{
			Label textLabel = new Label("<h2>"+text+"</h2>");
			textLabel.setHeight(25);
			this.addMember(textLabel);
		}
		
		calculateWidthAndPosition();
		
		new Timer(){

			@Override
			public void run() {
				TipNotification.this.animateHide(AnimationEffect.FADE);
				fireNotificationEvent(new NotificationEvent(TipNotification.this, NotificationEventType.CLOSED));
			}
			
		}.schedule(timeToHide);
		
		this.bringToFront();
		
	}
	
	private int calculateWidth(boolean title, String str)
	{
		if (str ==null || str.isEmpty())
			return 0;
		
		int width =0;
		if (title)
		{
			
			for (int i =0; i<str.length(); i++)
			{
				switch (str.charAt(i))
				{
					case 'W': width+=35; break;
					
					case 'Q':
					case 'C':
					case 'D':
					case 'B':
					case 'M':	
					case 'O':
					case '�': width+=26; break;
						 
					case 'S':
					case 'A':
					case 'K':
					case 'X': width+=24; break;
				
					
					case ' ':
					case 'I': width+=13; break;
					
					// Lower case
					case 'm':
					case 'w': width+=30;break;
					case 't': width+=13; break;
					case 'i': width+=7; break;
					case '-': width+=12;break;
					
					
					default: width+=21;
					
				}
			}
		}
		else // Calculate for an normal Tooltip text
			for (int i =0; i<str.length(); i++)
			{
				switch (str.charAt(i))
				{
					case 'W': width+=22; break;
					
					case 'Q':
					case 'C':
					case 'D':
					case 'B':
					case 'M':	
					case 'O':
					case '�': width+=18; break;
						 
					case 'S':
					case 'A':
					case 'K':
					case 'X': width+=15; break;
				
					
					case ' ':
					case 'I': width+=5; break;
					
					// Lower case
					case 'm':
					case 'w': width+=20;break;
					case 't': width+=8; break;
					case 'i': width+=7; break;
					case '-': width+=7;break;
					
					
					default: width+=13;
					
				}
			}
		
		
		return width;
		
	}
	
	
	private void calculateWidthAndPosition()
	{
		
		// calculate width and height for the given text
		// TODO find a better solution for width, in pure HTML if possible
		
		// If you change the AdminApplicationViewImpl.contentWidth field, you have to change the line below
		int maxWidth = Window.getClientWidth() * Integer.parseInt(AdminApplicationViewImpl.contentWidth.substring(0, AdminApplicationViewImpl.contentWidth.indexOf("%")) ) / 100  - 10;
		
		int titleWidth = calculateWidth(true, getTitle());
		
		
		int titleHeight = 27;
		int textHeight =0;
		
		int titleLines =0, textLines =0;
		int textWidth = 0;
		
		if (titleWidth> maxWidth)
		{
			titleLines = titleWidth/maxWidth +1 ;
			titleWidth = maxWidth;
			
			titleHeight = titleHeight * titleLines;
		}
		
		
		if (getText()!= null && getText().length()!=0 )
		{
			textWidth = calculateWidth(false, getText());
			textHeight = 22;
			if (textWidth>maxWidth)
			{
				textLines = textWidth / maxWidth + 1;
				textWidth = maxWidth;
				
				// A character has a height of 22 px
				textHeight = 22 * textLines;
			}
		}
		

		this.setWidth(titleWidth>textWidth? titleWidth : textWidth);
		this.setHeight(titleHeight+textHeight);
		
		
		switch (getNotificationPosition())
		{
			case BOTTOM_CENTERED:
				this.setPageLeft((Window.getClientWidth()-this.getWidth())/2);
				this.setPageTop((Window.getClientHeight()-this.getHeight()) -bottomPositionSpacer);
				
				break;
			
			case CENTER:
				this.setPageLeft((Window.getClientWidth()-this.getWidth())/2);
				this.setPageTop(Window.getClientHeight()/2-getHeight()/2);
				break;
		}
		
	}


	@Override
	public void onResize(ResizeEvent event) {
		calculateWidthAndPosition();
	}
	

}
