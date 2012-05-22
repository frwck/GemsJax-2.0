package org.gemsjax.shared.communication.message.experiment;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;

public class UserDTO implements Serializable{
	
	private int id;
	private String username;
	private String displayName;
	private String email;
	
	public UserDTO(){
	}
	
	public UserDTO(int id, String username, String displayName, String email){
		this.id = id;
		this.username = username;
		this.displayName = displayName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public void serialize(Archive a) throws Exception {
		id = a.serialize("id", id).value;
		username = a.serialize("username", username).value;
		displayName = a.serialize("displayName", displayName).value;
		email = a.serialize("email", email).value;
	}

}
