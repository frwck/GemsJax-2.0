package org.gemsjax.server.persistence.notification;

import org.gemsjax.shared.notification.QuickNotification;

public class QuickNotificationImpl extends NotificationImpl implements QuickNotification{

	/**
	 * Used for persistence only
	 */
	private int codeNumber;
	private String optionalMessage;
	
	private QuickNotificationType type;
	
	public QuickNotificationImpl()
	{
		
	}
	
	
	
	@Override
	public String getOptionalMessage() {
		return optionalMessage;
	}

	

	@Override
	public void setOptionalMessage(String arg0) {
		this.optionalMessage = arg0;
	}



	@Override
	public QuickNotificationType getQuickNotificationType() {
		return QuickNotificationType.fromConstant(codeNumber);
	}



	@Override
	public void setQuickNotificationType(QuickNotificationType type) {
		codeNumber = type.toConstant();
		
	}

}
