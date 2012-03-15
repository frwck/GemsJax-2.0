package org.gemsjax.client.admin.notification;

import org.gemsjax.client.admin.notification.NotificationEvent.NotificationEventType;
import org.gemsjax.client.util.Console;

import sun.font.TextLabel;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;

public class ShortInfoNotification extends Notification implements MouseOutHandler, MouseOverHandler, ClickHandler{
	
	private Label closeButton;
	private Label textLabel;
	private Timer hideTimer;
	private final static int TEXT_FONT_SIZE=12;
	private final static int TEXT_PADDING = 3;
	public static final int WIDTH=170;
	
	
	
	private static int timeToHide = 2500;
	
	public ShortInfoNotification(String text){
		
		this.setStyleName("shortInfoNotification");
		closeButton = new Label("X");
		closeButton.addMouseOverHandler(this);
		closeButton.addMouseOutHandler(this);
		closeButton.addClickHandler(this);
		setNormalButtonStyle();
		this.setMembersMargin(0);
		
		textLabel = new Label("<h1>"+text+"<h1>");
		textLabel.setWidth(WIDTH+"px");
		textLabel.setAutoHeight();
		textLabel.setPadding(TEXT_PADDING);
		
		
		closeButton.setWidth(WIDTH+"px");
		closeButton.setHeight("10px");
		closeButton.setPadding(TEXT_PADDING);
		
		this.addMember(closeButton);
		this.addMember(textLabel);
		this.setHeight(calculateTextHeight(text) * TEXT_FONT_SIZE + 25);
		
		hideTimer = new Timer(){

			@Override
			public void run() {
				close();
			}
			
		};
		
		hideTimer.schedule(timeToHide);
		
	}

	
	public void close(){
		hideTimer.cancel(); // has only impact, if the timer is running
		animateHide(AnimationEffect.FADE, new AnimationCallback() {
			
			@Override
			public void execute(boolean earlyFinish) {
				fireNotificationEvent(new NotificationEvent(ShortInfoNotification.this, NotificationEventType.CLOSED));
			}
		});
		
	}
	
	private void setNormalButtonStyle()
	{
		closeButton.setStyleName("shortInfoNotificationCloseButton");
	}
	
	
	private void setHoverButtonStyle()
	{
		closeButton.setStyleName("shortInfoNotificationCloseButtonHover");
	}
	
	@Override
	public void onMouseOut(MouseOutEvent event) {
		setNormalButtonStyle();
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		setHoverButtonStyle();	
	}

	@Override
	public void onClick(ClickEvent event) {
		
		if (event.getSource() == closeButton)
			close();
	}

	
	
	private int calculateTextHeight(String text)
	{
		return text.length()*TEXT_FONT_SIZE / (WIDTH-2*TEXT_PADDING);
	}

	
	

}
