package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.ResizeHandler;

public interface Resizeable {
	
	
	
	/**
	 * Check if this Drawable has a {@link ResizeArea}, which has the x-y coordinate as part of this {@link ResizeArea}
	 * @param x
	 * @param y
	 * @return The ResizeArea which is at the position or null if there is not a {@link ResizeArea}
	 */
	public ResizeArea isResizerAreaAt(double x, double y);
	
	
	/**
	 * Add a {@link ResizeHandler}
	 * @param resizeHandler
	 */
	public void addResizeHandler(ResizeHandler resizeHandler);
	
	/**
	 * remove a {@link ResizeHandler}
	 * @param resizeHandler
	 */
	public void removeResizeHandler(ResizeHandler resizeHandler);
	

	/**
	 * Fire a {@link ResizeEvent}
	 * @param event
	 * @return true if at least one handler is registered and has received this event. Otherwise false (if there is no handler registered).
	 */
	public boolean fireResizeEvent(ResizeEvent event);
	
	
	/**
	 * Get the current width. This makes it easier to work with resize events.
	 * @return
	 */
	public double getWidth();
	
	/**
	 * Get the current height. This makes it easier to work with the resize events.
	 * @return
	 */
	public double getHeight();

	public void setWidth(double width);
	
	public void setHeight(double height);
	
	
	public double getMinWidth();
	public double getMinHeight();
}
