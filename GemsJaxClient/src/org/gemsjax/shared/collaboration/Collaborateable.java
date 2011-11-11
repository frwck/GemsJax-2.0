package org.gemsjax.shared.collaboration;

import java.util.Map;
import java.util.Set;

import org.gemsjax.shared.Constants;
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
		 * No public access granted. That means, that only the owner and the collaborative working {@link User} (invited) have access to this {@link Collaborateable}
		 */
		public static int NO_PERMISSION = 0;
		/**
		 * Every {@link User} can access this with reading permission. Changing this {@link Collaborateable} is not possible except the owner {@link User}
		 * and the other collaborative working {@link User} (invited).
		 */
		public static int READ_ONLY_PERMISSION = 1;
		
		/**
		 * This means, that every user can make a copy of this {@link Collaborateable}
		 */
		public static int COPYABLE_PERMISSION = 2;
	
	
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
	 * Set the owner
	 * @param owner
	 */
	public void setOwner(RegisteredUser owner);
	
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
	
	/**
	 * Get the {@link PublicPermission}
	 * @return
	 */
	public int getPublicPermission();
	
	/**
	 * @see #getPublicPermission()
	 * @param permission
	 */
	public void setPublicPermission(int permission);
	
	
}
