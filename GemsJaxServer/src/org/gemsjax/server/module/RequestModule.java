package org.gemsjax.server.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.RequestChannelHandler;
import org.gemsjax.server.persistence.dao.RequestDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyBefriendedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistsException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateRequestDAO;
import org.gemsjax.server.persistence.request.AdministrateExperimentRequestImpl;
import org.gemsjax.server.persistence.request.CollaborateRequestImpl;
import org.gemsjax.server.persistence.request.FriendshipRequestImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.GetAllRequestsAnswerMessage;
import org.gemsjax.shared.communication.message.request.LiveAdminExperimentRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveCollaborationRequest;
import org.gemsjax.shared.communication.message.request.LiveFriendshipRequestMessage;
import org.gemsjax.shared.communication.message.request.RequestChangedAnswerMessage;
import org.gemsjax.shared.communication.message.request.RequestError;
import org.gemsjax.shared.communication.message.request.RequestErrorMessage;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.request.AdministrateExperimentRequest;
import org.gemsjax.shared.request.CollaborateRequest;
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
	public void onAcceptRequest(OnlineUser user, long requestId,
			String referenceId) {
		
		try {
			Request r = dao.getRequestById(requestId);
			
			if (r.getReceiver().equals(user.getUser()))
			{
				RegisteredUser acceptor = (RegisteredUser) user.getUser();
				
				if (r instanceof FriendshipRequestImpl){
					NotificationModule.getInstance().onFriendshipAccepted(r.getReceiver(), acceptor);
					try {
						FriendModule.getInstance().createFriendship(r.getSender(), r.getReceiver());
					} catch (AlreadyBefriendedException e) {
						// TODO what to do if message cant be sent
						e.printStackTrace();
					}
				}
				else
				if (r instanceof AdministrateExperimentRequestImpl)
					NotificationModule.getInstance().onAdminExperimentAccepted(r.getReceiver(), acceptor, ((AdministrateExperimentRequestImpl) r).getExperiment());
					//TODO implement
				else
				if (r instanceof CollaborateRequestImpl)
					NotificationModule.getInstance().onCollaborationAccepted(r.getReceiver(), acceptor, ((CollaborateRequestImpl) r).getCollaborateable());
					// TODO implement
				
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
	public void onRejectRequest(OnlineUser user, long requestId,
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

	@Override
	public void onGetAllRequests(OnlineUser user, String referenceId) {
		
		List<Request> requests = dao.getAllRequestsBy((RegisteredUser)user.getUser());
		Set<FriendshipRequest> friends = new LinkedHashSet<FriendshipRequest>();
		Set<AdminExperimentRequest> experiments = new LinkedHashSet<AdminExperimentRequest>();
		Set<CollaborationRequest> collaborations = new LinkedHashSet<CollaborationRequest>();
		
		
		for (Request r: requests)
		{
			if (r instanceof FriendshipRequestImpl)
			{
				FriendshipRequestImpl fr = (FriendshipRequestImpl)r;
				FriendshipRequest req = new FriendshipRequest(fr.getId(), fr.getSender().getDisplayedName(), fr.getSender().getUsername(), fr.getDate());
				friends.add(req);
			}
			else
			if (r instanceof AdministrateExperimentRequestImpl)
			{
				AdministrateExperimentRequestImpl er = (AdministrateExperimentRequestImpl)r;
				AdminExperimentRequest req = new AdminExperimentRequest(er.getId(), er.getSender().getDisplayedName(), er.getSender().getUsername(), er.getDate(), er.getExperiment().getId(), er.getExperiment().getName());
				experiments.add(req);
			}
			else
			if (r instanceof CollaborateRequestImpl)
			{
				CollaborateRequestImpl cr = (CollaborateRequestImpl)r;
				CollaborationRequest req = new CollaborationRequest(cr.getId(), cr.getSender().getDisplayedName(), cr.getSender().getUsername(), cr.getDate(), cr.getCollaborateable().getId(), cr.getCollaborateable().getName() );
				collaborations.add(req);
			}
		}
		
		
		
		try {
			user.getRequestChannel().send(new GetAllRequestsAnswerMessage(referenceId, friends, experiments, collaborations));
		} catch (IOException e) {
			// TODO What to do if cant be sent
			e.printStackTrace();
		}
		
	}
	
	
	
	public void createFriendshipRequest(RegisteredUser requester, RegisteredUser receiver) throws AlreadyBefriendedException, AlreadyExistsException, DAOException
	{
			FriendshipRequestImpl r = (FriendshipRequestImpl) dao.createFriendshipRequest(requester, receiver);
			
			OnlineUser receiverOU = OnlineUserManager.getInstance().getOnlineUser(receiver);
			if (receiverOU != null) // if receiver is online
			{
				try {
					receiverOU.getRequestChannel().send(new LiveFriendshipRequestMessage(new FriendshipRequest(r.getId(), r.getSender().getDisplayedName(), r.getSender().getUsername(), r.getDate())) );
				} catch (IOException e) {
					// TODO What to do, if message can not be send
					e.printStackTrace();
				}
			}
	}
	
	
	public void createAdministrateExperimentRequest(RegisteredUser requester, RegisteredUser receiver, Experiment experiment) throws AlreadyExistsException, DAOException, AlreadyAssignedException
	{
		AdministrateExperimentRequest r =   dao.createAdministrateExperimentRequest(requester, receiver, experiment);
		OnlineUser receiverOU = OnlineUserManager.getInstance().getOnlineUser(receiver);
		if (receiverOU != null) // if receiver is online
		{
			try {
				receiverOU.getRequestChannel().send(new LiveAdminExperimentRequestMessage(new AdminExperimentRequest(r.getId(),r.getSender().getDisplayedName(), r.getSender().getUsername(), r.getDate(), r.getExperiment().getId(),r.getExperiment().getName())));
			} catch (IOException e) {
				// TODO What to do, if message can not be send
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void createCollaborationRequest(RegisteredUser requester, RegisteredUser receiver, Collaborateable c) throws AlreadyExistsException, DAOException, AlreadyAssignedException
	{
		CollaborateRequest r = dao.createCollaborateRequest(requester, receiver, c);
		OnlineUser receiverOU = OnlineUserManager.getInstance().getOnlineUser(receiver);
		if (receiverOU != null) // if receiver is online
		{
			try {
				receiverOU.getRequestChannel().send(new LiveCollaborationRequest( new CollaborationRequest(r.getId(),r.getSender().getDisplayedName(), r.getSender().getUsername(), r.getDate(), r.getCollaborateable().getId(), r.getCollaborateable().getName())));
			} catch (IOException e) {
				// TODO What to do, if message can not be send
				e.printStackTrace();
			}
		}
	}

}
