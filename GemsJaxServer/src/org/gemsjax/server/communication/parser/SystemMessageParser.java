package org.gemsjax.server.communication.parser;

import java.io.IOException;
import java.io.StringReader;
import javax.servlet.http.HttpServletRequest;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.gemsjax.shared.communication.message.system.LogoutMessage;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A parser to parse a {@link LoginMessage}
 * @author Hannes Dorfmann
 *
 */
public class SystemMessageParser extends AbstractContentHandler {
	
	private String username;
	private String password;
	private boolean experimentLogin;
	
	
	private int logoutReason;
	
	
	private boolean startSys;
	private boolean endSys;
	private boolean startLogin;
	private boolean startLogout;
	private boolean endLogin;
	private boolean endLogout;
	

	public SystemMessageParser(){	
		
	}
	
	/**
	 * 
	 * @param xml The xml representation of a {@link LoginMessage}, which is parsed to a {@link LoginMessage} object
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public SystemMessage parse(String xml) throws SAXException, IOException
	{
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml));
	    
	    inputSource.setEncoding("UTF-8");
	    xmlReader.setContentHandler(this);
	    xmlReader.parse(inputSource);
	    
	    
	    if (startLogin && endLogin) 
	    	return new LoginMessage(username, password, experimentLogin);
	    
	    if (startLogout && endLogout)
	    	return new LogoutMessage(LogoutMessage.intToLogoutReason(logoutReason));
	    
	    throw new SAXException("Unexcpected Parse error: Could not determine, if the Message is a Login or Logout Message");
	}

	
	/**
	 * Parse a HTTP POST request to a {@link SystemMessage}
	 * @param request
	 * @return the parsed {@link SystemMessage} or null if its not parseable
	 */
	public SystemMessage parse(HttpServletRequest request) throws HttpParseException
	{
		
		String messageClass = request.getParameter(Message.CLASS_NAME_PARAMETER);
		
		if (messageClass== null || messageClass.isEmpty())
			throw new HttpParseException("Could not determine the MESSAGE_CLASS. Parsing is not possible without this parameter");
		

		
		if (messageClass.equals(NewRegistrationMessage.class.getName()))
			return parseNewRegistrationMessage(request);
		
		
		return null;
	}
	
	
	
	/**
	 * Parse a {@link NewRegistrationMessage}
	 * @param request
	 * @return
	 * @throws HttpParseException
	 */
	private NewRegistrationMessage parseNewRegistrationMessage(HttpServletRequest request) throws HttpParseException
	{
		
		if (! request.getParameter(Message.CLASS_NAME_PARAMETER).equals(NewRegistrationMessage.class.getName()))
			throw new HttpParseException("Not able to parse a NewRegistrationMessage, which is not claimed as this in the required POST parameter \""+Message.CLASS_NAME_PARAMETER+"\"\n"+
					"Do you forget to send the required parameter "+Message.CLASS_NAME_PARAMETER);
	
		// DECODE is done automatically by calling getParameter
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		if (username == null  || username.isEmpty())
			throw new HttpParseException("Username is empty");
		
		if (password== null || password.isEmpty())
			throw new HttpParseException("Password is empty");
		
		if (email == null || email.isEmpty())
			throw new HttpParseException("E-Mail address is empty");
			
			
		return new NewRegistrationMessage(username, password, email);
		
		
	}
	
	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (localName.equals(SystemMessage.TAG))
			endSys = true;
		
		if (localName.equals(LoginMessage.TAG))
			endLogin = true;
		
		if (localName.equals(LogoutMessage.TAG))
			endLogout = true;
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (localName.equals(SystemMessage.TAG))
			startSys = true;
		
		if (localName.equals(LoginMessage.TAG))
		{
			startLogin = true;
			
			username = atts.getValue(LoginMessage.USERNAME_ATRRIBUTE);
			password = atts.getValue(LoginMessage.PASSWORD_ATTRIBUTE);
			
			try{
				experimentLogin = Boolean.parseBoolean(atts.getValue(LoginMessage.FOR_EXPERIMENT_ATTRIBUTE));
			}catch(Exception e)
			{
				throw new SAXException(LoginMessage.FOR_EXPERIMENT_ATTRIBUTE+" attribute is not set to a valid boolean");
			}
		}
		
		if (localName.equals(LogoutMessage.TAG))
		{
			startLogout = true;
			
			try{
				logoutReason = Integer.parseInt(atts.getValue(LogoutMessage.REASON_ATTRIBUTE));
			} catch (Exception e)
			{
				throw new SAXException("Could not convert the reason to int: "+e.getMessage());
			}
		}
		
	}
	


	@Override
	public void endDocument() throws SAXException {
		
		if (!startSys)
			throw new SAXException("Start <"+SystemMessage.TAG+"> Tag not found");
		
		if (!endSys)
			throw new SAXException("End </"+SystemMessage.TAG+"> Tag not found");
		
		if ( (!startLogin && !endLogin) && (!startLogout && !endLogout))
			throw new SAXException("Found a valid <"+SystemMessage.TAG+">, but no child tag");
			
		if (startLogin != endLogin)
			throw new SAXException("<"+LoginMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (startLogout != endLogout)
			throw new SAXException("<"+LogoutMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		
		if (startLogin && endLogin && startLogout && endLogout)
			throw new SAXException("The message is a <"+LoginMessage.TAG+"> and a <"+LogoutMessage.TAG+"> message at the same time. That's not allowed.");
		
	}


	@Override
	public void startDocument() throws SAXException {
		startSys = false;
		endSys = false;
		endLogin = false;
		endLogout = false;
		startLogin = false;
		startLogout = false;
		logoutReason = -1;
		
		username = null;
		password = null;
		experimentLogin = false;
		
	}
}
