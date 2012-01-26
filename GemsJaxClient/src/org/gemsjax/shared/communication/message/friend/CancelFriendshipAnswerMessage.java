package org.gemsjax.shared.communication.message.friend;

import java.util.Set;

import org.gemsjax.shared.communication.message.Message;

/**
 * This {@link Message} is sent from server to client, 
 * as a response on a {@link CancelFriendshipMessage}. This message contains the id's of the ex-friends
 * @author Hannes Dorfmann
 *
 */
public class CancelFriendshipAnswerMessage extends ReferenceableFriendMessage{
	
	public static final String SUBTAG_FRIEND ="exfriend";
	public static final String ATTRIBUTE_FRIEND_ID ="id";
	public static final String TAG="removed";
	
	private Set<Integer> exFriendIds;
	
	public CancelFriendshipAnswerMessage(String referenceId, Set<Integer> exFriendIds)
	{
		super(referenceId);
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
		return "<"+FriendMessage.TAG+" "+ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+">"+friendIdsToXml()+"</"+TAG+"></"+FriendMessage.TAG+">";
	}
	
	
	public Set<Integer> getFriendIds()
	{
		return exFriendIds;
	}


}
