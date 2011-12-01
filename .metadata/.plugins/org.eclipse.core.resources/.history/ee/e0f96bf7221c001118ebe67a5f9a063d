package test.persistence;

import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import java.util.List;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.RequestDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateRequestDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.request.AdministrateExperimentRequest;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequestDAOTest {
	
	private static RequestDAO dao;
	private static UserDAO registeredUserDAO;
	private static CollaborateableDAO collaborateableDAO;
	private static ExperimentDAO experimentDAO;
	private static List<Request> createdRequests;
	
	
	 @BeforeClass 
	 public static void classSetup() throws UsernameInUseException, DAOException, EMailInUseExcpetion {

		 dao = new HibernateRequestDAO();
		 registeredUserDAO = new HibernateUserDAO();
		 collaborateableDAO = new HibernateCollaborateableDAO();
		 experimentDAO = new HibernateExperimentDAO();
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
	 public void testCollaborateRequests() throws DAOException, UsernameInUseException, EMailInUseExcpetion, AlreadyAssignedException, AlreadyExistException
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
			 
			 
			 // try to create the same Request --> Excpetion
			 try
			 {
				 dao.createCollaborateRequest(sender, receiver, collaborateable);
				 assertTrue(false);
			 }
			 catch(AlreadyExistException e) {}
			 
			 // try to create a request, where Receiver is already assigned to --> Exception
			 try
			 {
				 dao.createCollaborateRequest(receiver, sender, collaborateable); // sender is the creator --> sender is already assigned
				 assertTrue(false);
			 }
			 catch(AlreadyAssignedException e) {}
		 }
		 
		 for (Request r : createdRequests)
		 {
			 dao.deleteRequest(r);
		 }
		 
		 createdRequests.clear();
		 
		 registeredUserDAO.deleteRegisteredUser(receiver);
		 registeredUserDAO.deleteRegisteredUser(sender);
		 
		 //collaborateableDAO.deleteCollaborateable(collaborateable);  // NOT NEEDED, because DELTETE RegisteredUser will delete all owned Collaborateables
		 
	 }
	 
	 
	 @Test
	 public void testAdministrateExperiment() throws UsernameInUseException, DAOException, EMailInUseExcpetion, ArgumentException, AlreadyAssignedException, AlreadyExistException
	 {
		 RegisteredUser sender = registeredUserDAO.createRegisteredUser("RequestTestSender", "passwordHash", "requestTest@sender.com");
		 RegisteredUser receiver = registeredUserDAO.createRegisteredUser("RequestTestReceiver", "passwordHash", "requestTest@receiver.com");
		 
		 Experiment ex = experimentDAO.createExperiment("AdministrateRequestTestExperiment", "description", sender);
		 

		 int tests = 1;
		 
		 for (int i =0; i<tests; i++)
		 {
			 AdministrateExperimentRequest r = dao.createAdministrateExperimentRequest(sender, receiver, ex);
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
			 
			 
			// try to create the same Request --> Excpetion
			 try
			 {
				 dao.createAdministrateExperimentRequest(sender, receiver, ex);
				 assertTrue(false);
			 }
			 catch(AlreadyExistException e) {System.out.println("catched");}
			 
			 // try to create a request, where Receiver is already assigned to --> Exception
			 try
			 {
				 dao.createAdministrateExperimentRequest(receiver, sender, ex); // sender is the creator --> sender is an admin
				 assertTrue(false);
			 }
			 catch(AlreadyAssignedException e) {}
			 
		 }
		 
		 for (Request r : createdRequests)
		 {
			 dao.deleteRequest(r);
		 }
		 createdRequests.clear();
		 
		 registeredUserDAO.deleteRegisteredUser(receiver);
		 registeredUserDAO.deleteRegisteredUser(sender);
		
		 
		// experimentDAO.deleteExperiment(ex); // NOT needed, since experiment is deleted, when the owner (RegisteredUser) is deleted
		 
	 }
	 
	
}
