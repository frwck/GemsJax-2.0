package test.persistence;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.shared.user.RegisteredUser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class UserDAOTest {
	
	private static String username;
	private static String password;
	private static String email;
	
	private static List<RegisteredUser> createdRegisteredUsers;
	
	private static UserDAO dao;
	
	
	 @BeforeClass 
	 public static void classSetup() {

		 dao = new HibernateUserDAO();
		 
		 username = "username";
		 password = "password";
		 email = "email";
		 
		 createdRegisteredUsers = new ArrayList<RegisteredUser>();
			
	 }
	 
	 
	//@AfterClass
	 public static void classSetDown() throws DAOException
	 {
		 for (RegisteredUser u: createdRegisteredUsers)
		 {
			dao.deleteRegisteredUser(u); 
		 }
	 }
	
	
	//@Test
	public void createRegisteredUser() throws MoreThanOneExcpetion, UsernameInUseException, DAOException, NotFoundException, EMailInUseExcpetion
	{
		int createCount = 1;
		
		for (int i =1; i<=createCount;i++)
		{
			RegisteredUser u = dao.createRegisteredUser(username+i, password+i, email+i);
			createdRegisteredUsers.add(u);
		}
		
		// DO a reconnect
		HibernateUtil.reconnect();
		
		for (int i =1; i<=createCount;i++)
		{
			RegisteredUser u=null;
			try{
				u = dao.getUserByLogin(username+i, password+i);
				assertTrue(true);
			}
			catch(NotFoundException e)
			{
				assertTrue(false);
			}
			
			assertTrue(u!=null);
			checkForContainment(u);
			assertEquals(u.getDisplayedName(), username+i);
			assertEquals(u.getUsername(), username+i);
			assertEquals(u.getEmail(), email+i);
			
		}
		
		
		while (!createdRegisteredUsers.isEmpty())
		{
			RegisteredUser u = createdRegisteredUsers.get(0);
			dao.deleteRegisteredUser(u);
			try
			{
				RegisteredUser toDel = dao.getRegisteredUserById(u.getId());
				assertTrue(false);
			}
			catch(NotFoundException e)
			{
				assertTrue(true);
			}
			
			createdRegisteredUsers.remove(u);
		}
		
		
	}
	
	//@Test
	public void testPasswordChange() throws UsernameInUseException, DAOException, EMailInUseExcpetion, MoreThanOneExcpetion
	{	
		int test = 1;
		
		String newPasswordHash = "newPasswordHash";
		
		for (int i =0;i<test; i++)
		{
			RegisteredUser u = dao.createRegisteredUser(username+"PasswordTest"+i, password+"set"+i, email+"set"+i);
			createdRegisteredUsers.add(u);
			
			dao.updateRegisteredUserPassword(u, newPasswordHash);
		}
		
		HibernateUtil.reconnect();
		
		for (int i =0;i<test; i++)
		{
			try {
				RegisteredUser u = dao.getUserByLogin(username+"PasswordTest"+i, newPasswordHash);
				assertTrue(true);
			} catch (NotFoundException e) {
				e.printStackTrace();
				assertTrue(false);
			}
		}
		
	}
	
	
	
	
	private void checkForContainment(RegisteredUser toCheck)
	{
		for (RegisteredUser u : createdRegisteredUsers)
		{
			if (u.getId()==toCheck.getId())
			{
				assertTrue(true);
				return;
			}
		}	
		
		assertTrue(false);
	}
	
	//@Test
	public void duplicatedUsername() throws DAOException
	{
		try{
			RegisteredUser u = dao.createRegisteredUser("usernameDuplicateTest1", "passwordHash", "emailduplicate21");
			createdRegisteredUsers.add(u);
			RegisteredUser uu = dao.createRegisteredUser("usernameDuplicateTest1", "passwordHash", "emailduplicateasdq12");
			assertTrue(false);
			createdRegisteredUsers.add(uu);
		}
		catch(UsernameInUseException e)
		{
			assertTrue(true);
		}
		catch(EMailInUseExcpetion e)
		{
			assertTrue(false);
		}
		
	}
	
	
	
	//@Test
	public void duplicatedEmail() throws DAOException
	{
		try{
			RegisteredUser u = dao.createRegisteredUser("usernameDuplicatedEmailTest1", "passwordHash", "duplicated@email.com");
			createdRegisteredUsers.add(u);
			RegisteredUser uu = dao.createRegisteredUser("usernameDuplicatedEmailTest2", "passwordHash", "duplicated@email.com");
			assertTrue(false);
			createdRegisteredUsers.add(uu);
		}
		catch(UsernameInUseException e)
		{
			assertTrue(false);
		}
		catch(EMailInUseExcpetion e)
		{
			assertTrue(true);
		}
		
	}
	
	
	
	
	//@Test
	public void changeEmail() throws DAOException
	{
		String email = "duplicated@email.com";
		try{
			RegisteredUser u = dao.createRegisteredUser("usernameDuplicatedEmailChangeTest1", "passwordHash", email);
			createdRegisteredUsers.add(u);
			RegisteredUser uu = dao.createRegisteredUser("usernameDuplicatedEmailChangeTest2", "passwordHash", "another2.duplicated@email.com");
			createdRegisteredUsers.add(uu);
			
			assertTrue(true);
			dao.updateRegisteredUserEmail(uu, email);
			assertTrue(false);
		}
		catch(UsernameInUseException e)
		{
			assertTrue(false);
		}
		catch(EMailInUseExcpetion e)
		{
			assertTrue(true);
		}
		
	}
	
	//@Test
	public void testFriendship() throws UsernameInUseException, DAOException, EMailInUseExcpetion, NotFoundException
	{
	
		RegisteredUser user =  dao.createRegisteredUser("IWantFriendsTest", "passwordHash", "iwantfriends@email.com");
		createdRegisteredUsers.add(user);
		
		// Create new Friendships
		
		int fCount = 2;
		RegisteredUser friends[] = new RegisteredUser[fCount];
		
		for (int i =  0; i<fCount; i++)
		{
			friends[i] = dao.createRegisteredUser("FriendTest"+i, "passwordHash", "friendTest"+i+"@email.com");
			createdRegisteredUsers.add(friends[i]);
			
			dao.addFriendship(user, friends[i]);
	
			System.out.println(user.getAllFriends().size());
			System.out.println(friends[i].getAllFriends().size());
			assertTrue(user.getAllFriends().contains(friends[i]));
			assertTrue(friends[i].getAllFriends().contains(user));
		
		}
		
		
		
		for (int i = 0; i<fCount; i++)
		{
			RegisteredUser fCheck = dao.getRegisteredUserById(friends[i].getId());
			assertTrue(fCheck.getAllFriends().contains(user));
			assertTrue (fCheck.equals(friends[i]));
			assertFalse(fCheck == friends[i]);
		}
		
		assertTrue(user.getAllFriends().size() == fCount);
		
		
		// cancel Friendships as user as cancler
		for (int i=0; i<(fCount/2); i++)
		{

			dao.cancelFriendship(user, friends[i]);
			assertTrue(!user.getAllFriends().contains(friends[i]));
			assertTrue(!friends[i].getAllFriends().contains(user));
		}
		
		
		// Let the friend cancel the friendship with the  user
		for (int i=(fCount/2); i<fCount; i++)
		{
			dao.cancelFriendship(friends[i], user);
			assertTrue(!user.getAllFriends().contains(friends[i]));
			assertTrue(!friends[i].getAllFriends().contains(user));
		}
		
		
		
		
		// Do the final test, no friends should be 
		
		
		
		
	}
	
	
	@Test
	public void searchTest()
	{
		Set<RegisteredUser> res = dao.getBySearch(".com");
		
		for (RegisteredUser r : res)
			System.out.println("User: " + r.getUsername()+" "+r.getDisplayedName()+" "+r.getEmail());
	}
	
	

}
