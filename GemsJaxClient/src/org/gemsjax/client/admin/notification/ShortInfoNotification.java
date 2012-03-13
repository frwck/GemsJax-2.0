package org.gemsjax.client.admin.notification;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;

public class ShortInfoNotification extends Notification implements MouseOutHandler, MouseOverHandler, ClickHandler{
	
	private Label closeButton;
	
	public ShortInfoNotification(String text){
		
		this.setStyleName("shortInfoNotification");
		closeButton = new Label("X");
		closeButton.addMouseOverHandler(this);
		closeButton.addMouseOutHandler(this);
		closeButton.addClickHandler(this);
		setNormalButtonStyle();
		
		
		this.addMember(closeButton);
		this.addMember(new Label("<h1>"+text+"<h1>"));
		
		this.setPageLeft(100);
		this.setPageTop(100);
		
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
		// TODO Auto-generated method stub
		
	}


	
	

}
