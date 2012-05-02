package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.CollaborateableFileChannelHandler;
import org.gemsjax.client.communication.parser.CollaborateableFileMessageParser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileErrorMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileSuccessfulMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.GetAllCollaborateablesAnswerMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;

import com.google.gwt.regexp.shared.RegExp;

public class CollaborateableFileChannel<T extends Collaborateable> implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private RegExp regEx;
	private Set<CollaborateableFileChannelHandler> handlers;
	
	public CollaborateableFileChannel(CommunicationConnection connection){
		this.connection = connection;
		handlers = new LinkedHashSet<CollaborateableFileChannelHandler>();
		connection.registerInputChannel(this);
		regEx = RegExp.compile(RegExFactory.startWithTag(ReferenceableCollaborateableFileMessage.TAG));
	}
	
	public void addCollaborateableChannelHandler(CollaborateableFileChannelHandler h){
		handlers.add(h);
	}
	
	public void removeCollaborateChannelHandler(CollaborateableFileChannelHandler h)
	{
		handlers.remove(h);
	}
	
	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		ReferenceableCollaborateableFileMessage m = new CollaborateableFileMessageParser().parseMessage(msg.getText());
		
		if (m instanceof CollaborateableFileSuccessfulMessage)
			for (CollaborateableFileChannelHandler<T> h : handlers)
				h.onSuccessful(m.getReferenceId());
		
		else
		if (m instanceof CollaborateableFileErrorMessage)
			for (CollaborateableFileChannelHandler<T> h: handlers)
				h.onError(m.getReferenceId(), ((CollaborateableFileErrorMessage) m).getError());
		else
		if (m instanceof GetAllCollaborateablesAnswerMessage)
			for (CollaborateableFileChannelHandler<T> h: handlers)
				h.onGetAllResultReceived(m.getReferenceId(), (Set<T>)(((GetAllCollaborateablesAnswerMessage) m).getResult()));
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return regEx.test(msg);
	}

	@Override
	public void onMessageRecieved(Message msg) {
		// TODO Auto-generated method stub
		
	}
	

}
