package org.gemsjax.client.communication.channel.handler;

import java.util.List;

import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableError;

public interface CollaborationChannelHandler {
	
	public void onTransactionReceived(Transaction tx);

	public void onSubscribeSuccessful(String referenceId, List<Transaction> transactions, List<Collaborator> collaborators);
	
	public void onSubscribeError(String referenceId, SubscribeCollaborateableError error);
	
	public void onTransactionError(String transactionId);
	
	public void onUnsubscribeSuccessful(String referenceId);
	
	public void onCollaboratorJoined(Collaborator c);
	
	public void onCollaboratorLeft(Collaborator c);
	
}
