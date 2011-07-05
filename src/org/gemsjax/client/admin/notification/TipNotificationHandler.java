package org.gemsjax.client.admin.notification;

/**
 * This handler handels {@link TipNotificationEvent}s fired by a {@link Notification}
 * @author hannes
 *
 */
public interface TipNotificationHandler {
	
	public void onTipNotificationEvent(TipNotificationEvent evnet);

}
