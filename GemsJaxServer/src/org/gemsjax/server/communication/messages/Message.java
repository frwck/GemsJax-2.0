package org.gemsjax.server.communication.messages;

import org.gemsjax.server.communication.OutputChannel;
import org.gemsjax.shared.user.User;

/**
 * A {@link Message} is a message that will be send to a {@link User} via an {@link OutputChannel}
 * @author Hannes Dorfmann
 *
 */
public interface Message {
	
	
	/**
	 * This method is called to generate a xml representation of this {@link Message} object.
	 * This xml representation is send via the {@link OutputChannel#send(Message)} to the {@link User}
	 * @return
	 */
	public abstract String toXml();

}