package org.gemsjax.shared.communication.message.collaboration;

import java.util.List;

import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.serialisation.Archive;

public class SubscribeCollaborateableSuccessfulMessage extends ReferenceableCollaborationMessage{

	private List<Transaction> transactions;
	
	public SubscribeCollaborateableSuccessfulMessage(){}
	public SubscribeCollaborateableSuccessfulMessage(String referneceId){
		super(referneceId);
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
	}
	

}
