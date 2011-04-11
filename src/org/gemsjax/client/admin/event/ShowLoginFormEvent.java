package org.gemsjax.client.admin.event;

import org.gemsjax.client.admin.handler.ShowLoginFormEventHandler;

import com.google.gwt.event.shared.GwtEvent;

public class ShowLoginFormEvent extends GwtEvent<ShowLoginFormEventHandler>{

	public static Type<ShowLoginFormEventHandler> TYPE = new Type<ShowLoginFormEventHandler>();
	
	
	private String username;
	
	public ShowLoginFormEvent()
	{
		
	}
	
	public ShowLoginFormEvent(String username)
	{
		this.username = username;
	}
	
	
	public String getPreviousLoggedInUsername()
	{
		return username;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowLoginFormEventHandler> getAssociatedType() {
	
		return TYPE;
	}

	@Override
	protected void dispatch(ShowLoginFormEventHandler handler) {
		handler.onShowLoginForm(this);
	}
	
	
	
	
}
