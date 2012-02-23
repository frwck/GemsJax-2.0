package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.NotificationChannelHandler;
import org.gemsjax.client.communication.parser.NotificationMessageParser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsAnswerMessage;
import org.gemsjax.shared.communication.message.notification.LiveNotificationMessage;
import org.gemsjax.shared.communication.message.notification.NotificationErrorMessage;
import org.gemsjax.shared.communication.message.notification.NotificationMessage;
import org.gemsjax.shared.communication.message.notification.SuccessfulNotificationMessage;

import com.google.gwt.regexp.shared.RegExp;

public class NotificationChannel implements InputChannel, OutputChannel {
	
	private CommunicationConnection connection;
	private RegExp regEx;
	private NotificationMessageParser parser;
	private Set<NotificationChannelHandler> handlers;
	
	public NotificationChannel(CommunicationConnection connection){
		this.connection = connection;
		this.regEx = RegExp.compile(RegExFactory.startWithTag(NotificationMessage.TAG));
		this.parser = new NotificationMessageParser();
		handlers = new LinkedHashSet<NotificationChannelHandler>();
	}

	
	public void addNotificationChannelHandler(NotificationChannelHandler h){
		handlers.add(h);
	}
	
	public void removeNotificationChannelHandler(NotificationChannelHandler h){
		handlers.remove(h);
	}
	
	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		
		NotificationMessage m = parser.parseMessage(msg.getText());
		
		if (m instanceof GetAllNotificationsAnswerMessage)
			for (NotificationChannelHandler h: handlers)
				h.onGetAllAnswer((GetAllNotificationsAnswerMessage) m);
		else
		if (m instanceof LiveNotificationMessage)
			for (NotificationChannelHandler h: handlers)
				h.onLiveMessageReceived((LiveNotificationMessage)m);
		else
		if (m instanceof NotificationErrorMessage)
			for (NotificationChannelHandler h: handlers)
				h.onError(((NotificationErrorMessage) m).getReferenceId(), ((NotificationErrorMessage) m).getError());
		else
		if (m instanceof SuccessfulNotificationMessage)
			for (NotificationChannelHandler h: handlers)
				h.onSuccess(((SuccessfulNotificationMessage) m).getReferenceId());
		
		
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return regEx.test(msg);
	}

}
