package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;

public class Collaborator implements Serializable{
	
	private int userId;
	private String displayedName;
	
	

	public Collaborator(){}
	
	public Collaborator(int userId, String displayName){
		this.userId = userId;
		this.displayedName = displayName;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getDisplayedName() {
		return displayedName;
	}


	public void setDisplayedName(String dispalyName) {
		this.displayedName = dispalyName;
	}


	@Override
	public void serialize(Archive a) throws Exception {
		userId = a.serialize("userId", userId).value;
		displayedName = a.serialize("displayedName", displayedName).value;
	}

}
