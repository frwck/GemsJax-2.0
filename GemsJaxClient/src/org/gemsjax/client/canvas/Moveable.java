package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.handler.MoveHandler;

/**
 * This interface makes a Drawable moveable. That means, that the {@link Drawable} element can be moved freely on the canvas
 * by firing {@link MoveEvent}s to inform the {@link MoveHandler}s.
 * @author Hannes Dorfmann
 *
 */
public interface Moveable {
	
	public void addMoveHandler(MoveHandler handler);
	public void removeMoveHandler(MoveHandler handler);
	
	/**
	 * Fire a MoveEvent
	 * @param event
	 * @return true if at least one MoveHandler is registered and has received this event. Otherwise false (if there is no MoveHandler registered).
	 */
	public boolean fireMoveEvent(MoveEvent event);

	
	public double getX();
	
	public double getY();
	
	public void setX(double x);
	
	public void setY(double y);
}
