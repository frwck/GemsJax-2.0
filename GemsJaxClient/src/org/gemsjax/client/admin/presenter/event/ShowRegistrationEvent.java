package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.ShowRegistrationHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * A {@link ShowRegistrationEvent} is handled by an {@link ShowRegistrationHandler}.
 * 
 * @author Hannes Dorfmann
 *
 */
public class ShowRegistrationEvent extends GwtEvent<ShowRegistrationHandler>{
	
	
	public static Type<ShowRegistrationHandler>TYPE = new Type<ShowRegistrationHandler>();

	@Override
	protected void dispatch(ShowRegistrationHandler handler) {
		handler.onShowRegistrationEvent(this);
	}

	@Override
	public Type<ShowRegistrationHandler> getAssociatedType() {
		return TYPE;
	}

}
