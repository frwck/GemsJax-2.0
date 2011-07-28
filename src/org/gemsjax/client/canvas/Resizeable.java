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

}
