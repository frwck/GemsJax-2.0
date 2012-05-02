package org.gemsjax.server.communication.channel;

import java.io.IOException;

import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;

public class MetaModelAdministrationChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private String filterRegEx;
	
	public MetaModelAdministrationChannel(CommunicationConnection connection)
	{
		this.connection = connection;
	}
	
	
	@Override
	public void send(Message arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return msg.matches(filterRegEx);
	}

	@Override
	public void onMessageReceived(InputMessage arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMessageRecieved(Message arg0) {
		// TODO Auto-generated method stub
		
	}

}
