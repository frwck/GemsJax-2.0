package org.gemsjax.shared.communication.message.request;

/**
 * Sent from client to server to answer on {@link Request} (ACCEPT or REJECT)
 * @author Hannes Dorfmann
 *
 */
public class RejectRequestMessage extends  ReferenceableRequestMessage{

	public static final String TAG="reject";
	public static final String ATTRIBUTE_REQUEST_ID="id";
	
	private int requestId;
	
	public RejectRequestMessage(String referenceId, int requestId) {
		super(referenceId);
		this.requestId = requestId;
	}

	@Override
	public String toXml() {
	
		return super.openingXml()+"<"+TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+requestId+"\" />"+super.closingXml();
	}
	
	public int getRequestId()
	{
		return requestId;
	}

}