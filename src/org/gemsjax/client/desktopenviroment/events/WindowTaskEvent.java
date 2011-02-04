package org.gemsjax.client.desktopenviroment.events;

import org.gemsjax.client.desktopenviroment.GWindow;
import org.gemsjax.client.desktopenviroment.WindowTask;
import org.gemsjax.client.desktopenviroment.handler.WindowTaskEventHandler;

/**
 * This Event will be fired by an {@link WindowTask} to inform its {@link WindowTaskEventHandler}s
 * @author Hannes Dorfmann
 *
 */
public class WindowTaskEvent {

	public enum WindowTaskEventType
	{
		/**
		 * The {@link WindowTask} has been closed by the user (context menu)
		 */
		CLOSE,
		
		/**
		 * The user has set to maximize the corresponding {@link GWindow}  via the  {@link WindowTask} context menu
		 */
		MAXIMIZE,
		
		/**
		 * The user has clicked on the {@link WindowTask} and will bring the corresponding {@link GWindow} to the front (this window is the "active" window, where the user work in)
		 */
		FOCUS
	}
	
	private WindowTask source;
	private WindowTaskEventType type;
	
	public WindowTaskEvent(WindowTaskEventType type, WindowTask source)
	{
		this.type = type;
		this.source = source;
	}
	
	/**
	 * Get the {@link WindowTaskEventType}
	 * @return
	 */
	public WindowTaskEventType getType()
	{
		return this.type;
	}
	
	
	/**
	 * Get the {@link WindowTask} that has fired this event
	 * @return {@link WindowTask}
	 */
	public WindowTask getWindowTaskSource()
	{
		return source;
	}
	
}
