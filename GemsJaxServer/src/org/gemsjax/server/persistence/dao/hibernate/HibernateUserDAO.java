package org.gemsjax.server.persistence.dao.hibernate;

import java.util.List;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.collaboration.CollaborateableImpl;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.experiment.ExperimentImpl;
import org.gemsjax.server.persistence.notification.NotificationImpl;
import org.gemsjax.server.persistence.request.RequestImpl;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.server.persistence.user.UserImpl;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.cache.commands.read.GetNodeCommand;

public class HibernateUserDAO implements UserDAO {
	
	
	public HibernateUserDAO()
	{
	}
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.UserDAO#createRegisteredUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	public RegisteredUser createRegisteredUser(String username, String passwordHash, String email) throws UsernameInUseException, DAOException, EMailInUseExcpetion
	{
		
		Session session = null;
		Transaction tx = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			RegisteredUserImpl user = new RegisteredUserImpl();
			user.setUsername(username);
			user.setPasswordHash(passwordHash);
			user.setDisplayedName(username);
			user.setEmail(email);
			
			session.save(user);
	
			tx.commit();
			session.flush();
			session.close();
			
			return user;
		}
		catch (ConstraintViolationException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			if (e.getSQLException().getMessage().endsWith("for key 'email'"))
				throw new EMailInUseExcpetion();
			
			if (e.getSQLException().getMessage().endsWith("for key 'username'"))
				throw new UsernameInUseException(username, e.getMessage());
			
			
			throw e;
		}
		catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not create a new RegisteredUser");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.UserDAO#getUserByLogin(java.lang.String, java.lang.String)
	 */
	public RegisteredUser getUserByLogin(String username, String passwordHash) throws MoreThanOneExcpetion, NotFoundException
	{
		Session session = null;
		try 
		{
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery( "FROM RegisteredUserImpl WHERE username='"+username+"' AND password='"+passwordHash+"'" );
		      
		    List<RegisteredUserImpl> result = query.list();
		    
		    if (result.size()>0)
		    	if (result.size()>1){
		    		session.close();	
		    		throw new MoreThanOneExcpetion();
		    	}
		    	else
		    	{
		    		session.close();
		    		return result.get(0);
		    	}
		    else
		    {
		    	session.close();
		    	throw new NotFoundException();
		    }
	    	
		}// End Try
		catch(HibernateException e)
		{
			if (session!=null)
				session.close();
			
			
			throw e;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.UserDAO#deleteRegisteredUser(org.gemsjax.shared.user.RegisteredUser)
	 */
	public void deleteRegisteredUser(RegisteredUser u ) throws DAOException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			//u = (RegisteredUser) session.merge(u);
//			session.buildLockRequest(LockOptions.NONE).lock(u);
			
			session.saveOrUpdate(u);
			
			
			
			/*
			String hql = "from "+CollaborateableImpl.class.getName()+" C where :user in elements(C.users)";
		      Query query = session.createQuery( hql );
		      query.setEntity("user", u);
		      List<CollaborateableImpl> list = query.list();
		      
		     
		      */
			
			
				// DELETE Requests
				String delHql = "DELETE from "+RequestImpl.class.getName()+" C where sender = :user OR receiver = :user";
				Query query = session.createQuery( delHql );
				query.setEntity("user", u);
				query.executeUpdate();
				
			
				// DELETE Notifications
				delHql = "DELETE from "+NotificationImpl.class.getName()+" C where receiver = :user";
				query = session.createQuery( delHql );
				query.setEntity("user", u);
				query.executeUpdate();
				
				
		    	for (Collaborateable c: u.getCollaborateables())
				{
		    		//System.out.println("DELETE "+c+ " "+c.getName());
					c.getUsers().remove(u);
					//session.update(c);
				}
				
				u.getCollaborateables().clear();
				session.update(u);
				// End deleting Collaboarateables
				
				
				// Begin deleting Experiment Administrator connection
				/*hql = "from "+ExperimentImpl.class.getName()+" E where :user in elements(E.administrators)";
			    query = session.createQuery( hql );
			    query.setEntity("user", u);
			    List<ExperimentImpl> experimentList = query.list();
			      */
			    	for (Experiment e: u.getOwnedExperiments())
					{
			    		System.out.println("Delete "+u);
						e.getAdministrators().remove(u);
						session.update(e);
					}
					
				u.getAdministratedExperiments().clear();
				session.update(u);
				// End deleting Experiment Administrator connection
				
				
				
				session.delete(u);
	
			tx.commit();
			session.flush();
			session.close();
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (tx!=null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not delete the RegisteredUser");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.UserDAO#getRegisteredUserById(int)
	 */
	public RegisteredUser getRegisteredUserById(int id) throws NotFoundException
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
			RegisteredUser u = (RegisteredUserImpl)session.get(RegisteredUserImpl.class, id);
		session.close();
		
		if (u== null)
			throw new NotFoundException();
		
		return u;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.UserDAO#updateDisplayedName(org.gemsjax.shared.user.User, java.lang.String)
	 */
	public void updateDisplayedName(User u, String displayedName) throws ArgumentException, DAOException
	{
		if (FieldVerifier.isEmpty(displayedName))
			throw new ArgumentException("Displayed name is empty");
		
		Session session = null;
		Transaction tx = null;
		
		try 
		{
		
			if (!u.getDisplayedName().equals(displayedName))
			{
				session = HibernateUtil.getSessionFactory().openSession();
				tx = session.beginTransaction();

					session.update(u);
					
					u.setDisplayedName(displayedName);
				tx.commit();
				session.flush();
				session.close();
			}
			
		} catch(HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			throw new DAOException(e, "Could not update the display name");
		}
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.UserDAO#updateRegisteredUserPassword(org.gemsjax.shared.user.RegisteredUser, java.lang.String)
	 */
	public void updateRegisteredUserPassword(RegisteredUser u, String newPasswordHash) throws DAOException
	{
		Session session = null;
		Transaction tx = null;
		
		try 
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				((RegisteredUserImpl) u).setPasswordHash(newPasswordHash);
				session.update(u);
			tx.commit();
			session.flush();
			session.close();
			
		} catch(HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			throw new DAOException(e, "Could not update the password");
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.UserDAO#updateRegisteredUserEmail(org.gemsjax.shared.user.RegisteredUser, java.lang.String)
	 */
	public void updateRegisteredUserEmail(RegisteredUser u, String newEmail) throws DAOException, EMailInUseExcpetion
	{
		Session session = null;
		Transaction tx = null;
		try 
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				u.setEmail(newEmail);
				session.update(u);
			tx.commit();
			session.flush();
			session.close();
			
			
		}
		catch (ConstraintViolationException e)
		{
			if (tx != null)
				tx.rollback();
				
			if (session != null)
				session.close();
				
			if (e.getSQLException().getMessage().endsWith("for key 'email'"))
				throw new EMailInUseExcpetion();
			
			throw e;
				
		} catch(HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			throw new DAOException(e, "Could not update the email");
		}
	}
	
}