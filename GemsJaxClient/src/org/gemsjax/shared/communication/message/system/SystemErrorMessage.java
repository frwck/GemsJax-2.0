package org.gemsjax.shared.communication.message.system;

import org.gemsjax.shared.communication.message.CommunicationError;

/**
 * Is used to wrap an {@link CommunicationError} in {@link SystemMessage}
 * @author Hannes Dorfmann
 *
 */
public class SystemErrorMessage extends SystemMessage{

	public static final String TAG = CommunicationError.TAG;
	public static final String ATTRIBUTE_TYPE= CommunicationError.ATTRIBUTE_TYPE;
	
	private CommunicationError error;
	
	
	
	public SystemErrorMessage(CommunicationError error)
	{
		this.error = error;
	}

	@Override
	public String toXml() {
		return "<"+SystemMessage.TAG+">"+error.toXml()+"<"+SystemMessage.TAG+">";
	}
	
	
	public CommunicationError getError()
	{
		return error;
	}
	
}
