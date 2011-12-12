package org.gemsjax.server.communication;

import java.io.IOException;

import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;

/**
 * This channel listen to incoming system
 * @author Hannes Dorfmann
 *
 */
public class UserAuthenticationChannel implements InputChannel, OutputChannel{

	
	private CommunicationConnection communicationConnection;
	private String filterRegEx;
	
		
	public UserAuthenticationChannel(CommunicationConnection connection)
	{
		this.communicationConnection = connection;
		filterRegEx = RegExFactory.startWithTag("sys");
	}
		
		@Override
	public String getFilterRegEx() {
		return filterRegEx;
	}

	@Override
	public void onMessageReceived(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(Message message) throws IOException {
		communicationConnection.send(message.toString());
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
		
	
}
