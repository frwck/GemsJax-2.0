package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.handler.ShowMetaModelRequiredHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ShowMetaModelRequiredEvent extends GwtEvent<ShowMetaModelRequiredHandler>{

	public static Type<ShowMetaModelRequiredHandler>TYPE = new Type<ShowMetaModelRequiredHandler>();
	
	private int metaModelId;
	
	public ShowMetaModelRequiredEvent(int metaModelId){
		this.metaModelId = metaModelId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowMetaModelRequiredHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowMetaModelRequiredHandler handler) {
		handler.onShowMetaModelRequired(metaModelId);
	}
}
