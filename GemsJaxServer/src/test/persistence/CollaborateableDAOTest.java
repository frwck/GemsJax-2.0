package test.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CollaborateableDAOTest {
	
	private static CollaborateableDAO dao;
	private static UserDAO registeredUserDAO;
	private static Set<Collaborateable> createdCollaborateables;
	
	private static RegisteredUser owner1;
	private static Set<RegisteredUser> collaborativeUsers;
	
	 @BeforeClass 
	 public static void classSetup() throws UsernameInUseException {

		 dao = new CollaborateableDAO();
		 registeredUserDAO = new UserDAO();
		 
		 createdCollaborateables=new HashSet<Collaborateable>();
		 
		  owner1 = registeredUserDAO.createRegisteredUser("owner1", "passwordHash", "email"); 
		  collaborativeUsers = new HashSet<RegisteredUser>();
		  
		  for (int i =0; i<10; i++)
			  collaborativeUsers.add( registeredUserDAO.createRegisteredUser("TestCollaborativeUser"+i, "passwordHash", "email") ); 
		  
			
	 }
	 
	 
	 @AfterClass
	 public static void classSetDown() throws ArgumentException
	 {
		 for (Collaborateable c: createdCollaborateables)
		 {
			dao.deleteCollaborateable(c);
		 }
		 
		 
		 registeredUserDAO.deleteRegisteredUser(owner1);
		 
		 for (RegisteredUser u: collaborativeUsers)
			 registeredUserDAO.deleteRegisteredUser(u);
		  
	 }
	 
	 
	 
	 @Test
	 public void test() throws MoreThanOneExcpetion
	 {
		 createMetaModel();
	 }
	 
	 
	 
	 private void createMetaModel() throws MoreThanOneExcpetion
	 {
		 String name ="name";
		 
		 MetaModel m;
		 int tests = 10;
		 
		 for (int i =0; i<tests; i++)
		 {
			m = dao.createMetaModel(name+i, owner1);
			createdCollaborateables.add(m);
			
			assertTrue( m.getName().equals(name+i));
			assertTrue(m.getPublicPermission() == Collaborateable.NO_PERMISSION);
			assertTrue(m.getOwner() == owner1);
			
			assertTrue(dao.getMetaModelById(m.getId()) == m);
			
		 }
		 
		 
		 
	 }

}
