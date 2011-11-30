package test.persistence;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.gemsjax.server.data.metamodel.MetaModelImpl;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.RequestDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateRequestDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.persistence.user.RegisteredUserImpl;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestDAOTest {
	
	private static RequestDAO dao;
	private static UserDAO registeredUserDAO;
	private static CollaborateableDAO collaborateableDAO;
	private static List<Request> createdRequests;
	
	
	 @BeforeClass 
	 public static void classSetup() throws UsernameInUseException, DAOException, EMailInUseExcpetion {

		 dao = new HibernateRequestDAO();
		 registeredUserDAO = new HibernateUserDAO();
		 collaborateableDAO = new HibernateCollaborateableDAO();
		 createdRequests=new LinkedList<Request>();
		 	
	 }
	 
	 
//	 @AfterClass
	 public static void classSetDown() throws ArgumentException, DAOException
	 {
		 for (Request r: createdRequests)
		 {
			dao.deleteRequest(r);
		 }
		 
		 HibernateUtil.getSessionFactory().close();
	 }
	 
	 
	 @Test
	 public void testRequests() throws DAOException, UsernameInUseException, EMailInUseExcpetion
	 {
		 RegisteredUser sender = registeredUserDAO.createRegisteredUser("RequestTestSender", "passwordHash", "requestTest@sender.com");
		 RegisteredUser receiver = registeredUserDAO.createRegisteredUser("RequestTestReceiver", "passwordHash", "requestTest@receiver.com");
		 
		 MetaModel collaborateable = collaborateableDAO.createMetaModel("RequestTestMetaModel", sender);
		 
		 
		 int tests = 1;
		 
		 for (int i =0; i<tests; i++)
		 {
			 CollaborateRequest r = dao.createCollaborateRequest(sender, receiver, collaborateable);
			 createdRequests.add(r);
			 System.out.println(r.getReceiver() + "  "+r.getSender() + r.getReceiver().getId());
			 assertTrue(r.getReceiver().getId() == receiver.getId());
			 assertTrue(r.getSender().getId() == sender.getId());
			
			 
			 List<Request> recList = dao.getAllRequestsFor(receiver);
			 assertTrue(recList.size() == i+1);
			 assertTrue(recList.contains(r));
			 
			 List<Request> sendList = dao.getAllRequestsBy(sender);
			 assertTrue(sendList.size() == i+1);
			 assertTrue(sendList.contains(r));
			 
		 }
		 
		 
		 for (Request r : createdRequests)
		 {
			 dao.deleteRequest(r);
		 }
		 
		 registeredUserDAO.deleteRegisteredUser(receiver);
		 registeredUserDAO.deleteRegisteredUser(sender);
		// collaborateableDAO.deleteCollaborateable(collaborateable); NOT NEEDED, because DELTETE RegisteredUser will delete all owned Collaborateables
		 
	 }
	 
	
}
