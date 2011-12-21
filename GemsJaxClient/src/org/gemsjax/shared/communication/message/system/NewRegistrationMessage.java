package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.user.RegisteredUser;


/**
 * Sent from Client to Server, to register a new {@link RegisteredUser}
 * @author Hannes Dorfmann
 *
 */
public class NewRegistrationMessage extends SystemMessage {

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
		postData.append("username").append("=").append(username);
		postData.append("&");
		postData.append("password").append("=").append(password);
		postData.append("&");
		postData.append("email").append("=").append(email);
		
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
