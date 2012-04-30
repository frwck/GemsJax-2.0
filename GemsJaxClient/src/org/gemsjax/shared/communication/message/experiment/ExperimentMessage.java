package org.gemsjax.shared.communication.message.experiment;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

public abstract class ExperimentMessage implements Message {


	public static final MessageType<ExperimentMessage> TYPE = new MessageType<ExperimentMessage>();
	
	
	public final MessageType<?> getMessageType(){
		return TYPE;
	}
	
}
