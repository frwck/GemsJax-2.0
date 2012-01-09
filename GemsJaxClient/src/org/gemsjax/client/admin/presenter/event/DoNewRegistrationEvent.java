package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.DoNewRegistrationHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * A {@link DoNewRegistrationEvent} is handled by an {@link DoNewRegistrationHandler}.
 * 
 * @author Hannes Dorfmann
 *
 */
public class DoNewRegistrationEvent extends GwtEvent<DoNewRegistrationHandler>{
	
	
	public static Type<DoNewRegistrationHandler>TYPE = new Type<DoNewRegistrationHandler>();

	@Override
	protected void dispatch(DoNewRegistrationHandler handler) {
		handler.onShowRegistrationEvent(this);
	}

	@Override
	public Type<DoNewRegistrationHandler> getAssociatedType() {
		return TYPE;
	}

}
