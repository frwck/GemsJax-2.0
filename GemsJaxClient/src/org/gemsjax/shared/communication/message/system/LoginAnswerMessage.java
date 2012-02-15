package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.CommunicationConstants;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.user.RegisteredUser;

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
	public static final String ATTRUBUTE_UNREAD_NOTIFICATION_COUNT="noti";
	public static final String ATTRIBUTE_DISPLAYED_NAME="disp-name";
	public static final String ATTRIBUTE_USER_ID ="uid";
	
	private LoginAnswerStatus answer;
	private Integer experimentGroupId;
	private Long unreadNotifications;
	private String displayedName;
	private Integer userId;
	
	/**
	 * Create a FAIL - {@link LoginAnswerMessage}. The login has failed, so use this constructor and pass {@link LoginAnswerStatus#FAIL} as parameter
	 * @param answer
	 * @throws IllegalArgumentException The {@link IllegalArgumentException} is thrown if passed argument is not {@link LoginAnswerStatus#FAIL}
	 */
	public LoginAnswerMessage(LoginAnswerStatus answer) throws IllegalArgumentException
	{
		if (answer!=LoginAnswerStatus.FAIL)
			throw new IllegalArgumentException("The LoginAnswerStatus must be FAIL. OTHERWISE use another constructor");
		
		this.answer = answer;
		experimentGroupId = null;
		this.displayedName =null;
		this.userId = null;
	}
	
	
	/**
	 * Create a successful experiment {@link LoginAnswerMessage} with {@link LoginAnswerStatus#OK} and a ExperimentGroupId
	 * @param userId The unique id of the user
	 * @param experimentGroupId The {@link ExperimentGroup#getId()} value
	 * @param displayedName The displayed name of this user
	 *
	 */
	public LoginAnswerMessage(int userId, int experimentGroupId, String displayedName)
	{
		this.answer = LoginAnswerStatus.OK;
		this.experimentGroupId = experimentGroupId;
		this.unreadNotifications = null;
		this.displayedName = displayedName;
		this.userId = userId;
	}
	
	/**
	 * Create a new {@link LoginAnswerMessage} for a normal {@link RegisteredUser}, by
	 * automatically setting {@link LoginAnswerStatus#OK} and to the passed displayed
	 * @param userId The unique id of the user
	 * @param displayedName
	 * @param unreadNotifications
	 */
	public LoginAnswerMessage(int userId, String displayedName, long unreadNotifications)
	{
		this.answer = LoginAnswerStatus.OK;
		this.experimentGroupId = null;
		this.unreadNotifications = unreadNotifications;
		this.displayedName = displayedName;
		this.userId = userId;
	}
	
	
	@Override
	public String toXml() {
		
		String expGr ="", unread="", dispName="", uId="";
		
		
		if (this.displayedName!=null)
			dispName = ATTRIBUTE_DISPLAYED_NAME+"=\""+this.displayedName+"\" ";
		
		if (this.userId!=null)
			uId=ATTRIBUTE_USER_ID+"=\""+this.userId+"\" ";
		
		
		if (this.experimentGroupId!=null)
			expGr=ATTRIBUTE_EXPERIMENT_GROUP+"=\""+this.experimentGroupId+"\" ";
		else
			if (this.unreadNotifications!=null)
				unread = ATTRUBUTE_UNREAD_NOTIFICATION_COUNT+"=\""+this.unreadNotifications+"\" ";
		
		
		String attributes =ATTRIBUTE_STATUS+"=\""+answerStatusToString(answer)+"\" " +
						 	uId + dispName + expGr + unread;
		
		
		
		return "<"+SystemMessage.TAG+"><"+TAG+" "+ attributes+"/></"+SystemMessage.TAG+">";
		
			
		
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

	
	public Long getUnreadNotifications() {
		return unreadNotifications;
	}


	public String getDisplayedName() {
		return displayedName;
	}


	public Integer getUserId() {
		return userId;
	}
	

}
