package org.gemsjax.client.module.handler;

import org.gemsjax.client.module.FriendsModule;


/**
 * A handler for the {@link FriendsModule}
 * @author Hannes Dorfmann
 *
 */
public interface FriendsModuleHandler {
	
	/**
	 * Called, if anything with the friends has been updated
	 */
	public void onFriendsUpdate();
	
	/**
	 * Called if an authentication error has occurred
	 */
	public void onAuthenticationError();
	
	/**
	 * Called if an unexpected error has occurred
	 */
	public void onUnexpectedError();

}
