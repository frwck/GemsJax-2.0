package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;

/**
 * The base class for Collaboration {@link Message}
 * @author Hannes Dorfmann
 */
public abstract class CollaborationMessage implements Message{
	
	public static final MessageType<CollaborationMessage> TYPE = new MessageType<CollaborationMessage>();
	
	
	public MessageType<?> getMessageType(){
		return TYPE;
	}
	
}
