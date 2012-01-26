package org.gemsjax.shared.communication.message.friend;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.user.UserOnlineState;

/**
 * This {@link Message} is sent from Server to client as the response on a {@link NewFriendshipRequestMessage}
 * @author Hannes Dorfmann
 *
 */
public class NewFriendshipRequestAnswerMessage extends ReferenceableFriendMessage{
	
	
	public static final String TAG = "new";
	public static final String SUBTAG_FRIEND="friend";
	public static final String ATTRIBUTE_FRIEND_ID = "id";
	public static final String ATTRIBUTE_DISPLAY_NAME="dispName";
	public static final String ATTRIBUTE_ONLINE_STATE="state";
	public static final String ATTRIBUTE_PROFILE_PICTURE = "img";
	
	private Friend friend;
	
	public NewFriendshipRequestAnswerMessage(String referenceId, Friend friend)
	{
		super(referenceId);
		this.friend = friend;
	}
	
	
	private String friendToXml()
	{
		return "<"+SUBTAG_FRIEND+" "+ATTRIBUTE_FRIEND_ID+"=\""+friend.getId()+"\" "+ATTRIBUTE_ONLINE_STATE+"=\""+UserOnlineState.toCommunicationConstant(friend.getOnlineState())+"\" "+ATTRIBUTE_DISPLAY_NAME+"=\""+friend.getDisplayName()+"\" "+ATTRIBUTE_PROFILE_PICTURE+"=\""+friend.getProfilePicture()+"\" />";
	}

	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+" "+ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+">"+friendToXml()+"</"+TAG+"></"+FriendMessage.TAG+">";
	}
	
	
	public Friend getFriend()
	{
		return friend;
	}

}
