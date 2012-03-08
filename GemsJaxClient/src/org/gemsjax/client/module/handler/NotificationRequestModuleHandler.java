package org.gemsjax.client.module.handler;

import org.gemsjax.shared.communication.message.notification.LiveNotificationMessage;
import org.gemsjax.shared.communication.message.notification.Notification;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.Request;
import org.gemsjax.shared.communication.message.request.RequestError;

public interface NotificationRequestModuleHandler {
	
	/**
	 * Called, if a LiveNotification has been received.
	 * The passed message is used to be displayed as a popup notification or something similar on the gui
	 */
	public void onLiveNotificationReceived(LiveNotificationMessage msg);
	
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
	
	/**
	 * Called, if a Request has been answered corretly
	 * @param referenceId
	 */
	public void onRequestAnsweredSuccessfully(Request r);
	
	/**
	 * Called, if a request could not be answered correclty
	 * @param referenceId
	 * @param error
	 */
	public void onRequestAnsweredFail(Request r, RequestError error);
	
	
	/**
	 * Called, if an unexpected error has occrred
	 * @param t
	 */
	public void onUnexpectedError(Throwable t);
	
	/**
	 * Called, if  an error has occurred while querying the all notification from the server
	 * @param error
	 */
	public void onGetAllNotificationFailed(NotificationError error);
	
	/**
	 * Called, if all Notification has been retrieved successfully from the server
	 */
	public void onGetAllNotificationSuccessful();
	
	public void onNotificationDeleteError(Notification n, NotificationError error);
	
	public void onNotificationDeletedSuccessfully(Notification n);
	
	
	public void onNotificationMarkedAsReadError(Notification n, NotificationError error);
	
	public void onNotificationMarkedAsReadSuccessfully(Notification n);

}
