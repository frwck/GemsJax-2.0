package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.RegistrationPresenter;
import org.gemsjax.client.admin.presenter.event.ShowRegistrationEvent;
import org.gemsjax.client.admin.view.RegistrationView;

import com.google.gwt.event.shared.EventHandler;

/**
 * Implemented by the {@link RegistrationPresenter} to receive {@link ShowRegistrationEvent}s to display the {@link RegistrationView}
 * @author Hannes Dorfmann
 *
 */
public interface ShowRegistrationHandler extends EventHandler {

	public abstract void onShowRegistrationEvent(ShowRegistrationEvent event);
	
}
