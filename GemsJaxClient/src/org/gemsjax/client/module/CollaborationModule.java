package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.gemsjax.client.communication.channel.CollaborationChannel;
import org.gemsjax.client.communication.channel.handler.CollaborationChannelHandler;
import org.gemsjax.client.module.handler.CollaborationModuleHandler;
import org.gemsjax.client.util.UUID;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.collaboration.TransactionImpl;
import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.communication.message.collaboration.TransactionMessage;


public class CollaborationModule implements CollaborationChannelHandler{
	
	private int collaborateableId;
	private Collaborateable collaborateable;
	private CollaborationChannel channel;
	private List<Transaction> transactions;
	private Map<Integer, Long> vectorClock;
	
	private Set<CollaborationModuleHandler> handlers;
	
	
	private int  myUserId;
	
	public CollaborationModule(int myUserId, int collaborateableId, CollaborationChannel channel){
		this.collaborateableId = collaborateableId;
		this.myUserId = myUserId;
		this.channel = channel;
		
		this.handlers = new LinkedHashSet<CollaborationModuleHandler>();
		
		transactions = new LinkedList<Transaction>();
		vectorClock = new ConcurrentHashMap<Integer, Long>();
		
		channel.addCollaborationChannelHandler(this);
		
		
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
	
	
	public synchronized void sendTransaction(List<Command> commands) throws IOException{
		Transaction tx = new TransactionImpl(UUID.generate());
		tx.setCommands(commands);
		tx.setUserId(myUserId);
		tx.setCollaborateableId(collaborateableId);
		
		long value = getVectorClockValue(myUserId) + 1;
		vectorClock.put(myUserId, value);
		
		for (Entry<Integer, Long> e : vectorClock.entrySet())
			tx.setVectorClockEntry(e.getKey(), e.getValue());
	
		channel.send(new TransactionMessage(tx));
		
	}
	
	
	public void historyStepBack(){
		
	}
	
	public void historyStepForward(){
		
	}
	
	@Override
	public String toString(){
		return super.toString()+" VC : "+vectorClock;
		
	}
	
	

}
