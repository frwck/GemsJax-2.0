package org.gemsjax.server.persistence.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.InvitationException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.experiment.ExperimentGroupImpl;
import org.gemsjax.server.persistence.experiment.ExperimentImpl;
import org.gemsjax.server.persistence.experiment.ExperimentInvitationImpl;
import org.gemsjax.server.persistence.experiment.ExperimentUserImpl;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;
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

	private Session session ;
	
	public ExperimentDAO()
	{
		session = HibernateUtil.getOpenedSession();
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
		try
		{
			ExperimentImpl e = new ExperimentImpl();
			
			e.setName(name);
			e.setDescription(description);
			e.setOwner(owner);
			
			
			tx= session.beginTransaction();
				session.save(e);
			tx.commit();
			
			return e;
		}catch (RuntimeException ex )
		{
			if (tx != null)
				tx.rollback();
			
			throw ex;
		}
	}
	
	
	/**
	 * Get a {@link Experiment} by its unique id
	 * @param id
	 * @return
	 * @throws MoreThanOneExcpetion
	 */
	public Experiment getExperimentById(int id) throws MoreThanOneExcpetion
	{
		Query query = session.createQuery( "FROM ExperimentImpl WHERE id="+id );
	      
	    List<ExperimentImpl> result = query.list();
	    
	    if (result.size()>0)
	    	if (result.size()>1)
	    			throw new MoreThanOneExcpetion();
	    		else
	    			return result.get(0);
	    else
	    	return null;
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
		
		Transaction t = null;
		try
		{
			t = session.beginTransaction();
			
				if (!name.equals(e.getName()))
					e.setName(name);
				
				if (!description.equals(e.getDescription()))
					e.setDescription(description);
			
				session.update(e);
			t.commit();
		}
		catch (RuntimeException ex)
		{
			if (t != null)
				t.rollback();
			
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
		
		try
		{
			
			t = session.beginTransaction();
				ExperimentGroupImpl g = new ExperimentGroupImpl();
				g.setEndDate(endDate);
				g.setStartDate(startDate);
				g.setName(experimentGroupName);
				g.setExperiment(experiment);
				
				experiment.getExperimentGroups().add(g);
				
				session.save(g);
				session.update(experiment);
			t.commit();
			
			return g;
			
		}catch(RuntimeException e)
		{
			if (t!=null)
				t.rollback();
			
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
		
		Transaction t = null;
		try
		{
			t = session.beginTransaction();
		
				for (ExperimentGroup g: experimentGroups)
				{
					g.setExperiment(experiment);
					experiment.getExperimentGroups().add(g);
					
					session.save(g);
				}
				
				session.update(experiment);
			
			t.commit();
		}
		catch(RuntimeException e)
		{
			if (t != null)
				t.rollback();
			
			throw e;
		}
	}
	
	
	public void deleteExperimentGroup(ExperimentGroup group) throws ArgumentException
	{
		
		if (group == null)
			throw new ArgumentException("Group is null");
		
		Transaction t = null;
		try{
			t = session.beginTransaction();
				group.getExperiment().getExperimentGroups().remove(group);
				session.update(group.getExperiment());
				session.delete(group);
			t.commit();
		}
		catch(RuntimeException e)
		{
			if (t!=null)
				t.rollback();
			
			throw e;
		}
	}
	
	
	/**
	 * Delete a {@link Experiment}. This would 
	 * @param experiment
	 * @throws ArgumentException
	 */
	public void deleteExperiment(Experiment experiment) throws ArgumentException
	{
		if (experiment == null)
			throw new ArgumentException("Experiment is null");
		
		Transaction t = null;
		try{
			t = session.beginTransaction();
				session.delete(experiment);
			t.commit();
		}catch (RuntimeException e)
		{
			if (t != null)
				t.rollback();
			
			throw e;
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
		
		Transaction t = session.beginTransaction();
		
		try {
		
			
				ExperimentUserImpl u = new ExperimentUserImpl();
				u.setDisplayedName(username);
				u.setUsername(username);
				u.setPasswordHash(passwordHash);
				u.setExperimentGroup(experimentGroup);
				
				session.save(u);
				
				experimentGroup.getParticipants().add(u);
				
				session.update(experimentGroup);
				
			t.commit();	
		
			return u;
			
		} 
		catch (ConstraintViolationException e)
		{
			t.rollback();
			throw new UsernameInUseException(username, e.getMessage());
		}
		catch (RuntimeException e)
		{
			t.rollback();
			throw e;
		}
			
		
	}
	
	
	
	public ExperimentInvitation createExperimentInvitation(ExperimentGroup group, String email) throws ArgumentException, InvitationException
	{
		
		if (FieldVerifier.isEmpty(email))
			throw new ArgumentException("Email is empty");
		
		if (group == null)
			throw new ArgumentException("Group is null");
		
		
		// Check if the Email is already invited to an Experiment (Check all groups)
		if (isInvited(group.getExperiment(), email))
			throw new InvitationException(email+" is already invited");
		
		Transaction t = null;
		try
		{
		
			t = session.beginTransaction();
				ExperimentInvitationImpl i = new ExperimentInvitationImpl();
				i.setEmail(email);
				i.setExperimentGroup(group);
				i.setVerificationCode(UUID.randomUUID().toString());
				
				session.save(i);
				
				group.getExperimentInvitations().add(i);
				session.update(group);
				
			t.commit();
			
			return i;
		}
		catch(RuntimeException th)
		{
			th.printStackTrace();
			
			if (t != null)
				t.rollback();
			
			throw th;
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
	
	
	
	public void updateExperimentGroup(ExperimentGroup group, String name, Date startDate, Date endDate) throws ArgumentException
	{
		
		if (group == null)
			throw new ArgumentException("Group is null");
		
		if (FieldVerifier.isEmpty(name))
			throw new ArgumentException("Name is empty");
		
		Transaction t = null;
		
		try
		{
			t = session.beginTransaction();
				group.setName(name);
				group.setEndDate(endDate);
				group.setStartDate(startDate);
			t.commit();
		}
		catch(RuntimeException e)
		{
			if (t != null)
				t.rollback();
			
			throw e;
		}
		
	}
	
	
	
	
	
}
