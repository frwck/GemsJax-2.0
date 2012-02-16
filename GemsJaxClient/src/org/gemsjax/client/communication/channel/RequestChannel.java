package org.gemsjax.client.communication.channel;

import java.io.IOException;

import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.request.RequestMessage;

import com.google.gwt.regexp.shared.RegExp;

public class RequestChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private RegExp filer;
	
	public RequestChannel(CommunicationConnection connection)
	{
		this.connection = connection;
		filer = RegExp.compile(RegExFactory.startWithTag(RequestMessage.TAG));
		connection.registerInputChannel(this);
	}

	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return filer.test(msg);
	}
	
}
