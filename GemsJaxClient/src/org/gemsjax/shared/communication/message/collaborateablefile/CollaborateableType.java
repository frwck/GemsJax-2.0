package org.gemsjax.shared.communication.message.collaborateablefile;

import org.gemsjax.shared.communication.CommunicationConstants;

public enum CollaborateableType {
	
	MODEL,
	METAMODEL
	;
	
	/**
	 * Converts a {@link CollaborateableType} to its {@link CommunicationConstants.CollaborateableType} constant
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
	 * Converts a {@link CommunicationConstants.CollaborateableType} to its {@link CollaborateableType} enum
	 * @param c
	 * @return
	 */
	public static CollaborateableType fromConstant(String c)
	{
		if (c.equals(CommunicationConstants.CollaborateableType.TYPE_METAMODEL))
			return METAMODEL;
		else
		if(c.equals(CommunicationConstants.CollaborateableType.TYPE_MODEL))
		return MODEL;
		
		
		return null;
	}
}
