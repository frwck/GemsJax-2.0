package org.gemsjax.client.canvas.events;

import org.gemsjax.client.canvas.Drawable;

/**
 * FocusEvents will be thrown, when the Drawable get the focus or lost the focus:
 * <ul>
 * <li> Get focus:  That normaly means, that the {@link Drawable} is selected (for example by clicking on it)</li>
 * <li> Lost focus: That normaly means, that the {@link Drawable} is not longer selected (for example by clicking on another {@link Drawable})</li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class FocusEvent {
	
	public enum FocusEventType
	{
		GOT_FOCUS,
		LOST_FOCUS
	}
	
	
	private Drawable source;
	private FocusEventType type;
	
	public FocusEvent(Drawable source, FocusEventType type)
	{
		this.source = source;
		this.type = type;
	}

	/**
	 * @return The {@link Drawable} that has fired this Event
	 */
	public Drawable getSource() {
		return source;
	}

	public FocusEventType getType() {
		return type;
	}
	
	

}
