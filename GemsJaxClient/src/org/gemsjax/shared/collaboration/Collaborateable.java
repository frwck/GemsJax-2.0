package org.gemsjax.shared.collaboration;

import java.util.Map;
import java.util.Set;
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
	public int getId();
	
	/**
	 * Get the Owner who has created this {@link Collaborateable}
	 * @return
	 */
	public RegisteredUser getOwner();
	
	/**
	 * Get the name
	 * @return
	 */
	public String getName();
	
	
	/**
	 * Set the name
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Get all {@link User}s that work collaborative on this {@link Collaborateable}.
	 * @return
	 */
	public Set<User> getUsers();
	
	/**
	 * Get a map that contains the vector clock values.
	 * <b>NOTE:</b> if a {@link User} is not present in this map, then you can assume the vector clock value is 0 (zero) 
	 * @return
	 */
	public Map<User,Integer> getVectorClock();
	
	/**
	 * Used for the search system
	 * @return
	 */
	public String getKeywords();
	
	/**
	 * Set the keywords
	 * @param keywords
	 */
	public void setKeywords(String keywords);
	
	/**
	 * Get all Transactions
	 * @return
	 */
	public Set<Transaction> getTransactions();
	
}
