package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.editor.MetaModelCanvas;

/**
 * Make a Drawable focusable. That means that the Drawable can get or lose the focus (of the {@link MetaModelCanvas}).
 * @author Hannes Dorfmann
 *
 */
public interface Focusable {
	
	public void addFocusHandler(FocusHandler handler);
	public void removeFocusHandler(FocusHandler handler);

	/**
	 * Fire a FocusEvent
	 * @param event
	 * @return true if at least one Handler is registered and has received this event. Otherwise false (if there is no Handler registered).
	 */
	public boolean fireFocusEvent(FocusEvent event);

}
