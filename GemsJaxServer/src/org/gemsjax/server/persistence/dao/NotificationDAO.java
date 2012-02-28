package org.gemsjax.server.persistence.dao;

import java.util.List;

import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.notification.CollaborationRequestNotificationImpl;
import org.gemsjax.server.persistence.notification.ExperimentRequestNotificationImpl;
import org.gemsjax.server.persistence.notification.FriendshipRequestNotificationImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.notification.Notification;
import org.gemsjax.shared.notification.QuickNotification;
import org.gemsjax.shared.notification.QuickNotification.QuickNotificationType;
import org.gemsjax.shared.user.RegisteredUser;


/**
 * The DAO for access, create and delete {@link Notification}s
 * @author Hannes Dorfmann
 *
 */
public interface NotificationDAO {
	
	
	
	
	public abstract long getUnreadCount(RegisteredUser user);
	
	
	/**
	 * Set a {@link Notification} as read / unread
	 * @param notification
	 * @param read
	 * @throws DAOException
	 */
	public abstract void setRead(Notification notification, boolean read) throws DAOException;
	
	/**
	 * Get a list with unread {@link Notification}s for the passed {@link RegisteredUser}
	 * @param receiver
	 * @return
	 */
	public abstract List<Notification> getUnreadNotificationsFor(RegisteredUser receiver);
	
	
	/**
	 * Get a list with all notification (containing read and unread) {@link Notification}s for the passed {@link RegisteredUser}
	 * @param receiver
	 * @return 
	 */
	public abstract List<Notification> getNotificationsFor(RegisteredUser receiver);
	
	/**
	 * Delete a {@link Notification} permanently from the database
	 * @param n
	 * @throws DAOException
	 */
	public void deleteNotification(Notification n) throws DAOException;
	
	
	/**
	 * Get a {@link Notification} by his unique id
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	public Notification getNotification(long id) throws  NotFoundException;


	/**
	 * Create a new {@link Notification} with the passed arguments.<br />
	 * NOTE: read is set to false (--> unread) and the current timestamp of the servers time is used as the date of creation
	 * @param receiver
	 * @param codeNumber
	 * @param optionalMessage
	 * @return
	 * @throws DAOException
	 */
	public QuickNotification createQuickNotification(RegisteredUser receiver,
			QuickNotificationType type, String optionalMessage) throws DAOException;
	
	
	public ExperimentRequestNotificationImpl createExperimentRequestNotification(RegisteredUser receiver,
			RegisteredUser acceptor, Experiment experiment, boolean accepted) throws DAOException;
	
	
	public CollaborationRequestNotificationImpl createCollaborationRequestNotification(RegisteredUser receiver,
			RegisteredUser acceptor, Collaborateable collaborateable, boolean accepted) throws DAOException;
	
	
	public FriendshipRequestNotificationImpl createFriendshipRequestNotification(RegisteredUser receiver, RegisteredUser acceptor, boolean accepted) throws DAOException;
	
}
