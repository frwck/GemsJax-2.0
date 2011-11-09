package test.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
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
	private static RegisteredUser admins[];
	
	
	
	
	 @BeforeClass 
	 public static void classSetup() throws UsernameInUseException {

		 dao = new ExperimentDAO();
		 registeredUserDAO = new UserDAO();
		 
		 username = "username";
		 password = "password";
		 experimentName = "ExperimentName";
		 experimentDescription = "ExperimentDescription";
		 experimentGroupName ="TestExperimentGroupName";
		 
		 createdExperimentUsers = new ArrayList<ExperimentUser>();
		 createdExperiments = new ArrayList<Experiment>();
		 createdExperimentGroups = new ArrayList<ExperimentGroup>();
		 createdExperimentInvitations = new ArrayList<ExperimentInvitation>();
		 
		 
		  owner1 = registeredUserDAO.createRegisteredUser("owner1", "passwordHash", "email"); 
		  admins = new RegisteredUser[3];
		  
		  for (int i =0; i<admins.length; i++)
			  admins[i] = registeredUserDAO.createRegisteredUser("ExperimentAdmin"+i, "passwordHash", "email"); 
		  
			
	 }
	 
	 
	 @AfterClass
	 public static void classSetDown() throws ArgumentException
	 {
		 for (Experiment e: createdExperiments)
		 {
			dao.deleteExperiment(e);
		 }
		 
		 
		 registeredUserDAO.deleteRegisteredUser(owner1);
		 for (int i =0; i<admins.length; i++)
			 registeredUserDAO.deleteRegisteredUser(admins[i]);
		  
	 }
	 
	 
	 @Test
	 public void experimentTests() throws ArgumentException, MoreThanOneExcpetion, AlreadyAssignedException
	 {
		 
		 createExperiments();
		 checkExperimentOwner(owner1);
		 
		 createExperimentAdministrators();
		 //createExperimentGroups(); 
	 }
	 
	 
	 
	 
	 private void createExperiments() throws ArgumentException
	 {
		int tests = 10;
				
		 for (int i =0; i<tests; i++)
		 {
			 Experiment e = dao.createExperiment("experimentName"+i, experimentDescription+i, owner1);
			 createdExperiments.add(e);
		 }
	 }
	 
	 
	 private void checkExperimentOwner(RegisteredUser owner)
	 {
		 
		List<Experiment> exp = dao.getExperimentByOwner(owner1);
		
		assertTrue(exp.size() == createdExperiments.size());
		
		for (Experiment e: exp)
		{
			assertTrue(createdExperiments.contains(e));
		}
	 }
	 
	 
	private void createExperimentGroups() throws MoreThanOneExcpetion, ArgumentException
	{
		int tests = 10;
		
		Experiment queriedExperiment;
		for (Experiment e : createdExperiments)
		{
			queriedExperiment = dao.getExperimentById(e.getId());
			
			assertTrue(e == queriedExperiment);
			
			for (int i =0; i<tests; i++)
			{
				ExperimentGroup g = dao.createAndAddExperimentGroup(queriedExperiment, experimentGroupName+i, new Date(), new Date() );
				createdExperimentGroups.add(g);
			}
		}
	}
	
	
	private void createExperimentAdministrators() throws AlreadyAssignedException
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
	 

}
