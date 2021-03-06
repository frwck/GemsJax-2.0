package test.persistence;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import java.util.List;
import org.gemsjax.server.persistence.HibernateUtil;
import org.gemsjax.server.persistence.dao.CollaborateableDAO;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.NotificationDAO;
import org.gemsjax.server.persistence.dao.RequestDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.AlreadyAssignedException;
import org.gemsjax.server.persistence.dao.exception.AlreadyExistsException;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateCollaborateableDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateNotificationDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateRequestDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.persistence.notification.NotificationImpl;
import org.gemsjax.server.persistence.notification.QuickNotificationImpl;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.notification.Notification;
import org.gemsjax.shared.notification.QuickNotification;
import org.gemsjax.shared.notification.QuickNotification.QuickNotificationType;
import org.gemsjax.shared.request.AdministrateExperimentRequest;
import org.gemsjax.shared.request.CollaborateRequest;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.user.RegisteredUser;
import org.hibernate.Hibernate;
import org.junit.BeforeClass;
import org.junit.Test;

public class NotificationDAOTest {
	
	private static NotificationDAO dao;
	private static UserDAO registeredUserDAO;
	private static List<Notification> createdNotifications;
	
	
	 @BeforeClass 
	 public static void classSetup() throws UsernameInUseException, DAOException, EMailInUseExcpetion {

		 dao = new HibernateNotificationDAO();
		 registeredUserDAO = new HibernateUserDAO();
		 
		 createdNotifications=new LinkedList<Notification>();
		 	
	 }
	 
	 
//	 @AfterClass
	 public static void classSetDown() throws ArgumentException, DAOException
	 {
		 for (Notification n: createdNotifications)
		 {
			dao.deleteNotification(n);
		 }
		 
		 HibernateUtil.getSessionFactory().close();
	 }
	 
	 
	 @Test
	 public void testCollaborateRequests() throws DAOException, UsernameInUseException, EMailInUseExcpetion, AlreadyAssignedException, AlreadyExistsException, NotFoundException
	 {
		RegisteredUser receiver = registeredUserDAO.createRegisteredUser("NotificationTestReceiver", "passwordHash", "NotificationTest@receiver.com");
		 
		 int tests = 100;
		 
		 for (int i =0; i<tests; i++)
		 {
			 
			 QuickNotificationImpl n = (QuickNotificationImpl)dao.createQuickNotification(receiver, QuickNotificationType.COLLABORATEABLE_DELETED,"optionalMessage");
			 createdNotifications.add(n);
			
			
			 assertTrue(n.getReceiver().getId() == receiver.getId());
			 
			 List<Notification> unList = dao.getUnreadNotificationsFor(receiver);
			 assertTrue(unList.size() == 1);
			 assertTrue(unList.contains(n));
			 
			 QuickNotificationImpl queried = (QuickNotificationImpl)dao.getNotification(n.getId());
			 
			 assertEquals(queried, n);
			 //assertTrue(queried.getDate().getTime() == n.getDate().getTime());
			 assertTrue(queried.getOptionalMessage().equals( n.getOptionalMessage() ));
			 assertTrue(queried.getId() == n.getId());
			 assertTrue(queried.getQuickNotificationType() == n.getQuickNotificationType());
			// assertEquals(queried.getReceiver(), n.getReceiver());
			 assertFalse(queried.isRead());
			 

			 System.out.println(dao.getUnreadCount(receiver));
			 dao.setRead(n, true);

			 System.out.println(dao.getUnreadCount(receiver));
			 
			 queried = (QuickNotificationImpl)dao.getNotification(n.getId());
			 assertTrue(queried.isRead());
			 
			 
			 List<Notification> list = dao.getNotificationsFor(receiver);
			 assertTrue(list.size() == i+1);
			 assertTrue(list.contains(n));
			 assertTrue(list.contains(queried));
			 
			
		 }
		
		 for (Notification n : createdNotifications)
		 {
			 dao.deleteNotification(n);
		 }
		 
		 createdNotifications.clear();
		 
		 registeredUserDAO.deleteRegisteredUser(receiver);
		 
	 }
	 
	
	
}
