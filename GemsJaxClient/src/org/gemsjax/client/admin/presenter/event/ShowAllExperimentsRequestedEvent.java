package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.ShowAllExperimentsRequestedHandler;

import com.google.gwt.event.shared.GwtEvent;

public class ShowAllExperimentsRequestedEvent extends GwtEvent<ShowAllExperimentsRequestedHandler> {

	public static com.google.gwt.event.shared.GwtEvent.Type<ShowAllExperimentsRequestedHandler> TYPE = new com.google.gwt.event.shared.GwtEvent.Type<ShowAllExperimentsRequestedHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowAllExperimentsRequestedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowAllExperimentsRequestedHandler handler) {
		handler.onShowAllExperimentsRequested();
	}

}
