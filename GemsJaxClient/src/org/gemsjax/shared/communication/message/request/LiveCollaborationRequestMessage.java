package org.gemsjax.shared.communication.message.request;

public class LiveCollaborationRequestMessage extends LiveRequestMessage{

	public static final String TAG="collaboration";
	public static final String ATTRIBUTE_COLLABORATION_ID="id";
	public static final String ATTRIBUTE_COLLABORATION_NAME="name";
	
	private CollaborationRequest req;
	
	public LiveCollaborationRequestMessage(CollaborationRequest request) {
		super(request);
		this.req = request;
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+" "+ATTRIBUTE_COLLABORATION_ID+"=\""+req.getCollaborationId()+"\" "+ATTRIBUTE_COLLABORATION_NAME+"=\""+req.getName()+"\" />"+super.closingXml();
	}

	
	public CollaborationRequest getRequest()
	{
		return req;
	}
}
