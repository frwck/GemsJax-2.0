package org.gemsjax.server.module;

import java.io.IOException;

import org.gemsjax.server.communication.channel.handler.NotificationChannelHandler;
import org.gemsjax.server.persistence.dao.NotificationDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateNotificationDAO;
import org.gemsjax.server.persistence.notification.NotificationImpl;
import org.gemsjax.server.persistence.request.FriendshipRequestImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.notification.NotificationErrorMessage;
import org.gemsjax.shared.communication.message.notification.SuccessfulNotificationMessage;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.notification.Notification;
import org.gemsjax.shared.user.RegisteredUser;

public class NotificationModule implements NotificationChannelHandler{

	
	private static NotificationModule INSTANCE = new NotificationModule();
	private NotificationDAO dao;
	
	private NotificationModule()
	{
		dao = new HibernateNotificationDAO();
	}
	
	public static NotificationModule getInstance(){
		return INSTANCE;
	}
	
	
	
	
	public void onFriendshipAccepted(RegisteredUser receiver, RegisteredUser acceptor)
	{
		// TODO implement
	}
	
	
	public void onFriendshipRejected(RegisteredUser receiver, RegisteredUser acceptor)
	{
		// TODO implement
	} 
	
	
	public void onAdminExperimentAccepted(RegisteredUser receiver, RegisteredUser acceptor, Experiment experiment)
	{
		// TODO implement
	}
	
	public void onAdminExperimentRejected(RegisteredUser receiver, RegisteredUser acceptor, Experiment experiment)
	{
		// TODO implement
	}
	
	
	public void onCollaborationAccepted(RegisteredUser receiver, RegisteredUser acceptor, Collaborateable collaborateable)
	{
		// TODO implement
	}
	
	public void onCollaborationRejected(RegisteredUser receiver, RegisteredUser acceptor, Collaborateable collaborateable)
	{
		// TODO implement
	}
	
	
	public long getUnreadNotifications(RegisteredUser user){
		return dao.getUnreadCount(user);
	}

	@Override
	public void onMarkNotificationAsRead(String referenceId,
			long notificationId, OnlineUser u) {
		
		try {
			Notification n =  dao.getNotification(notificationId);
			
			if (n.getReceiver().getId() == u.getId()){
				
				try {
					dao.setRead(n, true);
					u.getNotificationChannel().send(new SuccessfulNotificationMessage(referenceId));
				} catch (IOException e) {
					// TODO What to do if message cant be sent
					e.printStackTrace();
				} catch (DAOException e) {
					try {
						u.getNotificationChannel().send(new NotificationErrorMessage(referenceId, NotificationError.DATABASE));
					} catch (IOException e1) {
						// TODO What to do if message cant be sent
						e1.printStackTrace();
					}
				}
			} else
				try {
					u.getNotificationChannel().send(new NotificationErrorMessage(referenceId, NotificationError.PERMISSION_DENIED));
				} catch (IOException e) {
					// TODO What to do if message cant be sent
					e.printStackTrace();
				}
						
		} catch (NotFoundException e) {
			try {
				u.getNotificationChannel().send(new NotificationErrorMessage(referenceId, NotificationError.NOT_FOUND));
			} catch (IOException e1) {
				// TODO What to do if message cant be sent
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void onDeleteNotification(String referenceId, long notificationId,
			OnlineUser u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetAllNotifications(String referenceId, OnlineUser u) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
