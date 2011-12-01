package org.gemsjax.server.persistence.user;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.persistence.collaboration.command.CommandImpl;
import org.gemsjax.server.persistence.request.RequestImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.UserOnlineState;



public class RegisteredUserImpl extends UserImpl implements RegisteredUser {
	
	private String email;
	private String username;
	private String passwordHash;
	
	private Set<Collaborateable>ownedCollaborateables;
	private Set<Experiment> ownedExperiments;
	private Set<Experiment> administratedExperiments;
	
	
	public RegisteredUserImpl()
	{
		setOnlineState( UserOnlineState.OFFLINE);
		ownedExperiments = new LinkedHashSet<Experiment>();
		administratedExperiments = new LinkedHashSet<Experiment>();
		ownedCollaborateables = new LinkedHashSet<Collaborateable>();
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



	@Override
	public Set<Experiment> getAdministratedExperiments() {
		return administratedExperiments;
	}



	@Override
	public Set<Collaborateable> getOwnedCollaborateables() {
		return ownedCollaborateables;
	}



	@Override
	public Set<Experiment> getOwnedExperiments() {
		return ownedExperiments;
	}


}
