package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.MouseOutEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.MouseOutHandler;

/**
 * Add the ability to react to mouse out events. That means, that first the mouse was on the object, but than the mouse has been
 * moved outside of the object. In other words:
 * A {@link MouseOutEvent} event is sent to an {@link MouseOutHandler} when the mouse pointer leaves the object
 * @author Hannes Dorfmann
 *
 */
public interface MouseOutable {
	
	public void addMouseOutHandler(MouseOutHandler handler);
	public void removeMouseOutHandler(MouseOutHandler handler);
	
	/**
	 * Fire a {@link MouseOutEvent}
	 * @param event
	 * @return true if at least one handler is registered and has received this event. Otherwise false (if there is no handler registered).
	 */
	public boolean fireMouseOutEvent(MouseOutEvent event);

}
