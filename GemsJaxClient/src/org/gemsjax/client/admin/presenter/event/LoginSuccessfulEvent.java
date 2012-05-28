package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.LoginSuccessfulHandler;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This Event is used to inform the {@link LoginSuccessfulHandler} that something happens
 * @author Hannes Dorfmann
 *
 */
public class LoginSuccessfulEvent extends GwtEvent<LoginSuccessfulHandler>{
	
	
	public static Type<LoginSuccessfulHandler>TYPE = new Type<LoginSuccessfulHandler>();
	
	private User authenticatedUser;
	private long unreadNotificationRequestCount;
	

	/**
	 * 
	 * @param authenticatedUser The {@link RegisteredUser} that is authenticated from now on
	 */
	public LoginSuccessfulEvent(User authenticatedUser, long unreadNotificationRequestCount )
	{
		this.authenticatedUser = authenticatedUser;
		this.unreadNotificationRequestCount= unreadNotificationRequestCount;
	}
	
	/**
	 * Get the authenticated user
	 * @return {@link RegisteredUser}
	 */
	public User getAuthenticatedUser()
	{
		return authenticatedUser;
	}
	
	

	@Override
	public Type<LoginSuccessfulHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginSuccessfulHandler handler) {
		handler.onLoginSuccessful(this);
	}

	public long getUnreadNotificationRequestCount() {
		return unreadNotificationRequestCount;
	}

}
