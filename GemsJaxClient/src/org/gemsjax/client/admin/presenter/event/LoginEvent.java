package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.handler.LoginHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This Event is used to inform the {@link LoginHandler} that something happens
 * @author Hannes Dorfmann
 *
 */
public class LoginEvent extends GwtEvent<LoginHandler>{
	
	public enum LoginEventType
	{
		/**
		 * Login was OK, successful authenticated
		 */
		SUCCESSFUL,
		
		/**
		 * Login failed
		 */
		FAIL
	}
	public static Type<LoginHandler>TYPE = new Type<LoginHandler>();
	
	private LoginEventType eventType;
	/**
	 * The username with which one the login was successfull or has failed
	 */
	private String username;
	
	/**
	 * 
	 * @param username The username with which one the login was successfull or has failed
	 * @param type {@link LoginEventType}
	 */
	public LoginEvent(String username, LoginEventType type )
	{
		this.username =username;
		this.eventType = type;
	}
	
	/**
	 * Check if the login was successful
	 * @return true if login was successful, else false
	 */
	public boolean wasSuccessful()
	{
		return eventType==LoginEventType.SUCCESSFUL && username!=null && !username.equals("");
	}
	
	

	@Override
	public Type<LoginHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginHandler handler) {
		handler.onLogin(this);
	}



	public String getUsername() {
		return username;
	}



	public LoginEventType getEventType() {
		return eventType;
	}
	
	

}
