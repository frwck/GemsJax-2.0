package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import org.gemsjax.server.communication.HttpCommunicationConnection;
import org.gemsjax.server.communication.parser.HttpParseException;
import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
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
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.SystemErrorMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.xml.sax.SAXException;

/**
 * This Channel received {@link NewRegistrationMessage}s by the underlying {@link CommunicationConnection}
 * and answer them (via the underlying {@link CommunicationConnection}) with a {@link RegistrationAnswerMessage}.
 * @author Hannes Dorfmann
 *
 */
public class RegistrationChannel implements InputChannel, OutputChannel{

	private UserDAO userDAO;
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
		parser = new SystemMessageParser();
		this.connection  = connection;
		this.connection.registerInputChannel(this);
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
	public void onMessageRecieved(Message arg0) {
		// TODO Auto-generated method stub
		
	}

}
