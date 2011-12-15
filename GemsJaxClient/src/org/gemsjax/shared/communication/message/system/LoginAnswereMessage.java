package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.CommunicationConstants;

/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class LoginAnswereMessage extends SystemMessage{

	public enum LoginAnswerStatus
	{
		/**
		 * Used to say, that the login was correct.
		 * This is mapped to the integer constant {@link CommunicationConstants.Login#OK}
		 */
		OK,
		/**
		 * Used to say, that the login had failed
		 * This is mapped to the integer constant {@link CommunicationConstants.Login#FAIL}
		 */
		FAIL
	}
	
	
	private LoginAnswerStatus answer;
	
	public LoginAnswereMessage(LoginAnswerStatus answer)
	{
		this.answer = answer;
	}
	
	
	@Override
	public String toXml() {
		String ret ="<sys><login status=\""+answerStatusToString(answer)+"\" /></sys>";
		return ret;
	}
	
	
	/**
	 * Converts a {@link LoginAnswerStatus} to a String according the {@link CommunicationConstants.Login} constants
	 * @param status
	 * @return
	 */
	private static String answerStatusToString(LoginAnswerStatus status)
	{
		switch (status)
		{
		case OK: return CommunicationConstants.Login.OK;
		case FAIL: return CommunicationConstants.Login.FAIL;
		}
		
		
		return null;
	}
	 
	/**
	 * Converts a String to {@link LoginAnswerStatus} according the {@link CommunicationConstants.Login} constants
	 * @param status
	 * @return
	 */
	public static LoginAnswerStatus stringToAnswerStatus(String status)
	{
		if (status.equals(CommunicationConstants.Login.OK))
			return LoginAnswerStatus.OK;
		else
		if (status.equals(CommunicationConstants.Login.FAIL))
			return LoginAnswerStatus.FAIL;
		
		
		return null;
	}


	public LoginAnswerStatus getAnswer() {
		return answer;
	}

}
