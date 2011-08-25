package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.PlaceEvent;
import org.gemsjax.client.canvas.handler.PlaceHandler;

/**
 * Placeable allows {@link AnchorPoint}s to be Placed somewhere over the canvas.
 * The difference between {@link Placeable} and {@link Moveable} is, that a {@link Placeable} can be moved and placed freely on the canvas, 
 * but can also (and thats the different)
 * only be placed in a certain range, called {@link AnchorPointDestination} ({@link AnchorPointDestination#canAnchorPointBePlacedAt(double, double)}).
 * 
 * You can also construct complex object, for example {@link MetaConnectionDrawable}, which contains many {@link Placeable} objects that can be placed
 * and interact without the parent object ({@link HasPlaceable}) itself.
 * @author Hannes Dorfmann
 *
 */
public interface Placeable {

	public void addPlaceHandler(PlaceHandler handler);
	public void removePlaceHandler(PlaceHandler handler);
	
	/**
	 * Fire a {@link PlaceEvent}
	 * @param event
	 * @return true if at least one {@link PlaceHandler} is registered to this {@link Placeable} and has received this event. Otherwise false
	 */
	boolean firePlaceEvent(PlaceEvent event);
	
	public void setX(double x);
	public void setY(double y);
	
	
	public boolean isSelected();
	public void setSelected(boolean selected);
	
}
