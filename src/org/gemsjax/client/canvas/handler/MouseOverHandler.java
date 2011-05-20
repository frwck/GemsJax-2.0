package org.gemsjax.client.canvas.handler;

import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.Drawable;

/**
 * A Handler to handle Events, when the mouse is over the {@link Drawable} in the {@link BufferedCanvas}.
 * @author Hannes Dorfmann
 *
 */
public interface MouseOverHandler {
	
	
	public void onMouseOver(double x, double y);

}
