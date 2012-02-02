package org.gemsjax.server.communication.channel;

import java.io.IOException;

import org.gemsjax.server.communication.HttpCommunicationConnection;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.search.ReferenceableSearchMessage;

public class GlobalSearchChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private String regex;
	
	public GlobalSearchChannel(CommunicationConnection connection)
	{
		this.connection = connection;
		connection.registerInputChannel(this);
		regex = RegExFactory.startWithTag(ReferenceableSearchMessage.TAG);
	}
	
	
	@Override
	public void send(Message m) throws IOException {
		connection.send(m);
	}

	
	
	private void setHttpResponseStatusCode(int httpStatusCode)
	{
		if (connection instanceof HttpCommunicationConnection)
			((HttpCommunicationConnection)connection).setResponseStatusCode(httpStatusCode);
	}
	
	
	@Override
	public boolean isMatchingFilter(String msg) {
		return msg.matches(regex);
	}

	@Override
	public void onMessageReceived(InputMessage arg0) {
		// TODO Auto-generated method stub
		
	}

}
