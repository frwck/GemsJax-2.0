package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.NotificationChannelHandler;
import org.gemsjax.server.communication.parser.NotificationMessageParser;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.notification.DeleteNotificationMessage;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsMessage;
import org.gemsjax.shared.communication.message.notification.NotificationAsReadMessage;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.notification.NotificationErrorMessage;
import org.gemsjax.shared.communication.message.notification.NotificationMessage;
import org.xml.sax.SAXException;

public class NotificationChannel implements InputChannel, OutputChannel{

	
	private CommunicationConnection connection;
	private OnlineUser user;
	private String filter;
	private Set<NotificationChannelHandler> handlers;
	
	
	public NotificationChannel(CommunicationConnection connection, OnlineUser user){
		this.connection = connection;
		connection.registerInputChannel(this);
		this.user = user;
		filter = RegExFactory.startWithTag(NotificationMessage.TAG);
		this.handlers = new LinkedHashSet<NotificationChannelHandler>();
	}
	
	public void addNotificationChannelHandler(NotificationChannelHandler h){
		handlers.add(h);
	}
	
	public void removeNotificationChannelHandler(NotificationChannelHandler h){
		handlers.remove(h);
	}
	
	@Override
	public void send(Message m) throws IOException {
		connection.send(m);
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return msg.matches(filter);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		
		NotificationMessageParser parser = new NotificationMessageParser();
		
		try {
			NotificationMessage m = parser.parse(msg.getText());
			
			
			if (m instanceof GetAllNotificationsMessage)
				for (NotificationChannelHandler h: handlers)
					h.onGetAllNotifications(((GetAllNotificationsMessage) m).getReferenceId(), user);
			
			else
			if (m instanceof NotificationAsReadMessage)
				for (NotificationChannelHandler h: handlers)
					h.onMarkNotificationAsRead(((NotificationAsReadMessage) m).getReferenceId(), ((NotificationAsReadMessage) m).getNotificationId(), user);
			
			else
			if (m instanceof DeleteNotificationMessage)
				for (NotificationChannelHandler h: handlers)
					h.onDeleteNotification(((DeleteNotificationMessage) m).getReferenceId(), ((DeleteNotificationMessage) m).getNotificationId(), user);
			
			
		} catch (SAXException e) {
			try {
				send(new NotificationErrorMessage(parser.getCurrentReferenceId(), NotificationError.PARSING));
			} catch (IOException e1) {
				// TODO What to do if message cant be sent
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			try {
				send(new NotificationErrorMessage(parser.getCurrentReferenceId(), NotificationError.PARSING));
			} catch (IOException e1) {
				// TODO What to do if message cant be sent
				e1.printStackTrace();
			}
		}
		
	}

}
