package org.gemsjax.shared.communication.message.search;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

public abstract class SearchMessage implements Message {
	
	public static final MessageType<SearchMessage> TYPE = new MessageType<SearchMessage>();
	
	@Override
	public MessageType<?> getMessageType(){
		return TYPE;
	}

}
