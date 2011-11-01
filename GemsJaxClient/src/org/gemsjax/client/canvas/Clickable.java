package org.gemsjax.client.canvas;


import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.handler.ClickHandler;

/**
 * This interface makes a drawable clickable.
 * That means, that {@link ClickEvent}s will be fired to inform the registered {@link ClickHandler}s
 * @author Hannes Dorfmann
 *
 */
public interface Clickable{

	public void addClickHandler(ClickHandler handler);
	public void removeClickHandler(ClickHandler handler);
	/**
	 * Fire a ClickEvent
	 * @param event
	 * @return true if at least one Handler is registered and has received this event. Otherwise false (if there is no Handler registered).
	 */
	public boolean fireClickEvent(ClickEvent event);
}
