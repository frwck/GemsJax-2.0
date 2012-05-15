package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.serialisation.Archive;

public class TransactionErrorMessage extends CollaborationMessage{

	private TransactionError error;
	private String transactionId;
	private int collaborateableId;
	
	
	public TransactionErrorMessage(){};
	
	public TransactionErrorMessage(TransactionError error, String transactionId, int collaborateableId){
		this.error = error;
		this.transactionId = transactionId;
		this.collaborateableId = collaborateableId;
	}

	
	
	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		transactionId = a.serialize("transactionId",transactionId).value;
		collaborateableId = a.serialize("collaborateableId", collaborateableId).value;
		String e = a.serialize("error", error!=null?error.toString():null).value;
		error = e==null?null:TransactionError.valueOf(e);
	}

	public TransactionError getError() {
		return error;
	}

	public void setError(TransactionError error) {
		this.error = error;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getCollaborateableId() {
		return collaborateableId;
	}

	public void setCollaborateableId(int collaborateableId) {
		this.collaborateableId = collaborateableId;
	}
	

}
