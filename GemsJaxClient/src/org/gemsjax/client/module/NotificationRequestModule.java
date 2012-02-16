package org.gemsjax.client.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.NotificationChannel;
import org.gemsjax.client.communication.channel.RequestChannel;
import org.gemsjax.client.communication.channel.handler.RequestChannelHandler;
import org.gemsjax.client.module.handler.NotificationRequestModuleHandler;
import org.gemsjax.shared.communication.message.friend.GetAllFriendsMessage;
import org.gemsjax.shared.communication.message.request.AcceptRequestMessage;
import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.GetAllRequestsAnswerMessage;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.RejectRequestMessage;
import org.gemsjax.shared.communication.message.request.RequestError;


/**
 * This module take care of any request handling (receiving live request, accept, reject etc) 
 * and notification handling (receiving live notifications, mark as read, delete)
 * @author Hannes Dorfmann
 *
 */
public class NotificationRequestModule implements RequestChannelHandler {
	
	
	private RequestChannel requestChannel;
	private NotificationChannel notificationChannel;
	private int getAllCounter;
	private String lastGetAllRequestRefId;
	
	
	private Set<FriendshipRequest> friendshipRequests;
	private Set<CollaborationRequest> collaborationRequests;
	private Set<AdminExperimentRequest> experimentRequests;
	private Set notificaions;
	
	private int initialNotificationCount;

	
	private Set<NotificationRequestModuleHandler> handlers;
	
	
	
	public NotificationRequestModule(int initialNotificationCount, NotificationChannel notificationChannel, RequestChannel requestChannel)
	{
		this.notificationChannel = notificationChannel;
		this.requestChannel = requestChannel;
		getAllCounter = 0;
		
		this.handlers = new LinkedHashSet<NotificationRequestModuleHandler>();
		this.initialNotificationCount = initialNotificationCount;
	}
	
	
	
	public void addNotificationRequestModuleHandler(NotificationRequestModuleHandler h)
	{
		this.handlers.add(h);
	}
	
	
	public void removeNotificationRequestModuleHandler(NotificationRequestModuleHandler h)
	{
		this.handlers.remove(h);
	}
	
	
	public void doGetAllRequests() throws IOException
	{
		getAllCounter++;
		lastGetAllRequestRefId = "GA"+getAllCounter;
		
		requestChannel.send(new GetAllFriendsMessage(lastGetAllRequestRefId));
	}
	
	/**
	 * Accept a request
	 * @param requestId
	 * @throws IOException 
	 */
	public void doAcceptRequest(String referenceId, long requestId) throws IOException
	{
		requestChannel.send(new AcceptRequestMessage(referenceId, requestId));
	}

	
	/**
	 * Reject a request
	 * @param referenceId
	 * @param requestId
	 * @throws IOException
	 */
	public void doRejectRequest(String referenceId, long requestId) throws IOException
	{
		requestChannel.send(new RejectRequestMessage(referenceId, requestId));
	}
	
	
	public int getUneradUnansweredCount()
	{
		if (friendshipRequests!=null && collaborationRequests!=null && friendshipRequests!=null && notificaions!=null)
			return friendshipRequests.size()+collaborationRequests.size()+friendshipRequests.size()+notificaions.size();
		
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
			for (NotificationRequestModuleHandler h: handlers)
				h.onRequestAnsweredFail(referenceId, error);
		
		fireUpdated();
	}



	@Override
	public void onRequestAnsweredSuccessfully(String referenceId) {
		
		for (NotificationRequestModuleHandler h: handlers)
			h.onRequestAnsweredSuccessfully(referenceId);
	}
	
	
}
