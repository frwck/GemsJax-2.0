package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.Moveable;
import org.gemsjax.shared.metamodel.MetaClass;

/**
 * This is an Event, that will be thrown, when a Drawable has been selected an will be moved (position) on the {@link BufferedCanvas}.
 * @author Hannes Dorfmann
 *
 */
public class MoveEvent {
	
	public enum MoveEventType
	{
		/**
		 * Indicates that this MoveEvent is fired, because of a temporary movement.
		 * This is used for example: a move animation,
		 * the user is moving this element with the mouse (but has not released the mouse yet),
		 * In this case the x/y coordinates of the Drawable should be set (but not of the underlying data object, like {@link MetaClass})
		 */
		TEMP_MOVE,
		/**
		 * Indicates that this MoveEvent is fired, because a movement is finished.
		 * That is normally be done, when the user releases the mouse button.
		 * In this case the x/y coordinate of the underlying data object should be set and synchronized with the corresponding Drawable
		 */
		MOVE_FINISHED
	}
	
	private double startX;
	private double startY;
	private double x;
	private double y;
	private int screenX;
	private int screenY;
	private boolean leftMouseDown;
	private double distanceToTopLeftX;
	private double distanceToTopLeftY;
	
	private Moveable source;
	
	private MoveEventType type;
	
	
	public MoveEvent(Moveable source, MoveEventType type, double startX, double startY, double x, double y,  double dTopLeftX, double dTopLeftY, int screenX, int screenY, boolean leftMouse)
	{
		this.startX = startX;
		this.startY = startY;
		this.x = x;
		this.y = y; 
		this.screenX =screenX;
		this.screenY = screenY;
		this.leftMouseDown = leftMouse;
		this.distanceToTopLeftX = dTopLeftX;
		this.distanceToTopLeftY = dTopLeftY;
		this.type = type;
		this.source = source;
		
	}
	
	public Moveable getSource()
	{
		return source;
	}
	
	/**
	 * @see MoveEventType
	 * @return
	 */
	public MoveEventType getType()
	{
		return type;
	}
	
	public double getX() {
		return x;
	}

	

	public double getY() {
		return y;
	}


	public int getScreenX() {
		return screenX;
	}


	public int getScreenY() {
		return screenY;
	}


	public boolean isLeftMouseDown() {
		return leftMouseDown;
	}


	public double getStartX() {
		return startX;
	}

	public double getStartY() {
		return startY;
	}

	
	public double getDistanceToTopLeftX() {
		return distanceToTopLeftX;
	}

	public double getDistanceToTopLeftY() {
		return distanceToTopLeftY;
	}
	

}
