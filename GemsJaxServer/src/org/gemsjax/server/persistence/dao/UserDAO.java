package org.gemsjax.server.persistence.dao;

import java.util.List;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.server.persistence.user.UserImpl;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.user.RegisteredUser;
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
		try
		{	
			RegisteredUserImpl user = new RegisteredUserImpl();
			user.setUsername(username);
			user.setPasswordHash(passwordHash);
			user.setDisplayedName(username);
			user.setEmail(email);
			
			Transaction tx = session.beginTransaction();
	
			session.save(user);
	
			tx.commit();
			
			return user;
		}
		catch (ConstraintViolationException e)
		{
			throw new UsernameInUseException(username, e.getMessage());
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
	
	
	public void deleteRegisteredUser(RegisteredUser u )
	{
		Transaction tx = session.beginTransaction();

		session.delete(u);

		tx.commit();
	}
	
	
	
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
	
	
	
	public void updateDisplayedName(UserImpl u, String displayedName) throws IllegalArgumentException
	{
		if (FieldVerifier.isEmpty(displayedName))
			throw new IllegalArgumentException("Displayed name is empty");
		
		if (!u.getDisplayedName().equals(displayedName))
		{
			Transaction tx = session.beginTransaction();
				u.setDisplayedName(displayedName);
			tx.commit();
		}
	}
	

}
