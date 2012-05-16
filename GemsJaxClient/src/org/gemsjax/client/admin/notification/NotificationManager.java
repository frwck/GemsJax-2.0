package org.gemsjax.client.admin.notification;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.adminui.Header;
import org.gemsjax.client.admin.notification.NotificationEvent.NotificationEventType;
import org.gemsjax.client.admin.view.AdminUIView;
import org.gemsjax.client.admin.view.implementation.AdminApplicationViewImpl;


import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
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
	
	private TipNotification activeTipNotification;
	
	/**
	 * Assumption: This list is always sorted on the y component of the absolute position of each {@link ShortInfoNotification}
	 */
	private List<ShortInfoNotification> activeShortNotifications;
	
	private int shortNotiStartX;
	private int shortNotiStartY;
	
	/**
	 * The space in pixel, between each short Notification
	 */
	private final int shortNotificationSpacer = 15;
	
	private NotificationManager()
	{
		activeTipNotification = null;
		activeShortNotifications = new LinkedList<ShortInfoNotification>();
		
		int percentWidth = Integer.parseInt(AdminApplicationViewImpl.contentWidth.split("%")[0]);
		int contentWidth = Window.getClientWidth() / 100 * percentWidth;
		int spaceLeft = (Window.getClientWidth()- contentWidth)/2;
		
		shortNotiStartX =  spaceLeft + contentWidth - ShortInfoNotification.WIDTH;
		shortNotiStartY = Integer.parseInt(Header.HEIGHT.split("px")[0]);
	}


	@Override
	public void onNotificationEvent(NotificationEvent event) {
		
		if (event.getType() == NotificationEventType.CLOSED)
		{
			if (event.getSource() == activeTipNotification)
				activeTipNotification = null;
			
			else
				onShortInfoClosed(event.getSource());
		}
				
		
	}
	
	
	private void onShortInfoClosed(Notification n){
		
		activeShortNotifications.remove(n);
		recalculateAllShortNotificationsY(n.getHeight()+shortNotificationSpacer);
	}
	
	
	private void recalculateAllShortNotificationsY(int heightOfRemoved){
		
		int delta = 0;
		if (!activeShortNotifications.isEmpty())
			delta = activeShortNotifications.get(0).getY() - shortNotiStartY - shortNotificationSpacer;
		
		for (ShortInfoNotification sn : activeShortNotifications){
			
			sn.animateMove(sn.getX(), sn.getY()-heightOfRemoved,null,130);
		}
		
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
	public void showTipNotification(TipNotification notification, AnimationEffect effect)
	{
		// check if there is already the same TipNotification on screen
		if (activeTipNotification != null){
			if (notification.isSameAs(activeTipNotification))
				return;
			else
			{
				activeTipNotification.hide();
				activeTipNotification = null;
			}
		}
		
		activeTipNotification = notification;
		notification.addNotificationHandler(this);
		notification.animateShow(effect);
	}
	
	
	public void showTipNotification(TipNotification notification){
		showTipNotification(notification, AnimationEffect.FADE);
	}
	
	
	public void showShortInfoNotification(ShortInfoNotification notification){
		
		notification.addNotificationHandler(this);
		notification.setPosition(shortNotiStartX, calculateNextShortNotificationY());
		activeShortNotifications.add(notification);
		notification.animateShow(AnimationEffect.FADE);
		
		
	}
	
	
	private int calculateNextShortNotificationY(){
		
		if (activeShortNotifications.isEmpty())
			return shortNotiStartY+shortNotificationSpacer;
		else
		{
			ShortInfoNotification n = activeShortNotifications.get(activeShortNotifications.size()-1);
			return n.getY() + n.getHeight() + shortNotificationSpacer;
		}
	}
	
	

}
