package org.gemsjax.client.desktopenviroment.handler;

import org.gemsjax.client.desktopenviroment.events.GWindowEvent;


/**
 * This event handler handles with {@link GWindowEvent}.
 * @author Hannes Dorfmann
 *
 */
public interface GWindowEventHandler {
	
	/**
	 * Receive a {@link GWindowEvent}
	 * @param event
	 */
	public void onGWindowEvent(GWindowEvent event);

}
