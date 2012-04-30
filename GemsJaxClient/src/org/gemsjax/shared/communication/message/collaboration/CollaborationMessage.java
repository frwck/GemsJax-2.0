package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;

/**
 * The base class for Collaboration {@link Message}
 * @author Hannes Dorfmann
 */
public abstract class CollaborationMessage implements Message{
	
	public static final String TAG = "col";
	
	public static final String ATTRIBUTE_COLLABORATEABLE_ID ="on";
	
	public static final MessageType<CollaborationMessage> TYPE = new MessageType<CollaborationMessage>();
	
	
	private int id;
	
	
	public CollaborationMessage(int id){
		this.id = id;
	}
	
	
	public String openingXml(){
		return "<"+TAG+" "+ ATTRIBUTE_COLLABORATEABLE_ID+"=\""+id+"\" >";
	}
	
	public String closingXml(){
		return "</"+TAG+">";
	}
	
	
	public MessageType<?> getMessageType(){
		return TYPE;
	}
	
}
