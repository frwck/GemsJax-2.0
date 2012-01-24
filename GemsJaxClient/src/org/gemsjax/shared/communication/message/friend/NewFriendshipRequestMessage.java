package org.gemsjax.shared.communication.message.friend;


/**
 * Is sent from client to server to make a new friendship request.
 * @author Hannes Dorfmann
 *
 */
public class NewFriendshipRequestMessage extends FriendMessage{

	public static final String TAG ="new";
	public static final String ATTRIBUTE_USER_ID = "receiver-id";
	
	
	private int id;
	
	public NewFriendshipRequestMessage(int receiverId)
	{
		this.id = receiverId;
	}
	
	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+"><"+TAG+" "+ATTRIBUTE_USER_ID+"=\""+id+"\"></"+TAG+"></"+FriendMessage.TAG+">";
	}
	
	
	public int getReceiverId()
	{
		return id;
	}
		
}
