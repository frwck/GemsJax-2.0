package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

/**
 * This is the common super class for system {@link Message}s like {@link LoginMessage}, {@link LogoutMessage} ...
 * @author Hannes Dorfmann
 *
 */
public abstract class SystemMessage  implements Message{

	public static final MessageType<SystemMessage> TYPE = new MessageType<SystemMessage>();
	
	/**
	 * Get the surrounding xml element tag.
	 * That means, that every {@link SystemMessage}
	 * is surroundet by this tag:  
	 * &lt;tag&gt; ... &lt;/tag&gt;
	 * @return
	 */
	public static final String TAG = "sys";
	
	
	
	@Override
	public MessageType<?> getMessageType(){
		return TYPE;
	}
	
}
