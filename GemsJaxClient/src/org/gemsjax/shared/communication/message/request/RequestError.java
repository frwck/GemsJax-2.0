package org.gemsjax.shared.communication.message.request;

import org.gemsjax.shared.communication.CommunicationConstants;

public enum RequestError {
	
	AUTHENTICATION,
	PARSING,
	DATABASE,
	REQUEST_NOT_FOUND,
	PERMISSION_DENIED;
	
	
	public String toConstant()
	{
		switch(this)
		{
		case AUTHENTICATION: return CommunicationConstants.RequestError.AUTHENTICATION;
		case PARSING: return CommunicationConstants.RequestError.PARSING;
		case DATABASE: return CommunicationConstants.RequestError.DATABASE;
		case REQUEST_NOT_FOUND: return CommunicationConstants.RequestError.REQUEST_NOT_FOUND;
		}
	
		return null;
	}
	
	
	public static RequestError fromConstant(String constant)
	{
		if (constant.equals(CommunicationConstants.RequestError.AUTHENTICATION))
			return AUTHENTICATION;
		
		if (constant.equals(CommunicationConstants.RequestError.PARSING))
			return PARSING;
		
		if (constant.equals(CommunicationConstants.RequestError.DATABASE))
			return DATABASE;
		
		if (constant.equals(CommunicationConstants.RequestError.REQUEST_NOT_FOUND))
			return REQUEST_NOT_FOUND;
		
		if (constant.equals(CommunicationConstants.RequestError.PERMISSION_DENIED))
			return PERMISSION_DENIED;
		
		
		return null;
		
	}
	

}
