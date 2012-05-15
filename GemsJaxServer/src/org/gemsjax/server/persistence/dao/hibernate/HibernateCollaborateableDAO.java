package org.gemsjax.server.persistence.dao.hibernate;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gemsjax.server.data.metamodel.MetaModelImpl;
import org.gemsjax.server.data.model.ModelImpl;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.collaboration.CollaborateableImpl;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.request.CollaborateRequestImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableType;
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
public class HibernateCollaborateableDAO implements CollaborateableDAO {
	
	
	public HibernateCollaborateableDAO()
	{
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#deleteCollaborateable(org.gemsjax.shared.collaboration.Collaborateable)
	 */
	public void deleteCollaborateable(Collaborateable c ) throws DAOException
	{
		
		Session session = null;
		Transaction tx = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				Collaborateable persistent = null;
				persistent = (Collaborateable) session.load(CollaborateableImpl.class, c.getId());
				//persistent = (Collaborateable) session.merge(c);
				
				// DELETE Requests
				String delHql = "DELETE from "+CollaborateRequestImpl.class.getName()+" C where collaborateable = :col";
				Query query = session.createQuery( delHql );
				query.setEntity("col", persistent);
				int del = query.executeUpdate();
				
				//persistent.getOwner().getOwnedCollaborateables().remove(persistent);
				//session.update(persistent.getOwner());
				session.delete(persistent); // TODO write Info message to other user, that this collaborateable was deleted
			tx.commit();
			session.flush();
			session.close();
			
			c.getOwner().getOwnedCollaborateables().remove(c);
			c.getUsers().clear();
			
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
	
	
	
	public void deleteCollaborateable(Model c ) throws DAOException
	{
		
		
		Session session = null;
		Transaction tx = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				Model persistent = null;
				
				System.out.print("Try to delete Model "+c.getId()); // TODO remove
				persistent = (Model) session.load(ModelImpl.class, c.getId());

				System.out.println(" loaded: "+persistent.getId()); // TODO remove
				
				session.delete(persistent); // TODO write Info message to other user, that this collaborateable was deleted
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
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#createMetaModel(java.lang.String, org.gemsjax.shared.user.RegisteredUser)
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
				metaModel.getUsers().add(owner);Model persistent = null;
				
				metaModel.setForExperiment(false);
				metaModel.setPublicPermission(Collaborateable.Permission.PRIVATE);
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
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not create a new MetaModel");
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#updatePublicPermission(org.gemsjax.shared.collaboration.Collaborateable, int)
	 */
	public void updatePublicPermission(Collaborateable col, int permission ) throws DAOException
	{
		Session session = null;
		Transaction tx = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				col.setPublicPermission(Collaborateable.Permission.PRIVATE);
			session.update(col);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not update the public permission");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#updateCollaborateableName(org.gemsjax.shared.collaboration.Collaborateable, java.lang.String)
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
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not update the name of the Collaborateable");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#updateCollaborateableKeywords(org.gemsjax.shared.collaboration.Collaborateable, java.lang.String)
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
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not update the keywords of the collaborateable");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#getMetaModelById(int)
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
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#getModelById(int)
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
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#addCollaborativeUser(org.gemsjax.shared.collaboration.Collaborateable, org.gemsjax.shared.user.User)
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
				User um = (User) session.merge(u);
				Collaborateable cm = (Collaborateable)session.merge(c);
				session.saveOrUpdate(um);
				session.saveOrUpdate(cm);
			tx.commit();
			session.flush();
			session.close();
			
			
		}
		catch (HibernateException e)
		{
			e.printStackTrace(); 
			if (tx != null)
				tx.rollback();
		

			if (session!= null)
				session.close();
			
			throw new DAOException(e,"Could not add User to this Collaborateable");
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#addCollaborativeUsers(org.gemsjax.shared.collaboration.Collaborateable, java.util.Set)
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
				{
					user.getCollaborateables().add(c);
					session.update(user);
				}
				
				session.update(c);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not add a collection of Users to this Collaborateable");
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#createModel(java.lang.String, org.gemsjax.shared.metamodel.MetaModel, org.gemsjax.shared.user.RegisteredUser)
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
				model.setPublicPermission(Collaborateable.Permission.PRIVATE);
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
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#removeCollaborativeUser(org.gemsjax.shared.collaboration.Collaborateable, org.gemsjax.shared.user.User)
	 */
	public void removeCollaborativeUser(Collaborateable c, User u) throws DAOException
	{
		Transaction tx = null;
		Session session = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				c.getUsers().remove(u);
				u.getCollaborateables().remove(c);
				session.update(c);
				session.update(u);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not remove a collaborative User from the Collaborateable");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.CollaborateableDAO#removeCollaborativeUsers(org.gemsjax.shared.collaboration.Collaborateable, java.util.Set)
	 */
	public void removeCollaborativeUsers(Collaborateable c, Set<User> users) throws DAOException
	{
		Transaction tx = null;
		Session session = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				
				for (User u : users)
				{
					c.getUsers().remove(u);
					u.getCollaborateables().remove(c);
					session.update(c);
					session.update(u);
				}
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not remove a collaborative User from the Collaborateable");
		}
	}


	@Override
	public Set<Collaborateable> getBySearch(String searchString, RegisteredUser requester) {
		
		
		
		String search = "%"+searchString.toLowerCase()+"%";
		System.out.println(requester+"   -    "+ search);
		String sql = "SELECT c FROM CollaborateableImpl c WHERE (c.owner = :user OR :user in elements(c.users)) AND (c.keywords like :searchString OR c.name like :searchString)";
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createQuery( sql );
		query.setParameter("user", requester);
		query.setParameter("searchString",search);
	    
	    List<Collaborateable> result = query.list();
	    session.close();
	    return new LinkedHashSet<Collaborateable>(result);
	}


	@Override
	public Collaborateable createCollaborateable(RegisteredUser owner,
			String name, String keywords, CollaborateableType type,
			Collaborateable.Permission permission, Set<User> collaborators)
			throws DAOException {
		
		
		
		Transaction tx = null;
		Session session = null;
		
		try
		{	

			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			CollaborateableImpl collaborateable = null;
			
			if (type == CollaborateableType.METAMODEL)
				collaborateable = new MetaModelImpl();
			
			else
			if (type == CollaborateableType.MODEL)
				collaborateable = new ModelImpl();
			
			collaborateable.setName(name);
			collaborateable.setOwner(owner);
			collaborateable.setKeywords(keywords);
			collaborateable.setPublicPermission(permission);
		
			collaborateable.getUsers().add(owner);
			if (collaborators!=null) collaborateable.getUsers().addAll(collaborators);
		
			collaborateable.setForExperiment(false);
			collaborateable.setPublicPermission(permission);
			session.save(collaborateable);
			
			owner.getOwnedCollaborateables().add(collaborateable);
			owner.getCollaborateables().add(collaborateable);
			session.update(owner);
		
			if (collaborators != null)
			for (User u : collaborators){
				u.getCollaborateables().add(collaborateable);
				session.update(u);
			}
			
			tx.commit();
			session.flush();
			session.close();
			
			
			return collaborateable;
			
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not create a new MetaModel");
		}
		
		
		
	}


	@Override
	public void updateCollaborateable(Collaborateable collaborateable, String name,
			String keywords, Collaborateable.Permission permission, Set<User> addCollaborators,
			Set<User> removeCollaborators) throws DAOException {
		
		
		
		Transaction tx = null;
		Session session = null;
		
		try
		{	

			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			if (name != null) collaborateable.setName(name);
			if (keywords!=null) collaborateable.setKeywords(keywords);
			if (permission!=null) collaborateable.setPublicPermission(permission);
			if (removeCollaborators!=null) collaborateable.getUsers().removeAll(addCollaborators);
			if (addCollaborators!=null) collaborateable.getUsers().addAll(addCollaborators);
			
			session.update(collaborateable);
			
			for (User u : addCollaborators){
				u.getCollaborateables().add(collaborateable);
				session.update(u);
			}
			
			for (User u : removeCollaborators){
				u.getCollaborateables().remove(collaborateable);
				session.update(u);
			}
			
			
			tx.commit();
			session.flush();
			session.close();
			
			
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not create a new MetaModel");
		}
		
		
		
		
	}


	@Override
	public Set<Collaborateable> getMetaModelsOf(RegisteredUser user) {
		
		String sql = "SELECT c FROM MetaModelImpl c WHERE (c.owner = :user OR :user in elements(c.users)) ";
	
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createQuery( sql );
		query.setParameter("user", user);
		
	    List<MetaModel> result = query.list();
	    session.close();
	    return new LinkedHashSet<Collaborateable>(result);
    
	}


	@Override
	public Set<Collaborateable> getModelsOf(RegisteredUser user) {
		
		String sql = "SELECT c FROM ModelImpl c WHERE (c.owner = :user OR :user in elements(c.users)) ";
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createQuery( sql );
		query.setParameter("user", user);
		
	    List<Model> result = query.list();
	    session.close();
	    return new LinkedHashSet<Collaborateable>(result);
	    
	}


	@Override
	public Collaborateable getCollaborateable(int id) throws NotFoundException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Collaborateable m = (CollaborateableImpl)session.get(CollaborateableImpl.class, id);
		session.close();
		
		if (m == null)
			throw new NotFoundException();
		
		else
			return m;
	
	}


	@Override
	public void addTransaction(Collaborateable c,
			org.gemsjax.shared.collaboration.Transaction t) throws DAOException {
		
		Session session = null;
		Transaction tx = null;
		
		try
		{	
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				c.addTransaction(t);
				session.update(c);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not update the name of the Collaborateable");
		}
		
		
	}
	
	
	
	
}
