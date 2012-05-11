package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.gemsjax.client.communication.channel.CollaborationChannel;
import org.gemsjax.client.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.client.module.handler.CollaborationModuleHandler;
import org.gemsjax.shared.UUID;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.collaboration.TransactionImpl;
import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableError;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableMessage;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;
import org.gemsjax.shared.communication.message.collaboration.UnsubscribeCollaborateableMessage;
import org.gemsjax.shared.user.User;


public class CollaborationModule implements CollaborationChannelHandler{
	
//	private Collaborateable collaborateable;
	private int collaborateableId;
	private CollaborationChannel channel;
	private List<Transaction> transactions;
	private Map<Integer, Long> vectorClock;
	private Set<CollaborationModuleHandler> handlers;
	private TransactionProcessor transactionProcessor;
	
	private User user;
	private static int moduleIdCounter = 0;
	private int moduleId;
	
	private String subscribeReferenceId;
	private int refIdCounter = 0;
	
	public CollaborationModule(User user, int collaborateableId, CollaborationChannel channel){
		this.collaborateableId = collaborateableId;
		this.user = user;
		this.channel = channel;
		
		this.handlers = new LinkedHashSet<CollaborationModuleHandler>();
		
		transactions = new LinkedList<Transaction>();
		vectorClock = new TreeMap<Integer, Long>();
		
		this.transactionProcessor = new TransactionProcessor(user);
		
		channel.addCollaborationChannelHandler(this);
		moduleIdCounter++;
		moduleId = moduleIdCounter;
	}
	
	
	private String nextRefId(){
		refIdCounter++;
		return this.toString()+"-"+refIdCounter;
	}
	
	
	public void addCollaborationModuleHandler(CollaborationModuleHandler h){
		handlers.add(h);
	}
	
	public void removeCollaborationModuleHandler(CollaborationModuleHandler h){
		handlers.remove(h);
	}
	


	@Override
	public synchronized void onTransactionReceived(Transaction tx) {
	
		mergeMaxVectorClocks(tx);
		
		
		
		fireUpdated();
		
	}
	
	private void fireUpdated(){
		for (CollaborationModuleHandler h : handlers)
			h.onCollaborateableUpdated();
	}
	
	
	private void mergeMaxVectorClocks(Transaction tx){
		
		// Adjust Vector Clock
		for (Entry<Integer, Long> e : tx.getVectorClock().entrySet()){
			long receivedVal = tx.getVectorClockEnrty(e.getKey());
			long currentVal = getVectorClockValue(e.getKey());
			
			if (receivedVal>currentVal)
				vectorClock.put(e.getKey(), receivedVal);
		}
	}
	
	
	private long getVectorClockValue(int userId){
		Long value = vectorClock.get(userId);
		if ( value==null)
			return 0;
		else
			return value;
		
	}
	
	
	public synchronized void sendAndCommitTransaction(List<Command> commands) throws IOException{
		
		Transaction tx = new TransactionImpl(UUID.generate());
		tx.setCommands(commands);
		tx.setUserId(user.getId());
		tx.setCollaborateableId(collaborateableId);
		
		long value = getVectorClockValue(user.getId()) + 1;
		vectorClock.put(user.getId(), value);
		
		
		for (Entry<Integer, Long> e : vectorClock.entrySet())
			tx.setVectorClockEntry(e.getKey(), e.getValue());
	
		channel.send(new TransactionMessage(tx));
		transactionProcessor.executeTransaction(tx);
		
	}
	
	
	public void historyStepBack(){
		
	}
	
	public void historyStepForward(){
		
	}
	
	@Override
	public String toString(){
		return super.toString()+" VC : "+vectorClock;
		
	}

	
	public void subscribe() throws IOException{
		String refId = nextRefId();
		SubscribeCollaborateableMessage msg = new SubscribeCollaborateableMessage();
		msg.setReferenceId(refId);
		msg.setCollaborateableId(collaborateableId);
		channel.send(msg);
	}
	
	
	public void unsubscribe() throws IOException{
		String refId = nextRefId();
		UnsubscribeCollaborateableMessage msg = new UnsubscribeCollaborateableMessage();
		msg.setReferenceId(refId);
		msg.setCollaborateableId(collaborateableId);
		channel.send(msg);
	}
	

	@Override
	public void onSubscribeSuccessful(String referenceId,
			List<Transaction> transactions, List<Collaborator> collaborators) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSubscribeError(String referenceId,
			SubscribeCollaborateableError error) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTransactionError(String transactionId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onUnsubscribeSuccessful(String referenceId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCollaboratorJoined(Collaborator c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCollaboratorLeft(Collaborator c) {
		// TODO Auto-generated method stub
		
	}
	
	

}
