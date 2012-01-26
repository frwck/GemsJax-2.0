package org.gemsjax.shared.communication.message.friend;

import java.util.Set;

/**
 * Sent from the client to the server to cancel a friendship.
 * @author Hannes Dorfmann
 * @see FriendshipCanceledMessage
 *
 */
public class CancelFriendshipMessage extends ReferenceableFriendMessage{

	public static final String SUBTAG_FRIEND ="friend";
	public static final String ATTRIBUTE_FRIEND_ID ="id";
	public static final String TAG="remove";
	
	private Set<Integer> friendIds;
	
	public CancelFriendshipMessage(String referenceId, Set<Integer> friendIds)
	{
		super(referenceId);
		this.friendIds = friendIds;
	}
	
	
	private String friendIdsToXml()
	{
		String r="";
		
		for (int id : friendIds)
		{
			r+="<"+SUBTAG_FRIEND+" "+ATTRIBUTE_FRIEND_ID+"=\""+id+"\" />";
		}
		
		return r;
	}
	
	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+" "+ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+">"+friendIdsToXml()+"</"+TAG+"></"+FriendMessage.TAG+">";
	}
	
	
	public Set<Integer> getFriendIds()
	{
		return friendIds;
	}

}
