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
	
	void addMoveHandler(MoveHandler handler);
	void removeHandler(MoveHandler handler);
	void fireMoveEvent(MoveEvent event);

}
