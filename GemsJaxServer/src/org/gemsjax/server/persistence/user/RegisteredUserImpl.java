package org.gemsjax.server.persistence.user;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.experiment.ExperimentGroupImpl;
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
	private Set<RegisteredUser> friends;
	private Set<RegisteredUser> friendOf;
	
	
	public RegisteredUserImpl()
	{
		setOnlineState( UserOnlineState.OFFLINE);
		ownedExperiments = new LinkedHashSet<Experiment>();
		administratedExperiments = new LinkedHashSet<Experiment>();
		ownedCollaborateables = new LinkedHashSet<Collaborateable>();
		friends = new LinkedHashSet<RegisteredUser>();
		friendOf = new LinkedHashSet<RegisteredUser>();
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
	

	/**
	 * This method is for Hibernate {@link UserDAO} only.
	 * Use {@link #getAllFriends()} to get all friends
	 * @return
	 */

	public Set<RegisteredUser> getFriends()
	{
		return friends;
	}
	
	/**
	 * This method is for Hibernate {@link UserDAO} only.
	 * Use {@link #getAllFriends()} to get all friends
	 * @return
	 */
	public Set<RegisteredUser> getFriendOf()
	{
		return friendOf;
	}
	
	
	public Set<RegisteredUser> getAllFriends()
	{
		Set<RegisteredUser> f = new LinkedHashSet<RegisteredUser>();
		
		for (RegisteredUser u: friends)
			f.add(u);
		
		for (RegisteredUser u: friendOf)
			f.add(u);
		
		
		return f;
		
	}
	
	/*
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof RegisteredUserImpl) ) return false;
		
		final RegisteredUserImpl that = (RegisteredUserImpl) other;
		
		if (getId() != null && that.getId() != null)
			return this.getId().equals(that.getId());
		
		return false;
	}
		
	@Override
	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		else
			return super.hashCode();
	}
	
	*/
}
