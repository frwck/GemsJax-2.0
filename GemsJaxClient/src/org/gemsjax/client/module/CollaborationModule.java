package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
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
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.exception.MetaBaseTypeException;
import org.gemsjax.shared.user.User;


public class CollaborationModule implements CollaborationChannelHandler{
	
	private int collaborateableId;
	private Collaborateable collaborateable;
	private CollaborationChannel channel;
	private Set<CollaborationModuleHandler> handlers;
	private TransactionProcessor transactionProcessor;
	private Set<Collaborator> collaborators;
	
	private List<MetaBaseType> metaBaseTypes;
	
	private User user;
	private static int moduleIdCounter = 0;
	private int moduleId;
	
	private String subscribeReferenceId;
	private int refIdCounter = 0;
	
	public CollaborationModule(User user, Collaborateable collaborateable, CollaborationChannel channel){
		this.collaborateableId = collaborateable.getId();
		this.collaborateable = collaborateable;
		this.user = user;
		this.channel = channel;
		
		this.handlers = new LinkedHashSet<CollaborationModuleHandler>();
		this.collaborators = new LinkedHashSet<Collaborator>();
		
		this.metaBaseTypes = new LinkedList<MetaBaseType>();
		
		this.transactionProcessor = new TransactionProcessor(user,collaborateable);
		
		channel.addCollaborationChannelHandler(this);
		moduleIdCounter++;
		moduleId = moduleIdCounter;
	}
	
	
	private String nextRefId(){
		refIdCounter++;
		return "ColMod"+moduleId+"-"+refIdCounter;
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
		
		transactionProcessor.executeTransaction(tx);
		
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
				collaborateable.getVectorClock().put(e.getKey(), receivedVal);
		}
	}
	
	
	private long getVectorClockValue(int userId){
		Long value = collaborateable.getVectorClock().get(userId);
		if ( value==null)
			return 0;
		else
			return value;
		
	}
	
	
	public synchronized void sendAndCommitTransaction(Command command) throws IOException{
		LinkedList<Command> list = new LinkedList<Command>();
		list.add(command);
		sendAndCommitTransaction(list);

	}
	
	public synchronized void sendAndCommitTransaction(List<Command> commands) throws IOException{
		
		Transaction tx = new TransactionImpl(UUID.generate());
		tx.setCommands(commands);
		tx.setUserId(user.getId());
		tx.setCollaborateableId(collaborateableId);
		
		long value = getVectorClockValue(user.getId()) + 1;
		collaborateable.getVectorClock().put(user.getId(), value);
		
		
		for (Entry<Integer, Long> e : collaborateable.getVectorClock().entrySet())
			tx.setVectorClockEntry(e.getKey(), e.getValue());
	
		channel.send(new TransactionMessage(tx));
		transactionProcessor.executeTransaction(tx);
		
		fireUpdated();
		
	}
	
	
	public void historyStepBack(){
		
	}
	
	public void historyStepForward(){
		
	}
	
	@Override
	public String toString(){
		return super.toString()+" VC : "+collaborateable.getVectorClock();
		
	}

	
	public void subscribe() throws IOException{
		subscribeReferenceId = nextRefId();
		SubscribeCollaborateableMessage msg = new SubscribeCollaborateableMessage();
		msg.setReferenceId(subscribeReferenceId);
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
	
	public Collaborateable getCollaborateable(){
		return collaborateable;
	}

	@Override
	public void onSubscribeSuccessful(String referenceId,
			List<Transaction> transactions, List<Collaborator> collaborators, List<MetaBaseType> optionalMetaBaseTypes) {
		
		if (subscribeReferenceId.equals(referenceId)){
			for (Transaction t : transactions){
				transactionProcessor.executeTransaction(t);
				mergeMaxVectorClocks(t);
			}
			
			this.metaBaseTypes.addAll(optionalMetaBaseTypes);
			
			if (collaborateable instanceof MetaModel){
				for (MetaBaseType baseType: optionalMetaBaseTypes)
					try {
						((MetaModel)collaborateable).addBaseType(baseType);
					} catch (MetaBaseTypeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			this.collaborators.addAll(collaborators);
			
			for (CollaborationModuleHandler h : handlers)
			{
				for (Collaborator c: collaborators)
					h.onCollaboratorJoined(c);
				

				h.onCollaborateableInitialized();
				h.onCollaborateableUpdated();
			}
			
			
			
			
		}
		
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
		
		
	}


	@Override
	public void onCollaboratorJoined(Collaborator c) {
		collaborators.add(c);
		for(CollaborationModuleHandler h : handlers)
			h.onCollaboratorJoined(c);
	}


	@Override
	public void onCollaboratorLeft(Collaborator c) {
		collaborators.remove(c);
		for (CollaborationModuleHandler h : handlers)
			h.onCollaboratorLeft(c);
	}
	
	
	
	

}
