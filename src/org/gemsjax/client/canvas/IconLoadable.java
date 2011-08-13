package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.IconLoadEvent;
import org.gemsjax.client.canvas.handler.IconLoadHandler;

/**
 * Add the ability to register / unregister {@link IconLoadHandler}s and to fire {@link IconLoadEvent}s
 * @author Hannes Dorfmann
 *
 */
public interface IconLoadable {

	public void addIconLoadHandler(IconLoadHandler h);
	public void removeIconLoadHanlder(IconLoadHandler h);
	
	public boolean fireIconLoadEvent(IconLoadEvent e);
}
