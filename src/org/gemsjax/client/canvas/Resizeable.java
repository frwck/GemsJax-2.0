package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.ResizeHandler;

public interface Resizeable {
	
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
	 * Can the Drawable be resized at the moment
	 * @return
	 */
	public boolean isResizeable();
	
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

}
