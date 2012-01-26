package org.gemsjax.shared.communication.message.friend;

/**
 * Is sent from server to client, to inform the client, that something went wrong with his last {@link FriendMessage}. In fact,
 * a error has occurred on server side, which is transmitted with this {@link FriendErrorAnswerMessage} to the client.
 * Reason for this message could be authentication, parsing or database errors
 * @author Hannes Dorfmann
 *
 */
public class FriendErrorAnswerMessage extends ReferenceableFriendMessage{

	public static final String TAG = "error";
	public static final String ATTRIBUTE_TYPE= "type";
	
	private FriendError error;
	private String additionalInfo;
	
	public FriendErrorAnswerMessage(String referenceId, FriendError error, String additionalInfo)
	{
		super(referenceId);
		this.error = error;
		this.additionalInfo = additionalInfo;
	}
	
	public FriendErrorAnswerMessage(String referenceId, FriendError error)
	{
		super(referenceId);
		this.error = error;
		this.additionalInfo = null;
	}
	
	private String errorXml()
	{
		return "<"+TAG+" "+ATTRIBUTE_TYPE+"=\""+error.toConstant()+"\">"+(additionalInfo!=null ? additionalInfo:"")+"</"+TAG+">";
	}
	
	
	@Override
	public String toXml() {
		return "<"+FriendMessage.TAG+" "+ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID+"=\""+getReferenceId()+"\">"+errorXml()+"<"+FriendMessage.TAG+">";
	}
	
	
	public FriendError getError()
	{
		return error;
	}
	
	
	public String getAdditionalInfo()
	{
		return additionalInfo;
	}

}
