package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.handler.LogoutHandler;

import com.google.gwt.event.shared.GwtEvent;
/**
 * 
 * This is the LogoutEvent.
 * This event can be thrown if the user has clicked to the logout button, 
 * or the server has lost the connection to the client and vice versa.
 * @author Hannes Dorfmann
 *
 */
public class LogoutEvent extends GwtEvent<LogoutHandler>{

	public enum LogoutReason
	{
		/**
		 * The connection to the server was lost
		 */
		CONNECTION_LOST,
		/**
		 * The user has finished his session and clicked to the logout button
		 */
		USER_LOGOUT,
		/**
		 * Timeout durring the server-client connection
		 */
		TIMEOUT
	}
	
	
	private LogoutReason reason;
	private String lastLogedInUsername;
	
	public static Type<LogoutHandler> TYPE = new Type<LogoutHandler>();
	
	/**
	 * Create a new LogoutEvent
	 * @param reason
	 * @param lastLogedInUsername The username of the user which was logged in as last from this client
	 */
	public LogoutEvent(LogoutReason reason, String lastLogedInUsername)
	{
		this.reason = reason;
		this.lastLogedInUsername = lastLogedInUsername;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LogoutHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LogoutHandler handler) {
		handler.onLogout(this);
	}

	
	public String getLastLogedInUsername() {
		return lastLogedInUsername;
	}


	public LogoutReason getReason() {
		return reason;
	}

	

}
