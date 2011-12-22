package org.gemsjax.client.communication.channel;

import java.io.IOException;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;

public class RegistrationChannel implements InputChannel, OutputChannel{

	
	private CommunicationConnection connection;
	
	
	public RegistrationChannel(CommunicationConnection connection)
	{
		this.connection = connection;
	}
	
	
	
	
	
	@Override
	public String getFilterRegEx() {
		// TODO Auto-generated method stub
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
