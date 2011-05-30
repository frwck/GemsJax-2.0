package org.gemsjax.client.admin.notification;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VStack;

public class TipNotification extends Notification{
	
	
	public TipNotification(String title, String text, int width, int height, int timeToHide,  NotificationPosition position)
	{
		super();
		this.setStyleName("tipNotification");
		this.setWidth(width);
		this.setHeight(height);
		this.setMembersMargin(5);
	
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
				
				// TODO calculate the right position
				this.setPageLeft(400);
				this.setPageTop(500);
				
			break;
		}
		
	}

}
