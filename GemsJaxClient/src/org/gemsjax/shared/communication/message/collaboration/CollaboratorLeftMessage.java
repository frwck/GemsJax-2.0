package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.serialisation.Archive;

public class CollaboratorLeftMessage extends CollaborationMessage{

	private Collaborator collaborator;
	private int collaborateableId;
	
	public CollaboratorLeftMessage() {}
	
	public CollaboratorLeftMessage(Collaborator collaborator) {
		this.collaborator = collaborator;
	}
	
	@Override
	public void serialize(Archive a) throws Exception {
		collaborator = a.serialize("collaborator",collaborator).value;	
		collaborateableId = a.serialize("collaboratorId", collaborateableId).value;
	}

	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
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

	public void setCollaborateableId(int colId) {
		this.collaborateableId = colId;
	}
}
