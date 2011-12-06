package org.gemsjax.server.persistence.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.RequestDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.request.AdministrateExperimentRequestImpl;
import org.gemsjax.server.persistence.request.CollaborateRequestImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.request.AdministrateExperimentRequest;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Session.LockRequest;

public class HibernateRequestDAO implements RequestDAO{
	
	public HibernateRequestDAO()
	{
		
	}

	@Override
	public CollaborateRequest createCollaborateRequest(RegisteredUser sender,
			RegisteredUser receiver, Collaborateable c) throws DAOException, AlreadyAssignedException, AlreadyExistException {
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				//session.buildLockRequest(LockOptions.NONE).setLockMode(LockMode.NONE).lock(sender);
				//session.buildLockRequest(LockOptions.NONE).setLockMode(LockMode.NONE).lock(receiver);
				//session.buildLockRequest(LockOptions.NONE).setLockMode(LockMode.NONE).lock(c);
				
				
				
				// Check if already assigned
				if (c.getUsers().contains(receiver))
					throw new AlreadyAssignedException("The receiver (User: "+receiver.getUsername()+") is already working on this Collaborateable");
				
				
				

				//Query query = session.createQuery( "SELECT id FROM Request INNER JOIN CollaborateRequest ON (Request.id = CollaborateRequest.Request_id) WHERE User_id_sender = :sid AND User_id_receiver = :rid AND Collaborateable_id = :cid ");
				
				Query query = session.createQuery( "FROM CollaborateRequestImpl WHERE receiver = :receiverUser AND sender = :senderUser AND collaborateable = :col");
				query.setEntity("receiverUser", receiver);
				query.setEntity("senderUser", sender);
				query.setEntity("col",c);
				
				int size = query.list().size() ;
				
				System.out.println("Size s" +size);
				
				if (size != 0 )
					throw new AlreadyExistException();
			    
			
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
				session.buildLockRequest(LockOptions.NONE).setLockMode(LockMode.NONE).lock(request);
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

	@Override
	public AdministrateExperimentRequest createAdministrateExperimentRequest(
			RegisteredUser sender, RegisteredUser receiver,
			Experiment experiment) throws DAOException, AlreadyExistException, AlreadyAssignedException {

		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx= session.beginTransaction();
				//session.buildLockRequest(LockOptions.NONE).lock(sender);
				//session.buildLockRequest(LockOptions.NONE).lock(receiver);
				//session.buildLockRequest(LockOptions.NONE).lock(experiment);
				
				
				// Check if already assigned
				if (experiment.getAdministrators().contains(receiver))
					throw new AlreadyAssignedException("The receiver (User: "+receiver.getUsername()+") is already working on this Collaborateable");
				
				
				Query query = session.createQuery( "FROM AdministrateExperimentRequestImpl WHERE receiver = :receiverUser AND sender = :senderUser AND experiment = :ex");
				query.setEntity("receiverUser", receiver);
				query.setEntity("senderUser", sender);
				query.setEntity("ex", experiment);
				
				if (query.list().size() > 0 )
					throw new AlreadyExistException();
				
			
				AdministrateExperimentRequest r = new AdministrateExperimentRequestImpl();
				r.setExperiment(experiment);
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

}