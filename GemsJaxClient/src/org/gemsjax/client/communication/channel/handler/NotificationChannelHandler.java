package org.gemsjax.client.communication.channel.handler;

import org.gemsjax.client.communication.channel.NotificationChannel;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsAnswerMessage;
import org.gemsjax.shared.communication.message.notification.LiveNotificationMessage;
import org.gemsjax.shared.communication.message.notification.NotificationError;
/**
 * The handler for {@link NotificationChannel}
 * @author Hannes Dorfmann
 *
 */
public interface NotificationChannelHandler {

	public abstract void onError(String referenceId, NotificationError error);
	
	public abstract void onSuccess(String referenceId);
	
	public abstract void onLiveMessageReceived(LiveNotificationMessage msg);
	
	public abstract void onGetAllAnswer(GetAllNotificationsAnswerMessage msg);
	
}
