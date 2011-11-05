package org.gemsjax.shared.collaboration;

import java.util.HashMap;
import java.util.Map;
import org.gemsjax.shared.user.User;

/**
 * A {@link VectorClock} is used in combination with {@link Collaborateable}s to determine the order of {@link Transaction}s in this distributed system 
 * @author Hannes Dorfmann
 *
 */
public class VectorClock {

	
	private Map<User, Long> clockEntries;
	
	
	public VectorClock()
	{
		clockEntries = new HashMap<User, Long>();
	}
	
	/**
	 * Set the clock value of the passed {@link User}
	 * @param user
	 * @param clockValue
	 */
	public void setValue(User user, long clockValue)
	{
		clockEntries.put(user, clockValue);
	}
	
	
	/**
	 * Get the clock value of the passed {@link User}
	 * @param u
	 * @return
	 */
	public long getValue(User u)
	{
		Long l =  clockEntries.get(u);
		
		if (l==null)
			return 0;
		else 
			return l;
	}
	
	
}
