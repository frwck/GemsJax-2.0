package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.MouseOverEvent;
import org.gemsjax.client.canvas.handler.MouseOverHandler;

/**
 * Add the ability to react to mouse over events
 * @author Hannes Dorfmann
 *
 */
public interface MouseOverable {
	
	public void addMouseOverHandler(MouseOverHandler handler);
	public void removeMouseOverHandler(MouseOverHandler handler);
	

	/**
	 * Fire a MouseOverEvent
	 * @param event
	 * @return true if at least one MoveHandler is registered and has received this event. Otherwise false (if there is no MoveHandler registered).
	 */
	public boolean fireMouseOverEvent(MouseOverEvent event);

}
