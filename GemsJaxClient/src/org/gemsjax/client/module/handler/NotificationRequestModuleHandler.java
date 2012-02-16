package org.gemsjax.client.module.handler;

import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.RequestError;

public interface NotificationRequestModuleHandler {
	
	/**
	 * Called, if
	 */
	public void onLiveNotificationReceived();
	
	/**
	 * Called a {@link LiveRequestMessage} has been received
	 * @param request
	 */
	public void onLiveRequestReceived(LiveRequestMessage request);
	
	/**
	 * Called, if something has chaneged on the unread notifications count
	 * or on the not answered request count
	 */
	public void onUpdated();
	
	/**
	 * Called, if all Request has been queried from the server
	 */
	public void onGetAllRequestSuccessfull();
	
	/**
	 * Called if an error has occrred while quering the messages from the server
	 * @param error
	 */
	public void onGetAllRequestFailed(RequestError error);
	
	public void onRequestAnsweredSuccessfully(String referenceId);
	
	public void onRequestAnsweredFail(String referenceId, RequestError error);
	
	
	/**
	 * Called, if an unexpected error has occrred
	 * @param t
	 */
	public void onUnexpectedError(Throwable t);

}
