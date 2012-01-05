package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.user.RegisteredUser;


/**
 * Sent from Client to Server, to register a new {@link RegisteredUser}
 * @author Hannes Dorfmann
 *
 */
public class NewRegistrationMessage extends SystemMessage {

	/**
	 * see the protocol specification
	 */
	public static final String TAG ="registration";
	
	public static final String ATTRIBUTE_USERNAME = "username";
	public static final String ATTRIBUTE_PASSWORD = "password";
	public static final String ATTRIBUTE_EMAIL = "email";
	
	
	
	private String username;
	private String password;
	private String email;
	
	
	public NewRegistrationMessage(String username, String password, String email)
	{
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	

	@Override
	public String toXml() {
		return "<"+SystemMessage.TAG+"><"+TAG+" "+ATTRIBUTE_USERNAME+"=\""+username+"\" "+ATTRIBUTE_PASSWORD+"=\""+password+"\" "+ATTRIBUTE_EMAIL+"=\""+email+"\"/> </"+SystemMessage.TAG+">";
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	public String getEmail() {
		return email;
	}

}
