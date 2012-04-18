package org.gemsjax.shared.communication.message.collaborateablefile;

import org.gemsjax.shared.communication.CommunicationConstants;

/**
 * This error enum is used by {@link ReferenceableCollaborateableFileMessage}s
 * @author Hannes Dorfmann
 *
 */
public enum CollaborateableFileError {
	
	NAME_MISSING,
	AUTHENTICATION,
	PERMISSION_DENIED,
	PARSING,
	DATABASE,
	NOT_FOUND;
	
	
	public String toConstant(){
		switch(this){
		case NAME_MISSING: return CommunicationConstants.CollaborateableFileError.NAME_MISSING;
		case AUTHENTICATION: return CommunicationConstants.CollaborateableFileError.AUTHENTICATION;
		case DATABASE: return CommunicationConstants.CollaborateableFileError.DATABASE;
		case NOT_FOUND: return CommunicationConstants.CollaborateableFileError.NOT_FOUND;
		case PARSING: return CommunicationConstants.CollaborateableFileError.PARSING;
		case PERMISSION_DENIED: return CommunicationConstants.CollaborateableFileError.PERMISSION_DENIED;
		}
		
		return null;
	}

	public static CollaborateableFileError fromConstant(String c){
		
		if (c.equals(CommunicationConstants.CollaborateableFileError.NAME_MISSING))
			return NAME_MISSING;
		
		if (c.equals(CommunicationConstants.CollaborateableFileError.AUTHENTICATION))
			return AUTHENTICATION;
		
		if (c.equals(CommunicationConstants.CollaborateableFileError.DATABASE))
			return DATABASE;
		
		if (c.equals(CommunicationConstants.CollaborateableFileError.NOT_FOUND))
			return NOT_FOUND;
		
		if (c.equals(CommunicationConstants.CollaborateableFileError.PARSING))
			return PARSING;
		
		if (c.equals(CommunicationConstants.CollaborateableFileError.PERMISSION_DENIED))
			return PERMISSION_DENIED;
		
		return null;
	}
}
