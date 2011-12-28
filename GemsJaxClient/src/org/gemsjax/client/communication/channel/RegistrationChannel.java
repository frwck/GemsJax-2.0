package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.RegistrationChannelHandler;
import org.gemsjax.client.communication.parser.SystemMessageParser;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.gemsjax.shared.user.RegisteredUser;

import com.smartgwt.client.util.SC;

/**
 * A {@link RegistrationChannel} interacts with the server by sending {@link NewRegistrationMessage}s and receiving
 * {@link RegistrationAnswerMessage}s from the server
 * to create a new {@link RegisteredUser} account.
 * @author Hannes Dorfmann
 *
 */
public class RegistrationChannel implements InputChannel, OutputChannel{

	
	private CommunicationConnection connection;
	private Set<RegistrationChannelHandler> handlers;
	private SystemMessageParser parser;
	
	
	public RegistrationChannel(CommunicationConnection connection) throws IOException
	{
		this.connection = connection;
		this.connection.registerInputChannel(this);
		
		this.handlers = new LinkedHashSet<RegistrationChannelHandler>();
		parser = new SystemMessageParser();
		
		this.connection.connect();
	}
	
	
	public void addRegistrationChannelHandler(RegistrationChannelHandler handler)
	{
		handlers.add(handler);
	}
	
	public void removeRegistrationChannelHandler(RegistrationChannelHandler handler)
	{
		handlers.remove(handler);
	}
	
	
	@Override
	public String getFilterRegEx() {
		return null;
	}

	@Override
	public void onMessageReceived(String msg) {
		
		SC.logWarn("Received "+msg); // TODO remove
		
		RegistrationAnswerMessage m = (RegistrationAnswerMessage) parser.parseMessage(msg);
		
		if (m.getAnswerStatus() == RegistrationAnswerStatus.OK)
			fireSuccessful();
		else
			fireFailed(m.getAnswerStatus(), m.getFailString());
		
	}

	/**
	 * Inform all {@link RegistrationChannelHandler}s that, the registration was successful.
	 * This is done, by calling {@link RegistrationChannelHandler#onRegistrationSuccessful()}
	 */
	private void fireSuccessful()
	{
		for (RegistrationChannelHandler h : handlers)
			h.onRegistrationSuccessful();
	}
	

	/**
	 * Inform all {@link RegistrationChannelHandler}s that, the registration has failed.
	 * This is done, by calling {@link RegistrationChannelHandler#onRegistrationFailed(RegistrationAnswerStatus, String)}
	 */
	private void fireFailed(RegistrationAnswerStatus status, String fail)
	{
		for (RegistrationChannelHandler h : handlers)
			h.onRegistrationFailed(status, fail);
	}
	
	
	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	
	
	
	
}
