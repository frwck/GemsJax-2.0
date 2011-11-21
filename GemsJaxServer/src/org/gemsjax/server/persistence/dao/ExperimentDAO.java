package org.gemsjax.server.persistence.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.InvitationException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.experiment.ExperimentGroupImpl;
import org.gemsjax.server.persistence.experiment.ExperimentImpl;
import org.gemsjax.server.persistence.experiment.ExperimentInvitationImpl;
import org.gemsjax.server.persistence.experiment.ExperimentUserImpl;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;


/**
 * Use this Data Access Object to get Instances of:
 * <ul>
 * <li> {@link Experiment}</li>
 * <li> {@link ExperimentUser}</li>
 * <li> {@link ExperimentGroup}</li>
 * <li> {@link ExperimentInvitation}</li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class ExperimentDAO {

	
	public ExperimentDAO()
	{
	}
	
	
	
	/**
	 * Create a new {@link Experiment} and assign it to the owner
	 * @param name
	 * @param description
	 * @param owner
	 * @return
	 * @throws ArgumentException
	 */
	public Experiment createExperiment(String name, String description, RegisteredUser owner) throws ArgumentException
	{
		if (FieldVerifier.isEmpty(name))
			throw new ArgumentException("Name for Experiment is not set");
		
		if (owner == null)
			throw new ArgumentException("Owner is null");
			
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			
			tx= session.beginTransaction();
			ExperimentImpl e = new ExperimentImpl();
			
			e.setName(name);
			e.setDescription(description);
			e.setOwner(owner);
			
			owner.getOwnedExperiments().add(e);
			
			session.save(e);
			tx.commit();
			session.flush();
			session.close();
			
			return e;
		}catch (HibernateException ex )
		{
			if (tx != null)
				tx.rollback();
			
			if (session!=null)
				session.close();
			
			throw ex;
		}
	}
	
	
	/**
	 * Get a {@link Experiment} by its unique id
	 * @param id
	 * @return
	 * @throws NotFoundException 
	 */
	public Experiment getExperimentById(int id) throws NotFoundException
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Experiment e = (ExperimentImpl) session.get(ExperimentImpl.class, id);
			
		session.close();
		if (e == null)
			throw new NotFoundException();
		else
			return e;
	}
	
	
	/**
	 * Update the {@link Experiment} details
	 * @param e
	 * @param name
	 * @param description
	 * @throws IllegalArgumentException
	 */
	public void updateExperiment(Experiment e, String name, String description) throws ArgumentException
	{
		if (FieldVerifier.isEmpty(name))
			throw new ArgumentException("Name is empty");
		
		if (FieldVerifier.isEmpty(description))
			throw new ArgumentException("Description is empty");
		
		Session session = null;
		Transaction t = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			
				if (!name.equals(e.getName()))
					e.setName(name);
				
				if (!description.equals(e.getDescription()))
					e.setDescription(description);
			
				session.update(e);
			t.commit();
			session.flush();
			session.close();
		}
		catch (HibernateException ex)
		{
			if (t != null)
				t.rollback();
			
			if (session != null)
				session.close();
			
			throw ex;
		}
	}
	
	
	/**
	 * Create a new {@link ExperimentGroup} and add this new created to the passed {@link Experiment}
	 * @param experiment
	 * @param experimentGroupName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ArgumentException
	 */
	public ExperimentGroup createAndAddExperimentGroup(Experiment experiment, String experimentGroupName, Date startDate, Date endDate) throws ArgumentException
	{
		
		if (FieldVerifier.isEmpty(experimentGroupName))
			throw new ArgumentException("Group name is empty");
	
		Transaction t = null;
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				ExperimentGroupImpl g = new ExperimentGroupImpl();
				g.setEndDate(endDate);
				g.setStartDate(startDate);
				g.setName(experimentGroupName);
				g.setExperiment(experiment);
				
				experiment.getExperimentGroups().add(g);
				g.setExperiment(experiment);
				
				session.save(g);
				session.update(experiment);
			t.commit();
			session.flush();
			session.close();
			
			return g;
			
		}catch(HibernateException e)
		{
			if (t!=null)
				t.rollback();
			
			if (session!= null)
				session.close();
			
			throw e;
		}
	}
	
	/**
	 * Add a set of new {@link ExperimentGroup}s to the {@link Experiment}.
	 * "new" means, that the {@link ExperimentGroup}s are not already stored in the database and the {@link ExperimentGroup} 
	 * has at least set the name of the group and optional the start date and end date.
	 * @param experiment
	 * @param experimentGroups
	 */
	public void addExperimentGroups(Experiment experiment, Set<ExperimentGroup> experimentGroups) throws ArgumentException
	{
		if (experimentGroups== null || experimentGroups.isEmpty())
			throw new ArgumentException("The set of ExperimentGroups is empty");
		
		for (ExperimentGroup g: experimentGroups)
		{
			if (FieldVerifier.isEmpty(g.getName()))
				throw new ArgumentException("At least one ExperimentGroup has no name set");
		}
		
		Session session = null;
		Transaction t = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
		
				for (ExperimentGroup g: experimentGroups)
				{
					g.setExperiment(experiment);
					experiment.getExperimentGroups().add(g);
					
					session.save(g);
				}
				
				session.update(experiment);
			
			t.commit();
			session.flush();
			session.close();
		}
		catch(RuntimeException e)
		{
			if (t != null)
				t.rollback();
			
			if (session != null)
				session.close();
			throw e;
		}
	}
	
	
	public void deleteExperimentGroup(ExperimentGroup group) throws ArgumentException, DAOException
	{
		
		if (group == null)
			throw new ArgumentException("Group is null");
		
		Session session = null;
		Transaction t = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				group.getExperiment().getExperimentGroups().remove(group);
				session.update(group.getExperiment());
				session.delete(group);
			t.commit();
			session.flush();
			session.close();
		}
		catch(HibernateException e)
		{
			if (t!=null)
				t.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not delete an ExperimentGroup");
		}
	}
	
	
	/**
	 * Delete a {@link Experiment}. This would 
	 * @param experiment
	 * @throws ArgumentException
	 * @throws DAOException 
	 */
	public void deleteExperiment(Experiment experiment) throws ArgumentException, DAOException
	{
		
		if (experiment == null)
			throw new ArgumentException("Experiment is null");
		
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				session.delete(experiment);
			t.commit();
			session.flush();
			session.close();
		}catch (HibernateException e)
		{
			if (t != null)
				t.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not delete Experiment");
		}
	}
	
	
	
	/**
	 * Create a new {@link ExperimentUser}
	 * @param username
	 * @param passwordHash
	 * @param experimentGroup
	 * @return
	 * @throws ArgumentException
	 * @throws UsernameInUseException
	 */
	public ExperimentUser createExperimentUser(String username, String passwordHash, ExperimentGroup experimentGroup) throws ArgumentException, UsernameInUseException
	{
		if (FieldVerifier.isEmpty(username))
			throw new ArgumentException("Username is not set");
		
		if (FieldVerifier.isEmpty(passwordHash))
			throw new ArgumentException("Password is not set");
		
		if (experimentGroup == null)
			throw new ArgumentException("ExperimentGroup is null");
		
		Transaction t = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			
				ExperimentUserImpl u = new ExperimentUserImpl();
				u.setDisplayedName(username);
				u.setUsername(username);
				u.setPasswordHash(passwordHash);
				u.setExperimentGroup(experimentGroup);
				
				session.save(u);
				
				experimentGroup.getParticipants().add(u);
				
				session.update(experimentGroup);
				
			t.commit();	
			session.flush();
			session.close();
		
			return u;
			
		} 
		catch (ConstraintViolationException e)
		{
			t.rollback();
			throw new UsernameInUseException(username, e.getMessage());
		}
		catch (HibernateException e)
		{
			if (t !=null)
				t.rollback();
			
			if (session != null)
				session.close();
			
			throw e;
		}
			
		
	}
	
	
	
	public ExperimentInvitation createExperimentInvitation(ExperimentGroup group, String email) throws ArgumentException, InvitationException, DAOException
	{
		
		if (FieldVerifier.isEmpty(email))
			throw new ArgumentException("Email is empty");
		
		if (group == null)
			throw new ArgumentException("Group is null");
		
		
		// Check if the Email is already invited to an Experiment (Check all groups)
		if (isInvited(group.getExperiment(), email))
			throw new InvitationException(email+" is already invited");
		
		Session session = null;
		Transaction t = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				ExperimentInvitationImpl i = new ExperimentInvitationImpl();
				i.setEmail(email);
				i.setExperimentGroup(group);
				i.setVerificationCode(UUID.randomUUID().toString());
				
				session.save(i);
				
				group.getExperimentInvitations().add(i);
				session.update(group);
				
			t.commit();
			session.flush();
			session.close();
			
			return i;
		}
		catch(HibernateException th)
		{	
			if (t != null)
				t.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(th, "Could not create a ExperimentInvitation");
		}
	}
	
	
	private boolean isInvited(Experiment e, String email)
	{
		for (ExperimentGroup g: e.getExperimentGroups())
			for (ExperimentInvitation i : g.getExperimentInvitations())
				if (i.getEmail().equals(email))
					return true;
					
		return false;
	}
	
	
	
	public void updateExperimentGroup(ExperimentGroup group, String name, Date startDate, Date endDate) throws ArgumentException, DAOException
	{
		
		if (group == null)
			throw new ArgumentException("Group is null");
		
		if (FieldVerifier.isEmpty(name))
			throw new ArgumentException("Name is empty");
		
		Session session = null;
		Transaction t = null;
		
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				group.setName(name);
				group.setEndDate(endDate);
				group.setStartDate(startDate);
			t.commit();
			session.flush();
			session.close();
		}
		catch(HibernateException e)
		{
			if (t != null)
				t.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not update ExperimentGroup");
		}
		
	}
	
	
	public void deleteExperimentInvitation(ExperimentInvitation invitation) throws ArgumentException, DAOException
	{
		if (invitation == null)
			throw new ArgumentException("Group is null");
		Session session = null;
		Transaction t = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				ExperimentGroup group = invitation.getExperimentGroup();
				group.getExperimentInvitations().remove(invitation);
				session.update(group);
				session.delete(invitation);
			t.commit();
			session.flush();
			session.close();
		}
		catch(HibernateException e)
		{
			if (t!=null)
				t.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e, "Could not delete an Experiment Invitation");
		}
	}
	
	
	/**
	 * Get a List with all Experiments, that belongs to the owner
	 * @param owner
	 * @return
	 */
	public List<Experiment> getExperimentByOwner(RegisteredUser owner)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery( "FROM ExperimentImpl WHERE user_id_owner = "+owner.getId() );
	      
	    List<Experiment> result = query.list();
	    session.close();
	    return result;
	    
	}
	
	/**
	 * Assign a {@link RegisteredUser} as administrator 
	 * @param experiment
	 * @param user
	 * @throws AlreadyAssignedException
	 * @throws DAOException 
	 */
	public void assignExperimentAdministrator(Experiment experiment, RegisteredUser user) throws AlreadyAssignedException, DAOException
	{
		
		if (experiment.getAdministrators().contains(user))
			throw new AlreadyAssignedException("User "+user.getId()+" is already assigned as experiment administrator");
		
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
				experiment.getAdministrators().add(user);
				user.getAdministratedExperiments().add(experiment);
			tx.commit();
			session.flush();
			session.close();
			
		}
		catch(HibernateException e)
		{
			if (tx != null)
				tx.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not assign an ExperimentAdministrator");
		}
		
	}
	
	
}
