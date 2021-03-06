package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.server.module.CollaborationModule;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.collaboration.CollaborationMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionError;
import org.gemsjax.shared.communication.message.collaboration.TransactionErrorMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;
import org.gemsjax.shared.communication.message.collaboration.UnsubscribeCollaborateableMessage;

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
		connection.send(arg0);
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
	public void onMessageRecieved(Message m) {
	
		if (m instanceof TransactionMessage){
		
			TransactionMessage mm = (TransactionMessage)m;
			
			try {
				Collaborateable col = CollaborationModule.getInstance().getOrLoadCollaborateable(mm.getTransaction().getCollaborateableId());
			
				mm.getTransaction().setUser(user.getUser());
				mm.getTransaction().setCollaborateable(col);
				
			for(Command c: mm.getTransaction().getCommands()){
				c.setTransaction(mm.getTransaction());
				c.setCollaborateable(col);
			}
			
			
			for(CollaborationChannelHandler h : handlers)
				h.onTransactionReceived(mm.getTransaction(), user);
			
			} catch (NotFoundException e) {
				e.printStackTrace();
				try {
					send(new TransactionErrorMessage(TransactionError.NOT_FOUND, mm.getTransaction().getId(), mm.getTransaction().getCollaborateableId()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		else
		if (m instanceof SubscribeCollaborateableMessage)
			for(CollaborationChannelHandler h : handlers)
				h.onSubscribe(((SubscribeCollaborateableMessage) m).getCollaborateableId(), ((SubscribeCollaborateableMessage) m).getReferenceId(), user);
		
		else
		if (m instanceof UnsubscribeCollaborateableMessage)
			for(CollaborationChannelHandler h : handlers)
				h.onUnsubscribe(((UnsubscribeCollaborateableMessage) m).getCollaborateableId(), ((UnsubscribeCollaborateableMessage) m).getReferenceId(), user);
		
	}
	
	
	public CommunicationConnection getConnection(){
		return connection;
	}

}
