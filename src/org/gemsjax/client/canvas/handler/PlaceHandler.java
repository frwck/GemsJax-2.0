package org.gemsjax.client.canvas.handler;

import org.gemsjax.client.canvas.events.PlaceEvent;

/**
 * The Handler to hande {@link PlaceEvent}s
 * @author Hannes Dorfmann
 *
 */
public interface PlaceHandler {

	public void onPlaceEvent(PlaceEvent event);
}
