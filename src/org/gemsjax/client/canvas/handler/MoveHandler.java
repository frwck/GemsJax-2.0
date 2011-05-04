package org.gemsjax.client.canvas.handler;

import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.Drawable;

/**
 * A Handler to hande when the {@link Drawable} has been moved on the {@link BufferedCanvas}
 * @author Hannes Dorfmann
 *
 */
public interface MoveHandler {
	
	
	/**
	 *  Will be called from the {@link BufferedCanvas}, if the object has been moved and {@link #isMoveable()} == true
	 * @param newX
	 * @param newY
	 */
	public void onMove(double newX, double newY);
}
