package org.gemsjax.shared.communication.message;

import org.gemsjax.shared.communication.CommunicationConstants;

/**
 * This message should notify the client about an unexpected server error,
 * like parsing error, database error ...
 * 
 * <b>Note: do not use this {@link UnexpectedErrorMessage} for sematic error handling like a modelling error </b>
 * @author Hannes Dorfmann
 *
 */
public class UnexpectedErrorMessage implements Message{

	/**
	 * Representing the error type
	 * @author Hannes Dorfmann
	 *
	 */
	public enum ErrorType
	{
		/**
		 * The XML protocol message could not be parsed.
		 * Mapped by {@link CommunicationConstants.Error#PARSE}.
		 */
		PARSE,
		
		/**
		 * An unexpected Database error has occurred.
		 * Mapped by {@link CommunicationConstants.Error#DATABASE}.
		 */
		DATABASE
	}
	
	private ErrorType type;
	
	private String additionalInfo;
	
	public UnexpectedErrorMessage(ErrorType type)
	{
		this.type = type;
		additionalInfo = null;
	}
	
	public UnexpectedErrorMessage(ErrorType type, String additionalInfo)
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
			case PARSE: return CommunicationConstants.UnexpectedError.PARSE;
			case DATABASE: return CommunicationConstants.UnexpectedError.DATABASE;
		}
		
		return null;
	}
	
	
	
	@Override
	public String toXml() {
		if (additionalInfo== null)
			return "<error type=\""+errorTypeToInt()+"\" >";
		else
			return "error type=\""+errorTypeToInt()+"\">"+additionalInfo+"</error>";
	}

	
	/**
	 * Converts a integer constant  (see {@link CommunicationConstants.UnexpectedError}) to {@link ErrorType}
	 * @param errorNr
	 * @return
	 */
	public static ErrorType intToErrorType(Integer errorNr)
	{
		switch (errorNr)
		{
		case CommunicationConstants.UnexpectedError.DATABASE: return ErrorType.DATABASE;
		case CommunicationConstants.UnexpectedError.PARSE: return ErrorType.PARSE;
		}
		
		
		return null;
	}

	@Override
	public String toHttpGet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toHttpPost() {
		// TODO Auto-generated method stub
		return null;
	}
}
