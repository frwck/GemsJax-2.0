package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.HasPlaceable;
import org.gemsjax.client.canvas.Placeable;
import org.gemsjax.client.canvas.handler.PlaceHandler;

/**
 * {@link PlaceEvent}s are handled by the {@link PlaceHandler}.
 * {@link PlaceEvent}s can be fired by {@link Placeable}s.
 * @author Hannes Dorfmann
 *
 */
public class PlaceEvent {

	public enum PlaceEventType
	{
		/**
		 * Indicates that its not allowed to palce the {@link Placeable} {@link PlaceEvent#getSource()} at the coordinate {@link PlaceEvent#getX()} /  {@link PlaceEvent#getY()}
		 */
		NOT_ALLOWED,
		/**
		 * Indicates that the {@link Placeable} is currently be moved on the canvas as result of a animation or user interaction (like mouse moving)
		 */
		TEMP_PLACING,
		/**
		 * Indicates, that the replacing is finished and the {@link Placeable} should be set at the new x / y position
		 */
		PLACING_FINISHED
	}
	
	
	private PlaceEventType type;
	private Placeable source;
	private double x;
	private double y;
	
	private HasPlaceable parent;
	
	/**
	 * 
	 * @param source
	 * @param type
	 * @param x The absolute x coordinate on the canvas
	 * @param y  The absolute y coordinate on the canvas
	 * @param The parent ( {@link HasPlaceable}) which contains the source {@link Placeable}
	 */
	public PlaceEvent(Placeable source, PlaceEventType type, double x, double y, HasPlaceable parent)
	{
		this.source = source;
		this.type = type;
		this.x = x;
		this.y = y;
		this.parent = parent;
	}

	public PlaceEventType getType() {
		return type;
	}

	public Placeable getSource() {
		return source;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public HasPlaceable getParent() {
		return parent;
	}
	
}
