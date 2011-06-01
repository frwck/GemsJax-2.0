package org.gemsjax.client.admin.notification;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VStack;

public class TipNotification extends Notification implements ResizeHandler{
	
	/**
	 * if the {@link NotificationPosition} is set to {@link NotificationPosition#BOTTOM_CENTERED}, {@link NotificationPosition#BOTTOM_LEFT} or 
	 * {@link NotificationPosition#BOTTOM_RIGHT} this space will be added from the bottom of the browser-window to the bottom of this notification
	 */
	private static final int bottomPositionSpacer = 10;
	
	private NotificationPosition position;
	
	
	public TipNotification(String title, String text, int width, int height, int timeToHide,  NotificationPosition position)
	{
		super();
		this.setStyleName("tipNotification");
		this.setWidth(width);
		this.setHeight(height);
		this.setMembersMargin(5);
		
		this.position = position;
		Window.addResizeHandler(this);
		
		Label titleLabel = new Label("<h1>"+title+"</h1>");
		titleLabel.setHeight(38);
		
		this.addMember(titleLabel);
		
		if (text!=null && !text.equals(""))
		{
			Label textLabel = new Label("<h2>"+title+"</h2>");
			textLabel.setHeight(25);
			this.addMember(textLabel);
		}
		
		calculatePosition(position);
		
		new Timer(){

			@Override
			public void run() {
				TipNotification.this.animateHide(AnimationEffect.FADE);
				
			}
			
		}.schedule(timeToHide);
		
		
		
	}
	
	
	private void calculatePosition(NotificationPosition position)
	{
		switch (position)
		{
			case BOTTOM_CENTERED:
				this.setPageLeft((Window.getClientWidth()-this.getWidth())/2);
				this.setPageTop((Window.getClientHeight()-this.getHeight()) -bottomPositionSpacer);
				
			break;
		}
		
	}


	@Override
	public void onResize(ResizeEvent event) {
		calculatePosition(position);
	}


	

}
