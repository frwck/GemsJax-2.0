package org.gemsjax.server.persistence.dao.exception;

import org.gemsjax.shared.user.RegisteredUser;


/**
 * Is throw, when the a new {@link RegisteredUser} should be created, but a {@link RegisteredUser} is already registered with this email address.
 * @author Hannes Dorfmann
 *
 */
public class EMailInUseExcpetion extends Exception{
	
}
