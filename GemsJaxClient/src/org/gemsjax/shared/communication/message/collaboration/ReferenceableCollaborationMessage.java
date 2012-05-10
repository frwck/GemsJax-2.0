package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.serialisation.Archive;

public class ReferenceableCollaborationMessage  extends CollaborationMessage{

	private String referenceId;
	
	public ReferenceableCollaborationMessage(){}
	
	public ReferenceableCollaborationMessage(String referenceId){
		this.referenceId = referenceId;
	}
	
	
	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		referenceId = a.serialize("referenceId", referenceId).value;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

}
