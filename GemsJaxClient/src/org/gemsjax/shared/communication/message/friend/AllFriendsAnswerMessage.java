package org.gemsjax.shared.communication.message.friend;

import java.util.Set;

import org.gemsjax.shared.user.UserOnlineState;

/**
 * A {@link AllFriendsAnswerMessage} is sent from server to the client, as a answer on a {@link GetAllFriendsMessage}, containing all
 * of the users friends
 * @author Hannes Dorfmann
 *
 */
public class AllFriendsAnswerMessage extends ReferenceableFriendMessage {
	
	public static final String TAG="all";
	public static final String SUBTAG_FRIEND="friend";
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_DISPLAY_NAME="dispName";
	public static final String ATTRIUBTE_ONLINE_STATE = "state";
	public static final String ATTRIBUTE_PROFILE_PICTURE="img";
	
	
	private Set<Friend> friends;
	
	
	public AllFriendsAnswerMessage(String referenceId, Set<Friend> friends)
	{
		super(referenceId);
		this.friends = friends;
	}
	
	
	private String friendToXml(Friend f)
	{
		return "<"+SUBTAG_FRIEND+" "+ATTRIBUTE_ID+"=\""+f.getId()+"\" "+ATTRIBUTE_DISPLAY_NAME+"=\""+f.getDisplayName()+"\" "+ATTRIUBTE_ONLINE_STATE+"=\""+UserOnlineState.toCommunicationConstant(f.getOnlineState())+"\" "+ATTRIBUTE_PROFILE_PICTURE+"=\""+f.getProfilePicture()+"\" />";
		
	}
	
	@Override
	public String toXml() {
		String ret="<"+FriendMessage.TAG+" "+ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+">";
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
