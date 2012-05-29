package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.collaboration.CollaborationMessage;
import org.gemsjax.shared.communication.message.collaboration.CollaboratorJoinedMessage;
import org.gemsjax.shared.communication.message.collaboration.CollaboratorLeftMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableErrorMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableSuccessfulMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;



/**
 * A {@link CollaborationChannel} is a {@link InputChannel} that receives collaboration {@link Message}
 * @author Hannes Dorfmann
 *
 */
public class CollaborationChannel implements InputChannel, OutputChannel {
	
	private Set<CollaborationChannelHandler> handlers;
	private CommunicationConnection connection;
	private Collaborateable collaborateable;
	
	public CollaborationChannel(CommunicationConnection connection, Collaborateable collaborateable)
	{
		this.collaborateable = collaborateable;
		this.handlers = new LinkedHashSet<CollaborationChannelHandler>();
		this.connection = connection;
		connection.registerInputChannel(this, CollaborationMessage.TYPE);
	}

	public void addCollaborationChannelHandler(CollaborationChannelHandler h){
		this.handlers.add(h);
	}
	
	public void removeCollaborationChannelHandler(CollaborationChannelHandler h){
		this.handlers.remove(h);
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return false;
	}


	@Override
	public void onMessageReceived(InputMessage msg) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}


	@Override
	public void onMessageRecieved(Message msg) {
		
		int collaborateableId = collaborateable.getId();
		
		if (msg instanceof TransactionMessage && ((TransactionMessage) msg).getTransaction().getCollaborateableId() == collaborateableId){
			
			Transaction t = ((TransactionMessage)msg).getTransaction();
			
			t.setCollaborateable(collaborateable);
			
			for (Command c : t.getCommands())
				c.setCollaborateable(collaborateable);
			
			for (CollaborationChannelHandler h: handlers)
				h.onTransactionReceived(((TransactionMessage)msg).getTransaction());
		}
		else
		if (msg instanceof SubscribeCollaborateableSuccessfulMessage && ((SubscribeCollaborateableSuccessfulMessage)msg).getCollaborateableId() == collaborateableId)
		{
			SubscribeCollaborateableSuccessfulMessage m = (SubscribeCollaborateableSuccessfulMessage) msg;
			
			for (Transaction t : m.getTransactions()){
		
				t.setCollaborateable(collaborateable);
				
				for (Command c : t.getCommands())
					c.setCollaborateable(collaborateable);
			}
			
			for (CollaborationChannelHandler h: handlers)
				h.onSubscribeSuccessful(m.getReferenceId(), m.getTransactions(), m.getCollaborators(), m.getAllCollaborators(),  m.getMetaBaseTypes());
		}
		else
		if (msg instanceof SubscribeCollaborateableErrorMessage && ((SubscribeCollaborateableErrorMessage)msg).getCollaborateableId() == collaborateableId)
		{
			SubscribeCollaborateableErrorMessage m = (SubscribeCollaborateableErrorMessage) msg;
			for (CollaborationChannelHandler h: handlers)
				h.onSubscribeError(m.getReferenceId(),m.getError());
		}	
		else
		if (msg instanceof CollaboratorJoinedMessage && ((CollaboratorJoinedMessage)msg).getCollaborateableId() == collaborateableId)
		{
			CollaboratorJoinedMessage m = (CollaboratorJoinedMessage) msg;
			for (CollaborationChannelHandler h: handlers)
				h.onCollaboratorJoined(m.getCollaborator());
		}	
		else
		if (msg instanceof CollaboratorLeftMessage && ((CollaboratorLeftMessage)msg).getCollaborateableId() == collaborateableId)
		{
			CollaboratorLeftMessage m = (CollaboratorLeftMessage) msg;
			for (CollaborationChannelHandler h: handlers)
				h.onCollaboratorLeft(m.getCollaborator());
		}	
		// TODO other Messages
		
	}

}
