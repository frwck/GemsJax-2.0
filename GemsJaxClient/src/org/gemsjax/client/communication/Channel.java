package org.gemsjax.client.communication;

import org.gemsjax.client.communication.exception.WebSocketSendException;

/**
 * A {@link Channel} is a communication object that interacts with the {@link WebSocket}.
 * A {@link Channel} listens to the {@link WebSocket} for incoming messages which are
 * filtered by the {@link WebSocket}. For the filtering the regular expression ({@link #getFilterRegEx()} ) is used.
 * @author Hannes Dorfmann
 *
 */
public interface Channel {
	
	/**
	 * This method is called by the {@link WebSocket} (observer-pattern) to push the new incoming message to this channel.
	 * <b>Notice:</b> This method is only called, if the incoming message matches the filer regex of this {@link Channel} ({@link #getFilterRegEx()})  
	 * @param xmlMsg
	 */
	public void onMessageReceived(String xmlMsg);
	
	/**
	 * Called if an Error has occurred
	 */
	public void onError();
	
	/**
	 * Send a message via the {@link WebSocket}.
	 * @param xmlMsg
	 * @throws WebSocketSendException If an error occurres while sending this message
	 */
	public void sendMessage(String xmlMsg) throws WebSocketSendException;
	
	/**
	 * 
	 * @return The regular expression which is used by the {@link WebSocket} to filter incoming messages.
	 * With this regular expression you can specify that only incoming messages are pushed to this {@link Channel} 
	 * that matches this regular expression.
	 */
	public String getFilterRegEx();
	
}