package org.gemsjax.server.persistence.notification;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.user.RegisteredUser;

public class CollaborationRequestNotificationImpl extends NotificationImpl{

	private Collaborateable collaborateable;
	private RegisteredUser acceptor;
	private boolean accepted;
	
	
	public CollaborationRequestNotificationImpl(){}


	public RegisteredUser getAcceptor() {
		return acceptor;
	}


	public void setAcceptor(RegisteredUser acceptor) {
		this.acceptor = acceptor;
	}


	public Collaborateable getCollaborateable() {
		return collaborateable;
	}


	public void setCollaborateable(Collaborateable collaborateable) {
		this.collaborateable = collaborateable;
	}


	public boolean isAccepted() {
		return accepted;
	}


	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	
}
