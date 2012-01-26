package org.gemsjax.shared.communication.message.friend;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.user.UserOnlineState;


/**
 * Sent from the server to the client (pushed), to inform that a FriendshipRequest was accepted and the currently online user is now befriended with the friend,
 * that is wraped in this {@link Message}
 * @author Hannes Dorfmann
 *
 */
public class NewFriendAddedMessage extends FriendMessage {

	public static final String TAG="add";
	public static final String SUBTAG_FRIEND="friend";
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_DISPLAY_NAME="dispName";
	public static final String ATTRIUBTE_ONLINE_STATE = "state";
	public static final String ATTRIBUTE_PROFILE_PICTURE="img";
	
	private Friend friend;
	
	
	public NewFriendAddedMessage(Friend friend)
	{
		this.friend = friend;
	}
	
	private String friendToXml(Friend f)
	{
		return "<"+SUBTAG_FRIEND+" "+ATTRIBUTE_ID+"=\""+f.getId()+"\" "+ATTRIBUTE_DISPLAY_NAME+"=\""+f.getDisplayName()+"\" "+ATTRIUBTE_ONLINE_STATE+"=\""+UserOnlineState.toCommunicationConstant(f.getOnlineState())+"\" "+ATTRIBUTE_PROFILE_PICTURE+"=\""+f.getProfilePicture()+"\" />";
		
	}
	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+"><"+TAG+">"+friendToXml(friend)+"</"+TAG+"></"+FriendMessage.TAG+">";
	}

	
	public Friend getFriend()
	{
		return friend;
	}
}
