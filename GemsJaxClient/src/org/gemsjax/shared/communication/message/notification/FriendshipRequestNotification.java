package org.gemsjax.shared.communication.message.notification;

import java.util.Date;

public class FriendshipRequestNotification extends Notification {
	
	
	private String username;
	private String displayName;
	private boolean accepted;
	
	public FriendshipRequestNotification(long id, Date date, boolean read, String displayName, String username, boolean accepted) {
		super(id, date, read);
		this.username = username;
		this.displayName = displayName;
		this.accepted = accepted;
		
	}

	public String getUsername() {
		return username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean isAccepted() {
		return accepted;
	}


}
