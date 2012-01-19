package org.gemsjax.shared.communication.message.friend;

import java.util.Set;

import org.gemsjax.shared.user.UserOnlineState;

/**
 * A {@link AddFriendsAnswerMessage} is sent from server to the client, as a answer on a {@link GetAllFriendsMessage}, containing all
 * of the users friends
 * @author Hannes Dorfmann
 *
 */
public class AddFriendsAnswerMessage extends FriendMessage {
	
	public static final String TAG="add";
	public static final String SUBTAG_FRIEND="friend";
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_DISPLAY_NAME="dispName";
	public static final String ATTRIUBTE_ONLINE_STATE = "state";
	public static final String ATTRIBUTE_PROFILE_PICTURE="img";
	
	public static final String ATTRIBUTE_FAIL="fail";
	
	
	private Set<Friend> friends;
	
	
	public AddFriendsAnswerMessage(Set<Friend> friends)
	{
		this.friends = friends;
	}
	
	
	private String friendToXml(Friend f)
	{
		return "<"+SUBTAG_FRIEND+" "+ATTRIBUTE_ID+"=\""+f.getId()+"\" "+ATTRIBUTE_DISPLAY_NAME+"=\""+f.getDisplayName()+"\" "+ATTRIUBTE_ONLINE_STATE+"=\""+UserOnlineState.toCommunicationConstant(f.getOnlineState())+"\" "+ATTRIBUTE_PROFILE_PICTURE+"=\""+f.getProfilePicture()+"\" />";
		
	}
	
	@Override
	public String toXml() {
		String ret="<"+FriendMessage.TAG+"><"+TAG+">";
		if (friends!=null)
			for (Friend f: friends)
				ret+=friendToXml(f);
		
		ret+="</"+TAG+"></"+FriendMessage.TAG+">";
		
		return ret;
	}

	
	public Set<Friend> getFriends()
	{
		return friends;
	}
}
