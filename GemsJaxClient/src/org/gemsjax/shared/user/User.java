package org.gemsjax.shared.user;

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
	public int getId();
	
	/**
	 * Get the username
	 * @return
	 */
	
	public String getDisplayedName();
	
	/**
	 * Get the {@link UserOnlineState}
	 * @return
	 */
	public UserOnlineState getOnlineState();
	
}
