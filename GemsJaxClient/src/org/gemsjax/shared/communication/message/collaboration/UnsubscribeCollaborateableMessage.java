package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.serialisation.Archive;

public class UnsubscribeCollaborateableMessage extends CollaborationMessage{

	private int collaborateableId;
	
	public UnsubscribeCollaborateableMessage(){}
	
	public UnsubscribeCollaborateableMessage(int collaborateableId){
		this.collaborateableId = collaborateableId;
	}
	
	
	
	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		collaborateableId = a.serialize("collaborateableId", collaborateableId).value;
	}

}
