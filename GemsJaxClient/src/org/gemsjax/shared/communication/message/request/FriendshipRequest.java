package org.gemsjax.shared.communication.message.request;

import java.util.Date;

public class FriendshipRequest extends Request{

	public FriendshipRequest(int id, String requesterDisplayName,
			String requesterUsername, Date date) {
		super(id, requesterDisplayName, requesterUsername, date);
		
	}

}
