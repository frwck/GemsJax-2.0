package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.communication.CommunicationConstants;

/**
 * Is the sent by the server to the client as the answer on a {@link NewRegistrationMessage}
 * @author Hannes Dorfmann
 *
 */
public class RegistrationAnswerMessage extends SystemMessage {

	public enum RegistrationAnswerStatus
	{
		/**
		 * Registration was successful.
		 * This is mapped to {@link CommunicationConstants.Registration#OK}.
		 */
		OK,
		
		/**
		 * Registration failed, because the username is not available (is already used by another user)
		 * This is mapped to {@link CommunicationConstants.Registration#FAIL_USERNAME}.
		 */
		FAIL_USERNAME,
		
		/**
		 * Registration failed, because the email is already used by another user for his registration.
		 * This is mapped to {@link CommunicationConstants.Registration#FAIL_EMAIL}.
		 */
		FAIL_EMAIL,
		
		/**
		 * Registration failed, because the passed username is invalid.
		 * That means, that the username failed the {@link FieldVerifier#isValidUsername(String)} check.
		 * This is mapped to {@link CommunicationConstants.Registration#FAIL_INVALID_USERNAME}
		 */
		FAIL_INVALID_USERNAME,
		
		/**
		 * Registration failed, because the passed email is invalid.
		 * That means, that the email failed the {@link FieldVerifier#isValidEmail(String)} check.
		 * This is mapped to {@link CommunicationConstants.Registration#FAIL_INVALID_EMAIL}
		 */
		FAIL_INVALID_EMAIL
	}
	
	
	/**
	 * The {@link RegistrationAnswerMessage} is embarked in this tag
	 */
	public static final String TAG = "registration";
	public static final String STATUS_ATTRIBUTE="status";
	public static final String FAIL_STRING_ATTRIBUTE="fail-string";
	
	
	private RegistrationAnswerStatus status;
	private String failString;
	
	public RegistrationAnswerMessage(RegistrationAnswerStatus status)
	{
		this.status = status;
	}
	
	
	public RegistrationAnswerMessage(RegistrationAnswerStatus status, String failString)
	{
		this.status = status;
		this.failString = failString;
	}
	
	/**
	 * Converts a {@link CommunicationConstants.Registration} string representation of a {@link RegistrationAnswerStatus}
	 * into a {@link RegistrationAnswerStatus} enum object.
	 * @param answereStatusAsString
	 * @return The {@link RegistrationAnswerStatus} or null if could not be mapped to a correct {@link RegistrationAnswerStatus}
	 */
	public static RegistrationAnswerStatus answereStatusFromString(String answereStatusAsString)
	{
		if (answereStatusAsString.equals(CommunicationConstants.Registration.OK))
			return RegistrationAnswerStatus.OK;
		
		if (answereStatusAsString.equals(CommunicationConstants.Registration.FAIL_EMAIL))
			return RegistrationAnswerStatus.FAIL_EMAIL;
		
		if (answereStatusAsString.equals(CommunicationConstants.Registration.FAIL_USERNAME))
			return RegistrationAnswerStatus.FAIL_USERNAME;
		
		if (answereStatusAsString.equals(CommunicationConstants.Registration.FAIL_INVALID_USERNAME))
			return RegistrationAnswerStatus.FAIL_INVALID_USERNAME;
		
		if (answereStatusAsString.equals(CommunicationConstants.Registration.FAIL_INVALID_EMAIL))
			return RegistrationAnswerStatus.FAIL_INVALID_EMAIL;
		
		return null;
	}
	
	
	private String answerStatusToString()
	{
		switch(status)
		{
			case OK: return CommunicationConstants.Registration.OK;
			case FAIL_EMAIL: return CommunicationConstants.Registration.FAIL_EMAIL;
			case FAIL_USERNAME: return CommunicationConstants.Registration.FAIL_USERNAME;
			case FAIL_INVALID_EMAIL: return CommunicationConstants.Registration.FAIL_INVALID_EMAIL;
			case FAIL_INVALID_USERNAME: return CommunicationConstants.Registration.FAIL_INVALID_USERNAME;
		}
		
		return null;
	}
	
	
	public String getFailString()
	{
		return failString;
	}
	
	
	public RegistrationAnswerStatus getAnswerStatus()
	{
		return status;
	}
	
	
	@Override
	public String toHttpGet() {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public String toHttpPost() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toXml() {
	
		if (status == RegistrationAnswerStatus.OK)
			return "<"+SystemMessage.TAG+"><"+TAG+" "+STATUS_ATTRIBUTE+"=\"" + answerStatusToString() + "\" /></"+SystemMessage.TAG+">";
		else
			return "<"+SystemMessage.TAG+"><"+TAG+" "+STATUS_ATTRIBUTE+"=\"" + answerStatusToString() + "\" "+FAIL_STRING_ATTRIBUTE+"=\""+failString+"\" /></"+SystemMessage.TAG+">";
		
	}

}
