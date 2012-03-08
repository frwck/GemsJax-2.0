package org.gemsjax.client.module.handler;

import org.gemsjax.client.module.AuthenticationModule;
import org.gemsjax.shared.communication.message.system.LogoutMessage.LogoutReason;
import org.gemsjax.shared.user.RegisteredUser;


/**
 * A handler to get updates from a {@link AuthenticationModule}l
 * @author Hannes Dorfmann
 *
 */
public interface AuthenticationModuleHandler {
	
	public void onLogoutReceived(LogoutReason reason);

	/**
	 * Is called, if the authentication was successful and the user no loged in
	 */
	public void onLoginSuccessful(RegisteredUser authenticatedUser, long unreadNotificationRequest);
	
	/**
	 * Is called, if the last submited authentication has failed
	 */
	public void onLoginFailed();
	
	/**
	 * Called, if an error occurres while parsing
	 * @param e
	 */
	public void onParseError(Exception e);

}
