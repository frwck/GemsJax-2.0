package org.gemsjax.shared.communication.message.friend;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.user.UserOnlineState;

/**
 * This {@link Message} is sent from Server to client as the response on a {@link NewFriendshipRequestMessage}
 * @author Hannes Dorfmann
 *
 */
public class NewFriendshipRequestAnswerMessage extends ReferenceableFriendMessage{
	
	
	public static final String TAG = "new-ok";

	
	public NewFriendshipRequestAnswerMessage(String referenceId)
	{
		super(referenceId);
	}
	
	

	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+" "+ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+" /></"+FriendMessage.TAG+">";
	}
	
	
}
