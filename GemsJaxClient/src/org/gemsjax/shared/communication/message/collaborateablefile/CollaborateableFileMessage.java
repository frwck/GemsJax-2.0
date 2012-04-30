package org.gemsjax.shared.communication.message.collaborateablefile;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

public abstract class CollaborateableFileMessage implements Message{
	
	public static final MessageType<CollaborateableFileMessage> TYPE = new MessageType<CollaborateableFileMessage>();

	
	public MessageType<?> getMessageType(){
		return TYPE;
	}
}
