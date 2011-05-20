package org.gemsjax.client.canvas.handler;

import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.events.ResizeEvent;

/**
 * A Handler to handle resize events for {@link Drawable}s
 * @author Hannes Dorfmann
 *
 */
public interface ResizeHandler {
	
	/**
	 * Will be called from the {@link BufferedCanvas}, if the object has been resized and {@link #isResizeable()} == true
	 * @param event
	 */
	public void onResize(ResizeEvent event);
	
}
