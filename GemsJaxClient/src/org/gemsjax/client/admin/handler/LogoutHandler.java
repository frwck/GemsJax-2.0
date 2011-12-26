package org.gemsjax.client.admin.handler;

import org.gemsjax.client.admin.presenter.event.LogoutEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * This is the handler to handle {@link LogoutEvent}s
 * @author Hannes Dorfmann
 *
 */
public interface LogoutHandler extends EventHandler{
	
	public void onLogout(LogoutEvent event);

}
