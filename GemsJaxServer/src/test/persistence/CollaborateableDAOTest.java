package test.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollaborateableDAOTest {
	
	private static HibernateCollaborateableDAO dao;
	private static UserDAO registeredUserDAO;
	private static Set<Collaborateable> createdCollaborateables;
	
	private static RegisteredUser owner1;
	private static Set<RegisteredUser> collaborativeUsers;
	
	
	 @BeforeClass 
	 public static void classSetup() throws UsernameInUseException, DAOException, EMailInUseExcpetion {

		 dao = new HibernateCollaborateableDAO();
		 registeredUserDAO = new HibernateUserDAO();
		 
		 createdCollaborateables=new HashSet<Collaborateable>();
		 
		  owner1 = registeredUserDAO.createRegisteredUser("CollaborateTestowner1", "passwordHash", "Testowneremail1"); 
		  
		  collaborativeUsers = new HashSet<RegisteredUser>();
			
	 }
	 
	 
//	 @AfterClass
	 public static void classSetDown() throws ArgumentException, DAOException
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
	 public void createMetaModel() throws MoreThanOneExcpetion, DAOException, NotFoundException
	 {
		 String name ="name";
		 
		 MetaModel m;
		 int tests = 1;
		 
		 for (int i =0; i<tests; i++)
		 {
			m = dao.createMetaModel(name+i, owner1);
			createdCollaborateables.add(m);
			
			assertTrue(m.getName().equals(name+i));
			assertTrue(m.getPublicPermission() == Collaborateable.Permission.PRIVATE);
			assertTrue(m.getOwner() == owner1);
			assertTrue(owner1.getCollaborateables().contains(m));
			assertTrue(dao.getMetaModelById(m.getId()).getId() == m.getId());
			
		 }
		 
	 }
	 
	
	 @Test
	 public void assignCollaborativeUser() throws DAOException, UsernameInUseException, EMailInUseExcpetion
	 { 
		 
		 for (int i =0; i<1; i++)
			collaborativeUsers.add( registeredUserDAO.createRegisteredUser("TestCollaborativeUser"+i, "passwordHash", "collaborativeemail"+i) ); 
		 
		 // Test with Registered User
		 for (Collaborateable c: createdCollaborateables)
		 {
			 for (RegisteredUser u :collaborativeUsers)
			 {
				 dao.addCollaborativeUser(c, u);
				 assertTrue(c.getUsers().contains(u));
				 assertTrue(((RegisteredUserImpl)u).getCollaborateables().contains(c));
			 }
				 
			 assertTrue (c.getUsers().containsAll(collaborativeUsers));
			 assertTrue (c.getUsers().contains(owner1));
			 assertTrue (c.getUsers().size() == collaborativeUsers.size()+1);
			 
		 }
		 
		 
		 Set<Collaborateable> searchRes=dao.getBySearch("name", owner1);
		 for (Collaborateable c: searchRes)
			 System.out.println(c+" "+c.getId()+" "+c.getName());
		 
		 
		 //TODO Test with ExperimentUser
		
		 
		 // remove the assignment
		 for (Collaborateable c: createdCollaborateables)
		 {
			 for (RegisteredUser u :collaborativeUsers)
			 {
				 dao.removeCollaborativeUser(c, u);
				 assertFalse (c.getUsers().contains(u));
				 assertFalse (((RegisteredUserImpl)u).getCollaborateables().contains(c));
			 }
			
			 assertTrue (c.getUsers().contains(owner1));
			 assertTrue (c.getUsers().size() == 1);
			 
			 // remove owner as collabrotive user
			 
			 dao.removeCollaborativeUser(c, owner1);
			 assertFalse (c.getUsers().contains(owner1));
			 assertTrue (c.getUsers().size() == 0);
			 assertFalse(owner1.getCollaborateables().contains(c));
		 }
		 
		 
		 Set<User> collSet = new LinkedHashSet<User>();
		 int count = 10;
		 for (int i =0; i<count; i++)
		 {
			 RegisteredUser u = registeredUserDAO.createRegisteredUser("collaborativeUserSetAssignmentTest"+i, "passwordHash", "emailcollaborativeUserSetAssignmentTest"+i);
			 collSet.add(u);
			 
			 collaborativeUsers.add(u);
		 }
		 
		 
		 
		 // Test assigning a Set of User
		 for (Collaborateable c: createdCollaborateables)
		 {
			 dao.addCollaborativeUsers(c, collSet);
			
			 for (User u : collSet)
			 {
				 assertTrue (c.getUsers().contains(u));
				 assertTrue (u.getCollaborateables().contains(c));
			 }
			 
			 assertTrue(c.getUsers().containsAll(collSet));
			
		 }
		 
		 
		 
		 // Test assigning a Set of User
		 for (Collaborateable c: createdCollaborateables)
		 {
			 dao.removeCollaborativeUsers(c, collSet);
			
			 for (User u : collSet)
			 {
				 assertFalse (c.getUsers().contains(u));
				 assertFalse (u.getCollaborateables().contains(c));
			 }
			 
			 assertFalse (c.getUsers().containsAll(collSet));
			
		 }
		 
		 
		 
		 
	 }
	 
	 @Test
	 public void createModel() throws MoreThanOneExcpetion, DAOException, NotFoundException
	 {
		 String name ="name";
		 
		 Model m;
		 int tests = 2;
		 
		 MetaModel baseModel = dao.createMetaModel("TestBaseMetaModel", owner1);
		 createdCollaborateables.add(baseModel);
		 
		 List<Model> models = new LinkedList<Model>();
		 
		 for (int i =0; i<tests; i++)
		 {
			m = dao.createModel(name+i, baseModel, owner1);
			createdCollaborateables.add(m);
			models.add(m);
			
			assertTrue( m.getName().equals(name+i));
			assertTrue(m.getPublicPermission() == Collaborateable.Permission.PRIVATE);
			assertTrue(m.getOwner() == owner1);
			assertTrue(m.getMetaModel() == baseModel);
			assertTrue(dao.getModelById(m.getId()).getId() == m.getId());
		 }
		 
		 
		 
		 // Try delete a Model
		 Model toDel = models.get(0);
		 dao.deleteCollaborateable(toDel);
		 
		 assertTrue(!baseModel.getModels().contains(toDel));
		 
		 createdCollaborateables.remove(toDel);
		 models.remove(toDel);
		 
		 dao.deleteCollaborateable(baseModel);
		 createdCollaborateables.remove(baseModel);
		 assertTrue(baseModel.getModels().isEmpty());
		 

		 createdCollaborateables.removeAll(models);
		 
		 
	 }
	 
	 
	 @Test
	 public void testDelete() throws DAOException
	 {
		 List<Collaborateable> toDel = new LinkedList<Collaborateable>(createdCollaborateables);
		while (!toDel.isEmpty())
		{
			System.out.println("List is: "+toDel.size());
			Collaborateable c = toDel.get(0);
			toDel.remove(0);
			dao.deleteCollaborateable(c);

			assertTrue(!owner1.getOwnedCollaborateables().contains(c));
		}
		
		createdCollaborateables.clear();
		System.out.println("Size "+createdCollaborateables.size());
	 }

}
