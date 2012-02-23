package org.gemsjax.server.communication.channel.handler;

import org.gemsjax.server.communication.channel.NotificationChannel;
import org.gemsjax.server.module.OnlineUser;

/**
 * The handler for {@link NotificationChannel}
 * @author Hannes Dorfmann
 *
 */
public interface NotificationChannelHandler {

	public abstract void onMarkNotificationAsRead(String referenceId, long notificationId, OnlineUser u);
	
	public abstract void onDeleteNotification(String referenceId, long notificationId, OnlineUser u);
	
	public abstract void onGetAllNotifications(String referenceId, OnlineUser u);
	
}
