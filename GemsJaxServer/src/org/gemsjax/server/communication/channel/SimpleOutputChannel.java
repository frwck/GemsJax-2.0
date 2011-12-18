package org.gemsjax.server.communication.channel;

import java.io.IOException;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;

/**
 * This is a simple {@link OutputChannel} to send {@link Message}s from Server to Client via a {@link CommunicationConnection}.
 * @author Hannes Dorfmann
 *
 */
public class SimpleOutputChannel implements OutputChannel{

	private CommunicationConnection communicationConnection;
	
	public SimpleOutputChannel(CommunicationConnection connection)
	{
		this.communicationConnection = connection;
	}
	
	
	@Override
	public void send(Message message) throws IOException {
		
		communicationConnection.send(message);
	
	}

}
