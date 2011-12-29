package org.gemsjax.server.communication.servlet.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gemsjax.server.communication.parser.HttpParseException;
import org.gemsjax.server.communication.parser.SystemMessageParser;
import org.gemsjax.server.communication.servlet.PostServlet;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.EMailInUseExcpetion;
import org.gemsjax.server.persistence.dao.exception.UsernameInUseException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.util.SHA;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.communication.message.UnexpectedErrorMessage;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.gemsjax.shared.user.RegisteredUser;



/**
 * This is the servlet to register a new {@link RegisteredUser} by waiting for incoming {@link NewRegistrationMessage}s (in POST format).
 * This servlet is accessible only via HTTP POST
 * and will respond with 
 * <ul>
 * <li> HTTP status 200 and a {@link RegistrationAnswerMessage} in xml, if registration was successful or failed because the username or email is already assigned
 * <li> HTTP status 400 and a {@link UnexpectedErrorMessage} in xml, if the received {@link NewRegistrationMessage} could not be parsed
 * <li> HTTP status 500 and a {@link UnexpectedErrorMessage} in xml, if an unexpected internal server error has occurred (Database, SHA algorithm error)
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class RegistrationServlet extends PostServlet{
	
	
	private UserDAO userDAO;
	private SystemMessageParser parser;
	
	public RegistrationServlet()
	{
		userDAO = new HibernateUserDAO();
		parser = new SystemMessageParser();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username ="";
		String email ="";
		
		try {
			NewRegistrationMessage m = (NewRegistrationMessage) parser.parse(request);
		
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
			
			resp.setContentType("text/xml;charset=UTF-8");
			resp.setStatus(200);
		    PrintWriter out = resp.getWriter();
		    out.println(am.toXml());
		    out.close();
				
		} catch (HttpParseException e) {
			e.printStackTrace();
		
			resp.setContentType("text/xml;charset=UTF-8");
			resp.setStatus(400);
		    PrintWriter out = resp.getWriter();
		    
		    UnexpectedErrorMessage em = new UnexpectedErrorMessage(UnexpectedErrorMessage.ErrorType.PARSE);
		    
		    out.println(em.toXml());
		    out.close();
		
		}
		catch (ClassCastException e)
		{
			resp.setContentType("text/xml;charset=UTF-8");
			resp.setStatus(400);
		    PrintWriter out = resp.getWriter();
		    UnexpectedErrorMessage em = new UnexpectedErrorMessage(UnexpectedErrorMessage.ErrorType.PARSE);
		    out.println(em.toXml());
		    out.close();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			resp.setContentType("text/xml;charset=UTF-8");
			resp.setStatus(500);
		    PrintWriter out = resp.getWriter();
		    UnexpectedErrorMessage em = new UnexpectedErrorMessage(UnexpectedErrorMessage.ErrorType.DATABASE);
		    out.println(em.toXml());
		    out.close();
		} catch (UsernameInUseException e) {
			
			RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_USERNAME,username);
			resp.setContentType("text/xml;charset=UTF-8");
			resp.setStatus(200);
		    PrintWriter out = resp.getWriter();
		    out.println(am.toXml());
		    out.close();
		    
		} catch (DAOException e) {
			e.printStackTrace();
			resp.setContentType("text/xml;charset=UTF-8");
			resp.setStatus(500);
		    PrintWriter out = resp.getWriter();
		    UnexpectedErrorMessage em = new UnexpectedErrorMessage(UnexpectedErrorMessage.ErrorType.DATABASE);
		    out.println(em.toXml());
		    out.close();
		} catch (EMailInUseExcpetion e) {
			
			RegistrationAnswerMessage am = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_EMAIL,email);
			resp.setContentType("text/xml;charset=UTF-8");
			resp.setStatus(200);
		    PrintWriter out = resp.getWriter();
		    out.println(am.toXml());
		    out.close();
		}
		
		
	}
	
	
	

}
