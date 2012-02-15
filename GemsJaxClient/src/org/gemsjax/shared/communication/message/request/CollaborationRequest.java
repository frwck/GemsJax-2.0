package org.gemsjax.shared.communication.message.request;

import java.util.Date;

public class CollaborationRequest extends Request {

	private String name;
	private int collaborationId;
	
	public CollaborationRequest(long id, String requesterDisplayName,
			String requesterUsername, Date date, int collaborationId, String name) {
		super(id, requesterDisplayName, requesterUsername, date);

		this.name = name;
		this.collaborationId = collaborationId;
	}

	public String getName() {
		return name;
	}

	public int getCollaborationId() {
		return collaborationId;
	}
	

}
