package org.gemsjax.server.module;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.gemsjax.server.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
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
import org.gemsjax.shared.communication.message.collaboration.TransactionError;
import org.gemsjax.shared.communication.message.collaboration.TransactionErrorMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.impl.MetaBaseTypeImpl;

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
			Collaborateable c = getOrLoadCollaborateable(tx.getCollaborateableId());
			
			if (c.getUsers().contains(sender.getUser())){
				
				Set<OnlineUser> subscribers = subscriptions.get(tx.getCollaborateableId());
				if (subscribers == null || !subscribers.contains(sender))
					sender.getCollaborationChannel().send(new TransactionErrorMessage(TransactionError.SUBSCRIPTION, tx.getId(), tx.getCollaborateableId()));
				else
				{	// Everything seems to be correct, so store transaction and deliver
					
					Map<Integer, Long> beforeCopy = new ConcurrentHashMap<Integer, Long>();
					copyVectorClock(c.getVectorClock(), beforeCopy);
					// Store
					try {
						
						tx.setUser(sender.getUser());
						tx.setCollaborateable(c);
						
						mergeMaxVectorClocks(tx, c);
						copyVectorClock(c.getVectorClock(), tx.getVectorClock());
//						dao.addTransaction(c, tx);
						
						// Deliver to other subscribers
						TransactionMessage tm = new TransactionMessage();
						tm.setTransaction(tx);
					
						for (OnlineUser u : subscribers)
							if (!u.equals(sender))u.getCollaborationChannel().send(tm);
						
						
						
						
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
			
			
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				sender.getCollaborationChannel().send(new TransactionErrorMessage(TransactionError.NOT_FOUND, tx.getId(), tx.getCollaborateableId()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private void  mergeMaxVectorClocks(Transaction tx, Collaborateable collaborateable){
		
		synchronized(collaborateable){
				// Adjust Vector Clock
				for (Entry<Integer, Long> e : tx.getVectorClock().entrySet()){
					long receivedVal = tx.getVectorClockEnrty(e.getKey());
					long currentVal = getVectorClockValue(e.getKey(), collaborateable);
					
					if (receivedVal>currentVal)
						collaborateable.getVectorClock().put(e.getKey(), receivedVal);
				}
			}
		}
		
		
		private long getVectorClockValue(int userId, Collaborateable collaborateable){
			Long value = collaborateable.getVectorClock().get(userId);
			if ( value==null)
				return 0;
			else
				return value;
			
	}
		
	// TODO check if synchronized is needed
	private void copyVectorClock(Map<Integer, Long> source, Map<Integer, Long> target){
		target.clear();
		for (Map.Entry<Integer, Long> e : source.entrySet())
			target.put(e.getKey(), e.getValue());
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
	
	
	
	
	
	private Collaborateable getOrLoadCollaborateable(int collaborateableId) throws NotFoundException{
		Collaborateable c = collaborateables.get(collaborateableId);
		
		if (c == null){ // id currently not in the memory, so try to laod if from the database
		{	
			c = dao.getCollaborateable(collaborateableId);
			for (Transaction t: c.getTransactions())
				mergeMaxVectorClocks(t, c);
				
		}
			
			collaborateables.put(collaborateableId, c);
		}
		
		
		return c;
		
	}
	
	
	
	/*
	
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
	
	*/
	
	
	

}
