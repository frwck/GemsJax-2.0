package org.gemsjax.client.admin.handler;

import org.gemsjax.client.admin.event.LoadingAnimationEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * The LoadingEventHandler is the Handler for {@link LoadingAnimationEvent}
 * @author Hannes Dorfmann
 *
 */
public interface LoadingAnimationEventHandler extends EventHandler {
	
	/**
	 * Receive a {@link LoadingAnimationEvent}
	 * @param event
	 */
	public void onLoadingAnimationEvent(LoadingAnimationEvent event);

}
