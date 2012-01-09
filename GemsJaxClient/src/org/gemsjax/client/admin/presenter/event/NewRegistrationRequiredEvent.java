package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.NewRegistrationRequiredHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * A {@link NewRegistrationRequiredEvent} is handled by an {@link NewRegistrationRequiredHandler}.
 * 
 * @author Hannes Dorfmann
 *
 */
public class NewRegistrationRequiredEvent extends GwtEvent<NewRegistrationRequiredHandler>{
	
	
	public static Type<NewRegistrationRequiredHandler>TYPE = new Type<NewRegistrationRequiredHandler>();

	@Override
	protected void dispatch(NewRegistrationRequiredHandler handler) {
		handler.onShowRegistrationEvent(this);
	}

	@Override
	public Type<NewRegistrationRequiredHandler> getAssociatedType() {
		return TYPE;
	}

}
