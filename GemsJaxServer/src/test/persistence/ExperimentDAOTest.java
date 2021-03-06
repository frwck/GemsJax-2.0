package test.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.InvitationAlreadyUsedException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExperimentDAOTest {
	
	private static String username;
	private static String password;
	
	private static String experimentName;
	private static String experimentDescription;
	
	private static String experimentGroupName;
	
	
	private static List<ExperimentUser> createdExperimentUsers;
	private static List<Experiment> createdExperiments;
	private static List<ExperimentGroup> createdExperimentGroups;
	private static List<ExperimentInvitation> createdExperimentInvitations;
	
	
	private static ExperimentDAO dao;
	private static UserDAO registeredUserDAO;
	
	private static RegisteredUser owner1;
	private static Set<RegisteredUser> admins;
	
	
	
	
	 @BeforeClass 
	 public static void classSetup() throws UsernameInUseException, DAOException, EMailInUseExcpetion {

		 dao = new HibernateExperimentDAO();
		 registeredUserDAO = new HibernateUserDAO();
		 
		 username = "username";
		 password = "password";
		 experimentName = "ExperimentName";
		 experimentDescription = "ExperimentDescription";
		 experimentGroupName ="TestExperimentGroupName";
		 
		 createdExperimentUsers = new ArrayList<ExperimentUser>();
		 createdExperiments = new ArrayList<Experiment>();
		 createdExperimentGroups = new ArrayList<ExperimentGroup>();
		 createdExperimentInvitations = new ArrayList<ExperimentInvitation>();
		 
		 
		  owner1 = registeredUserDAO.createRegisteredUser("ExperimentOwner1", "passwordHash", "ExperimentEmail"); 
		  admins = new HashSet<RegisteredUser>();
		  
		  for (int i =0; i<1; i++)
			  admins.add(registeredUserDAO.createRegisteredUser("ExperimentAdmin"+i, "passwordHash", "ExperimentEmail"+i)); 
		  
			
	 }
	 
	 
//	 @AfterClass
	 public static void classSetDown() throws ArgumentException, DAOException
	 {
		 for (Experiment e: createdExperiments)
		 {
			dao.deleteExperiment(e);
		 }
		 
		 registeredUserDAO.deleteRegisteredUser(owner1);
		 
		 for (RegisteredUser a : admins)
			 registeredUserDAO.deleteRegisteredUser(a);
		  
	 }
	 
	 
	 
	 
	 @Test
	 public void createExperiments() throws ArgumentException
	 {
		int tests = 1;
				
		 for (int i =0; i<tests; i++)
		 {
			 Experiment e = dao.createExperiment("experimentName"+i, experimentDescription+i, owner1);
			 createdExperiments.add(e);
		 }
	 }
	 
	 @Test
	 public void checkExperimentOwner()
	 {
		 
		List<Experiment> exp = dao.getExperimentByOwner(owner1);
		
		assertTrue(exp.size() == createdExperiments.size());
		
		for (Experiment e: exp)
		{
			boolean ok = false;
			for(Experiment ee : createdExperiments)
			{
				if (ee.getId()== e.getId())
				{
					ok = true;
					break;
				}
					
			}
			
			if (ok)
				assertTrue(true);
			else
				assertTrue(false);
		}
	 }
	 
	 
	@Test
	public void createExperimentGroups() throws MoreThanOneExcpetion, ArgumentException, NotFoundException
	{
		int tests = 1;
		
		Experiment queriedExperiment;
		for (Experiment e : createdExperiments)
		{
			queriedExperiment = dao.getExperimentById(e.getId());
			
			assertTrue(e.getId() == queriedExperiment.getId());
			
			for (int i =0; i<tests; i++)
			{
				ExperimentGroup g = dao.createAndAddExperimentGroup(queriedExperiment, experimentGroupName+i, new Date(), new Date() );
				createdExperimentGroups.add(g);
			}
		}
	}
	
	@Test
	public void createExperimentAdministrators() throws AlreadyAssignedException, DAOException
	{
		List<Experiment> experiments = dao.getExperimentByOwner(owner1);
		
		for (Experiment e: experiments)
			for (RegisteredUser u : admins)
			{
				dao.assignExperimentAdministrator(e, u);
				

				assertTrue(e.getAdministrators().contains(u));
				
				// Try duplicate entry
				try {
					dao.assignExperimentAdministrator(e,u);
					assertTrue(false);
				}catch (AlreadyAssignedException ex) 
				{
					assertTrue(true);
				}
				
			}
	}
	
	
	@Test
	public void testExperimentInvitations() throws NotFoundException, MoreThanOneExcpetion, DAOException, InvitationAlreadyUsedException
	{
		String email ="inviteEmail@email.com", verificationCode = "verificationCode";
		String exUsername = "ExperimentTestUser", exPassword ="passwordHash";
		int i = 1;
		
		for (ExperimentGroup group : createdExperimentGroups)
		{
			ExperimentInvitation in = dao.createExperimentInvitation(group, email+i, verificationCode+i);
			assertTrue(in.getEmail().equals(email+i));
			assertTrue(in.getVerificationCode().equals(verificationCode+i));
			assertTrue(in.getExperimentGroup().getId() == group.getId());
			
			ExperimentGroup queried = dao.getExperimentGroup(group.getId());
			
			for (ExperimentInvitation inv : queried.getExperimentInvitations())
			{
				if (inv.getId() == in.getId())
				{
					assertTrue(inv.getId() == in.getId());
					assertTrue(inv.getEmail().equals(email+i));
					assertTrue(inv.getVerificationCode().equals(verificationCode+i));
					assertTrue(inv.getExperimentGroup().getId() == group.getId());
					
				}
			}
			
			// CREATE ExperimentUsers
			ExperimentUser u = dao.createExperimentUser(in.getVerificationCode(), exUsername+i, exPassword+i);
			ExperimentUser loginUser = dao.getExperimentUserByLogin(exUsername+i, exPassword+i);
			
			assertTrue(u.getId() == loginUser.getId());
			assertTrue(u.getExperimentGroup().getId() == loginUser.getExperimentGroup().getId());
			assertTrue(u.getUsername().equals(loginUser.getUsername()));
			assertTrue(u.getDisplayedName().equals(exUsername+i));
			assertTrue(loginUser.getDisplayedName().equals(exUsername+i));
			
			i++;
		}
	}
	 

}
