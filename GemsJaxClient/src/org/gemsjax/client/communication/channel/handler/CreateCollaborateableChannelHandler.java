package org.gemsjax.client.communication.channel.handler;

import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;

public interface CreateCollaborateableChannelHandler {
	
	public void onError(String referrenceId, CollaborateableFileError error);
	
	public void onSuccessful(String referenceId);

}
