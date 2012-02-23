package org.gemsjax.shared.communication.message.notification;

import org.gemsjax.shared.communication.CommunicationConstants;

public enum NotificationError {
	
	AUTHENTICATION,
	PARSING,
	DATABASE,
	NOT_FOUND,
	PERMISSION_DENIED;
	
	
	public String toConstant(){
		
		switch (this){
		case AUTHENTICATION: return CommunicationConstants.NotificationError.AUTHENTICATION;
		case PARSING: return CommunicationConstants.NotificationError.PARSING;
		case DATABASE: return CommunicationConstants.NotificationError.DATABASE;
		case NOT_FOUND: return CommunicationConstants.NotificationError.NOT_FOUND;
		case PERMISSION_DENIED: return CommunicationConstants.NotificationError.PERMISSION_DENIED;
		
		}
		
		
		return null;
	}
	
	
	
	public static NotificationError fromConstant(String c){
		if (c.equals(CommunicationConstants.NotificationError.AUTHENTICATION))
			return AUTHENTICATION;
		
		if (c.equals(CommunicationConstants.NotificationError.PARSING))
			return PARSING;
		
		if (c.equals(CommunicationConstants.NotificationError.DATABASE))
			return DATABASE;
		
		if (c.equals(CommunicationConstants.NotificationError.NOT_FOUND))
			return NOT_FOUND;
		
		if (c.equals(CommunicationConstants.NotificationError.PERMISSION_DENIED))
			return PERMISSION_DENIED;
		
		return null;
	}

}
