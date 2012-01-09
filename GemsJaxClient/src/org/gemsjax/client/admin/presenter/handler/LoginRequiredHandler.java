package org.gemsjax.client.admin.presenter.handler;
import org.gemsjax.client.admin.presenter.event.LoginRequiredEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * The Handler for {@link LoginRequiredEvent}s
 * @author Hannes Dorfmann
 *
 */
public interface LoginRequiredHandler extends EventHandler{

	/**
	 * Called to dispatch {@link LoginRequiredEvent}s
	 * @param event
	 */
	public abstract void onLoginRequired(LoginRequiredEvent event);
}
