package org.gemsjax.client.admin.notification;

import java.util.List;

import com.smartgwt.client.widgets.layout.VStack;

/**
 * The basic class for {@link Notification}s.
 * It also implements functionality to add and remove {@link TipNotificationHandler} and a method
 * @author Hannes Dorfmann
 *
 */
public class Notification extends VStack{
	
	
	
	public enum NotificationPosition
	{
		CENTER,
		BOTTOM_CENTERED,
		BOTTOM_LEFT,
		BOTTOM_RIGHT
	}

	
	private List<TipNotificationHandler> handlers;
	
	public Notification()
	{
		super();
	}
	
	/**
	 * Add a {@link TipNotificationHandler}. If a {@link TipNotificationHandler} was already added, the same {@link TipNotificationHandler} will not be added a second time
	 * @param h
	 */
	public void addNotificationHandler( TipNotificationHandler h)
	{
		if (!handlers.contains(h))
			handlers.add(h);
	}
	
	/**
	 * Remove a {@link TipNotificationHandler}. If the handler was not registered previous to this {@link Notification}, nothing will be done.
	 * @param h
	 */
	public void removeNotificationHandler(TipNotificationHandler h)
	{
		handlers.remove(h);
	}
	
	/**
	 * Inform all {@link TipNotificationHandler}s ({@link #handlers} by firing a {@link TipNotificationEvent} 
	 * @param event
	 */
	protected void fireNotificationEvent(TipNotificationEvent event)
	{
		for (TipNotificationHandler h : handlers)
			h.onTipNotificationEvent(event);
	}
	
	
	
}
