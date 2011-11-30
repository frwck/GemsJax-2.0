package org.gemsjax.server.persistence.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.RequestDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.experiment.ExperimentImpl;
import org.gemsjax.server.persistence.request.CollaborateRequestImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateRequestDAO implements RequestDAO{
	
	public HibernateRequestDAO()
	{
		
	}

	@Override
	public CollaborateRequest createCollaborateRequest(RegisteredUser sender,
			RegisteredUser receiver, Collaborateable c) throws DAOException {
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx= session.beginTransaction();
				session.buildLockRequest(LockOptions.NONE).lock(sender);
				session.buildLockRequest(LockOptions.NONE).lock(receiver);
				session.buildLockRequest(LockOptions.NONE).lock(c);
			
				CollaborateRequest r = new CollaborateRequestImpl();
				r.setCollaborateable(c);
				r.setDate(new Date());
				r.setReceiver(receiver);
				r.setSender(sender);
				
				session.save(r);
			tx.commit();
			session.flush();
			session.close();
			
			return r;
		}catch (HibernateException ex )
		{
			ex.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session!=null)
				session.close();
			
			throw new DAOException(ex, "Could not create a new Request");
		}
	}

	@Override
	public void deleteRequest(Request request) throws DAOException {
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx= session.beginTransaction();
				session.buildLockRequest(LockOptions.NONE).lock(request);
				session.delete(request);
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
			
			throw new DAOException(ex, "Could not delete the Request");
		}
		
	}

	@Override
	public List<Request> getAllRequestsBy(RegisteredUser senderUser) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery( "FROM RequestImpl WHERE sender = :senderUser");
		query.setEntity("senderUser", senderUser);
	    List<Request> result = query.list();
	    session.close();
	    return result;
	}

	@Override
	public List<Request> getAllRequestsFor(RegisteredUser receiverUser) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery( "FROM RequestImpl WHERE receiver = :receiverUser");
		query.setEntity("receiverUser", receiverUser);
	    List<Request> result = query.list();
	    session.close();
	    return result;
	}

}
