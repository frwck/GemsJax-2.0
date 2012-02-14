package org.gemsjax.shared.communication.message.request;

/**
 * Is a negative response on any {@link ReferenceableRequestMessage}
 * @author Hannes Dorfmann
 *
 */
public class RequestErrorMessage extends ReferenceableRequestMessage{

	public static final String TAG="error";
	public static final String ATTRIBUTE_REASON="reason";
	
	private RequestError error;
	
	public RequestErrorMessage(String referenceId, RequestError error) {
		super(referenceId);
		this.error = error;
	}

	@Override
	public String toXml() {
		return super.openingXml()+"<"+TAG+" "+ATTRIBUTE_REASON+"=\""+error.toConstant()+"\" />"+super.closingXml();
	}
	
	
	public RequestError getRequestError()
	{
		return error;
	}
	
	
}
