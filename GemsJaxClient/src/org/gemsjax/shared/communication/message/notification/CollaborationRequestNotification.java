package org.gemsjax.shared.communication.message.notification;

import java.util.Date;

public class CollaborationRequestNotification extends Notification {

	private String username;
	private String displayName;
	private boolean accepted;
	private String collaborationName;
	private int collaborationId;
	
	public CollaborationRequestNotification(long id, Date date, boolean read, String displayName, String username, boolean accepted, int collaborationid, String collaborationName) {
		super(id, date, read);
		this.username = username;
		this.displayName = displayName;
		this.accepted = accepted;
		this.collaborationName = collaborationName;
		this.collaborationId = collaborationid;
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

	public String getCollaborationName() {
		return collaborationName;
	}

	public int getCollaborationId() {
		return collaborationId;
	}
	
	
}
