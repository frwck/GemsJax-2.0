package org.gemsjax.shared.communication.channel;

import java.io.IOException;

import org.gemsjax.shared.communication.message.Message;

/**
 * A {@link OutputChannel} is an abstract interface to give an API to send {@link Message}s
 * from client to server and vice versa.
 * @author Hannes Dorfmann
 *
 */
public interface OutputChannel {

	/**
	 * This method is used to send a {@link Message} to an {@link User}
	 * @param message
	 */
	public abstract void send(Message message) throws IOException;
	
}
