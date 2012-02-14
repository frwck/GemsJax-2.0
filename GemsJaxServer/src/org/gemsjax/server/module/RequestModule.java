package org.gemsjax.server.module;

import java.io.IOException;

import org.gemsjax.server.communication.channel.handler.RequestChannelHandler;
import org.gemsjax.server.persistence.dao.RequestDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateRequestDAO;
import org.gemsjax.shared.communication.message.request.RequestError;
import org.gemsjax.shared.communication.message.request.RequestErrorMessage;
import org.gemsjax.shared.request.Request;

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
				
			}
			else
			{ // if the request does not belongs to this user
				
				try {
					user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.PERMISSION_DENIED));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		} catch (DAOException e) {
			
			try {
				user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.DATABASE));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (NotFoundException e) {
			
			try {
				user.getRequestChannel().send(new RequestErrorMessage(referenceId, RequestError.DATABASE));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void onAcceptReject(OnlineUser user, int requestId,
			String referenceId) {
		// TODO Auto-generated method stub
		
	}
	

}
