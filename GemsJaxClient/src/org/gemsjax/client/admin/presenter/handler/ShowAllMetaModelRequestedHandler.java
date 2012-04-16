package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.event.ShowAllMetaModelsRequestedEvent;

import com.google.gwt.event.shared.EventHandler;

public interface ShowAllMetaModelRequestedHandler extends EventHandler{

	public abstract void onShowAllMetaModelsRequested(ShowAllMetaModelsRequestedEvent e);
	
}
