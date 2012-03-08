package org.gemsjax.client.admin.view;

import java.util.Set;

import org.gemsjax.shared.communication.message.notification.Notification;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.Request;
import org.gemsjax.shared.communication.message.request.RequestError;

import com.smartgwt.client.widgets.events.HasClickHandlers;

/**
 * Define the operations, that a {@link NotificationRequestView} should offer to his presenter
 * @author Hannes Dorfmann
 *
 */
public interface NotificationRequestView {

	/**
	 * This handler handles answers (accept or reject) on requests, that are not answered yet
	 * @author Hannes Dorfmann
	 *
	 */
	public interface AnswerRequestHandler{
		/**
		 * Called, if the user has decide, if he wants to accept or reject a request
		 * @param request
		 * @param accepted
		 */
		public abstract void onRequestAnswer(Request request, boolean accepted);
	}
	
	/**
	 * This handler handles the Notifications. That means, the user can mark them as read or delete them permanently
	 * @author Hannes Dorfmann
	 *
	 */
	public interface ChangeNotificationHandler{
		/**
		 * Called to inform, that a user has read a notification, and can now be marked as "read"
		 * @param notification
		 */
		public abstract void onNotificationAsRead(Notification notification);
		
		/**
		 * Called, if the user has clicked on the delete notification button and the notification should be deleted now
		 * @param notification
		 */
		public abstract void onDeleteNotification(Notification notification);
	}
	
	
	public void addAnswerRequestHandler(AnswerRequestHandler h);
	public void removeAnswerRequestHandler(AnswerRequestHandler h);
	
	public void addChangeNotificationHandler(ChangeNotificationHandler h);
	public void removeChangeNotificationHandler(ChangeNotificationHandler h);
	
	
	public void setNotifications(Set<Notification> notifications);
	
	public void setFriendshipRequests(Set<FriendshipRequest> requests);
	
	public void setAdministrateExperimentRequests(Set<AdminExperimentRequest> requests);
	
	public void setCollaborationRequests(Set<CollaborationRequest> requests);
	
	
	public void showIt(boolean show);
	
	
	public void showLoading();
	
	public void showContent();
	
	
	public void showRequestError(Request r, RequestError error);
	
	public void showNotificationError(Notification n, NotificationError error);
	
	/**
	 * Show a info message, that retrieving the initial notifications or requests has failed,
	 */
	public void showInitializeError();
	
	/**
	 * By clicking on this button, the initial (all) notifications and request should be reloaded
	 * @return
	 */
	public HasClickHandlers getReInitializeButton();
	
	public void setNotificationAsRead(Notification n, boolean read);
	
	public void addNotification(Notification n);
	
	public void addRequest(Request r);
	
	public void setCount(int unreadNotifications, int friendshipRequests, int experimentRequests, int collaborationRequests);
}
