package org.gemsjax.client.canvas;

import java.util.List;

import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.handler.ClickHandler;

/**
 * This interface makes a drawable clickable.
 * That means, that {@link ClickEvent}s will be fired to inform the registered {@link ClickHandler}s
 * @author Hannes Dorfmann
 *
 */
public interface Clickable {

	void addClickHandler(ClickHandler handler);
	void removeClickHandler(ClickHandler handler);
	void fireClickEvent(ClickEvent event);
}
