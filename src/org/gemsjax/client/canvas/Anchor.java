package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.PlaceHandler;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.Point;
import org.gemsjax.client.canvas.events.PlaceEvent;
import com.google.gwt.canvas.dom.client.Context2d;


/**
 * {@link Anchor} represents a {@link AnchorPoint} in a graphical way on the {@link MetaModelCanvas} and {@link ModelCanvas}.
 * A Anchor is used to draw "agile" connection between {@link Drawable} objects like {@link MetaClassDrawable} and {@link MetaConnectionDrawable}.
 * "Agile" connection means, that the user can set with the mouse the points (with {@link Anchor}s) where the line of the connection goes by.
 * @author Hannes Dorfmann
 *
 */
public class Anchor implements Placeable{
	
	/**
	 * The current x coordinate.
	 * <b>Notice:</b> If {@link AnchorPointDestination} is not null, than this coordinate is a relative coordinate.
	 * The absolute coordinate can be computed by {@link #x} + {@link AnchorPointDestination#getX()}
	 * @see #point
	 */
	private double x;
	/**
	 * The current y coordinate.
	 *  <b>Notice:</b> If {@link AnchorPointDestination} is not null, than this coordinate is a relative coordinate.
	 * The absolute coordinate can be computed by {@link #y} + {@link AnchorPointDestination#getY()}
	 * @see #point
	 */
	private double y;
	private double width = 6;
	private double height = 6;
	private String borderColor = "2A4596";
	private String backgroundColor = "F7F3DC";
	private String selectedBackgroundColor = "FAD816";
	private double borderWeight = 1; 
	
	private AnchorPointDestination destination;
	
	private Anchor nextAnchorPoint;
	
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
	
	/**
	 * 
	 * @param point
	 * @param destination If destination == null than this AnchorPoint can be placed freely on the canvas, otherwise it can be only placed where {@link AnchorPointDestination#canAnchorPointBePlacedAt(double, double)} == true.
	 * That is the case, if you want to create an {@link Anchor} for the source (or target) Drawable.
	 * That also indicates, that the {@link #x} and {@link #y} coordinates are relative coordinates according to the source (or targets) current coordinates which can be computed to 
	 * absolute coordinates via {@link AnchorPointDestination#getX()} + #x and {@link AnchorPointDestination#getY()} + {@link #y}
	 */
	public Anchor(AnchorPoint point, AnchorPointDestination destination)
	{

		placeHandlers = new ArrayList<PlaceHandler>();
		
		this.anchorPoint = point;
		this.x = point.x;
		this.y = point.y;
		this.setDestination(destination);
		this.selected = false;
	}

	public void draw(Context2d context) {
		
		double x = this.x -width/2 , y= this.y-height/2; // minus 3 to draw the AnchorPoint in the middle
		
		if (destination != null) // than the current x
		{
			x = destination.getX() + this.x - width/2;
			y = destination.getY() + this.y - height/2;
		}
		
		
		if (selected && destination!= null)
			destination.highlightDestinationArea(context);
		
		
		context.setFillStyle(borderColor);
		context.fillRect(x, y, width, height);
		
		context.setFillStyle(selected ? selectedBackgroundColor : backgroundColor);
		context.fillRect(x+borderWeight, y+borderWeight, width-2*borderWeight, height-2*borderWeight);
		
	}
	
	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	public boolean hasCoordinate(double x, double y)
	{
		if (isBetween(this.x-(width/2), this.x+(width/2),x) && isBetween(this.y-(height/2), this.y+(height/2),y))
			return true;
		else
			return false;
	}


	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Set the destination
	 * @param destination
	 */
	public void setDestination(AnchorPointDestination destination) {
		this.destination = destination;
	}

	public AnchorPointDestination getDestination() {
		return destination;
	}

	/**
	 * Get the next {@link Anchor}
	 * @return The next {@link Anchor} or null if we are at the end
	 */
	public Anchor getNextAnchor() {
		return nextAnchorPoint;
	}

	public void setNextAnchor(Anchor nextAnchor) {
		this.nextAnchorPoint = nextAnchor;
	}

	public AnchorPoint getAnchorPoint() {
		return anchorPoint;
	}


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
			placeHandlers.add(handler);
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

	
	

}
