package org.gemsjax.shared.communication.message.experiment;

import java.util.Set;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;

public class ExperimentDTO implements Serializable{
	
	private int id;
	private String name;
	private String ownerDisplayName;
	private Set<UserDTO> admins;
	private String desription;
	
	
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


	public Set<UserDTO> getAdmins() {
		return admins;
	}


	public void setAdmins(Set<UserDTO> admins) {
		this.admins = admins;
	}


	public String getDesription() {
		return desription;
	}


	public void setDesription(String desription) {
		this.desription = desription;
	}


	@Override
	public void serialize(Archive a) throws Exception {
		id = a.serialize("id", id).value;
		name = a.serialize("name", name).value;
		admins = a.serialize("admins", admins).value;
		desription = a.serialize("desription", desription).value;
	}
}
