package org.gemsjax.server.persistence.request;

import java.util.Date;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;

public class CollaborateRequestImpl extends RequestImpl implements CollaborateRequest{

	
	private Collaborateable collaborateable;
	
	
	@Override
	public Collaborateable getCollaborateable() {
		return collaborateable;
	}
	@Override
	public void setCollaborateable(Collaborateable collaborateable) {
		this.collaborateable = collaborateable;
	}
}
