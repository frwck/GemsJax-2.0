package org.gemsjax.shared.communication.message.notification;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

public abstract class NotificationMessage implements Message{
	
	public static final MessageType<NotificationMessage> TYPE = new MessageType<NotificationMessage>();

	
	public static final String TAG="noti";
	
	
	protected String openingXml()
	{
		return "<"+TAG+">";
	}
	
	
	protected String closingXml(){
		return "</"+TAG+">";
	}

	
	public MessageType<?> getMessageType(){
		return TYPE;
	}
}
