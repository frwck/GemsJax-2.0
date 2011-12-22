package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.user.RegisteredUser;


/**
 * Sent from Client to Server, to register a new {@link RegisteredUser}
 * @author Hannes Dorfmann
 *
 */
public class NewRegistrationMessage extends SystemMessage {

	
	public static final String USERNAME_POST = "username";
	public static final String PASSWORD_POST = "password";
	public static final String EMAIL_POST = "email";
	
	
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
	public String toHttpGet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toHttpPost() {
		
		StringBuffer postData = new StringBuffer();
		// note param pairs are separated by a '&' 
		// and each key-value pair is separated by a '='
		postData.append(Message.CLASS_NAME_PARAMETER).append("=").append(this.getClass().getName());
		postData.append("&");
		postData.append(USERNAME_POST).append("=").append(username);
		postData.append("&");
		postData.append(PASSWORD_POST).append("=").append(password);
		postData.append("&");
		postData.append(EMAIL_POST).append("=").append(email);
		
		return postData.toString();
	}

	@Override
	public String toXml() {
		throw new UnsupportedOperationException();
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
