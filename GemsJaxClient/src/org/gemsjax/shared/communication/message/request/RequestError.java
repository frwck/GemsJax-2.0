package org.gemsjax.shared.communication.message.request;

import org.gemsjax.shared.communication.CommunicationConstants;

public enum RequestError {
	
	AUTHENTICATION,
	PARSING,
	DATABASE,
	REQUEST_NOT_FOUND,
	PERMISSION_DENIED,
	ALREADY_BEFRIENDED,
	ALREADY_IN_COLLABORATION,
	ALREADY_EXPERIMENT_ADMIN,
	ALREADY_REQUESTED;
	
	
	public String toConstant()
	{
		switch(this)
		{
		case AUTHENTICATION: return CommunicationConstants.RequestError.AUTHENTICATION;
		case PARSING: return CommunicationConstants.RequestError.PARSING;
		case DATABASE: return CommunicationConstants.RequestError.DATABASE;
		case REQUEST_NOT_FOUND: return CommunicationConstants.RequestError.REQUEST_NOT_FOUND;
		case PERMISSION_DENIED: return CommunicationConstants.RequestError.PERMISSION_DENIED;
		case ALREADY_BEFRIENDED: return CommunicationConstants.RequestError.ALREADY_BEFRIENDED;
		case ALREADY_EXPERIMENT_ADMIN: return CommunicationConstants.RequestError.ALREADY_EXPERIMENT_ADMIN;
		case ALREADY_IN_COLLABORATION: return CommunicationConstants.RequestError.ALREADY_IN_COLLABORATION;
		case ALREADY_REQUESTED: return CommunicationConstants.RequestError.ALREADY_REQUESTED;
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
		
		if (constant.equals(CommunicationConstants.RequestError.ALREADY_BEFRIENDED))
			return ALREADY_BEFRIENDED;
		
		
		if (constant.equals(CommunicationConstants.RequestError.ALREADY_EXPERIMENT_ADMIN))
			return ALREADY_EXPERIMENT_ADMIN;
		
		
		if (constant.equals(CommunicationConstants.RequestError.ALREADY_IN_COLLABORATION))
			return ALREADY_IN_COLLABORATION;
		
		
		if (constant.equals(CommunicationConstants.RequestError.ALREADY_REQUESTED))
			return RequestError.ALREADY_REQUESTED;
		
		
		return null;
		
	}
	

}
