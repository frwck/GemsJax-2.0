package org.gemsjax.shared.communication.message.experiment;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;

public class ExperimentInvitationDTO implements Serializable{
	
	private int id;
	private String email;
	
	public ExperimentInvitationDTO(){}
	public ExperimentInvitationDTO(int id, String email){
		this.id = id;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Override
	public void serialize(Archive a) throws Exception {
		id = a.serialize("id", id).value;
		email = a.serialize("email", email).value;
	}
	

}
