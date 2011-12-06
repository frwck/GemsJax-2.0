package org.gemsjax.server.persistence.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.gemsjax.server.data.metamodel.MetaModelImpl;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.NotificationDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.notification.NotificationImpl;
import org.gemsjax.server.persistence.request.CollaborateRequestImpl;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.notification.Notification;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateNotificationDAO implements NotificationDAO{

	@Override
	public Notification createNotification(RegisteredUser receiver,
			int codeNumber, String optionalMessage) throws DAOException {
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				NotificationImpl n = new NotificationImpl();
				n.setCodeNumber(codeNumber);
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

}