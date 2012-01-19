package org.gemsjax.shared.communication.message;

import org.gemsjax.shared.communication.CommunicationConstants;

/**
 * This message should notify the client about an unexpected server error,
 * like parsing error, database error or authentication error 
 * <b>Note: do not use this {@link CommunicationError} for sematic error handling like a modelling error </b>
 * @author Hannes Dorfmann
 *
 */
public class CommunicationError {

	public static final String TAG ="error";
	
	public static final String ATTRIBUTE_TYPE ="type";
	
	
	/**
	 * Representing the error type
	 * @author Hannes Dorfmann
	 *
	 */
	public enum ErrorType
	{
		/**
		 * The protocol message (xml or http post/get) could not be parsed.
		 * Mapped by {@link CommunicationConstants.Error#PARSE}.
		 */
		PARSE,
		
		/**
		 * An unexpected Database error has occurred.
		 * Mapped by {@link CommunicationConstants.Error#DATABASE}.
		 */
		DATABASE,
		
		/**
		 * User which has sent a message and is now waiting for the answer is not authenticated and not allowed to get a answer
		 */
		AUTHENTICATION
	}
	
	private ErrorType type;
	
	private String additionalInfo;
	
	public CommunicationError(ErrorType type)
	{
		this.type = type;
		additionalInfo = null;
	}
	
	public CommunicationError(ErrorType type, String additionalInfo)
	{
		this.type = type;
		this.additionalInfo = additionalInfo;
	}
	
	
	public ErrorType getErrorType()
	{
		return type;
	}
	
	
	public String getAdditionalInfo()
	{
		return additionalInfo;
	}
	
	
	private Integer errorTypeToInt()
	{
		switch (type)
		{
			case PARSE: return CommunicationConstants.Error.PARSE;
			case DATABASE: return CommunicationConstants.Error.DATABASE;
		}
		
		return null;
	}
	
	
	
	
	public String toXml() {
		if (additionalInfo== null)
			return "<"+TAG+" "+ATTRIBUTE_TYPE+"=\""+errorTypeToInt()+"\" />";
		else
			return "<"+TAG+" "+ATTRIBUTE_TYPE+"=\""+errorTypeToInt()+"\">"+additionalInfo+"</"+TAG+">";
	}

	
	/**
	 * Converts a integer constant  (see {@link CommunicationConstants.Error}) to {@link ErrorType}
	 * @param errorNr
	 * @return
	 */
	public static ErrorType intToErrorType(Integer errorNr)
	{
		switch (errorNr)
		{
		case CommunicationConstants.Error.DATABASE: return ErrorType.DATABASE;
		case CommunicationConstants.Error.PARSE: return ErrorType.PARSE;
		}
		
		
		return null;
	}

}
