package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.handler.PlaceHandler;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.client.canvas.events.PlaceEvent;
import com.google.gwt.canvas.dom.client.Context2d;


/**
 * {@link Anchor} represents a {@link AnchorPoint} in a graphical way on the {@link MetaModelCanvas} and {@link ModelCanvas}.
 * A Anchor is used to draw "agile" connection between {@link Drawable} objects like {@link MetaClassDrawable} and {@link MetaConnectionDrawable}.
 * "Agile" connection means, that the user can set with the mouse the points (with {@link Anchor}s) where the line of the connection goes by.
 * 
 * <b> NOTICE:</b> There exists a subclass {@link DockableAnchor} which are special {@link Anchor}s that can be docked on {@link Drawable}s
 * which implements the interface {@link PlaceableDestination}.
 * @author Hannes Dorfmann
 *
 */
public class Anchor implements Placeable{
	
	/**
	 * The current x coordinate.
	 */
	protected double x;
	/**
	 * The current y coordinate.
	 * @see #point
	 */
	protected double y;
	private double width = 6;
	private double height = 6;
	private String borderColor = "2A4596";
	private String backgroundColor = "F7F3DC";
	private String selectedBackgroundColor = "FAD816";
	private String canBePlacedBackgroundColor = "22CF00";
	private double borderWeight = 1; 
	
	
	private List<PlaceHandler> placeHandlers;
	
	
	/**
	 * The {@link AnchorPoint}, that is displayed with this {@link Anchor}.
	 * <b>Note</b> The Point's coordinates itself are only set, when the Mouse was released via the corresponding presenter.
	 * {@link #x} and {@link #y} are the current coordinate, which are used to display the object and animations (for example move the AnchorPoint with the mouse),
	 * so {@link #x} and {@link #y} are set permanently, but the {@link #point} itself is only set when the 
	 * {@link MoveEvent} with the {@link MoveEventType#MOVE_FINISHED} is received (in the Presenter)
	 */
	private AnchorPoint anchorPoint;
	
	private boolean selected;
	private boolean canBePlaced;
	
	/**
	 * 
	 * @param point
	 * @param destination If destination == null than this AnchorPoint can be placed freely on the canvas, otherwise it can be only placed where {@link PlaceableDestination#canPlaceableBePlacedAt(double, double)} == true.
	 * That is the case, if you want to create an {@link Anchor} for the source (or target) Drawable.
	 * That also indicates, that the {@link #x} and {@link #y} coordinates are relative coordinates according to the source (or targets) current coordinates which can be computed to 
	 * absolute coordinates via {@link PlaceableDestination#getX()} + #x and {@link PlaceableDestination#getY()} + {@link #y}
	 */
	public Anchor(AnchorPoint point)
	{

		placeHandlers = new ArrayList<PlaceHandler>();
		
		this.anchorPoint = point;
		this.x = point.x;
		this.y = point.y;
		this.selected = false;
		this.canBePlaced = false;
	}

	public void draw(Context2d context) {
		
		double x = getX() -width/2 , y= getY()-height/2; // minus 3 to draw the AnchorPoint in the middle
		
		context.setFillStyle(borderColor);
		context.fillRect(x, y, width, height);
		
		if (canBePlaced)
			context.setFillStyle(canBePlacedBackgroundColor);
		else
		if (selected)
			context.setFillStyle(selectedBackgroundColor);
		else
			context.setFillStyle(backgroundColor);
				
		context.fillRect(x+borderWeight, y+borderWeight, width-2*borderWeight, height-2*borderWeight);
		
	}
	
	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	/**
	 * Checks if the coordinate is part of the Anchor
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasCoordinate(double x, double y)
	{
		if (isBetween(getX()-(getWidth()/2), getX()+(getWidth()/2),x) && isBetween(getY()-(getHeight()/2), getY()+(getHeight()/2),y))
			return true;
		else
			return false;
	}

	/**
	 * Get the width of the {@link Anchor}
	 * @return
	 */
	public double getWidth()
	{
		return width;
	}
	
	/**
	 * Get the height
	 * @return
	 */
	public double getHeight()
	{
		return height;
	}

	/**
	 * Get the absolute x coordinate
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the absolute y coordinate
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * Set the absolute x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Set the absolute y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}


	/**	
	 * Get the next {@link AnchorPoint},
	 * @see AnchorPoint#getNextAnchorPoint()
	 * @return
	 */
	public AnchorPoint getNextAnchorPoint()
	{
		return anchorPoint.getNextAnchorPoint();
	}
	
	/**
	 * Get the {@link AnchorPoint} that is displayed with this {@link Anchor}
	 * @return
	 */
	public AnchorPoint getAnchorPoint() {
		return anchorPoint;
	}

	/**
	 * Is the Anchor currently selected
	 */
	public boolean isSelected() {
		return selected;
	}
	
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	@Override
	public void addPlaceHandler(PlaceHandler handler) {
		if (!placeHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				placeHandlers.add(placeHandlers.size(), handler);
			else
				placeHandlers.add(0,handler);
	}

	@Override
	public boolean firePlaceEvent(PlaceEvent event) {
		boolean delivered = false;
		
		for (PlaceHandler h : placeHandlers)
		{
			h.onPlaceEvent(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void removePlaceHandler(PlaceHandler handler) {
		placeHandlers.remove(handler);
	}

	/**
	 * By setting this flag to true will cause the {@link #draw(Context2d)} method to draw 
	 * the {@link Anchor} in a other way to make a visual effect to indicate that the {@link Anchor} 
	 * can be placed at the current mouse position
	 */
	@Override
	public void setCanBePlaced(boolean canPlaced) {
		canBePlaced = canPlaced;
	}

	/**
	 * Always return null to indicate, that the Anchor can be placed anywhere
	 */
	@Override
	public PlaceableDestination getPlaceableDestination() {
		return null;
	}

	
	

}
