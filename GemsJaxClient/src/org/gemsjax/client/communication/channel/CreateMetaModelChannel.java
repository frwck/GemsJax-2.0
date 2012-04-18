package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.CreateCollaborateableChannelHandler;
import org.gemsjax.client.communication.parser.CollaborateableFileMessageParser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileErrorMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileSuccessfulMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;

import com.google.gwt.regexp.shared.RegExp;

public class CreateMetaModelChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private RegExp regEx;
	private Set<CreateCollaborateableChannelHandler> handlers;
	
	public CreateMetaModelChannel(CommunicationConnection connection){
		this.connection = connection;
		handlers = new LinkedHashSet<CreateCollaborateableChannelHandler>();
		connection.registerInputChannel(this);
		regEx = RegExp.compile(RegExFactory.startWithTag(ReferenceableCollaborateableFileMessage.TAG));
	}
	
	public void addCollaborateableChannelHandler(CreateCollaborateableChannelHandler h){
		handlers.add(h);
	}
	
	public void removeCollaborateChannelHandler(CreateCollaborateableChannelHandler h)
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
			for (CreateCollaborateableChannelHandler h : handlers)
				h.onSuccessful(m.getReferenceId());
		
		else
		if (m instanceof CollaborateableFileErrorMessage)
			for (CreateCollaborateableChannelHandler h: handlers)
				h.onError(m.getReferenceId(), ((CollaborateableFileErrorMessage) m).getError());
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return regEx.test(msg);
	}
	

}
