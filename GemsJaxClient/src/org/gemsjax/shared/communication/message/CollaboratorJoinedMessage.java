package org.gemsjax.shared.communication.message;

import org.gemsjax.shared.communication.message.collaboration.CollaborationMessage;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.serialisation.Archive;

public class CollaboratorJoinedMessage extends CollaborationMessage{

	private Collaborator collaborator;
	
	public CollaboratorJoinedMessage() {}
	
	public CollaboratorJoinedMessage(Collaborator collaborator) {
		this.collaborator = collaborator;
	}
	
	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		collaborator = a.serialize("collaborator",collaborator).value;	
	}

	
	public Collaborator getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(Collaborator collaborator) {
		this.collaborator = collaborator;
	}
}
