package org.gemsjax.server.persistence.notification;

import org.gemsjax.shared.user.RegisteredUser;

public class FriendshipRequestNotificationImpl extends NotificationImpl {
	
	private RegisteredUser acceptor;
	private boolean accepted;
	
	public FriendshipRequestNotificationImpl(){}

	public RegisteredUser getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(RegisteredUser acceptor) {
		this.acceptor = acceptor;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

}
