package org.gemsjax.server.persistence.dao;

import java.util.List;
import java.util.Set;

import org.gemsjax.server.data.metamodel.MetaModelImpl;
import org.gemsjax.server.data.model.ModelImpl;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * This Data Access Object (DAO) is used to access {@link Collaborateable}s like {@link MetaModel} and {@link Model}
 * @author Hannes Dorfmann
 *
 */
public class CollaborateableDAO {
	
	
	public CollaborateableDAO()
	{
	}
	
	
	
	/**
	 * Delete an {@link Collaborateable} like {@link MetaModel} and {@link Model}
	 * @param u
	 * @throws DAOException 
	 */
	public void deleteCollaborateable(Collaborateable c ) throws DAOException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				session.delete(c);
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
			
			throw new DAOException(e, "Could not delete the Collaborateable");
		}
	}
	
	
	/**
	 * Create a new {@link MetaModel} and store this into the database
	 * @param name
	 * @param owner The owner of this {@link MetaModel}
	 * @return
	 * @throws DAOException 
	 */
	public MetaModel createMetaModel(String name, RegisteredUser owner) throws DAOException
	{
		Transaction tx = null;
		Session session = null;
		
		try
		{	

			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			MetaModelImpl metaModel = new MetaModelImpl();
				metaModel.setName(name);
				metaModel.setOwner(owner);
				metaModel.getUsers().add(owner);
				metaModel.setForExperiment(false);
				metaModel.setPublicPermission(Collaborateable.NO_PERMISSION);
				session.save(metaModel);
				
				owner.getOwnedCollaborateables().add(metaModel);
				owner.getCollaborateables().add(metaModel);
				session.update(owner);
			
			tx.commit();
			session.flush();
			session.close();
			
			return metaModel;
		}
		catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not create a new MetaModel");
		}
		
	}
	
	/**
	 * Set the public permission of an {@link Collaborateable}
	 * @param col
	 * @param permission
	 * @throws DAOException 
	 */
	public void updatePublicPermission(Collaborateable col, int permission ) throws DAOException
	{
		Session session = null;
		Transaction tx = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				col.setPublicPermission(Collaborateable.NO_PERMISSION);
			session.update(col);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not update the public permission");
		}
	}
	
	
	/**
	 * Set the name of the {@link Collaborateable}.
	 * If the new name is the same (equals) as the old, than nothing happens
	 * @param c
	 * @param newName
	 * @throws DAOException 
	 */
	public void updateCollaborateableName(Collaborateable c, String newName) throws DAOException
	{
		// If the name is the same, then do nothing
		if (newName.equals(c.getName()))
			return;
		
		Session session = null;
		Transaction tx = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				c.setName(newName);
			session.update(c);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not update the name of the Collaborateable");
		}
	}
	
	
	/**
	 * Set the keywords (for search) for the MetaModel.
	 * If the new keywords-string is the same (equals) as the old, than nothing happens
	 * @param c
	 * @param newKeywords
	 * @throws DAOException 
	 */
	public void updateCollaborateableKeywords(Collaborateable c, String newKeywords) throws DAOException
	{
		// If the name is the same, then do nothing
		if (newKeywords.equals(c.getKeywords()))
			return;
		
		Session session = null;
		Transaction tx = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				c.setKeywords(newKeywords);
			session.update(c);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not update the keywords of the collaborateable");
		}
	}
	
	
	/**
	 * Get a {@link MetaModel} by his unique id
	 * @param id
	 * @return
	 * @throws NotFoundException 
	 */
	public MetaModel getMetaModelById(int id) throws NotFoundException 
	{ 

		Session session = HibernateUtil.getSessionFactory().openSession();
		MetaModel m = (MetaModelImpl)session.get(MetaModelImpl.class, id);
		
		session.close();
		if (m == null)
			throw new NotFoundException();
		
		else
			return m;
		
	}
	
	
	
	/**
	 * Get a {@link Model} by his unique id
	 * @param id
	 * @return
	 * @throws NotFoundException 
	 * 
	 */
	public Model getModelById(int id) throws NotFoundException 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
			Model m = (ModelImpl) session.get(ModelImpl.class, id);
		session.close();

		if (m == null)
			throw new NotFoundException();
		
		return m;
	}
	
	
	
	/**
	 * Add a {@link User} to the {@link Collaborateable} to work collaborative together
	 * @param c
	 * @param u
	 * @throws DAOException 
	 */
	public void addCollaborativeUser(Collaborateable c, User u ) throws DAOException
	{
		Transaction tx = null;
		Session session = null;
		
		try
		{	
			
			session = HibernateUtil.getSessionFactory().openSession();
		
			tx = session.beginTransaction();
				c.getUsers().add(u);
				u.getCollaborateables().add(c);
				
				session.update(u);
				session.update(c);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			if (tx != null)
				tx.rollback();
		

			if (session!= null)
				session.close();
			
			throw new DAOException(e,"Could not add User to this Collaborateable");
		}
	}
	
	
	
	/**
	 * Add a Set of {@link User}s to the {@link Collaborateable} to work collaborative together
	 * @param c
	 * @param u
	 * @throws DAOException 
	 */
	public void addCollaborativeUsers(Collaborateable c, Set<User> u ) throws DAOException
	{
		Session session = null;
		Transaction tx = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			
			tx = session.beginTransaction();
				c.getUsers().addAll(u);
				for (User user: u)
					user.getCollaborateables().add(c);
				
				session.update(u);
				session.update(c);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			
			if (tx != null)
				tx.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not add a collection of Users to this Collaborateable");
		}
	}
	
	
	
	/**
	 * Create a {@link Model}
	 * @param name
	 * @param owner
	 * @return
	 * @throws DAOException 
	 */
	public Model createModel(String name, MetaModel metaModel, RegisteredUser owner) throws DAOException
	{
		
		Transaction tx = null;
		Session session = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			ModelImpl model = new ModelImpl();
				model.setName(name);
				model.setOwner(owner);
				model.setMetaModel(metaModel);
				model.getUsers().add(owner);
				model.setForExperiment(false);
				model.setPublicPermission(Collaborateable.NO_PERMISSION);
				session.save(model);
				
				owner.getCollaborateables().add(model);
				owner.getOwnedCollaborateables().add(model);
				session.update(owner);
			
			tx.commit();
			session.flush();
			session.close();
			
			return model;
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not create a new Model");
		}
	}
	
	
}