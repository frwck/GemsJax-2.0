package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.serialisation.Archive;

public class SubscribeCollaborateableErrorMessage extends ReferenceableCollaborationMessage{

	private SubscribeCollaborateableError error;
	private int collaborateableId;
	
	public SubscribeCollaborateableErrorMessage(){}
	
	public SubscribeCollaborateableErrorMessage(SubscribeCollaborateableError error){
		this.error = error;
	}

	public SubscribeCollaborateableError getError() {
		return error;
	}

	public void setError(SubscribeCollaborateableError error) {
		this.error = error;
	}
	
	@Override
	public void serialize(Archive a) throws Exception {
		super.serialize(a);

		String e = a.serialize("error", error!=null?error.toString():null).value;
		error = e==null?null:SubscribeCollaborateableError.valueOf(e);
		collaborateableId = a.serialize("collaborateableId", collaborateableId).value;
	}

	public int getCollaborateableId() {
		return collaborateableId;
	}

	public void setCollaborateableId(int collaborateableId) {
		this.collaborateableId = collaborateableId;
	}
	
}
