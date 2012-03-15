package org.gemsjax.client.admin.notification;

/**
 * This handler handels {@link NotificationEvent}s fired by a {@link Notification}
 * @author hannes
 *
 */
public interface NotificationHandler {
	
	public void onNotificationEvent(NotificationEvent evnet);

}
