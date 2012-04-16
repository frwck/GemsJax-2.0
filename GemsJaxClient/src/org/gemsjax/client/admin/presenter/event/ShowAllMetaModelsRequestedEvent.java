package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.ShowAllMetaModelRequestedHandler;

import com.google.gwt.event.shared.GwtEvent;

public class ShowAllMetaModelsRequestedEvent extends GwtEvent<ShowAllMetaModelRequestedHandler>{

	public static Type<ShowAllMetaModelRequestedHandler>TYPE = new Type<ShowAllMetaModelRequestedHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowAllMetaModelRequestedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowAllMetaModelRequestedHandler handler) {
		handler.onShowAllMetaModelsRequested(this);
	}

}
