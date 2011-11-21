package org.gemsjax.server.persistence.dao;

import java.util.List;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.collaboration.CollaborateableImpl;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.server.persistence.user.UserImpl;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

public class UserDAO {
	
	
	public UserDAO()
	{
	}
	
	/**
	 * Creates and stores a new {@link RegisteredUser}
	 * @param username
	 * @param passwordHash
	 * @param email
	 * @return
	 * @throws UsernameInUseException 
	 * @throws DAOException 
	 * @throws EMailInUseExcpetion 
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
			
			if (e.getMessage().startsWith("Duplicate entry 'email'"))
			throw new EMailInUseExcpetion();
			
			if (e.getMessage().startsWith("Duplicate entry 'username'"))
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
	
	
	/**
	 * Get a {@link RegisteredUser} by his username and password hash. This is used for the login
	 * @param username
	 * @param passwordHash
	 * @return
	 * @throws MoreThanOneExcpetion
	 * @throws NotFoundException 
	 */
	public RegisteredUser getUserByLogin(String username, String passwordHash) throws MoreThanOneExcpetion, NotFoundException
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
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
	    	
	}
	
	
	/**
	 * Delete an {@link User}
	 * @param u
	 * @throws DAOException 
	 */
	public void deleteRegisteredUser(RegisteredUser u ) throws DAOException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			// BEGIN deleting Collaborateables
			 String hql = "from "+CollaborateableImpl.class.getName()+" C where :user in elements(C.users)";
		      Query query = session.createQuery( hql );
		      query.setEntity("user", u);
		      List<CollaborateableImpl> list = query.list();
		      
		    	for (CollaborateableImpl c: list)
				{
					c.getUsers().remove(u);
					session.update(c);
				}
				
				u.getCollaborateables().clear();
				session.update(u);
			// End deleting Collaboarateables
				
				
				session.delete(u);
	
			tx.commit();
			session.flush();
			session.close();
		}
		catch (HibernateException e)
		{
			if (tx!=null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not delete the RegisteredUser");
		}
	}
	
	
	/**
	 * Get a {@link User} by his unique id
	 * @param id
	 * @return
	 * @throws MoreThanOneExcpetion
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
	
	
	
	/**
	 * 
	 * @param u
	 * @param displayedName
	 * @throws ArgumentException
	 * @throws DAOException 
	 */
	public void updateDisplayedName(UserImpl u, String displayedName) throws ArgumentException, DAOException
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
	
	
}
