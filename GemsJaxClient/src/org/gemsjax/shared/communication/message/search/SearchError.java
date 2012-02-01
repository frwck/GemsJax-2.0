package org.gemsjax.shared.communication.message.search;

import org.gemsjax.shared.communication.CommunicationConstants;


public enum SearchError {


	/**
	 * The search request has failed, because the {@link ReferenceableSearchMessage} could not be parsed correctly
	 */
	PARSING,
	
	/**
	 * an unexpected database error has occurred
	 */
	DATABASE,
	
	/**
	 * The user that has sent the {@link ReferenceableSearchMessage} is not authenticated
	 */
	AUTHENTICATION
	;
	
	
	/**
	 * Converts a {@link SearchError} to its {@link CommunicationConstants.SearchError} constants equivalent
	 * @return
	 */
	public String toConstant()
	{
		switch(this)
		{
		case PARSING: return CommunicationConstants.SearchError.PARSING;
		case DATABASE: return CommunicationConstants.SearchError.DATABASE;
		case AUTHENTICATION: return CommunicationConstants.SearchError.AUTHENTICATION;
		
		}
		
		return null;
	}
	
	
	/**
	 * Converts a {@link CommunicationConstants.SearchError} to its {@link SearchError} enum
	 * @param c
	 * @return
	 */
	public static SearchError fromConstant(String c)
	{
		if (c.equals(CommunicationConstants.SearchError.AUTHENTICATION))
			return AUTHENTICATION;
		else
		if (c.equals(CommunicationConstants.SearchError.PARSING))	
			return PARSING;
		else
		if (c.equals(CommunicationConstants.SearchError.DATABASE))
			return DATABASE;
		
		else
		return null;
	}
}
