package org.gemsjax.client.canvas;

import java.util.List;

import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.shared.Point;

import com.google.gwt.canvas.dom.client.Context2d;

public interface PlaceableDestination {
	
	

	/**
	 *  Highlight the destination where the AnchorPoint can be moved to in a graphical way on the canvas.
	 *  This method is called directly from the {@link Anchor} if this {@link PlaceableDestination} is the currently set 
	 *  destination of the {@link Anchor} 
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
	 * Checks and determine, wherever the {@link Anchor} can be placed to.
	 * Internal there is a offset, so the user dont need to place the {@link Anchor} exactly on the correct position.
	 * @param x
	 * @param y
	 * @return null, if the Pointer can not be placed there, or a correct {@link Point} calculated by respecting a offset.
	 * 
	 */
	public Point canPlaceableBePlacedAt(double x, double y);
	
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
	
	/**
	 * Use this method to add an {@link Anchor} to the {@link #getDockedAnchors()} list
	 */
	public void dockAnchor(Anchor a);
	
	/**
	 * Use this method to remove an {@link Anchor} from the {@link #getDockedAnchors()} list
	 */
	public void undockAnchor(Anchor a);
	
	/**
	 * Get a list with all docked {@link Anchor}.
	 * This list is important to determine which {@link Anchor}s must be replaced after {@link ResizeEvent}s etc. 
	 * @return
	 */
	public List<Anchor> getDockedAnchors();

}
