package org.gemsjax.shared.communication.message.friend;


import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

/**
 * The basic abstract class for every "friends message"
 * @author Hannes Dorfmann
 *
 */
public abstract class FriendMessage  implements Message{
	
	public static final String TAG ="friends";
	
	
	public static final MessageType<FriendMessage> TYPE = new MessageType<FriendMessage>();
	
	
	@Override
	public MessageType<?> getMessageType(){
		return TYPE;
	}

}
