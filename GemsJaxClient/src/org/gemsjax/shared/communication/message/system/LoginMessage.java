package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * The {@link SystemMessage} which is sent, to do a login
 * @author Hannes Dorfmann
 *
 */
public class LoginMessage extends SystemMessage{

	private String username;
	private String password;
	private boolean experimentLogin;
	
	/**
	 * The {@link LoginMessage} is embarked in this tag
	 */
	public static final String TAG = "login";
	
	/**
	 * The xml tag attribute name for the username
	 */
	public static final String USERNAME_ATRRIBUTE ="username";
	/**
	 * The xml tag attribute name for the password
	 */
	public static final String PASSWORD_ATTRIBUTE ="password";
	
	/**
	 * The xml tag attribute name for the experiment login (boolean) attribute
	 */
	public static final String FOR_EXPERIMENT_ATTRIBUTE="exp";
	
	
	/**
	 * 
	 * @param username The username
	 * @param password The password
	 * @param experimentLogin Set it to true, if you want to make a normal login as "normal" {@link RegisteredUser} or
	 * set it to false, if you want to make a login for a {@link ExperimentUser}
	 */
	public LoginMessage(String username, String password, boolean experimentLogin)
	{
		this.username = username;
		this.password = password;
		this.experimentLogin = experimentLogin;
	}
	
	
	
	
	
	@Override
	public String toXml() {
		return "<"+SystemMessage.TAG+"><"+TAG+" "+USERNAME_ATRRIBUTE+"=\""+username+"\" "+PASSWORD_ATTRIBUTE+"=\""+password+"\" "+FOR_EXPERIMENT_ATTRIBUTE+"=\""+(experimentLogin?"true":"false")+"\"/> </"+SystemMessage.TAG+">";
	}



	public String getUsername() {
		return username;
	}



	public String getPassword() {
		return password;
	}



	public boolean isExperimentLogin() {
		return experimentLogin;
	}
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof LoginMessage) ) return false;
		
		final LoginMessage that = (LoginMessage) other;
		
		
		if (username != null && that.username != null && username.equals(that.username)
				&& password!=null && that.password!=null && password.equals(that.password)  
				 && experimentLogin == that.experimentLogin)
			return true;
		
		return false;
	}
		
	@Override
	public int hashCode() {
		if (username != null && password!=null)
			return username.hashCode() + password.hashCode();
		else
			return super.hashCode();
	}



	@Override
	public String toHttpGet() {
		throw new UnsupportedOperationException();
	}



	@Override
	public String toHttpPost() {
		throw new UnsupportedOperationException();
	}

}
