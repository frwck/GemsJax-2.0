package org.gemsjax.client.communication.channel.handler;

import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;

public interface CollaborateableFileChannelHandler<T extends Collaborateable> {
	
	public void onError(String referrenceId, CollaborateableFileError error);
	
	public void onSuccessful(String referenceId);
	
	public void onGetAllResultReceived(String referenceId, Set<T> collaborateables);
	
}
