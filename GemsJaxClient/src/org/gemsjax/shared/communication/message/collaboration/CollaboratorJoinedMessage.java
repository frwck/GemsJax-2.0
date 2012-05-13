package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.serialisation.Archive;

public class CollaboratorJoinedMessage extends CollaborationMessage{

	private Collaborator collaborator;
	private int collaborateableId;
	
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
		collaborateableId = a.serialize("collaborateableId", collaborateableId).value;
	}

	
	public Collaborator getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(Collaborator collaborator) {
		this.collaborator = collaborator;
	}

	public int getCollaborateableId() {
		return collaborateableId;
	}

	public void setCollaborateableId(int collaborateableId) {
		this.collaborateableId = collaborateableId;
	}
}
