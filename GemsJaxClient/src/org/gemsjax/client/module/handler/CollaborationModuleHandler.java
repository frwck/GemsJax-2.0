package org.gemsjax.client.module.handler;

import org.gemsjax.shared.communication.message.collaboration.Collaborator;

public interface CollaborationModuleHandler {
	
	public void onCollaborateableUpdated();

	public void onCollaborateableInitialized();
	
	public void onCollaboratorJoined(Collaborator c);
	
	public void onCollaboratorLeft(Collaborator c);
}
