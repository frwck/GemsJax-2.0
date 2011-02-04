package org.gemsjax.client.desktopenviroment.handler;

import org.gemsjax.client.desktopenviroment.WindowTask;
import org.gemsjax.client.desktopenviroment.events.WindowTaskEvent;

/**
 * This event handler receives {@link WindowTaskEvent}s  form the {@link WindowTask} via the method {@link #onWindowTaskEvent(WindowTaskEvent)}
 * @author Hannes Dorfmann
 *
 */
public interface WindowTaskEventHandler {

	/**
	 * Receive a {@link WindowTaskEvent} from the {@link WindowTask}
	 * @param event
	 */
	public void onWindowTaskEvent(WindowTaskEvent event);
}
