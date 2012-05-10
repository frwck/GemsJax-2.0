package org.gemsjax.client.communication.channel.handler;

import org.gemsjax.shared.collaboration.Transaction;

public interface CollaborationChannelHandler {
	
	public void onTransactionReceived(Transaction tx);

}
