package org.gemsjax.client.communication.channel;

import java.io.IOException;

import org.gemsjax.client.communication.parser.NotificationMessageParser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.notification.NotificationMessage;

import com.google.gwt.regexp.shared.RegExp;

public class NotificationChannel implements InputChannel, OutputChannel {
	
	private CommunicationConnection connection;
	private RegExp regEx;
	private NotificationMessageParser parser;
	
	public NotificationChannel(CommunicationConnection connection){
		this.connection = connection;
		this.regEx = RegExp.compile(RegExFactory.startWithTag(NotificationMessage.TAG));
		this.parser = new NotificationMessageParser();
	}

	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return regEx.test(msg);
	}

}
