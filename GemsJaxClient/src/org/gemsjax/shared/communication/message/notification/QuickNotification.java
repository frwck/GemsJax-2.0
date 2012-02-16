package org.gemsjax.shared.communication.message.notification;

import java.util.Date;

import org.gemsjax.shared.notification.QuickNotification.QuickNotificationType;

public class QuickNotification extends Notification {

	
	private String optionalMessage;
	private QuickNotificationType type;
	
	public QuickNotification(long id, Date date, boolean read, QuickNotificationType type, String optionalMessage) {
		super(id, date, read);
		this.optionalMessage = optionalMessage;
		this.type = type;
	}

	public String getOptionalMessage() {
		return optionalMessage;
	}

	public QuickNotificationType getType() {
		return type;
	}
	
	

}
