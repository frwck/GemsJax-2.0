package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.PlaceHandler;
import org.gemsjax.shared.Point;
import org.gemsjax.client.canvas.events.PlaceEvent;
import com.google.gwt.canvas.dom.client.Context2d;


/**
 * {@link AnchorPoint} represents a Point that is displayed on the {@link MetaModelCanvas} and {@link ModelCanvas}.
 * A AnchorPoint is used to draw "agile" Connection between {@link Drawable} objects like {@link MetaClassDrawable}.
 * "Agile" connection means, that the user can set with the mouse the points (with {@link AnchorPoint}s) where the line of the connection goes by.
 * @author Hannes Dorfmann
 *
 */
public class AnchorPoint implements Drawable, Placeable, Focusable{
	
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
	private String borderColor = "black";
	private String backgroundColor = "white";
	private double borderWeight = 1; 
	
	private AnchorPointDestination destination;
	
	private AnchorPoint nextAnchorPoint;
	
	private List<PlaceHandler> placeHandlers;
	private List<FocusHandler> focusHandlers;
	
	
	/**
	 * The Point, that is displayed with this {@link AnchorPoint}.
	 * <b>Note</b> The Point's coordinates itself are only set, when the Mouse was released via the corresponding presenter.
	 * {@link #x} and {@link #y} are the current coordinate, which are used to display the object and animations (for example move the AnchorPoint with the mouse),
	 * so {@link #x} and {@link #y} are set permanently, but the {@link #point} itself is only set when the 
	 * {@link MoveEvent} with the {@link MoveEventType#MOVE_FINISHED} is received (in the Presenter)
	 */
	private Point point;
	
	private boolean selected;
	
	/**
	 * 
	 * @param point
	 * @param destination If destination == null than this AnchorPoint can be placed freely on the canvas, otherwise it can be only placed where {@link AnchorPointDestination#canAnchorPointBePlacedAt(double, double)} == true.
	 * That is the case, if you want to create an {@link AnchorPoint} for the source (or target) Drawable.
	 * That also indicates, that the {@link #x} and {@link #y} coordinates are relative coordinates according to the source (or targets) current coordinates which can be computed to 
	 * absolute coordinates via {@link AnchorPointDestination#getX()} + #x and {@link AnchorPointDestination#getY()} + {@link #y}
	 */
	public AnchorPoint(Point point, AnchorPointDestination destination)
	{

		placeHandlers = new ArrayList<PlaceHandler>();
		
		this.point = point;
		this.x = point.x;
		this.y = point.y;
		this.setDestination(destination);
		this.selected = false;
	}

	public void draw(Context2d context) {
		double x = this.x , y= this.y;
		
		if (destination != null) // than the current x
		{
			x = destination.getX() + this.x;
			y = destination.getY() + this.y;
		}
		
		
		context.setFillStyle(borderColor);
		context.fillRect(x, y, width, height);
		
		context.setFillStyle(backgroundColor);
		context.fillRect(x+borderWeight, y+borderWeight, width-2*borderWeight, height-2*borderWeight);
		
		
		if (selected && destination!= null)
			destination.highlightDestinationArea(context);
	}
	
	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}
	
	public boolean hasCoordinate(double x, double y)
	{
		if (isBetween(this.x, this.x+width,x) && isBetween(this.y, this.y+height,y))
			return true;
		else
			return false;
	}



	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
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

	public AnchorPoint getNextAnchorPoint() {
		return nextAnchorPoint;
	}

	public void setNextAnchorPoint(AnchorPoint nextAnchorPoint) {
		this.nextAnchorPoint = nextAnchorPoint;
	}

	@Override
	public Object getDataObject() {
		return point;
	}

	@Override
	public double getZIndex() {
		//used?
		return 0;
	}

	@Override
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

	@Override
	public void addFocusHandler(FocusHandler handler) {
		if (!focusHandlers.contains(handler))
			focusHandlers.add(handler);
	}

	@Override
	public boolean fireFocusEvent(FocusEvent event) {
		boolean delivered = false;
		
		for (FocusHandler h : focusHandlers)	
		{
			h.onFocusEvent(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void removeFocusHandler(FocusHandler handler) {
		
		focusHandlers.remove(handler);
	}
	
	

}
