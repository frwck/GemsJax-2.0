package org.gemsjax.server.module;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gemsjax.server.communication.channel.handler.NotificationChannelHandler;
import org.gemsjax.server.persistence.dao.NotificationDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateNotificationDAO;
import org.gemsjax.server.persistence.notification.CollaborationRequestNotificationImpl;
import org.gemsjax.server.persistence.notification.ExperimentRequestNotificationImpl;
import org.gemsjax.server.persistence.notification.FriendshipRequestNotificationImpl;
import org.gemsjax.server.persistence.notification.NotificationImpl;
import org.gemsjax.server.persistence.notification.QuickNotificationImpl;
import org.gemsjax.server.persistence.request.CollaborateRequestImpl;
import org.gemsjax.server.persistence.request.FriendshipRequestImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.notification.CollaborationRequestNotification;
import org.gemsjax.shared.communication.message.notification.ExperimentRequestNotification;
import org.gemsjax.shared.communication.message.notification.FriendshipRequestNotification;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsAnswerMessage;
import org.gemsjax.shared.communication.message.notification.LiveExperimentRequestNotification;
import org.gemsjax.shared.communication.message.notification.LiveFriendshipNotificationMessage;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.notification.NotificationErrorMessage;
import org.gemsjax.shared.communication.message.notification.QuickNotification;
import org.gemsjax.shared.communication.message.notification.SuccessfulNotificationMessage;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.LiveFriendshipRequestMessage;
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
	
	
	
	
	public void onFriendshipAccepted(RegisteredUser notificationReceiver, RegisteredUser acceptor) throws DAOException
	{
		FriendshipRequestNotificationImpl r = dao.createFriendshipRequestNotification(notificationReceiver, acceptor, true);
		
		// chek if the notification receiver is online right now, and sent him a live notification
		OnlineUser ou = OnlineUserManager.getInstance().getOnlineUser(notificationReceiver);
		
		if (ou != null)
			try {
				ou.getNotificationChannel().send(new LiveFriendshipNotificationMessage(new FriendshipRequestNotification(r.getId(), r.getDate(), r.isRead(), r.getAcceptor().getDisplayedName(), r.getAcceptor().getUsername(),r.isAccepted())));
			} catch (IOException e) {
				// TODO What to do if message cant be sent
				e.printStackTrace();
			}

		
	}
	
	
	public void onFriendshipRejected(RegisteredUser notificationReceiver, RegisteredUser acceptor) throws DAOException
	{
		FriendshipRequestNotificationImpl r = dao.createFriendshipRequestNotification(notificationReceiver, acceptor, false);
		
		// chek if the notification receiver is online right now, and sent him a live notification
		OnlineUser ou = OnlineUserManager.getInstance().getOnlineUser(notificationReceiver);
		
		if (ou != null)
			try {
				ou.getNotificationChannel().send(new LiveFriendshipNotificationMessage(new FriendshipRequestNotification(r.getId(), r.getDate(), r.isRead(), r.getAcceptor().getDisplayedName(), r.getAcceptor().getUsername(),r.isAccepted())));
			} catch (IOException e) {
				// TODO What to do if message cant be sent
				e.printStackTrace();
			}

	} 
	
	
	public void onAdminExperimentAccepted(RegisteredUser notificationReceiver, RegisteredUser acceptor, Experiment experiment) throws DAOException
	{
		ExperimentRequestNotificationImpl r = dao.createExperimentRequestNotification(notificationReceiver, acceptor, experiment,true);
		
		// chek if the notification receiver is online right now, and sent him a live notification
		OnlineUser ou = OnlineUserManager.getInstance().getOnlineUser(notificationReceiver);
		
		if (ou != null)
			try {
				ou.getNotificationChannel().send(new LiveExperimentRequestNotification(new ExperimentRequestNotification(r.getId(), r.getDate(), r.isRead(), r.getAcceptor().getDisplayedName(), r.getAcceptor().getUsername(),r.isAccepted(), r.getExperiment().getId(), r.getExperiment().getName())));
			} catch (IOException e) {
				// TODO What to do if message cant be sent
				e.printStackTrace();
			}
	}
	
	public void onAdminExperimentRejected(RegisteredUser notificationReceiver, RegisteredUser acceptor, Experiment experiment) throws DAOException
	{
		ExperimentRequestNotificationImpl r = dao.createExperimentRequestNotification(notificationReceiver, acceptor, experiment,false);
		
		// chek if the notification receiver is online right now, and sent him a live notification
		OnlineUser ou = OnlineUserManager.getInstance().getOnlineUser(notificationReceiver);
		
		if (ou != null)
			try {
				ou.getNotificationChannel().send(new LiveExperimentRequestNotification(new ExperimentRequestNotification(r.getId(), r.getDate(), r.isRead(), r.getAcceptor().getDisplayedName(), r.getAcceptor().getUsername(),r.isAccepted(), r.getExperiment().getId(), r.getExperiment().getName())));
			} catch (IOException e) {
				// TODO What to do if message cant be sent
				e.printStackTrace();
			}
	}
	
	
	public void onCollaborationAccepted(RegisteredUser notificationReceiver, RegisteredUser acceptor, Collaborateable collaborateable) throws DAOException
	{
		CollaborationRequestNotificationImpl r = dao.createCollaborationRequestNotification(notificationReceiver, acceptor, collaborateable,true);
		
		// chek if the notification receiver is online right now, and sent him a live notification
		OnlineUser ou = OnlineUserManager.getInstance().getOnlineUser(notificationReceiver);
		
		if (ou != null)
			try {
				ou.getNotificationChannel().send(new LiveExperimentRequestNotification(new ExperimentRequestNotification(r.getId(), r.getDate(), r.isRead(), r.getAcceptor().getDisplayedName(), r.getAcceptor().getUsername(),r.isAccepted(), r.getCollaborateable().getId(), r.getCollaborateable().getName())));
			} catch (IOException e) {
				// TODO What to do if message cant be sent
				e.printStackTrace();
			}
	}
	
	public void onCollaborationRejected(RegisteredUser notificationReceiver, RegisteredUser acceptor, Collaborateable collaborateable) throws DAOException
	{
		CollaborationRequestNotificationImpl r = dao.createCollaborationRequestNotification(notificationReceiver, acceptor, collaborateable,false);
		
		// chek if the notification receiver is online right now, and sent him a live notification
		OnlineUser ou = OnlineUserManager.getInstance().getOnlineUser(notificationReceiver);
		
		if (ou != null)
			try {
				ou.getNotificationChannel().send(new LiveExperimentRequestNotification(new ExperimentRequestNotification(r.getId(), r.getDate(), r.isRead(), r.getAcceptor().getDisplayedName(), r.getAcceptor().getUsername(),r.isAccepted(), r.getCollaborateable().getId(), r.getCollaborateable().getName())));
			} catch (IOException e) {
				// TODO What to do if message cant be sent
				e.printStackTrace();
			}
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
		
		try {
			Notification n =  dao.getNotification(notificationId);
			
			if (n.getReceiver().getId() == u.getId()){
				
				try {
					dao.deleteNotification(n);
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
	public void onGetAllNotifications(String referenceId, OnlineUser u) {
		
		RegisteredUser user = (RegisteredUser) u.getUser();
		
		List<Notification> notifications = dao.getNotificationsFor(user);
		
		Set<QuickNotification> quicks = new LinkedHashSet<QuickNotification>();
		Set<ExperimentRequestNotification> experiments = new LinkedHashSet<ExperimentRequestNotification>();
		Set<FriendshipRequestNotification> friends = new LinkedHashSet<FriendshipRequestNotification>();
		Set<CollaborationRequestNotification> collaborations = new LinkedHashSet<CollaborationRequestNotification>();
		
		
		for (Notification n: notifications){
			
			if (n instanceof QuickNotificationImpl)
				quicks.add(new QuickNotification(n.getId(), n.getDate(), n.isRead(), ((QuickNotificationImpl) n).getQuickNotificationType(), ((QuickNotificationImpl) n).getOptionalMessage()));
			
			else
			if (n instanceof ExperimentRequestNotificationImpl)
				experiments.add(new ExperimentRequestNotification(n.getId(),n.getDate(),n.isRead(), ((ExperimentRequestNotificationImpl) n).getAcceptor().getDisplayedName(), ((ExperimentRequestNotificationImpl) n).getAcceptor().getUsername(), ((ExperimentRequestNotificationImpl) n).isAccepted(),((ExperimentRequestNotificationImpl) n).getExperiment().getId(), ((ExperimentRequestNotificationImpl) n).getExperiment().getName()));
			
			else
			if (n instanceof FriendshipRequestImpl)
				friends.add(new FriendshipRequestNotification(n.getId(), n.getDate(), n.isRead(), ((ExperimentRequestNotificationImpl) n).getAcceptor().getDisplayedName(), ((ExperimentRequestNotificationImpl) n).getAcceptor().getUsername(), ((ExperimentRequestNotificationImpl) n).isAccepted()));
		
			else
			if (n instanceof CollaborateRequestImpl)
				collaborations.add(new CollaborationRequestNotification(n.getId(), n.getDate(), n.isRead(), ((ExperimentRequestNotificationImpl) n).getAcceptor().getDisplayedName(), ((ExperimentRequestNotificationImpl) n).getAcceptor().getUsername(),((ExperimentRequestNotificationImpl) n).isAccepted(), ((CollaborateRequestImpl) n).getCollaborateable().getId(), ((CollaborateRequestImpl) n).getCollaborateable().getName()));
		}
		
		
		try {
			u.getNotificationChannel().send(new GetAllNotificationsAnswerMessage(referenceId, quicks, experiments, friends, collaborations));
		} catch (IOException e) {
			// TODO What to do if message cant be sent
			e.printStackTrace();
		}
		
	}
	
	
	
}
