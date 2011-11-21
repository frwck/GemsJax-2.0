package test.persistence;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.shared.user.RegisteredUser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class UserDAOTest {
	
	private static String username;
	private static String password;
	private static String email;
	private static int userCounter;
	
	private static List<RegisteredUser> createdRegisteredUsers;
	
	private static UserDAO dao;
	
	
	 @BeforeClass 
	 public static void classSetup() {

		 dao = new UserDAO();
		 
		 username = "username";
		 password = "password";
		 email = "email";
		 userCounter = 0;
		 
		 createdRegisteredUsers = new ArrayList<RegisteredUser>();
			
	 }
	 
	 
	 @AfterClass
	 public static void classSetDown() throws DAOException
	 {
		 for (RegisteredUser u: createdRegisteredUsers)
		 {
			dao.deleteRegisteredUser(u); 
		 }
	 }
	
	
	@Test
	public void createRegisteredUser() throws MoreThanOneExcpetion, UsernameInUseException, DAOException, NotFoundException, EMailInUseExcpetion
	{
		int createCount = 10;
		
		for (int i =1; i<=createCount;i++)
		{
			RegisteredUser u = dao.createRegisteredUser(username+i, password+i, email+i);
			createdRegisteredUsers.add(u);
		}
		
		
		for (int i =1; i<=createCount;i++)
		{
			RegisteredUser u = dao.getUserByLogin(username+i, password+i);
			
			assertTrue(u!=null);
			assertTrue(createdRegisteredUsers.contains(u));
			assertEquals(u.getDisplayedName(), username+i);
			assertEquals(u.getUsername(), username+i);
			assertEquals(u.getEmail(), email+i);
			
		}
		
		
		while (!createdRegisteredUsers.isEmpty())
		{
			RegisteredUser u = createdRegisteredUsers.get(0);
			dao.deleteRegisteredUser(u);
			assertNull(dao.getRegisteredUserById(u.getId()));
			createdRegisteredUsers.remove(u);
		}
		
		
	}
	
	
	public void duplicatedUsername() throws DAOException
	{
		try{
			RegisteredUser u = dao.createRegisteredUser("username", "passwordHash", "email");
			createdRegisteredUsers.add(u);
			RegisteredUser uu = dao.createRegisteredUser("username", "passwordHash", "email");
			assertTrue(false);
			createdRegisteredUsers.add(uu);
		}
		catch(UsernameInUseException e)
		{
			assertTrue(true);
		}
		catch(EMailInUseExcpetion e)
		{
			assertTrue(true);
		}
		
	}
	
	
	
	

}