package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.admin.presenter.handler.LoginRequiredHandler;
import org.gemsjax.client.admin.view.LoginView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;

/**
 * {@link LoginRequiredEvent}s are sent through the {@link EventBus} from {@link Presenter} to {@link Presenter}
 * to let the {@link LoginView} appear and to let the user authenticate himself.
 * @author Hannes Dorfmann
 *
 */
public class LoginRequiredEvent extends GwtEvent<LoginRequiredHandler> {

	
	public static Type<LoginRequiredHandler> TYPE = new Type<LoginRequiredHandler>();
	
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginRequiredHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginRequiredHandler handler) {
		handler.onLoginRequired(this);
	}

}
