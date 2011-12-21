package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.CommunicationConstants;

public class RegistrationAnswereMessage extends SystemMessage {

	public enum RegistrationAnswereStatus
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
		FAIL_EMAIL
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

	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

}
