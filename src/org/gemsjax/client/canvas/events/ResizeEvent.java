package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.ResizeArea;
import org.gemsjax.client.canvas.Resizeable;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.shared.metamodel.MetaClass;

/**
 * Inform the {@link ResizeHandler} that the width and height of a {@link Drawable} should be resized to the given width and height
 * @author Hannes Dorfmann
 *
 */
public class ResizeEvent {
	
	public enum ResizeEventType
	{
		/**
		 * Indicates that this ResizeEvent is fired, cause of a temporary resizement.
		 * This is used for example for resize animation:
		 * the user is resizing a element with the mouse (but has not released the mouse button yet).
		 * In this case the width / height of the Drawable should be set (but not of the underlying data object, like {@link MetaClass})
		 */
		TEMP_RESIZE,
		
		/**
		 * Indicates that this ResizeEvent is fired, because a resizement is finished.
		 * That is normally be done, when the user releases the mouse button.
		 * In this case the x/y coordinate of the underlying data object should be set and synchronized with the corresponding Drawable
		 */
		RESIZE_FINISHED
	}
	
	
	private ResizeEventType type;
	
	private double width;
	private double height;
	private double x;
	private double y;
	private ResizeArea resizeArea;
	private Resizeable source;
	
	private double xDifference;
	private double yDifference;
	
	
	private double startX;
	private double startY;
	private double endY;
	private double endX;
	
	public ResizeEvent(Resizeable source,ResizeEventType type, double xDifference, double yDifference)
	{
		this.source = source;
		this.xDifference = xDifference;
		this.yDifference = yDifference;
		this.type = type;
	}
	
	public ResizeEvent(Resizeable source, ResizeEventType type, double width, double height, double x, double y, ResizeArea r)
	{
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.resizeArea = r;
		this.source = source;
		this.type = type;
	}
	
	public ResizeEvent(Resizeable source, ResizeEventType type,  double startX, double startY, double endX, double endY)
	{
		this.source = source;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.type = type;
	}


	public ResizeEventType getType()
	{
		return type;
	}
	
	public double getWidth() {
		return width;
	}


	public double getHeight() {
		return height;
	}


	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public ResizeArea getResizeArea() {
		return resizeArea;
	}
	
	public Resizeable getSource()
	{
		return source;
	}

	public double getEndY() {
		return endY;
	}

	public void setEndY(double endY) {
		this.endY = endY;
	}

	public double getStartX() {
		return startX;
	}

	public double getStartY() {
		return startY;
	}

	public double getEndX() {
		return endX;
	}

}
