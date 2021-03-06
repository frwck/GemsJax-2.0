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
	private String email; 
	private boolean experimentLogin;
	
	
	private int logoutReason;
	
	
	private boolean startSys;
	private boolean endSys;
	private boolean startLogin;
	private boolean startLogout;
	private boolean endLogin;
	private boolean endLogout;
	private boolean startNewRegistration, endNewRegistration;
	

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
	    
	    if (startNewRegistration && endNewRegistration)
	    	return new NewRegistrationMessage(username, password, email);
	    
	    throw new SAXException("Unexcpected Parse error: Could not determine, if the Message is a Login or LogoutMessage or NewRegistrationMessage");
	}

	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (localName.equals(SystemMessage.TAG))
			endSys = true;
		else
		if (localName.equals(LoginMessage.TAG))
			endLogin = true;
		else
		if (localName.equals(LogoutMessage.TAG))
			endLogout = true;
		else
		if (localName.equals(NewRegistrationMessage.TAG))
			endNewRegistration = true;
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (localName.equals(SystemMessage.TAG))
			startSys = true;
		else
		if (localName.equals(LoginMessage.TAG))
		{
			startLogin = true;
			
			username = atts.getValue(LoginMessage.ATTRIBUTE_USERNAME);
			password = atts.getValue(LoginMessage.ATTRIBUTE_PASSWORD);
			
			try{
				experimentLogin = Boolean.parseBoolean(atts.getValue(LoginMessage.ATTRIBUTE_FOR_EXPERIMENT));
			}catch(Exception e)
			{
				throw new SAXException(LoginMessage.ATTRIBUTE_FOR_EXPERIMENT+" attribute is not set to a valid boolean");
			}
		}
		else
		if (localName.equals(LogoutMessage.TAG))
		{
			startLogout = true;
			
			try{
				logoutReason = Integer.parseInt(atts.getValue(LogoutMessage.ATTRIBUTE_REASON));
			} catch (Exception e)
			{
				throw new SAXException("Could not convert the reason to int: "+e.getMessage());
			}
		}
		
		else
		if (localName.equals(NewRegistrationMessage.TAG))
		{
			startNewRegistration = true;
			username = atts.getValue(NewRegistrationMessage.ATTRIBUTE_USERNAME);
			password = atts.getValue(LoginMessage.ATTRIBUTE_PASSWORD);
			email = atts.getValue(NewRegistrationMessage.ATTRIBUTE_EMAIL);
		}
	}
	


	@Override
	public void endDocument() throws SAXException {
		
		if (!startSys)
			throw new SAXException("Start <"+SystemMessage.TAG+"> Tag not found");
		
		if (!endSys)
			throw new SAXException("End </"+SystemMessage.TAG+"> Tag not found");
		
		if ( (!startLogin && !endLogin) && (!startLogout && !endLogout) && (!startNewRegistration && !endNewRegistration))
			throw new SAXException("Found a valid <"+SystemMessage.TAG+">, but no child tag");
			
		if (startLogin != endLogin)
			throw new SAXException("<"+LoginMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (startLogout != endLogout)
			throw new SAXException("<"+LogoutMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (startNewRegistration!=endNewRegistration)
			throw new SAXException("<"+NewRegistrationMessage.TAG+"> missmatch: AN opening or closing tag is missing");
		
		
		if (startLogin && endLogin && startLogout && endLogout && startNewRegistration && endNewRegistration)
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
