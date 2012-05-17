package org.gemsjax.server.module;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.gemsjax.server.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.message.collaboration.CollaboratorJoinedMessage;
import org.gemsjax.shared.communication.message.collaboration.CollaboratorLeftMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableError;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableErrorMessage;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableSuccessfulMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionError;
import org.gemsjax.shared.communication.message.collaboration.TransactionErrorMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.exception.MetaBaseTypeException;
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
		
		try {
			
			Collaborateable c = tx.getCollaborateable();
			
			synchronized (c) {
			
				if (isCollaborativeUser(c, sender.getUser())){
					
					Set<OnlineUser> subscribers = subscriptions.get(tx.getCollaborateableId());
					if (subscribers == null || !subscribers.contains(sender))
						sender.getCollaborationChannel().send(new TransactionErrorMessage(TransactionError.SUBSCRIPTION, tx.getId(), tx.getCollaborateableId()));
					else
					{	// Everything seems to be correct, so store transaction and deliver
						
						Map<Integer, Long> beforeCopy = new ConcurrentHashMap<Integer, Long>();
						copyVectorClock(c.getVectorClock(), beforeCopy);
						
						
						TransactionProcessor tp = new TransactionProcessor();
						try {
							tp.executeTransaction(tx);
						} catch (ManipulationException e1) {
							// Manipulation Exceptions are allowed
						}
						
						copyVectorClock(c.getVectorClock(), tx.getVectorClock());
						// Store
						try {
							dao.addTransaction(c, tx);
							
							// Deliver to other subscribers
							TransactionMessage tm = new TransactionMessage();
							tm.setTransaction(tx);
						
							for (OnlineUser u : subscribers)
								if (!u.equals(sender))
									try{
										u.getCollaborationChannel().send(tm);
									}catch(IOException e){
										
									}
							
						} catch (Exception e) { // DAOException
							
							copyVectorClock(beforeCopy, c.getVectorClock());
							e.printStackTrace();
							sender.getCollaborationChannel().send(new TransactionErrorMessage(TransactionError.DATABASE, tx.getId(), tx.getCollaborateableId()));
							
						}
						
						// 
						
					}
					
				}
				else
				{
					sender.getCollaborationChannel().send(new TransactionErrorMessage(TransactionError.PERMISSION_DENIED, tx.getId(), tx.getCollaborateableId()));
				}
				
			} // End synchronized
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
		
	

	@Override
	public void onSubscribe(int collaborateableId, String refId, OnlineUser sender) {
		
		try {
			Collaborateable c = getOrLoadCollaborateable(collaborateableId);
			
			if (isCollaborativeUser(c, sender.getUser()) || c.getOwner().equals(sender.getUser()))
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
					transactions.add(t);
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
				SubscribeCollaborateableErrorMessage sm = new SubscribeCollaborateableErrorMessage(SubscribeCollaborateableError.PERMISSION_DENIED);
				sm.setCollaborateableId(collaborateableId);
				sm.setReferenceId(refId);
				sender.getCollaborationChannel().send(sm);
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
		
		if (user == null) // It seems that this happens from time to time
			return;
		
		if (user.getUser().getCollaborateables()!=null)
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
	
	
	
	
	
	public Collaborateable getOrLoadCollaborateable(int collaborateableId) throws NotFoundException{
		Collaborateable c = collaborateables.get(collaborateableId);
		
		if (c == null){ // id currently not in the memory, so try to laod if from the database
			c = dao.getCollaborateable(collaborateableId);
			
			if (c instanceof MetaModel)
				for (MetaBaseType bt: metaBaseTypes)
					try {
						((MetaModel)c).addBaseType(bt);
					} catch (MetaBaseTypeException e1) {
						// Its Possible, that a MetaBaseType has been removed?
						e1.printStackTrace();
					}
			
			try {
				TransactionProcessor tp = new TransactionProcessor();
				tp.executeTransactions(c.getTransactions());
				
			} catch (ManipulationException e) {
				// ManipulationExceptions are allowed
			}
				
			collaborateables.put(collaborateableId, c);
		}
		
		
		return c;
		
	}
	
	
	// TODO check if synchronized is needed
	private void copyVectorClock(Map<Integer, Long> source, Map<Integer, Long> target){
		target.clear();
		for (Map.Entry<Integer, Long> e : source.entrySet())
			target.put(e.getKey(), e.getValue());
	}
	
	
	
	private boolean isCollaborativeUser(Collaborateable c, User user){
		if (c.getOwner().getId() == user.getId())
			return true;
		
		for (User u : c.getUsers())
			if (u.getId() == user.getId())
				return true;
		
		return false;
	}

}
