package org.gemsjax.client.module.handler;

import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;

public interface CollaborateableFileModuleHandler<T extends Collaborateable> {
	
	public void onNewCreateSuccessful();
	public void onNewCreateError(CollaborateableFileError error);
	
	public void onGetAllSuccessful(Set<T> result);
	public void onGetAllError(CollaborateableFileError error);

	// TODO implement update / change
}
