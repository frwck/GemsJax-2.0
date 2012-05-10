package org.gemsjax.shared.communication.message.collaboration;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.serialisation.Archive;

public class SubscribeCollaborateableSuccessfulMessage extends ReferenceableCollaborationMessage{

	private List<Transaction> transactions;
	private List<Collaborator> collaborators;
	
	public SubscribeCollaborateableSuccessfulMessage(){
		
		transactions = new LinkedList<Transaction>();
		collaborators = new LinkedList<Collaborator>();
		
	}
	
	public SubscribeCollaborateableSuccessfulMessage(String referneceId){
		super(referneceId);
		transactions = new LinkedList<Transaction>();
		collaborators = new LinkedList<Collaborator>();
	}
	
	
	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		super.serialize(a);
		transactions = a.serialize("transactions", transactions).value;
		collaborators = a.serialize("collaborators", collaborators).value;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	

}
