package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.gemsjax.server.logger.UnexpectedErrorLogger;
import org.gemsjax.server.module.NotificationModule;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.server.module.OnlineUserManager;
import org.gemsjax.server.module.RequestModule;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.util.SHA;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.CommunicationError;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage.LoginAnswerStatus;
import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.gemsjax.shared.communication.message.system.SystemErrorMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;
import org.xml.sax.SAXException;

/**
 * This channel listen to incoming system messages as xml with a beginning  &lt sys &gt xml tag
 * @author Hannes Dorfmann
 *
 */
public class UserAuthenticationChannel implements InputChannel, OutputChannel{

	
	private CommunicationConnection communicationConnection;
	private HttpSession httpSession;
	private String filterRegEx;
	
	private UserDAO userDAO;
	private ExperimentDAO experimentDAO;
		
	public UserAuthenticationChannel(CommunicationConnection connection, HttpSession httpSession)
	{
		this.communicationConnection = connection;
		this.httpSession = httpSession;
		filterRegEx = RegExFactory.startWithTag(SystemMessage.TAG);
		userDAO = new HibernateUserDAO();
		experimentDAO = new HibernateExperimentDAO();
	}
		
		@Override
	public boolean isMatchingFilter(String msg) {
		return Pattern.matches(filterRegEx, msg);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		
		SystemMessageParser parser = new SystemMessageParser();
		
		try {
			
			SystemMessage m = parser.parse(msg.getText());
			
			if (m instanceof LoginMessage)
			{
				LoginMessage lm = (LoginMessage) m;
				
				OnlineUser ou = null;
				
				
				if (lm.isExperimentLogin())
				{	
						ExperimentUser u = experimentDAO.getExperimentUserByLogin(lm.getUsername(), SHA.generate256(lm.getPassword()) );
						ou = OnlineUser.createForExperiment(u, communicationConnection, httpSession);
						
						if (ou == null)
						{  // should never be reached
							UnexpectedErrorLogger.severe("- Could not create a OnlineUser (is null): \n\t"+msg+"\n");
							send(new LoginAnswerMessage(LoginAnswerStatus.FAIL));
						}
						else
						{
							OnlineUserManager.getInstance().addOnlineUser(ou);
							
							send(new LoginAnswerMessage(ou.getId(), u.getExperimentGroup().getId(), u.getDisplayedName())); 
							communicationConnection.deregisterInputChannel(this);
						}
					
				} // ende if experiment
				else
				{
					RegisteredUser u = userDAO.getUserByLogin(lm.getUsername(), SHA.generate256(lm.getPassword()));
					ou = OnlineUser.create(u, communicationConnection, httpSession);
					
					if (ou == null)
					{  // should never be reached
						UnexpectedErrorLogger.severe("- Could not create a OnlineUser (is null): \n\t"+msg+"\n");
						send(new LoginAnswerMessage(LoginAnswerStatus.FAIL));
					}
					else
					{
						OnlineUserManager.getInstance().addOnlineUser(ou);
						
						long unread = NotificationModule.getInstance().getUnreadNotifications(u);
						unread+= RequestModule.getInstance().getRequestCount(u);
						
						send(new LoginAnswerMessage(ou.getId(), u.getDisplayedName() , unread)); 
						communicationConnection.deregisterInputChannel(this);
					}
				}
				
			}
			
			
		}catch (NotFoundException e) {
			try {
				send(new LoginAnswerMessage(LoginAnswerStatus.FAIL));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (SAXException e) {
			
			e.printStackTrace();
			UnexpectedErrorLogger.severe("- Could not Parse the message: \n\t"+msg+"\n");
			try {
				send(new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.PARSE)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			UnexpectedErrorLogger.severe("- IO EXcpetion: "+e.getMessage()+ " \n\tOriginal incoming msg"+msg+"\n");
			try {
				send(new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.PARSE)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} catch (DAOException e) {
			e.printStackTrace();
			UnexpectedErrorLogger.severe("- Database Experiment DAO on Message: \n\t"+msg+"\n");
			try {
				send(new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.DATABASE)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		
		} catch (MoreThanOneExcpetion e) {
			
			e.printStackTrace();
			UnexpectedErrorLogger.severe("- Database Experiment DAO. More than one user found! on Message: \n\t"+msg+"\n");
			try {
				send(new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.DATABASE)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		
	}

	@Override
	public void send(Message message) throws IOException {
		communicationConnection.send(message);
	}

	@Override
	public void onMessageRecieved(Message arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
