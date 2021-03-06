package org.gemsjax.server.persistence.dao.exception;

/**
 * This Exception is thrown if an email address would be invited to an Experiment for a second time
 * @author Hannes Dorfmann
 *
 */
public class InvitationException extends Exception {

	public InvitationException(String msg)
	{
		super (msg);
		
	}
}
