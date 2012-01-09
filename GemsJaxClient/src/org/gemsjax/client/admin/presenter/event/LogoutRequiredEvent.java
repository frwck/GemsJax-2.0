package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.LogoutRequiredHandler;

import com.google.gwt.event.shared.GwtEvent;
/**
 * 
 * This is the LogoutEvent.
 * This event can be thrown if the user has clicked to the logout button, 
 * or the server has lost the connection to the client and vice versa.
 * @author Hannes Dorfmann
 *
 */
public class LogoutRequiredEvent extends GwtEvent<LogoutRequiredHandler>{

	private String lastLogedInUsername;
	
	public static Type<LogoutRequiredHandler> TYPE = new Type<LogoutRequiredHandler>();
	
	/**
	 * Create a new {@link LogoutRequiredEvent}
	 * @param lastLogedInUsername The username of the user which was logged in as last from this client
	 */
	public LogoutRequiredEvent(String lastLogedInUsername)
	{
		this.lastLogedInUsername = lastLogedInUsername;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LogoutRequiredHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LogoutRequiredHandler handler) {
		handler.onLogoutRequired(this);
	}

	
	public String getLastLogedInUsername() {
		return lastLogedInUsername;
	}


}
