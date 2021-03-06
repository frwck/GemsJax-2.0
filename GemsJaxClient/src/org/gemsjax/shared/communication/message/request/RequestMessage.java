package org.gemsjax.shared.communication.message.request;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

public abstract class RequestMessage implements Message {

	public static final MessageType<RequestMessage> TYPE = new MessageType<RequestMessage>();

	
	
	public static final String TAG ="request";
	
	
	protected String openingXml(){
		return "<"+TAG+">";
	}
	
	protected String closingXml()
	{
		return "</"+TAG+">";
	}
	
	
	@Override
	public MessageType<?> getMessageType(){
		return TYPE;
	}
	
}
