package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.shared.AnchorPoint;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This is a special {@link Anchor} that can be docked on {@link Drawable}s
 * which implements the interface {@link PlaceableDestination}.
 * So a {@link DockableAnchor} can only be placed at the position,
 * where the {@link PlaceableDestination#canPlaceableBePlacedAt(double, double)} returns a valid Point and not null.
 * 
 * <br/><br/>
 * <b>NOTICE:</b> The coordinates {@link Anchor#x} and {@link Anchor#y} are used as relative coordinate to the {@link #destination} ({@link PlaceableDestination#getX()}, @link PlaceableDestination#getY()} ).
 * This allows an easy way to handle {@link MoveEvent}s of the {@link PlaceableDestination}, so that there is nothing special to do to adjust the {@link DockableAnchor}s coordinates,
 * since the method {@link #getX()} and {@link #getY()} calculates dynamically the absolute coordinates of this {@link DockableAnchor}. 
 * @author Hannes Dorfmann
 *
 */
public class DockableAnchor extends Anchor{

	private PlaceableDestination destination;
	
	public DockableAnchor(AnchorPoint point, PlaceableDestination destination) {
		super(point);
		this.destination = destination;
	}
	
	
	@Override
	public double getX() {
		return destination.getX() + x;
	}

	@Override
	public double getY() {
		return destination.getY() + y;
	}

	@Override
	public void setX(double x) {
		this.x = x - destination.getX();
	}

	@Override
	public void setY(double y) {
		this.y = y - destination.getY();
	}
	
	@Override
	public PlaceableDestination getPlaceableDestination() {
		return destination;
	}
	
	/**
	 * Set the destination
	 * @param destination
	 */
	public void setPlaceableDestination(PlaceableDestination destination) {
		this.destination = destination;
	}
	
	@Override
	public void draw(Context2d context)
	{
		if (destination!=null && isSelected())
			destination.highlightDestinationArea(context);
		
		super.draw(context);
	}
	
	/**
	 * Get the relative x coordinate, that is used internally to calculate 
	 * the absolute coordinate that can be accessed via {@link #getX()}
	 * @return
	 */
	public double getRelativeX()
	{
		return x;
	}
	
	/**
	 * Get the relative y coordinate, that is used internally to calculate 
	 * the absolute coordinate that can be accessed via {@link #getY()}
	 * @return
	 */
	public double getRelativeY()
	{
		return y;
	}
	
	/**
	 * @see #getRelativeX()
	 * @param x
	 */
	public void setRelativeX(double x)
	{
		this.x  = x;
	}
	
	/**
	 * @see #getRelativeY()
	 * @param y
	 */
	public void setRelativeY(double y)
	{
		this.y = y;
	}

}
