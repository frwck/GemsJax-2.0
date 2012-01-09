package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.event.LoginSuccessfulEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * This is the Hander to handle {@link LoginSuccessfulEvent}'s
 * @author Hannes Dorfmann
 *
 */
public interface LoginSuccessfulHandler extends EventHandler{

	public void onLoginSuccessful(LoginSuccessfulEvent event);
}
