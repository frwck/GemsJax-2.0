package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.event.LoginEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * This is the Hander to handle {@link LoginEvent}'s
 * @author Hannes Dorfmann
 *
 */
public interface LoginHandler extends EventHandler{

	public void onLogin(LoginEvent event);
}
