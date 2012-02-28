package org.gemsjax.server.persistence.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.NotificationDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.notification.CollaborationRequestNotificationImpl;
import org.gemsjax.server.persistence.notification.ExperimentRequestNotificationImpl;
import org.gemsjax.server.persistence.notification.FriendshipRequestNotificationImpl;
import org.gemsjax.server.persistence.notification.NotificationImpl;
import org.gemsjax.server.persistence.notification.QuickNotificationImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.notification.Notification;
import org.gemsjax.shared.notification.QuickNotification;
import org.gemsjax.shared.notification.QuickNotification.QuickNotificationType;
import org.gemsjax.shared.user.RegisteredUser;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateNotificationDAO implements NotificationDAO{
	
	public HibernateNotificationDAO()
	{
		
	}

	@Override
	public QuickNotification createQuickNotification(RegisteredUser receiver,
			QuickNotificationType type, String optionalMessage) throws DAOException {
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				QuickNotificationImpl n = new QuickNotificationImpl();
				n.setQuickNotificationType(type);
				n.setDate(new Date());
				n.setOptionalMessage(optionalMessage);
				n.setRead(false);
				n.setReceiver(receiver);
				
				session.save(n);
			tx.commit();
			session.flush();
			session.close();
			
			return n;
		}catch (HibernateException ex )
		{
			ex.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session!=null)
				session.close();
			
			throw new DAOException(ex, "Could not create a new Notification");
		}
		
	}

	@Override
	public void deleteNotification(Notification n) throws DAOException {
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				session.delete(n);
			tx.commit();
			session.flush();
			session.close();
			
		}catch (HibernateException ex )
		{
			ex.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session!=null)
				session.close();
			
			throw new DAOException(ex, "Could not delete the  Notification "+n.getId());
		}
	}

	@Override
	public List<Notification> getNotificationsFor(RegisteredUser receiver) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery( "FROM NotificationImpl WHERE receiver = :rec ORDER BY date DESC");
		query.setEntity("rec", receiver);
	    List<Notification> result = (List<Notification>)query.list();
	    session.close();
	    return result;
	}

	@Override
	public List<Notification> getUnreadNotificationsFor(RegisteredUser receiver) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery( "FROM NotificationImpl WHERE receiver = :rec AND read = false ORDER BY date DESC");
		query.setEntity("rec", receiver);
	    List<Notification> result = (List<Notification>)query.list();
	    session.close();
	    return result;
	}

	@Override
	public void setRead(Notification notification, boolean read) throws DAOException {
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				notification.setRead(read);
				session.update(notification);
			tx.commit();
			session.flush();
			session.close();
			
		}catch (HibernateException ex )
		{
			ex.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session!=null)
				session.close();
			
			throw new DAOException(ex, "Could not create a new Notification");
		}
	}

	@Override
	public Notification getNotification(long id) throws NotFoundException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
			Notification n = (Notification) session.get(NotificationImpl.class, id);
		session.close();
		
		if (n == null)
			throw new NotFoundException();
		
		else
			return n;
	
	}

	@Override
	public ExperimentRequestNotificationImpl createExperimentRequestNotification(
			RegisteredUser receiver, RegisteredUser acceptor,
			Experiment experiment, boolean accepted) throws DAOException {
		
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				ExperimentRequestNotificationImpl n = new ExperimentRequestNotificationImpl();
				n.setDate(new Date());
				n.setExperiment(experiment);
				n.setAccepted(accepted);
				n.setAcceptor(acceptor);
				n.setRead(false);
				n.setReceiver(receiver);
				
				session.save(n);
			tx.commit();
			session.flush();
			session.close();
			
			return n;
		}catch (HibernateException ex )
		{
			ex.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session!=null)
				session.close();
			
			throw new DAOException(ex, "Could not create a new Notification");
		}
	}

	@Override
	public CollaborationRequestNotificationImpl createCollaborationRequestNotification(
			RegisteredUser receiver, RegisteredUser acceptor,
			Collaborateable collaborateable, boolean accepted)
			throws DAOException {
		
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				CollaborationRequestNotificationImpl n = new CollaborationRequestNotificationImpl();
				n.setDate(new Date());
				n.setCollaborateable(collaborateable);
				n.setAccepted(accepted);
				n.setAcceptor(acceptor);
				n.setRead(false);
				n.setReceiver(receiver);
				
				session.save(n);
			tx.commit();
			session.flush();
			session.close();
			
			return n;
		}catch (HibernateException ex )
		{
			ex.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session!=null)
				session.close();
			
			throw new DAOException(ex, "Could not create a new Notification");
		}
		
	}

	@Override
	public FriendshipRequestNotificationImpl createFriendshipRequestNotification(
			RegisteredUser receiver, RegisteredUser acceptor, boolean accepted) throws DAOException {
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				FriendshipRequestNotificationImpl n = new FriendshipRequestNotificationImpl();
				n.setDate(new Date());
				n.setAccepted(accepted);
				n.setAcceptor(acceptor);
				n.setRead(false);
				n.setReceiver(receiver);
				
				session.save(n);
			tx.commit();
			session.flush();
			session.close();
			
			return n;
		}catch (HibernateException ex )
		{
			ex.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session!=null)
				session.close();
			
			throw new DAOException(ex, "Could not create a new Notification");
		}
	}

	@Override
	public long getUnreadCount(RegisteredUser user) {
			
		Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery( "SELECT count(*) FROM NotificationImpl WHERE receiver = :receiverUser AND read=false");
			query.setEntity("receiverUser", user);
			
			return (Long) query.list().get(0);
		
	}

}
