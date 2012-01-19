package org.gemsjax.shared.communication.message.friend;

import java.util.Set;

/**
 * Sent from server to client, to inform the client - user, that while he is online, one of his friends or the user himself
 * has canceled a friendship.
 * @author Hannes Dorfmann
 *
 */
public class FriendshipCanceledMessage extends FriendMessage{

	public static final String TAG ="remove";
	public static final String SUBTAG_FRIEND = "friend";
	public static final String ATTRIBUTE_FRIEND_ID="id";
	
	private Set<Integer> exFriendIds;
	
	public FriendshipCanceledMessage(Set<Integer> exFriendIds)
	{
		this.exFriendIds = exFriendIds;
	}
	
	private String friendIdsToXml()
	{
		String r="";
		
		for (int id : exFriendIds)
		{
			r+="<"+SUBTAG_FRIEND+" "+ATTRIBUTE_FRIEND_ID+"=\""+id+"\" />";
		}
		
		return r;
	}
	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+"><"+TAG+">"+friendIdsToXml()+"</"+TAG+"></"+FriendMessage.TAG+">";
	}
	
	
	public Set<Integer> getExFriendId()
	{
		return exFriendIds;
	}

}
