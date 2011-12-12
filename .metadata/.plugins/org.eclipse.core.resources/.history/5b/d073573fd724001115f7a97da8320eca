package org.gemsjax.shared.communication.channel;

import org.gemsjax.client.communication.WebSocketCommunicationConnection;
import org.gemsjax.client.communication.exception.WebSocketSendException;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.message.Message;

/**
 * A {@link InputChannel} is registered to the {@link CommunicationConnection} to receive incoming data (as String).
 * The {@link CommunicationConnection} forwards incoming data to every registered {@link InputChannel}, 
 * if the {@link #getFilterRegEx()} regular expression matches incoming data-string. 
 * <b>Note</b> that the incoming data string is forwarded by the {@link CommunicationConnection} to the {@link InputChannel}
 * by calling {@link InputChannel#onMessageReceived(String)}. <b>Note also</b> that the passed string (to {@link InputChannel#onMessageReceived(String)})
 * is a string representation of a {@link Message} object specified in the communication protocol.
 * So after receiving a data-string, its the job of a {@link InputChannel} to parse this data-sting into a {@link Message} object and
 * to do something useful with this parsed {@link Message}.
 * @author Hannes Dorfmann
 *
 */
public interface InputChannel {
	
	/**
	 * This method is called by the {@link WebSocketCommunicationConnection} (observer-pattern) to push the new incoming message to this channel.
	 * <b>Notice:</b> This method is only called, if the incoming message matches the filer regex of this {@link InputChannel} ({@link #getFilterRegEx()})  
	 * @param msg
	 */
	public void onMessageReceived(String msg);
	
	/**
	 * 
	 * @return The regular expression which is used by the {@link WebSocketCommunicationConnection} to filter incoming messages.
	 * With this regular expression you can specify that only incoming messages are pushed to this {@link InputChannel} 
	 * that matches this regular expression.
	 */
	public String getFilterRegEx();
	
}
