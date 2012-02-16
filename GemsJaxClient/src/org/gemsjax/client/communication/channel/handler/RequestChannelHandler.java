package org.gemsjax.client.communication.channel.handler;

import org.gemsjax.shared.communication.message.request.GetAllRequestsAnswerMessage;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.ReferenceableRequestMessage;
import org.gemsjax.shared.communication.message.request.RequestError;

public interface RequestChannelHandler {

	/**
	 * Called if an incoming message could not be parsed successfully
	 * @param e
	 */
	public abstract void onParseError(Exception e);
	
	/**
	 * Called if a {@link LiveRequestMessage} has been received
	 * @param msg
	 */
	public abstract void onLiveRequestReceived(LiveRequestMessage msg);
	
	
	public abstract void onGetAllRequestsAnswer(GetAllRequestsAnswerMessage m);
	
	
	public abstract void onRequestAnsweredSuccessfully(String referenceId);
	
	/**
	 * Called, if the server has answered on a previous {@link ReferenceableRequestMessage} with a negative result
	 * @param referenceId
	 * @param error
	 */
	public abstract void onRequestError(String referenceId, RequestError error);
	
	
}
