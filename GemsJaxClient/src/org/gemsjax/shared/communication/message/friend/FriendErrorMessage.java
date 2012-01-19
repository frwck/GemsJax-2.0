package org.gemsjax.shared.communication.message.friend;

import org.gemsjax.shared.communication.message.CommunicationError;

/**
 * Is sent from server to client, to inform the client, that something went wrong with his last {@link FriendMessage}. In fact,
 * a error has occurred on server side, which is transmitted with this {@link FriendErrorMessage} to the client.
 * Reason for this message could be authentication, parsing or database errors
 * @author Hannes Dorfmann
 *
 */
public class FriendErrorMessage extends FriendMessage{

	public static final String TAG = CommunicationError.TAG;
	public static final String ATTRIBUTE_TYPE= CommunicationError.ATTRIBUTE_TYPE;
	
	private CommunicationError error;
	
	
	public FriendErrorMessage(CommunicationError error)
	{
		this.error = error;
	}
	
	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+">"+error.toXml()+"<"+FriendMessage.TAG+">";
	}
	
	
	public CommunicationError getError()
	{
		return error;
	}
	

}
