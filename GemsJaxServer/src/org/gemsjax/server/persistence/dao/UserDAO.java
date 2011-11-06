package org.gemsjax.server.persistence.dao;

import java.util.List;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.server.persistence.user.UserImpl;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

public class UserDAO {
	
	private Session session ;
	
	public UserDAO()
	{
		session = HibernateUtil.getOpenedSession();
	}
	
	/**
	 * Creates and stores a new {@link RegisteredUser}
	 * @param username
	 * @param passwordHash
	 * @param email
	 * @return
	 * @throws UsernameInUseException 
	 */
	public RegisteredUser createRegisteredUser(String username, String passwordHash, String email) throws UsernameInUseException
	{
		
		
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
			RegisteredUserImpl user = new RegisteredUserImpl();
			user.setUsername(username);
			user.setPasswordHash(passwordHash);
			user.setDisplayedName(username);
			user.setEmail(email);
			
			session.save(user);
	
			tx.commit();
			
			return user;
		}
		catch (ConstraintViolationException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw new UsernameInUseException(username, e.getMessage());
		}
		catch (RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
	}
	
	
	/**
	 * Get a {@link RegisteredUser} by his username and password hash. This is used for the login
	 * @param username
	 * @param passwordHash
	 * @return
	 * @throws MoreThanOneExcpetion
	 */
	public RegisteredUser getUserByLogin(String username, String passwordHash) throws MoreThanOneExcpetion
	{
		Query query = session.createQuery( "FROM RegisteredUserImpl WHERE username='"+username+"' AND password='"+passwordHash+"'" );
	      
	    List<RegisteredUserImpl> result = query.list();
	    
	    if (result.size()>0)
	    	if (result.size()>1)
	    			throw new MoreThanOneExcpetion();
	    		else
	    			return result.get(0);
	    else
	    	return null;
	}
	
	
	/**
	 * Delete an {@link User}
	 * @param u
	 */
	public void deleteRegisteredUser(RegisteredUser u )
	{
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
	
				session.delete(u);
	
			tx.commit();
		}
		catch (RuntimeException e)
		{
			if (tx!=null)
				tx.rollback();
			
			throw e;
		}
	}
	
	
	/**
	 * Get a {@link User} by his unique id
	 * @param id
	 * @return
	 * @throws MoreThanOneExcpetion
	 */
	public RegisteredUser getRegisteredUserById(int id) throws MoreThanOneExcpetion
	{
		Query query = session.createQuery( "FROM RegisteredUserImpl WHERE id = "+id );
	      
	    List<RegisteredUserImpl> result = query.list();
	    
	    if (result.size()>0)
	    	if (result.size()>1)
	    			throw new MoreThanOneExcpetion();
	    		else
	    			return result.get(0);
	    else
	    	return null;
	}
	
	
	
	/**
	 * 
	 * @param u
	 * @param displayedName
	 * @throws ArgumentException
	 */
	public void updateDisplayedName(UserImpl u, String displayedName) throws ArgumentException
	{
		if (FieldVerifier.isEmpty(displayedName))
			throw new ArgumentException("Displayed name is empty");
		
		Transaction tx = null;
		
		try 
		{
		
			if (!u.getDisplayedName().equals(displayedName))
			{
				tx = session.beginTransaction();
					u.setDisplayedName(displayedName);
				tx.commit();
			}
			
		} catch(RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
		
	}
	

}
