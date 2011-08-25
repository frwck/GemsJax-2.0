package org.gemsjax.client.canvas;

import com.google.gwt.canvas.dom.client.Context2d;

public interface AnchorPointDestination {
	
	

	/**
	 *  Highlight the destination where the AnchorPoint can be moved to in a graphical way on the canvas.
	 *  This method is called directly from the {@link AnchorPoint} if this {@link AnchorPointDestination} is the currently set 
	 *  destination of the {@link AnchorPoint} 
	 *  @param context
	 */
	public void highlightDestinationArea(Context2d context);
	
	/**
	 * This method is called when the mouse is over this object, but this object is not the currently set destination (or parent) of the 
	 * AnchorPoint which is already selected
	 * @param context
	 */
	public void highlightOnMouseOverDestinationArea(Context2d context);
	
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
