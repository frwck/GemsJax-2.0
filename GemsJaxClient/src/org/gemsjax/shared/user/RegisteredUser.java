package org.gemsjax.shared.user;

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
	
	public void setDisplayedName(String displayedName);
	
	
}
