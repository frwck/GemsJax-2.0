package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.CreateNewMetaModelRequiredHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CreateNewMetaModelRequiredEvent extends GwtEvent<CreateNewMetaModelRequiredHandler> {
	public static Type<CreateNewMetaModelRequiredHandler> TYPE = new Type<CreateNewMetaModelRequiredHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CreateNewMetaModelRequiredHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateNewMetaModelRequiredHandler handler) {
		handler.onCreateNewMetaModelRequired();
	}

}
