package org.gemsjax.shared.communication.message.request;

public class LiveFriendshipRequestMessage extends NewRequestMessage{
	
	public static final String TAG="friendship";
	
	private FriendshipRequest request;
	
	public LiveFriendshipRequestMessage(FriendshipRequest request)
	{
		super(request);
		this.request = request;
	}
	
	
	public FriendshipRequest getRequest()
	{
		return request;
	}
	
	@Override
	public String toXml() {
		
		return super.openingXml()+"<"+TAG+"/>"+super.closingXml();
	}

}
