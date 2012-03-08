package org.gemsjax.client.admin.view;

import org.gemsjax.shared.communication.message.notification.LiveNotificationMessage;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;

public interface NotificationRequestShortInfoView {
	
	/**
	 * Set the count, that should be displayed somewhere on the gui
	 * @param unread
	 */
	public abstract void setUnreadNotificationRequest(long unread);
	
	/**
	 * Called to display a notification popup somewhere on the gui
	 * @param msg
	 */
	public abstract void showShortNotification(LiveNotificationMessage msg);
	
	/**
	 * Called to display a request notification popup somewhere on the gui
	 * @param msg
	 */
	public abstract void showShortRequestNotification(LiveRequestMessage msg);

}
