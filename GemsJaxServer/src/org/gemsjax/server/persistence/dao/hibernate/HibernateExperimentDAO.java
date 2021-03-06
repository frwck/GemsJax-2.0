package org.gemsjax.server.persistence.dao.hibernate;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.InvitationAlreadyUsedException;
import org.gemsjax.server.persistence.dao.exception.InvitationException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.experiment.ExperimentGroupImpl;
import org.gemsjax.server.persistence.experiment.ExperimentImpl;
import org.gemsjax.server.persistence.experiment.ExperimentInvitationImpl;
import org.gemsjax.server.persistence.experiment.ExperimentUserImpl;
import org.gemsjax.server.persistence.request.AdministrateExperimentRequestImpl;
import org.gemsjax.server.persistence.request.RequestImpl;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Collaborateable.Permission;
import org.gemsjax.shared.communication.message.experiment.ExperimentGroupDTO;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.impl.MetaModelImpl;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
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
public class HibernateExperimentDAO implements ExperimentDAO {

	
	public HibernateExperimentDAO()
	{
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#createExperiment(java.lang.String, java.lang.String, org.gemsjax.shared.user.RegisteredUser)
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
			//session.buildLockRequest(LockOptions.NONE).lock(owner);
			
			ExperimentImpl e = new ExperimentImpl();
			
			e.setName(name);
			e.setDescription(description);
			e.setOwner(owner);
			
			owner = (RegisteredUser) session.merge(owner);
			
			owner.getOwnedExperiments().add(e);
			
			
			e.getAdministrators().add(owner);
			owner.getAdministratedExperiments().add(e);
			
			session.save(e);
			session.update(owner);
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
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#getExperimentById(int)
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
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#updateExperiment(org.gemsjax.shared.experiment.Experiment, java.lang.String, java.lang.String)
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
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#createAndAddExperimentGroup(org.gemsjax.shared.experiment.Experiment, java.lang.String, java.util.Date, java.util.Date)
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
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#addExperimentGroups(org.gemsjax.shared.experiment.Experiment, java.util.Set)
	 */
	@Override
	public void addExperimentGroups(Experiment experiment,
			Set<ExperimentGroupDTO> experimentGroups) throws ArgumentException {
		
		if (experimentGroups== null || experimentGroups.isEmpty())
			throw new ArgumentException("The set of ExperimentGroups is empty");
		
		for (ExperimentGroupDTO g: experimentGroups)
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
			
			session.buildLockRequest(LockOptions.NONE).lock(experiment);
			
		
				for (ExperimentGroupDTO dto: experimentGroups)
				{
					ExperimentGroup g = new ExperimentGroupImpl();
					
					g.setName(dto.getName());
					g.setStartDate(dto.getStartDate());
					g.setEndDate(dto.getEndDate());
					
					g.setExperiment(experiment);
					experiment.getExperimentGroups().add(g);
					
					session.save(g);
					
					
					// Create MetaModel
					createEmptyMetaModel(g, experiment.getOwner(), session);
					
					// Create Model
					createEmptyModel();
					
					
					// Generate Invitations
					for (String email : dto.getEmailToCreateInvitation())
					{
						ExperimentInvitationImpl i = new ExperimentInvitationImpl();
						i.setEmail(email);
						i.setExperimentGroup(g);
						i.setVerificationCode(UUID.randomUUID().toString());
						i.setParticipated(false);
						session.save(i);
						g.getExperimentInvitations().add(i);
						session.update(g);
						
					}
					
					
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
	
	
	private void createEmptyMetaModel(ExperimentGroup group, RegisteredUser experimentOwner, Session session){
		
		MetaModelImpl collaborateable = new MetaModelImpl();
		
		collaborateable.setName("Experiment - "+group.getExperiment().getName()+" - "+group.getName());
		collaborateable.setOwner(experimentOwner);
		collaborateable.setPublicPermission(Permission.PRIVATE);
	
		collaborateable.setForExperiment(true);
		session.save(collaborateable);
		
		experimentOwner.getOwnedCollaborateables().add(collaborateable);
		experimentOwner.getCollaborateables().add(collaborateable);
		session.update(experimentOwner);
		
		group.setMetaModel(collaborateable);
		session.update(group);
		
	}
	
	
	
	private void createEmptyModel(){
		// TODO implement
	}
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#deleteExperimentGroup(org.gemsjax.shared.experiment.ExperimentGroup)
	 */
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
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#deleteExperiment(org.gemsjax.shared.experiment.Experiment)
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
			

				Experiment persistentExperiment = (Experiment)session.get(ExperimentImpl.class, experiment.getId());
				// DELETE Requests
				String delHql = "DELETE from "+AdministrateExperimentRequestImpl.class.getName()+" C where experiment = :experiment";
				Query query = session.createQuery( delHql );
				query.setEntity("experiment", persistentExperiment);
				int del = query.executeUpdate();
				
				//experiment = (Experiment) session.merge(experiment);
				session.delete(persistentExperiment);
			t.commit();
			session.flush();
			session.close();
		}catch (HibernateException e)
		{
			e.printStackTrace();
			if (t != null)
				t.rollback();
			
			if (session != null)
				session.close();
			
			throw new DAOException(e, "Could not delete Experiment");
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#createExperimentUser(java.lang.String, java.lang.String, org.gemsjax.shared.experiment.ExperimentGroup)
	 */
	public ExperimentUser createExperimentUser(String username, String passwordHash, ExperimentGroup experimentGroup, String displayedName) throws ArgumentException, UsernameInUseException
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
				u.setDisplayedName(displayedName);
				u.setUsername(username);
				u.setPasswordHash(passwordHash);
				u.setExperimentGroup(experimentGroup);
				
				
				
				session.save(u);
				
				
				
				u.getCollaborateables().add(experimentGroup.getMetaModel());
				experimentGroup.getMetaModel().getUsers().add(u);
				
				if (experimentGroup.getModel()!=null)
					u.getCollaborateables().add(experimentGroup.getModel());
				
				
				session.update(u);
				session.update(experimentGroup.getMetaModel());
				
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
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#createExperimentInvitation(org.gemsjax.shared.experiment.ExperimentGroup, java.lang.String)
	 */
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
	
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#updateExperimentGroup(org.gemsjax.shared.experiment.ExperimentGroup, java.lang.String, java.util.Date, java.util.Date)
	 */
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
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#deleteExperimentInvitation(org.gemsjax.shared.experiment.ExperimentInvitation)
	 */
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
	
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#getExperimentByOwner(org.gemsjax.shared.user.RegisteredUser)
	 */
	public List<Experiment> getExperimentByOwner(RegisteredUser owner)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery( "FROM ExperimentImpl e WHERE user_id_owner = "+owner.getId() +"  OR :user in elements(e.administrators)" );
		query.setParameter("user", owner);
	    List<Experiment> result = query.list();
	    session.close();
	    return result;
	    
	}
	
	/* (non-Javadoc)
	 * @see org.gemsjax.server.persistence.dao.hibernate.ExperimentDAO#assignExperimentAdministrator(org.gemsjax.shared.experiment.Experiment, org.gemsjax.shared.user.RegisteredUser)
	 */
	public void assignExperimentAdministrator(Experiment experiment, RegisteredUser user) throws AlreadyAssignedException, DAOException
	{
		
		
		
		Transaction tx = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			/*
			session.buildLockRequest(LockOptions.NONE).lock(experiment);
			session.buildLockRequest(LockOptions.NONE).lock(user);
			*/
			if (experiment.getAdministrators().contains(user))
			{
				session.close();
				throw new AlreadyAssignedException("User "+user.getId()+" is already assigned as experiment administrator");
			}
			
			tx = session.beginTransaction();
				experiment.getAdministrators().add(user);
				user.getAdministratedExperiments().add(experiment);
				session.update(experiment);
				user = (RegisteredUser) session.merge(user);
				session.update(user);
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
			
			e.printStackTrace();
			
			throw new DAOException(e, "Could not assign an ExperimentAdministrator");
		}
		
	}



	@Override
	public ExperimentInvitation createExperimentInvitation(
			ExperimentGroup group, String email, String verificationCode) {
		
		Transaction t = null;
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				ExperimentInvitationImpl i = new ExperimentInvitationImpl();
				i.setEmail(email);
				i.setExperimentGroup(group);
				i.setVerificationCode(verificationCode);
				i.setParticipated(false);
				session.save(i);
				group.getExperimentInvitations().add(i);
				session.update(group);
			t.commit();
			session.flush();
			session.close();
			
			return i;
			
		}catch(HibernateException e)
		{
			if (t!=null)
				t.rollback();
			
			if (session!= null)
				session.close();
			
			throw e;
		}
	}



	@Override
	public Set<ExperimentInvitation> createExperimentInvitations(
			ExperimentGroup group, List<String> emails) throws ArgumentException, DAOException {
	

		int size = emails.size();
		
		Transaction t = null;
		Session session = null;
		
		Set<ExperimentInvitation> invites = new LinkedHashSet<ExperimentInvitation>();
		
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			
			for (int i = 0; i<size; i++)
			{
				ExperimentInvitationImpl inv = new ExperimentInvitationImpl();
				inv.setEmail(emails.get(i));
				inv.setExperimentGroup(group);
				inv.setVerificationCode(UUID.randomUUID().toString());
				inv.setParticipated(false);
				
				session.save(i);
				group.getExperimentInvitations().add(inv);
				session.update(group);
			}
			
			t.commit();
			session.flush();
			session.close();
			
			return invites;
			
		}catch(HibernateException e)
		{
			if (t!=null)
				t.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e,"Could not create ExperimentInvitations");
		}
		
	}



	@Override
	public ExperimentGroup getExperimentGroup(int id) throws 
			NotFoundException, MoreThanOneExcpetion {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery( "FROM ExperimentGroupImpl WHERE id = "+id );
	      
	    List<ExperimentGroup> result = (List<ExperimentGroup>) query.list();
	    
	    session.close();
	    if (result.size()>1)
	    	throw new MoreThanOneExcpetion();
	    else
	    	if (result.size()==0)
	    		throw new NotFoundException();
	    
	    
	    return result.get(0);
	    
	}



	@Override
	public ExperimentUser createExperimentUser(
			String invitationVerificationCode, String username,
			String passwordHash) throws DAOException, InvitationAlreadyUsedException {
		
		
		Transaction t = null;
		Session session = null;
		
		try {
			ExperimentInvitation invitation = getExperimentInvitation(invitationVerificationCode);
			
			if (invitation.hasParticipated())
				throw new InvitationAlreadyUsedException();
			
			
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				ExperimentUserImpl u = new ExperimentUserImpl();
				u.setDisplayedName(username);
				u.setUsername(username);
				u.setPasswordHash(passwordHash);
				u.setExperimentGroup(invitation.getExperimentGroup());
				session.save(u);
				invitation.setParticipated(true);
				session.update(invitation);
			t.commit();
			session.flush();
			session.close();
			
			return u;
			
		}catch(HibernateException e)
		{
			if (t!=null)
				t.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e,"Could not create ExperimentInvitations");
			
		} catch (MoreThanOneExcpetion e) {
			throw new DAOException("More than one ExperimentInivitation with the given verification code:"+invitationVerificationCode);
		} catch (NotFoundException e) {
			throw new DAOException("No ExperimentInvitation with the verification code "+invitationVerificationCode+" found");
		}
		
		
		
	}



	@Override
	public ExperimentInvitation getExperimentInvitation(String verificationCode) throws MoreThanOneExcpetion, NotFoundException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery( "FROM ExperimentInvitationImpl i WHERE i.verificationCode = :vCode" );
		query.setString("vCode", verificationCode);
	      
	    List<ExperimentInvitation> result = (List<ExperimentInvitation>) query.list();
	    
	    
	    if (result.size()>1){
	    	 session.close();
	    	 throw new MoreThanOneExcpetion();
	    }
	    else
	    	if (result.size()==0){
	    		 session.close();
	    		 throw new NotFoundException();
	    	}
	    		
	    
	    
	    ExperimentInvitation inv = result.get(0);
	    Hibernate.initialize(inv.getExperimentGroup());
	    session.close();
	    
	    
	    
	    
	    
	    return inv;
	    
	}



	@Override
	public ExperimentUser getExperimentUserByLogin(String username,
			String passwordHash) throws MoreThanOneExcpetion, NotFoundException, DAOException {
		
		Transaction t = null;
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			
			Query query = session.createQuery( "FROM ExperimentUserImpl WHERE username = :username and password =:password" );
			query.setString("username", username);
			query.setString("password", passwordHash);
		      
		    List<ExperimentUser> result = (List<ExperimentUser>) query.list();
		    
		    ExperimentUserImpl u = (ExperimentUserImpl) result.get(0);
		    Hibernate.initialize(u.getExperimentGroup());
		    
		    
		    session.close();
		    if (result.size()>1)
		    	throw new MoreThanOneExcpetion();
		    else
		    	if (result.size()==0)
		    		throw new NotFoundException();
		    
		    
		    return u;
			
		}catch(HibernateException e)
		{
			if (t!=null)
				t.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e,"Could not create ExperimentInvitations");
		}
	}
	
	
	
	@Override
	public Set<Experiment> getBySearch(String searchString, RegisteredUser requester) {
		
		String search = "%"+searchString.toLowerCase()+"%";
			String sql = "SELECT c FROM ExperimentImpl c WHERE (c.owner = :user OR :user in elements(c.administrators)) AND (c.description like :searchString OR c.name like :searchString)";
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createQuery( sql );
		query.setParameter("user", requester);
		query.setParameter("searchString",search);
	    
	    List<Experiment> result = query.list();
	    
	    return new LinkedHashSet<Experiment>(result);
	}



	@Override
	public boolean isDisplayedNameInExperimentGroupAvailable(
			String displayedName, ExperimentGroup group) {
		
		String sql = "SELECT c FROM ExperimentUserImpl c WHERE c.experimentGroup=:group AND c.displayedName=:dispName";
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createQuery( sql );
		query.setParameter("group", group);
		query.setParameter("dispName", displayedName);
		
		if (query.list().size()==0)
			return true;
		else		
			return false;
	}



	@Override
	public void setExperimentInvitationParticipated(ExperimentInvitation invitation, boolean participated) throws DAOException {
		
		Transaction t= null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
				invitation.setParticipated(participated);
				invitation = (ExperimentInvitation) session.merge(invitation);
			session.update(invitation);
			t.commit();
			
		}catch(HibernateException e)
		{
			if (t!=null)
				t.rollback();
			
			if (session!= null)
				session.close();
			
			throw new DAOException(e,"Could not set participated in an ExperimentInvitation");
		}
	}



	
	
	
}
