package org.gemsjax.server.communication;

import org.gemsjax.server.communication.messages.Message;
import org.gemsjax.shared.user.User;

/**
 * A {@link OutputChannel} is an abstract interface to give an API how to send Messages to an {@link User}
 * @author Hannes Dorfmann
 *
 */
public interface OutputChannel {

	/**
	 * This method is used to send a {@link Message} to an {@link User}
	 * @param message
	 */
	public abstract void send(Message message);
	
}
