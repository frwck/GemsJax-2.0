package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.event.LogoutRequiredEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * This is the handler to handle {@link LogoutRequiredEvent}s
 * @author Hannes Dorfmann
 *
 */
public interface LogoutRequiredHandler extends EventHandler{
	
	public void onLogoutRequired(LogoutRequiredEvent event);

}
