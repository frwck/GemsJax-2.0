package org.gemsjax.shared.communication.message.request;

public abstract class ReferenceableRequestMessage extends RequestMessage{
	
	public static final String ATTRIBUTE_REFERENCE_ID="ref-id";
	
	private String referenceId;
	
	public ReferenceableRequestMessage(String referenceId)
	{
		this.referenceId = referenceId;
	}
	
	@Override
	protected String openingXml()
	{
		return "<"+RequestMessage.TAG+" "+ATTRIBUTE_REFERENCE_ID+"=\""+referenceId+"\">";
	}
	
	@Override
	protected String closingXml()
	{
		return "</"+RequestMessage.TAG+">";
	}

}
