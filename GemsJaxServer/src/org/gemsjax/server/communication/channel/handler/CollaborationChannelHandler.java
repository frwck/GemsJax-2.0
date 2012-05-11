package org.gemsjax.server.communication.channel.handler;

import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.collaboration.Transaction;

public interface CollaborationChannelHandler {
	
	public void onTransactionReceived(Transaction tx, OnlineUser sender);
	
	public void onSubscribe(int collaborateableId, String refId, OnlineUser sender);
	
	public void onUnsubscribe(int collaboratebaleId, String refId, OnlineUser sender);

}
