package org.gemsjax.shared.communication.message.notification;

import org.gemsjax.shared.communication.message.Message;

public abstract class NotificationMessage implements Message{
	
	
	public static final String TAG="noti";
	
	
	protected String openingXml()
	{
		return "<"+TAG+">";
	}
	
	
	protected String closingXml(){
		return "</"+TAG+">";
	}

}
