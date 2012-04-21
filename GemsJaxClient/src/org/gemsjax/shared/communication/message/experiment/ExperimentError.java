package org.gemsjax.shared.communication.message.experiment;

import org.gemsjax.shared.communication.CommunicationConstants;

public enum ExperimentError {

	AUTHENTICATION,
	DATABASE,
	PARSE;
	
	
	
	public String toConstant(){
		switch(this){
		case AUTHENTICATION: return CommunicationConstants.ExperimentError.AUTEHNTICATION;
		case DATABASE: return CommunicationConstants.ExperimentError.DATABASE;
		case PARSE: return CommunicationConstants.ExperimentError.PARSE;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param constant
	 * @return The {@link ExperimentError} enum value or <code>null</code>, if an unexpected parameter value has been passed
	 */
	public static ExperimentError fromConstant(String constant){
		if(constant.equals(CommunicationConstants.ExperimentError.AUTEHNTICATION))
			return AUTHENTICATION;
		else
		if(constant.equals(CommunicationConstants.ExperimentError.DATABASE))
			return DATABASE;
		else
		if(constant.equals(CommunicationConstants.ExperimentError.PARSE))
			return PARSE;
		
		
		return null;
		
	}
	
	
}
