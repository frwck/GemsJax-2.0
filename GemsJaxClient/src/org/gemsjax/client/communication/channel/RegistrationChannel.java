package org.gemsjax.client.communication.channel;

import java.io.IOException;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * A {@link RegistrationChannel} interacts with the server by sending {@link NewRegistrationMessage}s and receiving
 * {@link RegistrationAnswerMessage}s from the server
 * to create a new {@link RegisteredUser} account.
 * @author Hannes Dorfmann
 *
 */
public class RegistrationChannel implements InputChannel, OutputChannel{

	
	private CommunicationConnection connection;
	
	
	public RegistrationChannel(CommunicationConnection connection)
	{
		this.connection = connection;
	}
	
	
	
	
	
	@Override
	public String getFilterRegEx() {
		return null;
	}

	@Override
	public void onMessageReceived(String msg) {
		
		
	}

	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	
	
	
	
}
