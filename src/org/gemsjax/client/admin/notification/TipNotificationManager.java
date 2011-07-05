package org.gemsjax.client.admin.notification;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.notification.TipNotificationEvent.TipNotificationEventType;


import com.smartgwt.client.types.AnimationEffect;

/**
 * This singleton class manages all {@link TipNotification}s
 * that are actually displayed on screen. 
 * Use {@link #show(TipNotification, AnimationEffect)} to display a new {@link TipNotification} on screen.
 * You always should use the {@link TipNotificationManager} to display {@link TipNotification}s
 * @author Hannes Dorfmann
 *
 */
public class TipNotificationManager implements TipNotificationHandler{
	
	private static TipNotificationManager instance = new TipNotificationManager();
	
	private List<TipNotification> activeOnScreenNotifications;
	
	
	private TipNotificationManager()
	{
		activeOnScreenNotifications = new LinkedList<TipNotification>();
	}


	@Override
	public void onTipNotificationEvent(TipNotificationEvent event) {
		
		if (event.getType() == TipNotificationEventType.CLOSED)
			activeOnScreenNotifications.remove(event.getSource());
		
	}
	
	
	/**
	 * Get the singleton instance
	 * @return
	 */
	public static TipNotificationManager  getInstance()
	{
		return instance;
	}
	
	
	/**
	 * Displayes a {@link TipNotification} on screen.
	 * It checks if there is already the same Notification on screen by calling {@link TipNotification#isSameAs(TipNotification)} and prevent to display
	 * the same {@link TipNotification} for a second time.
	 * 
	 * @param notification
	 * @param effect
	 */
	public void show(TipNotification notification, AnimationEffect effect)
	{
		// check if there is already the same TipNotification on screen
		for (TipNotification n: activeOnScreenNotifications)
		{
			if (n.isSameAs(notification))
				return;
		}
		
		// TODO, calculate Position where the new TipNotification should be placed on screen
		activeOnScreenNotifications.add(notification);
		notification.addNotificationHandler(this);
		notification.animateShow(effect);
		
	}
	
	

}
