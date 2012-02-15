package org.gemsjax.shared.communication.message.request;
/**
 * Sent from client to server to get a list with all requests
 * @author Hannes Dorfmann
 *
 */
public class GetAllRequestsMessage extends ReferenceableRequestMessage{

	public static final String TAG="get-all";
	
	public GetAllRequestsMessage(String referenceId) {
		super(referenceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+"/>"+super.closingXml();
	}

}
