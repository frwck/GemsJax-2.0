package org.gemsjax.server.communication;

import java.util.HashMap;
import java.util.Map;

import org.gemsjax.shared.user.User;


/**
 * This singleton class handles all {@link User} that are actually online.
 * That means, that every {@link User} that is online authenticated and has a {@link OnlineUser} Wrapper-Class that wraps
 * and maps a {@link User} and his corresponding {@link OutputChannel}.
 * <br />
 * <b>NOTE:</b> Since this is a singleton use the method {@link #getInstance()} to access this singleton instance
 * @author Hannes Dorfmann
 *
 */
public class OnlineUserManager {

	/**
	 * The singleton instance
	 */
	private static OnlineUserManager INSTANCE = new OnlineUserManager();
	
	/**
	 * This Map maps the {@link User} id ({@link User#getId()}) to his {@link OnlineUser} Wrapper, which contains the {@link OutputChannel}
	 */
	private Map<Integer, OnlineUser> onlineUserMap;
	
	
	/**
	 * The private constructor, used to create the singleton instance
	 */
	private OnlineUserManager()
	{
		onlineUserMap = new HashMap<Integer, OnlineUser>();
	}
	
	
	public OnlineUserManager getInstance()
	{
		return INSTANCE;
	}
	
	
	/**
	 * Get the outputChannel for a given User or <code>null</code>  if the user is not Online
	 * @param u
	 * @return the {@link OutputChannel} or <code>null</code>  if the user is not Online
	 */
	public OutputChannel getOutputChannel(User u)
	{
		OnlineUser o = onlineUserMap.get(u.getId());
		if (o == null) 
			return null;
	
		return o.getOutputChannel();
	}
	
	/**
	 * Get the {@link OnlineUser} object
	 * @param u
	 * @return The {@link OnlineUser} object or <code>null</code> if the user is not online
	 */
	public OnlineUser getOnlineUser( User u)
	{
		return onlineUserMap.get(u.getId());
	}
	
	
	public void addOnlineUser(User u, OutputChannel o)
	{
		OnlineUser ou = new OnlineUser(u);
		
		if (onlineUserMap.containsKey(u.getId()))
		{
			
		}
	}
	
}