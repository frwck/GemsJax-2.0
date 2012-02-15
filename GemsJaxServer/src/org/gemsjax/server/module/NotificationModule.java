package org.gemsjax.server.module;

import org.gemsjax.server.persistence.dao.NotificationDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateNotificationDAO;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;

public class NotificationModule {

	
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
	
}
