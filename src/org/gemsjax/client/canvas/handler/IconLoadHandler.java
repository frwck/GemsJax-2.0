package org.gemsjax.client.canvas.handler;

import org.gemsjax.client.canvas.events.IconLoadEvent;

/**
 * Used to handle {@link IconLoadEvent}s, for loading icons, which are displayed on the canvas
 * @author Hannes Dorfmann
 *
 */
public interface IconLoadHandler {

	/**
	 * To Inform, when a Icon has been loaded completely
	 * @param e
	 */
	public void onIconLoaded(IconLoadEvent e);
}
