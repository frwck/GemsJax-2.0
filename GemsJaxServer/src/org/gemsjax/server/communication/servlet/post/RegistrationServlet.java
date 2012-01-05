package org.gemsjax.server.communication.servlet.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gemsjax.server.communication.HttpCommunicationConnection;
import org.gemsjax.server.communication.channel.RegistrationChannel;
import org.gemsjax.server.communication.parser.HttpParseException;
import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.gemsjax.server.communication.servlet.HttpPostServlet;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.util.SHA;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.UnexpectedErrorMessage;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.gemsjax.shared.user.RegisteredUser;



/**
 * This is the servlet to register a new {@link RegisteredUser} by waiting for incoming {@link NewRegistrationMessage}s (which is sent via HTTP POST).
 * This servlet is accessible only via HTTP POST
 * and will respond with 
 * <ul>
 * <li> HTTP status 200 and a {@link RegistrationAnswerMessage} in xml, if registration was successful or failed because the username or email is already assigned or are invalid.
 * <li> HTTP status 400 and a {@link UnexpectedErrorMessage} in xml, if the received {@link NewRegistrationMessage} could not be parsed
 * <li> HTTP status 500 and a {@link UnexpectedErrorMessage} in xml, if an unexpected internal server error has occurred (Database, SHA algorithm error)
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class RegistrationServlet extends HttpPostServlet{
	
	public RegistrationServlet()
	{
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		CommunicationConnection connection = new HttpCommunicationConnection(request, response);
		RegistrationChannel rc = new RegistrationChannel(connection);
		connection.connect();
	}


	
	

}
