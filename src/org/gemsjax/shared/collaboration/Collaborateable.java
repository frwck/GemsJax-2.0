package org.gemsjax.shared.collaboration;

import org.gemsjax.shared.collaboration.command.Command;

/**
 * This interface adds the ability to work collaborative on something.
 * "To work collaborative" means, that {@link Transaction}s, which are containing {@link Command}s, can be executed  on the {@link Collaborateable}s
 * from different users.
 * 
 * @author Hannes Dorfmann
 *
 */
public interface Collaborateable {

	/**
	 * Get the unique ID.
	 * The ID is very important for the communication between client, server and other clients
	 * via {@link Transaction}s
	 * @return
	 */
	public String getID();
	
}
