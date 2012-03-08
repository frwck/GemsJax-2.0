package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.gemsjax.client.communication.channel.NotificationChannel;
import org.gemsjax.client.communication.channel.RequestChannel;
import org.gemsjax.client.communication.channel.handler.NotificationChannelHandler;
import org.gemsjax.client.communication.channel.handler.RequestChannelHandler;
import org.gemsjax.client.module.handler.NotificationRequestModuleHandler;
import org.gemsjax.shared.communication.message.friend.GetAllFriendsMessage;
import org.gemsjax.shared.communication.message.notification.DeleteNotificationMessage;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsAnswerMessage;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsMessage;
import org.gemsjax.shared.communication.message.notification.LiveNotificationMessage;
import org.gemsjax.shared.communication.message.notification.Notification;
import org.gemsjax.shared.communication.message.notification.NotificationAsReadMessage;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.request.AcceptRequestMessage;
import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.GetAllRequestsAnswerMessage;
import org.gemsjax.shared.communication.message.request.LiveAdminExperimentRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveCollaborationRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveFriendshipRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.RejectRequestMessage;
import org.gemsjax.shared.communication.message.request.Request;
import org.gemsjax.shared.communication.message.request.RequestError;


/**
 * This module take care of any request handling (receiving live request, accept, reject etc) 
 * and notification handling (receiving live notifications, mark as read, delete)
 * @author Hannes Dorfmann
 *
 */
public class NotificationRequestModule implements RequestChannelHandler, NotificationChannelHandler {
	
	/**
	 * This prefix is used as the reference id prefix for each {@link DeleteNotificationMessage}.
	 * In addition, with this prefix could be determine, if the server response was on a "delete" or "mark as read" operation.
	 */
	private static final String deleteNotificationRefIdPrefix = "delNot";
	
	/**
	 * This prefix is used as the reference id prefix for each {@link NotificationAsReadMessage}.
	 * In addition, with this prefix could be determine, if the server response was on a "delete" or "mark as read" operation.
	 */
	private static final String asReadNotificationRefIdPrefix = "readNot";
	
	
	private RequestChannel requestChannel;
	private NotificationChannel notificationChannel;
	private long getAllCounter;
	private String lastGetAllRequestRefId;
	private String lastGetAllNotificationRefId;
	
	
	private Set<FriendshipRequest> friendshipRequests;
	private Set<CollaborationRequest> collaborationRequests;
	private Set<AdminExperimentRequest> experimentRequests;
	private Set<Notification> notifications;
	
	private long refIdCounter;
	
	private Map<String, Notification> currentPendingNotifications;
	private Map<String, Request> currentPendingRequests;
	
	
	private long initialNotificationCount;

	
	private Set<NotificationRequestModuleHandler> handlers;
	
	
	private boolean initialAllNotifications = false;
	private boolean initialAllRequests = false;
	
	
	public NotificationRequestModule(long l, NotificationChannel notificationChannel, RequestChannel rc)
	{
		this.notificationChannel = notificationChannel;
		this.requestChannel = rc;
		this.notificationChannel.addNotificationChannelHandler(this);
		this.requestChannel.addRequestChannelHandler(this);
		
		getAllCounter = 0;
		refIdCounter = 0;
		
		currentPendingNotifications = new TreeMap<String, Notification>();
		currentPendingRequests = new TreeMap<String, Request>();
		
		this.handlers = new LinkedHashSet<NotificationRequestModuleHandler>();
		this.initialNotificationCount = l;
		
		
		this.notifications = new LinkedHashSet<Notification>();
		this.friendshipRequests = new LinkedHashSet<FriendshipRequest>();
		this.experimentRequests = new LinkedHashSet<AdminExperimentRequest>();
		this.collaborationRequests = new LinkedHashSet<CollaborationRequest>();
	}
	
	
	
	public void addNotificationRequestModuleHandler(NotificationRequestModuleHandler h)
	{
		this.handlers.add(h);
	}
	
	
	public void removeNotificationRequestModuleHandler(NotificationRequestModuleHandler h)
	{
		this.handlers.remove(h);
	}
	
	
	/**
	 * Retrieve asynchronously all Requests from the server.
	 * After receiving all Notification {@link NotificationRequestModuleHandler#onGetAllRequestSuccessfull()}
	 * or {@link NotificationRequestModuleHandler#onGetAllRequestFailed(RequestError)}
	 * will be called.
	 * @throws IOException
	 */
	public void doGetAllRequests() throws IOException
	{
		getAllCounter++;
		lastGetAllRequestRefId = "GAR"+getAllCounter;
		
		requestChannel.send(new GetAllFriendsMessage(lastGetAllRequestRefId));
	}
	
	/**
	 * Retrieve asynchronously all Notifications from the server.
	 * After receiving all Notification {@link NotificationRequestModuleHandler#onGetAllNotificationSuccessful()}
	 * or {@link NotificationRequestModuleHandler#onGetAllNotificationFailed(NotificationError)()}
	 * will be called.
	 * @throws IOException
	 */
	public void doGetAllNotifications() throws IOException
	{
		getAllCounter++;
		lastGetAllNotificationRefId = "GAN"+getAllCounter;
		
		notificationChannel.send(new GetAllNotificationsMessage(lastGetAllNotificationRefId));
	}
	
	private synchronized String generateReferenceId(){
		
		refIdCounter++;
		String ref = "refNR"+refIdCounter;
		while (currentPendingNotifications.containsKey(ref)|| currentPendingRequests.containsKey(ref) )
		{ 
			refIdCounter ++;
			ref= "refNR"+refIdCounter;
		}
		
		
		return ref;
	}
	
	/**
	 * Accept a request
	 * @param requestId
	 * @throws IOException 
	 */
	public void doAcceptRequest(Request request) throws IOException
	{
		String referenceId = generateReferenceId();
		currentPendingRequests.put(referenceId, request);
		requestChannel.send(new AcceptRequestMessage(referenceId, request.getId()));
	}

	
	/**
	 * Reject a request.
	 * 
	 * @param request
	 * @throws IOException
	 */
	public void doRejectRequest(Request request) throws IOException
	{
		String referenceId = generateReferenceId();
		currentPendingRequests.put(referenceId, request);
		requestChannel.send(new RejectRequestMessage(referenceId, request.getId()));
	}
	
	/**
	 * Mark a {@link Notification} as read.
	 * {@link NotificationRequestModuleHandler#onNotificationMarkedAsReadError(Notification, NotificationError)} or {@link NotificationRequestModuleHandler#onNotificationMarkedAsReadSuccessfully(Notification)}
	 * will be called when the result is ready
	 * @param n
	 * @throws IOException
	 */
	public void markNotificationAsRead(Notification n) throws IOException{
		String referenceId = generateReferenceId();
		currentPendingNotifications.put(referenceId, n);
		requestChannel.send(new NotificationAsReadMessage(referenceId, n.getId()));
	}
	
	/**
	 * Mark a {@link Notification} as read.
	 * {@link NotificationRequestModuleHandler#onNotificationDeletedSuccessfully(Notification)} or {@link NotificationRequestModuleHandler#onNotificationDeleteError(Notification, NotificationError)}
	 * will be called when this operation is done
	 * @param n
	 * @throws IOException
	 */
	public void deleteNotification(Notification n) throws IOException{
		String referenceId = generateReferenceId();
		currentPendingNotifications.put(referenceId, n);
		requestChannel.send(new NotificationAsReadMessage(referenceId, n.getId()));
	}
	
	/**
	 * Get the total count of unread {@link Notification} (not marked as read) and the unanswered {@link Request}s
	 * @return
	 */
	public long getUneradUnansweredCount()
	{
		if (isInitializedWithGetAll())
			return friendshipRequests.size()+collaborationRequests.size()+friendshipRequests.size()+getUnreadNotificationCount();
		
		else
			return initialNotificationCount;
	}
	
	

	@Override
	public void onParseError(Exception e) {
		for (NotificationRequestModuleHandler h : handlers)
			h.onUnexpectedError(e);
	}


	@Override
	public void onLiveRequestReceived(LiveRequestMessage msg) {
		
		if (msg instanceof LiveFriendshipRequestMessage)
			friendshipRequests.add((FriendshipRequest)msg.getRequest());
		else
		if (msg instanceof LiveAdminExperimentRequestMessage)
			experimentRequests.add((AdminExperimentRequest)msg.getRequest());
		else
		if (msg instanceof LiveCollaborationRequestMessage)
			collaborationRequests.add((CollaborationRequest)msg.getRequest());
		
		fireUpdated();	
		
		for (NotificationRequestModuleHandler h : handlers)
			h.onLiveRequestReceived(msg);
	}


	@Override
	public void onGetAllRequestsAnswer(GetAllRequestsAnswerMessage m) {
		
		// the received result is the expected, because its the latest
		if (m.getReferenceId().equals(lastGetAllRequestRefId))
		{
			friendshipRequests = m.getFriendshipRequests();
			collaborationRequests = m.getCollaborationRequests();
			experimentRequests = m.getExperimentRequests();
			initialAllRequests = true;
			fireUpdated();
		}
	}

	
	private void fireUpdated()
	{
		for (NotificationRequestModuleHandler h : handlers)
			h.onUpdated();
	}

	@Override
	public void onRequestError(String referenceId, RequestError error) {
		
		if (referenceId.equals(lastGetAllRequestRefId))
			for (NotificationRequestModuleHandler h : handlers)
				h.onGetAllRequestFailed(error);
		
		else
		{
			Request r = currentPendingRequests.get(referenceId);
			currentPendingRequests.remove(referenceId);
			
			for (NotificationRequestModuleHandler h: handlers)
				h.onRequestAnsweredFail(r, error);
		}
		
	}



	@Override
	public void onRequestAnsweredSuccessfully(String referenceId) {
		
		if (referenceId.equals(lastGetAllRequestRefId))
			for (NotificationRequestModuleHandler h : handlers)
				h.onGetAllRequestSuccessfull();
		
		else
		{
			Request r = currentPendingRequests.get(referenceId);
			currentPendingRequests.remove(referenceId);
			
			for (NotificationRequestModuleHandler h: handlers)
				h.onRequestAnsweredSuccessfully(r);
		}
		
		
		fireUpdated();
		
	}



	@Override
	public void onNotificationError(String referenceId, NotificationError error) {
		
		if (referenceId.equals(lastGetAllNotificationRefId))
			for (NotificationRequestModuleHandler h: handlers)
				h.onGetAllNotificationFailed(error);
		else{
			
			Notification n = currentPendingNotifications.get(referenceId);
			currentPendingNotifications.remove(n);
			
			if (referenceId.startsWith(asReadNotificationRefIdPrefix))
				for (NotificationRequestModuleHandler h: handlers)
					h.onNotificationMarkedAsReadError(n, error);
			else
			if (referenceId.startsWith(deleteNotificationRefIdPrefix))
				for (NotificationRequestModuleHandler h: handlers)
					h.onNotificationDeleteError(n, error);
		}
			
	}



	@Override
	public void onNotificationSuccess(String referenceId) {
		
		if (referenceId.equals(lastGetAllNotificationRefId))
			for (NotificationRequestModuleHandler h: handlers)
				h.onGetAllNotificationSuccessful();
		else{
			
			Notification n = currentPendingNotifications.get(referenceId);
			currentPendingNotifications.remove(n);
			
			if (referenceId.startsWith(asReadNotificationRefIdPrefix))
				for (NotificationRequestModuleHandler h: handlers)
					h.onNotificationMarkedAsReadSuccessfully(n);
			else
			if (referenceId.startsWith(deleteNotificationRefIdPrefix)){
				for (NotificationRequestModuleHandler h: handlers)
					h.onNotificationDeletedSuccessfully(n);
			
				notifications.remove(n);
			}
		}
		
		
		fireUpdated();
		
	}



	@Override
	public void onLiveNotificationReceived(LiveNotificationMessage msg) {
		
		notifications.add(msg.getNotification());
		fireUpdated();
		
		for (NotificationRequestModuleHandler h: handlers)
			h.onLiveNotificationReceived(msg);
	}



	@Override
	public void onGetAllNotificationAnswer(String referenceId, GetAllNotificationsAnswerMessage msg) {
		if (referenceId.equals(lastGetAllNotificationRefId))
		{
			this.notifications.clear();
			this.notifications.addAll(msg.getCollaborationNotifications());
			this.notifications.addAll(msg.getExperimentNotifications());
			this.notifications.addAll(msg.getFriendshipNotifications());
			this.notifications.addAll(msg.getQuicknotificatins());
			initialAllNotifications = true;
			fireUpdated();
			
			for (NotificationRequestModuleHandler h: handlers)
				h.onGetAllNotificationSuccessful();
		}
	}
	
	
	/**
	 * Return only true, if all notifications and all request has been retrieved at least at the beginning
	 * @return
	 */
	public boolean isInitializedWithGetAll()
	{
		return initialAllNotifications && initialAllRequests;
	}
	
	
	/**
	 * Ge the count of unread notifications.
	 * Note: The Request are not counted to the returning value
	 * @return
	 */
	public int getUnreadNotificationCount(){
		int c =0; 
		for (Notification n: notifications)
			if (!n.isRead())
				c++;
			
		return c;
	}
	
	


	public Set<FriendshipRequest> getFriendshipRequests() {
		return friendshipRequests;
	}



	public Set<CollaborationRequest> getCollaborationRequests() {
		return collaborationRequests;
	}



	public Set<AdminExperimentRequest> getExperimentRequests() {
		return experimentRequests;
	}



	public Set<Notification> getNotifications() {
		return notifications;
	}
	
}
