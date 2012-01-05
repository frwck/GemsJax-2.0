package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.CommunicationConstants;

/**
 * 
 * @author Hannes Dorfmann
 *
 */
public class LoginAnswerMessage extends SystemMessage{

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
	
	/**
	 * The {@link LoginAnswerMessage} is embarked in this tag
	 */
	public static final String TAG = "login";
	
	
	public static final String ATTRIBUTE_STATUS="status";
	public static final String ATTRIBUTE_EXPERIMENT_GROUP="exp-group";
	
	
	private LoginAnswerStatus answer;
	private Integer experimentGroupId;
	
	public LoginAnswerMessage(LoginAnswerStatus answer)
	{
		this.answer = answer;
		experimentGroupId = null;
	}
	
	
	
	public LoginAnswerMessage(LoginAnswerStatus answer, Integer experimentGroupId)
	{
		this.answer = answer;
		this.experimentGroupId = experimentGroupId;
	}
	
	
	
	@Override
	public String toXml() {
		if (experimentGroupId==null)
			return "<"+SystemMessage.TAG+"><"+TAG+" "+ATTRIBUTE_STATUS+"=\""+answerStatusToString(answer)+"\" /></"+SystemMessage.TAG+">";
		else
			return "<"+SystemMessage.TAG+"><"+TAG+" "+ATTRIBUTE_STATUS+"=\""+answerStatusToString(answer)+"\" "+ATTRIBUTE_EXPERIMENT_GROUP+"=\""+experimentGroupId+"\" /></"+SystemMessage.TAG+">";
			
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

	
	public Integer getExperimentGroupId()
	{
		return experimentGroupId;
	}


}
