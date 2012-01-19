package org.gemsjax.shared.user;

import org.gemsjax.shared.communication.CommunicationConstants;

/**
 * Is used to determine the {@link User}s online state
 * @author Hannes Dorfmann
 *
 */
public enum UserOnlineState {
	
	/**
	 * The user is online.
	 * Mapped to {@link CommunicationConstants.OnlineState#ONLINE}
	 */
	ONLINE,
	/**
	 * The user is offline.
	 * Mapped to {@link CommunicationConstants.OnlineState#OFFLINE}
	 */
	OFFLINE;

	
	
	public static String toCommunicationConstant(UserOnlineState state)
	{
		switch (state)
		{
		case OFFLINE: return CommunicationConstants.OnlineState.OFFLINE;
		case ONLINE: return CommunicationConstants.OnlineState.ONLINE;
		
		}
		
		return null;
	}
	
	
	
	public static UserOnlineState toOnlineState(String stateConstant)
	{
		if(CommunicationConstants.OnlineState.OFFLINE.equals(stateConstant))
			return OFFLINE;
		else
		if (CommunicationConstants.OnlineState.ONLINE.equals(stateConstant))
			return ONLINE;
		
		
		return null;
	
	}
	
	
}
