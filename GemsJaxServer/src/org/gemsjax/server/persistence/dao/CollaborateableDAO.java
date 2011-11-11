package org.gemsjax.server.persistence.dao;

import java.util.List;
import java.util.Set;

import org.gemsjax.server.data.metamodel.MetaModelImpl;
import org.gemsjax.server.data.model.ModelImpl;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * This Data Access Object (DAO) is used to access {@link Collaborateable}s like {@link MetaModel} and {@link Model}
 * @author Hannes Dorfmann
 *
 */
public class CollaborateableDAO {
	
	private Session session ;
	
	public CollaborateableDAO()
	{
		session = HibernateUtil.getOpenedSession();
	}
	
	
	
	/**
	 * Delete an {@link Collaborateable} like {@link MetaModel} and {@link Model}
	 * @param u
	 */
	public void deleteCollaborateable(Collaborateable c )
	{
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
	
				session.delete(c);
	
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
	 * Create a new {@link MetaModel} and store this into the database
	 * @param name
	 * @param owner The owner of this {@link MetaModel}
	 * @return
	 */
	public MetaModel createMetaModel(String name, RegisteredUser owner)
	{
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
			MetaModelImpl metaModel = new MetaModelImpl();
				metaModel.setName(name);
				metaModel.setOwner(owner);
				metaModel.getUsers().add(owner);
				metaModel.setForExperiment(false);
				metaModel.setPublicPermission(Collaborateable.NO_PERMISSION);
			session.save(metaModel);
			tx.commit();
			
			return metaModel;
		}
		catch (RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
		
	}
	
	/**
	 * Set the public permission of an {@link Collaborateable}
	 * @param col
	 * @param permission
	 */
	public void updatePublicPermission(Collaborateable col, int permission )
	{
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
				col.setPublicPermission(Collaborateable.NO_PERMISSION);
			session.update(col);
			tx.commit();
			
		}
		catch (RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
	}
	
	
	/**
	 * Set the name of the {@link Collaborateable}.
	 * If the new name is the same (equals) as the old, than nothing happens
	 * @param c
	 * @param newName
	 */
	public void updateCollaborateableName(Collaborateable c, String newName)
	{
		// If the name is the same, then do nothing
		if (newName.equals(c.getName()))
			return;
		
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
				c.setName(newName);
			session.update(c);
			tx.commit();
			
		}
		catch (RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
	}
	
	
	/**
	 * Set the keywords (for search) for the MetaModel.
	 * If the new keywords-string is the same (equals) as the old, than nothing happens
	 * @param c
	 * @param newKeywords
	 */
	public void updateCollaborateableKeywords(Collaborateable c, String newKeywords)
	{
		// If the name is the same, then do nothing
		if (newKeywords.equals(c.getKeywords()))
			return;
		
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
				c.setKeywords(newKeywords);
			session.update(c);
			tx.commit();
			
		}
		catch (RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
	}
	
	
	/**
	 * Get a {@link MetaModel} by his unique id
	 * @param id
	 * @return
	 * @throws MoreThanOneExcpetion
	 */
	public MetaModel getMetaModelById(int id) throws MoreThanOneExcpetion
	{ 
		Query query = session.createQuery( "FROM MetaModelImpl WHERE id = "+id );
	      
	    List<MetaModel> result = query.list();
	    
	    if (result.size()>0)
	    	if (result.size()>1)
	    			throw new MoreThanOneExcpetion();
	    		else
	    			return result.get(0);
	    else
	    	return null;
	}
	
	
	
	/**
	 * Get a {@link Model} by his unique id
	 * @param id
	 * @return
	 * @throws MoreThanOneExcpetion
	 */
	public Model getModelById(int id) throws MoreThanOneExcpetion
	{
		Query query = session.createQuery( "FROM ModelImpl WHERE id = "+id );
	      
	    List<Model> result = query.list();
	    
	    if (result.size()>0)
	    	if (result.size()>1)
	    			throw new MoreThanOneExcpetion();
	    		else
	    			return result.get(0);
	    else
	    	return null;
	}
	
	
	/**
	 * Add a {@link User} to the {@link Collaborateable} to work collaborative together
	 * @param c
	 * @param u
	 */
	public void addCollaborativeUser(Collaborateable c, User u )
	{
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
				c.getUsers().add(u);
			session.update(u);
			tx.commit();
			
		}
		catch (RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
	}
	
	
	
	/**
	 * Add a Set of {@link User}s to the {@link Collaborateable} to work collaborative together
	 * @param c
	 * @param u
	 */
	public void addCollaborativeUser(Collaborateable c, Set<User> u )
	{
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
				c.getUsers().addAll(u);
			session.update(u);
			tx.commit();
			
		}
		catch (RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
	}
	
	
	
	/**
	 * Create a {@link Model}
	 * @param name
	 * @param owner
	 * @return
	 */
	public Model createModel(String name, RegisteredUser owner)
	{
		
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
			ModelImpl model = new ModelImpl();
				model.setName(name);
				model.setOwner(owner);
				model.getUsers().add(owner);
				model.setForExperiment(false);
				model.setPublicPermission(Collaborateable.NO_PERMISSION);
			session.save(model);
			tx.commit();
			
			return model;
		}
		catch (RuntimeException e)
		{
			if (tx != null)
				tx.rollback();
			
			throw e;
		}
	}
	
	
}
