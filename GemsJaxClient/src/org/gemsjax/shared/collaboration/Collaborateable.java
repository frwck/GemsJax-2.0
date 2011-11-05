package org.gemsjax.shared.collaboration;

import java.util.List;

import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

/**
 * This interface adds the ability to work collaborative on something.
 * "To work collaborative" means, that {@link Transaction}s, which are containing {@link Command}s, can be executed  on the {@link Collaborateable}s
 * from different users.
 * 
 * @author Hannes Dorfmann
 *
 */
public interface Collaborateable{

	/**
	 * Get the unique ID.
	 * The ID is very important for the communication between client, server and other clients
	 * via {@link Transaction}s
	 * @return
	 */
	public String getID();
	
	/**
	 * Get the Owner who has created this {@link Collaborateable}
	 * @return
	 */
	public RegisteredUser getOwner();
	
	/**
	 * Get the namecine espanol
	 * @return
	 */
	public String getName();
	
	/**
	 * Get all {@link User}s that work collaborative on this {@link Collaborateable}.
	 * @return
	 */
	public List<User> getUsers();
	
	/**
	 * Get the {@link VectorClock}
	 * @return
	 */
	public VectorClock getVectorClock();
	
	/**
	 * Used for the search system
	 * @return
	 */
	public String getKeyWords();
	
}
