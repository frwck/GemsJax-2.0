package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * The {@link Message} which is sent, to do a login
 * @author Hannes Dorfmann
 *
 */
public class LoginMessage extends SystemMessage{

	private String username;
	private String password;
	private boolean experimentLogin;
	
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
		return "<sys> <login username=\""+username+"\" password=\""+password+"\" exp=\""+(experimentLogin?"true":"false")+"\"/> </sys>";
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
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String toHttpPost() {
		// TODO Auto-generated method stub
		return null;
	}

}
