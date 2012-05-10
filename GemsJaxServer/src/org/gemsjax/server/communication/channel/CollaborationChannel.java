package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.collaboration.CollaborationMessage;

public class CollaborationChannel implements InputChannel, OutputChannel {

	private CommunicationConnection connection;
	private OnlineUser user;
	private Set<CollaborationChannelHandler> handlers;
	
	
	public CollaborationChannel(CommunicationConnection connection, OnlineUser user){
		this.user = user;
		this.connection = connection;
		connection.registerInputChannel(this, CollaborationMessage.TYPE);
		handlers = new LinkedHashSet<CollaborationChannelHandler>();
	}
	
	
	public void addCollaborationChannelHandler(CollaborationChannelHandler h )
	{
		handlers.add(h);
	}
	
	public void removeCollaborationChannelHandler(CollaborationChannelHandler h)

	{
		handlers.remove(h);
	}
	
	@Override
	public void send(Message arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMatchingFilter(String arg0) {
		// TODO Auto-generated method stub
		return false;
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