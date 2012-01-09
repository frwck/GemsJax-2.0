package org.gemsjax.client.admin.view;

import org.gemsjax.shared.communication.message.system.LogoutMessage.LogoutReason;

/**
 * The view, that displayes the logout message 
 * @author Hannes Dorfmann
 *
 */
public interface LogoutView {

	/**
	 * Display the view with the passed {@link LogoutReason}
	 * @param reason
	 */
	public void show(LogoutReason reason);
}
