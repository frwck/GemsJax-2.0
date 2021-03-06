package org.gemsjax.shared.user;

import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;

/**
 * This is a {@link User} that represents a normal registered {@link User}
 * @author hannes
 *
 */
public interface RegisteredUser extends User{

	
	public String getUsername();
	
	/**
	 * Get the email address
	 * @return
	 */
	public String getEmail();
	
	
	public void setEmail(String email);
	
	public void setDisplayedName(String displayedName);
	
	/**
	 * Get the {@link Collaborateable}s, which this {@link RegisteredUser} has created. That means, that this {@link RegisteredUser} is the owner.
	 * @return
	 */
	public Set<Collaborateable> getOwnedCollaborateables();
	
	/**
	 * Get the {@link Experiment}s, which are (co-)administrated by this {@link RegisteredUser}. <b>NOTE:</b> administrator is not the same as owner
	 * @return
	 */
	public Set<Experiment> getAdministratedExperiments();
	
	/**
	 * Get the {@link Experiment}s, which this {@link RegisteredUser} has created. That means, that this {@link RegisteredUser} is the owner.
	 * @return
	 */
	public Set<Experiment> getOwnedExperiments();
	
	
	public Set<RegisteredUser> getAllFriends();
	
	
}
