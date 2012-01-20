package org.gemsjax.shared.communication.request;

import java.util.Set;


/**
 * Make a new friendshiprequests.
 * Note: its possible to make more than one friendship request with a single {@link NewFriendshipRequestMessage} 
 * by passing a set of unique user ids as constructor parameter
 * @author Hannes Dorfmann
 *
 */
public class NewFriendshipRequestMessage extends RequestMessage {

	
	public static final String TAG="friendship";
	public static final String SUBTAG_USER="user";
	public static final String ATTRIBUTE_FRIEND_ID="id";
	
	private Set<Integer> ids;
	
	public NewFriendshipRequestMessage(Set<Integer> userIds){
		
		if (userIds.size()==0)
			throw new IllegalArgumentException("At least one user id is required to make create a new "+NewFriendshipRequestMessage.class.getName());
		this.ids = userIds;
	}
	
	private String userToXml()
	{
		String users = "";
		for (Integer id: ids)
		{
			users+="<"+SUBTAG_USER+" "+ATTRIBUTE_FRIEND_ID+"=\""+id+"\" />";
		}
		return users;
	}
	
	@Override
	public String toXml() {
		return "<"+RequestMessage.TAG+"><"+TAG+">"+userToXml()+"</"+TAG+"></"+RequestMessage.TAG+">";
	}
	
	

}
