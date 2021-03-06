package org.gemsjax.shared.user;

import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;

/**
 * This is the shared interface for User. It provides the public methods, that 
 * @author hannes
 *
 */
public interface User {

	/**
	 * Get the users internal id
	 * @return
	 */
	public Integer getId();
	
	/**
	 * Get the username
	 * @return
	 */
	
	public String getDisplayedName();
	
	/**
	 * Set the displayedName
	 * @param newName
	 */
	public void setDisplayedName(String newName);
	
	/**
	 * Get a set with all {@link Collaborateable}s, on which this {@link User} works collaborative.
	 * @return
	 */
	public Set<Collaborateable> getCollaborateables();
	
	/**
	 * Get the {@link UserOnlineState}
	 * @return
	 */
	public UserOnlineState getOnlineState();
	
	/**
	 * Set the {@link UserOnlineState}
	 * @param onlineState
	 */
	public void setOnlineState(UserOnlineState onlineState);
	
	
	public void setProfilePicture(String pictureUrl);
	
	public String getProfilePicture();
	
}
