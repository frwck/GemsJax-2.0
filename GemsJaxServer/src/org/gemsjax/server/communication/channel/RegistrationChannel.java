package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.gemsjax.server.communication.HttpCommunicationConnection;
import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.gemsjax.server.persistence.dao.ExperimentDAO;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.ArgumentException;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateExperimentDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.util.SHA;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.CommunicationError;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.NewExperimentRegistrationMessage;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.gemsjax.shared.communication.message.system.SystemErrorMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.gemsjax.shared.experiment.ExperimentInvitation;
import org.xml.sax.SAXException;

/**
 * This Channel received {@link NewRegistrationMessage}s by the underlying {@link CommunicationConnection}
 * and answer them (via the underlying {@link CommunicationConnection}) with a {@link RegistrationAnswerMessage}.
 * @author Hannes Dorfmann
 *
 */
public class RegistrationChannel implements InputChannel, OutputChannel{

	private UserDAO userDAO;
	private ExperimentDAO experimentDAO;
	private SystemMessageParser parser;
	private CommunicationConnection connection;
	private String filterRegex;
	
	/**
	 * Create a new {@link RegistrationChannel}. Since the {@link RegistrationChannel} is a {@link InputChannel},
	 * it automatically registers itself as a {@link InputChannel} on the passed {@link CommunicationConnection}.
	 * @param connection The {@link CommunicationConnection}
	 */
	public RegistrationChannel(CommunicationConnection connection)
	{
		userDAO = new HibernateUserDAO();
		experimentDAO = new HibernateExperimentDAO();
		parser = new SystemMessageParser();
		this.connection  = connection;
		this.connection.registerInputChannel(this);
		this.connection.registerInputChannel(this, SystemMessage.TYPE);
		this.filterRegex = RegExFactory.startWithTagSubTag(SystemMessage.TAG, NewRegistrationMessage.TAG);
		
	}
	
	
	@Override
	public void send(Message arg0) throws IOException {
		connection.send(arg0);
	}

	@Override
	public boolean isMatchingFilter(String msg) {
		return msg.matches(filterRegex);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		try {
			parseAndAnswerIncomingMessag(msg.getText());
		} catch (IOException e) {
			// TODO What to do, if message could not be answered
			e.printStackTrace();
		}
	}
	
	
	private void setHttpResponseStatusCode(int httpStatusCode)
	{
		if (connection instanceof HttpCommunicationConnection)
			((HttpCommunicationConnection)connection).setResponseStatusCode(httpStatusCode);
	}
	
	
	private void parseAndAnswerIncomingMessag(String msg) throws IOException
	{
		String username ="";
		String email ="";
		
		try {
			NewRegistrationMessage m = (NewRegistrationMessage) parser.parse(msg);
		
			username =  m.getUsername();
			email = m.getEmail();
			
			RegistrationAnswerMessage am;
			
			if (!FieldVerifier.isValidUsername(username))
			{
				// Username is not valid
				am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_INVALID_USERNAME, username);
			}
			else
			if(!FieldVerifier.isValidEmail(email))
			{
				// Email is not Valid
				am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_INVALID_EMAIL, email);
			}
			else
			{
				// username and email is valid, so register Username and Password by saving into the database
				userDAO.createRegisteredUser(m.getUsername(), SHA.generate256(m.getPassword()),m.getEmail());
				
				// if it was successful
				am = new RegistrationAnswerMessage(RegistrationAnswerStatus.OK);
			}
			
			setHttpResponseStatusCode(200);
			send(am);
				
		} catch (SAXException e) {
			e.printStackTrace();
		
			SystemMessage em = new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.PARSE));
		    setHttpResponseStatusCode(400);
		    send(em);
		
		}
		catch (ClassCastException e)
		{
			SystemMessage em = new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.PARSE));
		    setHttpResponseStatusCode(400);
		    send(em);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			SystemMessage em = new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.DATABASE));
		    setHttpResponseStatusCode(500);
		    send(em);
		} catch (UsernameInUseException e) {
			
			RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_USERNAME,username);
			setHttpResponseStatusCode(200);
			send(am);
		    
		} catch (DAOException e) {
			e.printStackTrace();
			
			SystemMessage em = new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.DATABASE));
		    setHttpResponseStatusCode(500);
		    send(em);
		} catch (EMailInUseExcpetion e) {
			
			RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_EMAIL,email);
			setHttpResponseStatusCode(200);
			send(am);
		}
		

	}


	@Override
	public void onMessageRecieved(Message m) {
		
		if (m instanceof NewExperimentRegistrationMessage){
			doExperimentUserRegistration((NewExperimentRegistrationMessage) m);
		}
	}


	private void doExperimentUserRegistration(NewExperimentRegistrationMessage m) {
		try {
			
			ExperimentInvitation inv = experimentDAO.getExperimentInvitation(m.getVerificationCode());
			
			if (inv.hasParticipated()){ // Already a username created
				RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_EXPERIMENT_VERIFICATION_CODE);
				setHttpResponseStatusCode(200);
				try {
					send(am);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				return;
			}
			
			
			
			if (experimentDAO.isDisplayedNameInExperimentGroupAvailable(m.getDisplayedName(), inv.getExperimentGroup())){
				try {
				experimentDAO.createExperimentUser(m.getVerificationCode(), SHA.generate256(m.getPassword()), inv.getExperimentGroup(), m.getDisplayedName());
				experimentDAO.setExperimentInvitationParticipated(inv, true);
				
				RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.OK);
				setHttpResponseStatusCode(200);
				
					send(am);
				} catch (IOException e) {
					// TODO what to do if message cant be sent
					e.printStackTrace();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_EXPERIMENT_VERIFICATION_CODE);
					setHttpResponseStatusCode(200);
					try {
						send(am);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			else // DisplayedName is not available
			{
				RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_EXPERIMENT_DISPLAYED_NAME_IN_USE);
				setHttpResponseStatusCode(200);
				try {
					send(am);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		} catch (MoreThanOneExcpetion e) {
			
			SystemMessage em = new SystemErrorMessage(new CommunicationError(CommunicationError.ErrorType.DATABASE));
		    setHttpResponseStatusCode(500);
		    try {
				send(em);
			} catch (IOException e1) {
				// TODO What to do if message can't be sent
				e1.printStackTrace();
			}
		    
			e.printStackTrace();
		} catch (NotFoundException e) {
			RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_EXPERIMENT_VERIFICATION_CODE);
			setHttpResponseStatusCode(200);
			try {
				send(am);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsernameInUseException e) {
			
			RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_EXPERIMENT_VERIFICATION_CODE);
			setHttpResponseStatusCode(200);
			try {
				send(am);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
