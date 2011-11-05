package org.gemsjax.server.persistence.dao.exception;

/**
 * Is thrown if the desired Username is already used by another user
 * @author Hannes Dorfmann
 *
 */
public class UsernameInUseException  extends Exception {

	private String username;
	
	public UsernameInUseException(String username, String msg)
	{
		super(msg);
		this.username = username;
	}
	
	
	public String getUsername()
	{
		return username;
	}
	
}
