package org.gemsjax.server.persistence.user;

import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.UserOnlineState;



public class RegisteredUserImpl extends UserImpl implements RegisteredUser {
	
	private String email;
	private String username;
	private String passwordHash;
	
	
	public RegisteredUserImpl()
	{
		setOnlineState( UserOnlineState.OFFLINE);
	}
	
	
	
	public String getPasswordHash() {
		return passwordHash;
	}



	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	

	public void setEmail(String email) {
		this.email = email;
	}



	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return username;
	}


}
