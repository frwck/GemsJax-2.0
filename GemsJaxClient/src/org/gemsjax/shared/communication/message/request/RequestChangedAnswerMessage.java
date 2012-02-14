package org.gemsjax.shared.communication.message.request;

/**
 * This is a positive answer message (sent from the server to client) on a previousl sent {@link ReferenceableRequestMessage} 
 * (sent from client to server).
 * @author Hannes Dorfmann
 *
 */
public class RequestChangedAnswerMessage extends ReferenceableRequestMessage{

	public static final String TAG="ok";
	
	public RequestChangedAnswerMessage(String referenceId) {
		super(referenceId);
		
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+"/>"+super.closingXml();
	}

	
}
