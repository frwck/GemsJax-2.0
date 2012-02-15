package org.gemsjax.server.persistence.notification;

import org.gemsjax.shared.notification.QuickNotification;

public class QuickNotificationImpl extends NotificationImpl implements QuickNotification{

	private int codeNumber;
	private String optionalMessage;
	
	
	public QuickNotificationImpl()
	{
		
	}
	
	
	@Override
	public int getCodeNumber() {
		return codeNumber;
	}

	@Override
	public String getOptionalMessage() {
		return optionalMessage;
	}

	@Override
	public void setCodeNumber(int arg0) {
		this.codeNumber = arg0;
	}

	@Override
	public void setOptionalMessage(String arg0) {
		this.optionalMessage = arg0;
	}

}
