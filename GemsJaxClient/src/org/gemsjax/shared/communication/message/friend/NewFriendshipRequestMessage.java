package org.gemsjax.shared.communication.message.friend;


/**
 * Is sent from client to server to make a new friendship request.
 * @author Hannes Dorfmann
 *
 */
public class NewFriendshipRequestMessage extends ReferenceableFriendMessage{

	public static final String TAG ="new";
	public static final String ATTRIBUTE_USER_ID = "receiver-id";
	
	
	private int id;
	
	public NewFriendshipRequestMessage(String referenceId, int receiverId)
	{
		super(referenceId);
		this.id = receiverId;
	}
	
	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+" "+ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\"><"+TAG+" "+ATTRIBUTE_USER_ID+"=\""+id+"\" /></"+FriendMessage.TAG+">";
	}
	
	
	public int getReceiverId()
	{
		return id;
	}
		
}
