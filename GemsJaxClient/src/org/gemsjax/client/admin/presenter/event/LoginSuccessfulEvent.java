package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.LoginSuccessfulHandler;
import org.gemsjax.shared.user.RegisteredUser;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This Event is used to inform the {@link LoginSuccessfulHandler} that something happens
 * @author Hannes Dorfmann
 *
 */
public class LoginSuccessfulEvent extends GwtEvent<LoginSuccessfulHandler>{
	
	
	public static Type<LoginSuccessfulHandler>TYPE = new Type<LoginSuccessfulHandler>();
	
	private RegisteredUser authenticatedUser;
	

	/**
	 * 
	 * @param authenticatedUser The {@link RegisteredUser} that is authenticated from now on
	 */
	public LoginSuccessfulEvent(RegisteredUser authenticatedUser )
	{
		this.authenticatedUser = authenticatedUser;
	}
	
	/**
	 * Get the authenticated user
	 * @return {@link RegisteredUser}
	 */
	public RegisteredUser getAuthenticatedUser()
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


}
