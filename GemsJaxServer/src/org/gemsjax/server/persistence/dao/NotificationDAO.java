package org.gemsjax.server.persistence.dao;

import java.util.List;

import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.shared.notification.Notification;
import org.gemsjax.shared.user.RegisteredUser;


/**
 * The DAO for access, create and delete {@link Notification}s
 * @author Hannes Dorfmann
 *
 */
public interface NotificationDAO {
	
	/**
	 * Create a new {@link Notification} with the passed arguments.<br />
	 * NOTE: read is set to false (--> unread) and the current timestamp of the servers time is used as the date of creation
	 * @param receiver
	 * @param codeNumber
	 * @param optionalMessage
	 * @return
	 * @throws DAOException
	 */
	public abstract Notification createNotification(RegisteredUser receiver, int codeNumber, String optionalMessage ) throws DAOException;

	
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
	
	
}
