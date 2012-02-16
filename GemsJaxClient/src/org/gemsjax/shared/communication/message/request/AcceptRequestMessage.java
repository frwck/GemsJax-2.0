package org.gemsjax.shared.communication.message.request;

/**
 * Sent from client to server to answer on {@link Request} (ACCEPT or REJECT)
 * @author Hannes Dorfmann
 *
 */
public class AcceptRequestMessage extends  ReferenceableRequestMessage{

	public static final String TAG="accept";
	public static final String ATTRIBUTE_REQUEST_ID="id";
	
	private long requestId;
	
	public AcceptRequestMessage(String referenceId, long requestId) {
		super(referenceId);
		this.requestId = requestId;
	}

	@Override
	public String toXml() {
	
		return super.openingXml()+"<"+TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+requestId+"\" />"+super.closingXml();
	}
	
	public long getRequestId()
	{
		return requestId;
	}

}
