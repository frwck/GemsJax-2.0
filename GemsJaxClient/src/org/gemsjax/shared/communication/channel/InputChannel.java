package org.gemsjax.shared.communication.channel;


import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.message.Message;


/**
 * A {@link InputChannel} is registered to the {@link CommunicationConnection} to receive incoming messages (wrapped by the class {@link InputMessage}).
 * The {@link CommunicationConnection} forwards incoming messages ({@link InputMessage}) to every registered {@link InputChannel}, 
 * if the {@link #isMatchingFilter()} returns true.<br /> 
 * <b>Note</b> that the incoming data string is forwarded by the {@link CommunicationConnection} to the {@link InputChannel}
 * by calling {@link InputChannel#onMessageReceived(InputMessage)}. <b>Note also</b> that the passed {@link InputMessage} (to {@link InputChannel#onMessageReceived(InputMessage)})
 * is a representation of a {@link Message} object specified in the communication protocol.
 * So after receiving, its the job of a {@link InputChannel} to parse the {@link InputMessage#getText()} into a {@link Message} object and
 * to do something useful with this parsed {@link Message}.
 * @author Hannes Dorfmann
 */

public interface InputChannel {
	
	/**
	 * This method is called by the underlying {@link CommunicationConnection} (observer-pattern) to push the new received message to this channel.
	 * <b>Notice:</b> This method is only called, if ({@link #isMatchingFilter()}) returns true.
	 * @param msg The {@link InputMessage} which wraps the incoming data
	 */
	@Deprecated
	public void onMessageReceived(InputMessage msg);

	public void onMessageRecieved(Message msg);
	
	
	/**
	 * This method is called by the underlying {@link CommunicationConnection} to determine, if an incoming message should be pushed (by calling {@link #onMessageReceived(InputMessage)})
	 * to this {@link InputChannel}. In fact, the incoming message is pushed only, if this method returns true.
	 * The passed parameter is the incoming message (or a little part of it). In the implementation you should not complete parse the passed message,
	 * but do a quick validation, for example by using regular expressions.
	 * @param msg The textual protocol message
	 * @return
	 */
	@Deprecated
	public boolean isMatchingFilter(String msg);
	
}
