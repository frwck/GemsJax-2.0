package org.gemsjax.client.desktopenviroment.events;

import org.gemsjax.client.desktopenviroment.GWindow;
import org.gemsjax.client.desktopenviroment.handler.GWindowEventHandler;

/**
 * This Event will be fired by an {@link GWindow} to inform its {@link GWindowEventHandler}'s
 * @author Hannes Dorfmann
 *
 */
public class GWindowEvent {
	
	public enum GWindowEventType
	{
		/**
		 * The {@link GWindow} has been closed
		 */
		CLOSE,
		
		/**
		 * The {@link GWindow} has been minimized
		 */
		MINIMIZE,
		
		/**
		 * The {@link GWindow} has been maximized
		 */
		MAXIMIZE,
		
		/**
		 * The {@link GWindow} has the Focus and is the "active" window
		 */
		FOCUS
	}
	
	private GWindowEventType type;
	private GWindow sourceWindow;
	
	public GWindowEvent(GWindowEventType type, GWindow sourceWindow)
	{
		this.type = type;
		this.sourceWindow = sourceWindow;
	}
	
	/**
	 * Get the {@link GWindowEventType}
	 * @return
	 */
	public GWindowEventType getType()
	{
		return type;
	}
	
	/**
	 * Get the {@link GWindow} that has fired this event
	 * @return
	 */
	public GWindow getSourceWindow()
	{
		return sourceWindow;
	}

}
