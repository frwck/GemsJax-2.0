package org.gemsjax.shared.communication.message.request;

import org.gemsjax.shared.communication.message.Message;

public abstract class RequestMessage implements Message {

	public static final String TAG ="request";
	
	
	protected String openingXml(){
		return "<"+TAG+">";
	}
	
	protected String closingXml()
	{
		return "</"+TAG+">";
	}
	
}
