package org.gemsjax.server.module;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.gemsjax.server.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.message.CollaboratorJoinedMessage;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableError;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableErrorMessage;

public class CollaborationModule implements CollaborationChannelHandler {
	
	private Map<Integer, Collaborateable> collaborateables;
	private Map<Integer, Set<OnlineUser> > subscriptions;
	
	private CollaborateableDAO dao;
	
	private static CollaborationModule INSTANCE = new CollaborationModule();
	
	
	public CollaborationModule(){
		collaborateables = new ConcurrentHashMap<Integer, Collaborateable>();
		subscriptions = new ConcurrentHashMap<Integer, Set<OnlineUser>>();
		dao = new HibernateCollaborateableDAO();
	}
	
	public static CollaborationModule getInstance(){
		return INSTANCE;
	}

	@Override
	public void onTransactionReceived(Transaction tx, OnlineUser sender) {
		
	}

	@Override
	public void onSubscribe(int collaborateableId, OnlineUser sender) {
		
		try {
			Collaborateable c = getOrLoadCollaborateable(collaborateableId);
			
			if (c.getUsers().contains(sender.getUser()))
			{
				Set<OnlineUser> subscribers = subscriptions.get(collaborateableId);
				if (subscribers == null) // The first User who subscribes
				{
					subscribers = new ConcurrentHashSet<OnlineUser>();
					subscribers.add(sender);
					subscriptions.put(collaborateableId, subscribers);
				}
				else
				{
					
					// Inform all the other subscribers
					for (OnlineUser u : subscribers)
						try{
							u.getCollaborationChannel().send(new CollaboratorJoinedMessage(new Collaborator(sender.getId(), sender.getUser().getDisplayedName())));
						}
						catch(IOException e) {}
					
					subscribers.add(sender);
							
				}
				
			}
			else // Not a collaborative user, 
			{
				sender.getCollaborationChannel().send(new SubscribeCollaborateableErrorMessage(SubscribeCollaborateableError.PERMISSION_DENIED));
			}
			
		} catch (NotFoundException e) {
			try {
				sender.getCollaborationChannel().send(new SubscribeCollaborateableErrorMessage(SubscribeCollaborateableError.NOT_FOUND));
			} catch (IOException e1) {
				// What to do if message cant be sent
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// What to do if maessage cant be sent
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUnsubscribe(int collaboratebaleId, OnlineUser sender) {
		
		
	}
	
	
	
	private Collaborateable getOrLoadCollaborateable(int collaborateableId) throws NotFoundException{
		Collaborateable c = collaborateables.get(collaborateableId);
		
		if (c == null){ // id currently not in the memory, so try to laod if from the database
			c = dao.getCollaborateable(collaborateableId);
			
			collaborateables.put(collaborateableId, c);
		}
		
		
		return c;
		
	}
	
	
	

}
