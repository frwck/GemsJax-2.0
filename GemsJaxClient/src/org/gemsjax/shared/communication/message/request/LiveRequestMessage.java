package org.gemsjax.shared.communication.message.request;


/**
 * Pushed from Server to the client, if a new friendshiprequest has been created while the user is online.
 * @author Hannes Dorfmann
 *
 */
public abstract class LiveRequestMessage extends RequestMessage{
	
	public static final String TAG="new";
	public static final String ATTRIBUTE_ID="id";
	public static final String ATTRIBUTE_REQUESTER_USERNAME="requester";
	public static final String ATTRIBUTE_REQUESTER_DISPLAY_NAME="requesterDispName";
	public static final String ATTRIBUTE_DATETIME="date";
	
	private Request request;
	
	public LiveRequestMessage(Request request)
	{
		this.request = request;
	}
	
	protected String openingXml()
	{
		return super.openingXml()+"<"+TAG+" "+ATTRIBUTE_ID+"=\""+request.getId()+"\" "+ATTRIBUTE_REQUESTER_USERNAME+"=\""+request.getRequesterUsername()+"\" "+ATTRIBUTE_REQUESTER_DISPLAY_NAME+"=\""+request.getRequesterDisplayName()+"\" "+ATTRIBUTE_DATETIME+"=\""+request.getDate().getTime()+"\" >";
	}

	protected String closingXml()
	{
		return super.closingXml()+"</"+TAG+">";
	}
}
