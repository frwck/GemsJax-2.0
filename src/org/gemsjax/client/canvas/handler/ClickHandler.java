package org.gemsjax.client.canvas.handler;

import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.events.ClickEvent;

/**
 * A handler to handle mouse clicks on {@link Drawable}s
 * @author Hannes Dorfmann
 *
 */
public interface ClickHandler {
	
	public void onClick(ClickEvent event);

}
