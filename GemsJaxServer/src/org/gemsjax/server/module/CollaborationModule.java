package org.gemsjax.server.module;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.gemsjax.server.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.server.persistence.collaboration.TransactionImpl;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.message.collaboration.CollaboratorJoinedMessage;
import org.gemsjax.shared.communication.message.collaboration.CollaboratorLeftMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableError;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableErrorMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableSuccessfulMessage;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.impl.MetaBaseTypeImpl;
import org.gemsjax.shared.user.User;

public class CollaborationModule implements CollaborationChannelHandler{
	
	private Map<Integer, Collaborateable> collaborateables;
	private Map<Integer, Set<OnlineUser> > subscriptions;
	
	private List<MetaBaseType> metaBaseTypes;
	
	private CollaborateableDAO dao;
	
	private static CollaborationModule INSTANCE = new CollaborationModule();
	
	
	public CollaborationModule(){
		collaborateables = new ConcurrentHashMap<Integer, Collaborateable>();
		subscriptions = new ConcurrentHashMap<Integer, Set<OnlineUser>>();
		dao = new HibernateCollaborateableDAO();
		
		generateMetaBaseTypes();
	}
	
	private void generateMetaBaseTypes(){

		metaBaseTypes = new LinkedList<MetaBaseType>();
		metaBaseTypes.add(new MetaBaseTypeImpl("1", "String"));
		metaBaseTypes.add(new MetaBaseTypeImpl("2", "Integer"));
		metaBaseTypes.add(new MetaBaseTypeImpl("3", "Float"));
		metaBaseTypes.add(new MetaBaseTypeImpl("4", "Boolean"));
		
	}
	
	public static CollaborationModule getInstance(){
		return INSTANCE;
	}

	@Override
	public void onTransactionReceived(Transaction tx, OnlineUser sender) {
		
	}

	@Override
	public void onSubscribe(int collaborateableId, String refId, OnlineUser sender) {
		
		try {
			Collaborateable c = getOrLoadCollaborateable(collaborateableId);
			
			if (c.getUsers().contains(sender.getUser()) || c.getOwner().equals(sender.getUser()))
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
							CollaboratorJoinedMessage m =new CollaboratorJoinedMessage(new Collaborator(sender.getId(), sender.getUser().getDisplayedName()));
							m.setCollaborateableId(collaborateableId);
							u.getCollaborationChannel().send(m);
						}
						catch(IOException e) {}
					
					subscribers.add(sender);
							
				}
				
				// Generate answer message
				
				SubscribeCollaborateableSuccessfulMessage m = new SubscribeCollaborateableSuccessfulMessage();
				m.setReferenceId(refId);
				m.setCollaborateableId(collaborateableId);
				m.setMetaBaseTypes(metaBaseTypes);
				
				LinkedList<Transaction> transactions = new LinkedList<Transaction>();
				for (Transaction t : c.getTransactions())
				{
					transactions.add(transformToClientFormat((TransactionImpl) t));
				}
				
				m.setTransactions(transactions);
				
				List<Collaborator> collaborators = new LinkedList<Collaborator>();
				for (OnlineUser u : subscribers)
					if (u != sender)
						collaborators.add(new Collaborator(u.getId(), u.getUser().getDisplayedName()));
				
				m.setCollaborators(collaborators);
				sender.getCollaborationChannel().send(m);
				
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
	public void onUnsubscribe(int collaboratebaleId, String refId, OnlineUser sender) {
		
		Set<OnlineUser> subscribers =subscriptions.get(collaboratebaleId);
		
		if (subscribers != null)
			subscribers.remove(sender);
		
		
		if (subscribers.isEmpty())
			collaborateables.remove(collaboratebaleId);
		
	}
	
	
	
	public void unSubscribeAllOf(OnlineUser user){
		
		for (Collaborateable c : user.getUser().getCollaborateables())
		{
			Set<OnlineUser> subscribers =subscriptions.get(c.getId());
			
			if(subscribers == null)
				continue;
			
			subscribers.remove(user);
			
			
			CollaboratorLeftMessage m = new CollaboratorLeftMessage();
			m.setCollaborator(new Collaborator(user.getId(), user.getUser().getDisplayedName()));
			m.setCollaborateableId(c.getId());
			
			for (OnlineUser u : subscribers){
				try {
					u.getCollaborationChannel().send(m);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			if (subscribers.isEmpty())
				collaborateables.remove(c.getId());
		}
	}
	
	
	
	
	
	private Collaborateable getOrLoadCollaborateable(int collaborateableId) throws NotFoundException{
		Collaborateable c = collaborateables.get(collaborateableId);
		
		if (c == null){ // id currently not in the memory, so try to laod if from the database
			c = dao.getCollaborateable(collaborateableId);
			
			collaborateables.put(collaborateableId, c);
		}
		
		
		return c;
		
	}
	
	
	
	
	
	private org.gemsjax.shared.collaboration.TransactionImpl transformToClientFormat(TransactionImpl t){
		
		org.gemsjax.shared.collaboration.TransactionImpl tx = new org.gemsjax.shared.collaboration.TransactionImpl();
		tx.setUserId(t.getUserId());
		tx.setCollaborateableId(t.getCollaborateableId());
		tx.setCommands(t.getCommands());
		
		for (Map.Entry<User, Long> e : t.getUserVectorClock().entrySet()) {
			tx.setVectorClockEntry(e.getKey().getId(), e.getValue());
		}
		
		tx.setId(t.getId());
		
		return tx;
	}
	
	
	
	private TransactionImpl transformToServerFormat(org.gemsjax.shared.collaboration.TransactionImpl t) throws NotFoundException{
		
		TransactionImpl tx = new TransactionImpl();
		tx.setId(t.getId());
		tx.setUserId(t.getUserId());
		tx.setUser(OnlineUserManager.getInstance().getOrLoadUser(t.getUserId()));
		tx.setCollaborateableId(t.getCollaborateableId());
		tx.setCollaborateable(getOrLoadCollaborateable(t.getCollaborateableId()));
		tx.setCommands(t.getCommands());
		
		for (Map.Entry<Integer, Long> e : t.getVectorClock().entrySet()) {
			tx.getUserVectorClock().put(OnlineUserManager.getInstance().getOrLoadUser(e.getKey()), e.getValue());
		}
		
		tx.setId(t.getId());
		
		return tx;
	}
	
	
	
	

}
