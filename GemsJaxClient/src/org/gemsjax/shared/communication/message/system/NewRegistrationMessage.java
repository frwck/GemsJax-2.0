package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.user.RegisteredUser;

import com.google.gwt.http.client.URL;

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
		postData.append(URL.encode("username")).append("=").append(URL.encode(username));
		postData.append("&");
		postData.append(URL.encode("password")).append("=").append(URL.encode(password));
		postData.append("&");
		postData.append(URL.encode("email")).append("=").append(URL.encode(email));
		
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
