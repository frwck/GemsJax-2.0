package org.gemsjax.server.persistence.dao;

import java.util.List;

import org.gemsjax.server.data.metamodel.MetaModelImpl;
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
	 * Set the name of the {@link MetaModel}.
	 * If the new name is the same (equals) as the old, than nothing happens
	 * @param metaModel
	 * @param newName
	 */
	public void updateMetaModelName(MetaModel metaModel, String newName)
	{
		// If the name is the same, then do nothing
		if (newName.equals(metaModel.getName()))
			return;
		
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
				metaModel.setName(newName);
			session.save(metaModel);
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
	 * @param metaModel
	 * @param newKeywords
	 */
	public void updateMetaModelKeywords(MetaModel metaModel, String newKeywords)
	{
		// If the name is the same, then do nothing
		if (newKeywords.equals(metaModel.getKeywords()))
			return;
		
		Transaction tx = null;
		
		try
		{	
			tx = session.beginTransaction();
				metaModel.setKeywords(newKeywords);
			session.save(metaModel);
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
	 * Get a {@link User} by his unique id
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
	
	

	
}
