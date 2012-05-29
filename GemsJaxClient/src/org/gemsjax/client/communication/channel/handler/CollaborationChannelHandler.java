package org.gemsjax.client.communication.channel.handler;

import java.util.List;

import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.message.collaboration.SubscribeCollaborateableError;
import org.gemsjax.shared.metamodel.MetaBaseType;

public interface CollaborationChannelHandler {
	
	public void onTransactionReceived(Transaction tx);

	
	public void onSubscribeError(String referenceId, SubscribeCollaborateableError error);
	
	public void onTransactionError(String transactionId);
	
	public void onUnsubscribeSuccessful(String referenceId);
	
	public void onCollaboratorJoined(Collaborator c);
	
	public void onCollaboratorLeft(Collaborator c);

	void onSubscribeSuccessful(String referenceId,
			List<Transaction> transactions, List<Collaborator> onlineCollaborators, List<Collaborator> allCollaborators,
			List<MetaBaseType> optionalMetaBaseTypes);
	
}
