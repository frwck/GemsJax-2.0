package org.gemsjax.shared.communication.message.collaboration;

import org.gemsjax.shared.communication.CommunicationConstants;

public enum CollaborationType {
	
	MODEL,
	METAMODEL
	;
	
	/**
	 * Converts a {@link CollaborationType} to its {@link CommunicationConstants.CollaborateableType} constant
	 * @return
	 */
	public String toConstant()
	{
		switch (this)
		{
		case METAMODEL: return CommunicationConstants.CollaborateableType.TYPE_METAMODEL;
		case MODEL: return CommunicationConstants.CollaborateableType.TYPE_MODEL;
		}
		
		return null;
	}
	
	
	/**
	 * Converts a {@link CommunicationConstants.CollaborateableType} to its {@link CollaborationType} enum
	 * @param c
	 * @return
	 */
	public static CollaborationType fromConstant(String c)
	{
		if (c.equals(CommunicationConstants.CollaborateableType.TYPE_METAMODEL))
			return METAMODEL;
		else
		if(c.equals(CommunicationConstants.CollaborateableType.TYPE_MODEL))
		return MODEL;
		
		
		return null;
	}
}
