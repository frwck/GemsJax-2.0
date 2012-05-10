package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;
import org.gemsjax.shared.communication.serialisation.Serializable;

/**
 * The base class for Collaboration {@link Message}
 * @author Hannes Dorfmann
 */
public abstract class CollaborationMessage implements Message, Serializable{
	
	public static final MessageType<CollaborationMessage> TYPE = new MessageType<CollaborationMessage>();
	
	
	public MessageType<?> getMessageType(){
		return TYPE;
	}
	
}
