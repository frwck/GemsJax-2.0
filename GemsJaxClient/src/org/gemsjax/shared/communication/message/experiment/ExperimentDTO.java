package org.gemsjax.shared.communication.message.experiment;

import java.util.Set;

public class ExperimentDTO {
	
	private int id;
	private String name;
	private String ownerDisplayName;
	private Set<UserDTO> admins;
	
	
	public ExperimentDTO(){}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getOwnerDisplayName() {
		return ownerDisplayName;
	}


	public void setOwnerDisplayName(String ownerDisplayName) {
		this.ownerDisplayName = ownerDisplayName;
	}


	public Set<UserDTO> getAdminDisplayNames() {
		return admins;
	}


	public void setAdminDisplayNames(Set<UserDTO> admins) {
		this.admins = admins;
	}
	
}
