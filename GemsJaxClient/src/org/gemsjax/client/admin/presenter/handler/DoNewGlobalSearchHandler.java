package org.gemsjax.client.admin.presenter.handler;

import org.gemsjax.client.admin.presenter.event.DoNewGlobalSearchEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * The handler for {@link DoNewGlobalSearchEvent}
 * @author Hannes Dorfmann
 *
 */
public interface DoNewGlobalSearchHandler extends EventHandler{
	
	public void onDoNewGlobalSerch(DoNewGlobalSearchEvent event);
}
