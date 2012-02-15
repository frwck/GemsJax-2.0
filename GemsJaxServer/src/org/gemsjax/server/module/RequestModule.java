package org.gemsjax.server.module;

import java.io.IOException;

import org.gemsjax.server.communication.channel.handler.RequestChannelHandler;
import org.gemsjax.server.persistence.dao.RequestDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateRequestDAO;
import org.gemsjax.server.persistence.request.AdministrateExperimentRequestImpl;
import org.gemsjax.server.persistence.request.CollaborateRequestImpl;
import org.gemsjax.shared.communication.message.request.RequestChangedAnswerMessage;
import org.gemsjax.shared.communication.message.request.RequestError;
import org.gemsjax.shared.communication.message.request.RequestErrorMessage;
import org.gemsjax.shared.request.FriendshipRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;

public class RequestModule implements RequestChannelHandler{

	private static RequestModule INSTANCE = new RequestModule();
	
	private RequestDAO dao;
	
	
	private RequestModule(){
		
		dao = new HibernateRequestDAO();
		
	}
	
	public static RequestModule getInstance(){
		return INSTANCE;
	}
	
	
	@Override
	public void onAcceptRequest(OnlineUser user, int requestId,
			String referenceId) {
		
		try {
			Request r = dao.getRequestById(requestId);
			
			if (r.getReceiver().equals(user.getUser()))
			{
				RegisteredUser acceptor = (RegisteredUser) user.getUser();
				
				if (r instanceof FriendshipRequest)
					NotificationModule.getInstance().onFriendshipAccepted(r.getReceiver(), acceptor);
				else
				if (r instanceof AdministrateExperimentRequestImpl)
					NotificationModule.getInstance().onAdminExperimentAccepted(r.getReceiver(), acceptor, ((AdministrateExperimentRequestImpl) r).getExperiment());
			
				else
				if (r instanceof CollaborateRequestImpl)
					NotificationModule.getInstance().onCollaborationAccepted(r.getReceiver(), acceptor, ((CollaborateRequestImpl) r).getCollaborateable());
			
				
				// finally delete the request and send a positive response
				dao.deleteRequest(r);
				try {
					user.getRequestChannel().send(new RequestChangedAnswerMessage(referenceId));
				} catch (IOException e) {
					// TODO What to do if cant be sent
					e.printStackTrace();
				}
			
			}
			else
			{ // if the request does not belongs to this user
				
				try {
					user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.PERMISSION_DENIED));
				} catch (IOException e1) {
					// TODO What to do if can't be sent
					e1.printStackTrace();
				}
				
			}
			
		} catch (DAOException e) {
			
			try {
				user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.DATABASE));
			} catch (IOException e1) {
				// TODO What to do if can't be sent
				e1.printStackTrace();
			}
			
		} catch (NotFoundException e) {
			
			try {
				user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.DATABASE));
			} catch (IOException e1) {
				// TODO What to do if cant be sent
				e1.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void onAcceptReject(OnlineUser user, int requestId,
			String referenceId) {
		
		try {
			Request r = dao.getRequestById(requestId);
			
			if (r.getReceiver().equals(user.getUser()))
			{
				RegisteredUser acceptor = (RegisteredUser) user.getUser();
				
				if (r instanceof FriendshipRequest)
					NotificationModule.getInstance().onFriendshipRejected(r.getReceiver(), acceptor);
				else
				if (r instanceof AdministrateExperimentRequestImpl)
					NotificationModule.getInstance().onAdminExperimentRejected(r.getReceiver(), acceptor, ((AdministrateExperimentRequestImpl) r).getExperiment());
			
				else
				if (r instanceof CollaborateRequestImpl)
					NotificationModule.getInstance().onCollaborationRejected(r.getReceiver(), acceptor, ((CollaborateRequestImpl) r).getCollaborateable());
			
				
				// finally delete the request and send a positive response
				dao.deleteRequest(r);
				try {
					user.getRequestChannel().send(new RequestChangedAnswerMessage(referenceId));
				} catch (IOException e) {
					// TODO What to do if cant be sent
					e.printStackTrace();
				}
			
			}
			else
			{ // if the request does not belongs to this user
				
				try {
					user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.PERMISSION_DENIED));
				} catch (IOException e1) {
					// TODO What to do if can't be sent
					e1.printStackTrace();
				}
				
			}
			
		} catch (DAOException e) {
			
			try {
				user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.DATABASE));
			} catch (IOException e1) {
				// TODO What to do if can't be sent
				e1.printStackTrace();
			}
			
		} catch (NotFoundException e) {
			
			try {
				user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.DATABASE));
			} catch (IOException e1) {
				// TODO What to do if cant be sent
				e1.printStackTrace();
			}
			
		}
	}
	
	
	
	public long getRequestCount(RegisteredUser user) throws DAOException
	{
		return dao.getRequestCount(user);
	}
	

}
