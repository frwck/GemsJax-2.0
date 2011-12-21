package org.gemsjax.shared.communication.message;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.user.User;

/**
 * A {@link Message} wraps the underlying communication protocol in a object.
 * In addition a this class provides a method
 * to transform a {@link Message} into a format ({@link #toXml()}) that can be send by the {@link OutputChannel} 
 * (via {@link CommunicationConnection} and the underlying network object like a socket) and can be received by 
 * an {@link InputChannel} (through {@link CommunicationConnection}) on the other side of the connection.
 * 
 * So a {@link Message} can be send from Client ({@link OutputChannel}) to the Server ({@link InputChannel})
 * and vice versa from Server ({@link OutputChannel} to Client ({@link InputChannel})
 * @see OutputChannel#send(Message)
 * @see CommunicationConnection#registerInputChannel(InputChannel)
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
	
	/**
	 * Generates an String in form of HTTP GET parameters that reprensents this {@link Message}
	 * and can be send via HTTP GET to a Servlet
	 * @return
	 */
	public abstract String toHttpGet();
	
	/**
	 * Generates an String in form of HTTP POST parameters that represents this {@link Message}
	 * and can be send via an HTTP POST to a Servlet
	 * @return
	 */
	public abstract String toHttpPost();

}
