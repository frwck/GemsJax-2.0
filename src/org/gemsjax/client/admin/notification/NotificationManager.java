package org.gemsjax.client.admin.notification;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.notification.NotificationEvent.NotificationEventType;


import com.smartgwt.client.types.AnimationEffect;

/**
 * This singleton class manages all {@link TipNotification}s
 * that are actually displayed on screen. 
 * Use {@link #show(TipNotification, AnimationEffect)} to display a new {@link TipNotification} on screen.
 * You always should use the {@link NotificationManager} to display {@link TipNotification}s
 * @author Hannes Dorfmann
 *
 */
public class NotificationManager implements NotificationHandler{
	
	private static NotificationManager instance ;
	
	private List<Notification> activeOnScreenNotifications;
	
	
	private NotificationManager()
	{
		activeOnScreenNotifications = new LinkedList<Notification>();
	}


	@Override
	public void onTipNotificationEvent(NotificationEvent event) {
		
		if (event.getType() == NotificationEventType.CLOSED)
			activeOnScreenNotifications.remove(event.getSource());
		
	}
	
	
	/**
	 * Get the singleton instance
	 * @return
	 */
	public static NotificationManager  getInstance()
	{
		if (instance == null)
			instance = new NotificationManager();
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
	public void show(Notification notification, AnimationEffect effect)
	{
		// check if there is already the same TipNotification on screen
		for (Notification n: activeOnScreenNotifications)
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
