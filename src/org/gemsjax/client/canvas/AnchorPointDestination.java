package org.gemsjax.client.canvas;

import com.google.gwt.canvas.dom.client.Context2d;

public interface AnchorPointDestination {
	
	

	/**
	 *  Highlight the destination where the AnchorPoint can be moved to in a graphical way on the canvas
	 *  @param context
	 */
	public void highlightDestinationArea(Context2d context);
	
	/**
	 * Checks and Determine, wherever the {@link AnchorPoint} can be moved to
	 * @param x
	 * @param y
	 * @return true, if the Anchor can be  moved to this position, else false. Moved means in this case placed (for example by mouse release) at the position x/y
	 */
	public boolean canAnchorPointBePlacedAt(double x, double y);
	
	/**
	 * Get the x coordinate 
	 * @return
	 */
	public double getX();
	
	/**
	 * Get the y coordinate
	 * @return
	 */
	public double getY();

}
