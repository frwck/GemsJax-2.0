package org.gemsjax.server.communication.parser;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;

import org.gemsjax.server.communication.servlet.post.NewRegistrationServlet;
import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.gemsjax.shared.communication.message.system.LogoutMessage;
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
	 * @return
	 */
	public SystemMessage parse(HttpServletRequest request)
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		/*
		if (username != null && password!= null && email != null)
			return new NewRegistrationMessage();
		*/
		
		return null;
	}
	
	
	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (localName.equals("sys"))
			endSys = true;
		
		if (localName.equals("login"))
			endLogin = true;
		
		if (localName.equals("logout"))
			endLogout = true;
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (localName.equals("sys"))
			startSys = true;
		
		if (localName.equals("login"))
		{
			startLogin = true;
			
			username = atts.getValue("username");
			password = atts.getValue("password");
			
			try{
				experimentLogin = Boolean.parseBoolean(atts.getValue("exp"));
			}catch(Exception e)
			{
				throw new SAXException("exp is not set to a valid boolean");
			}
		}
		
		if (localName.equals("logout"))
		{
			startLogout = true;
			
			try{
				logoutReason = Integer.parseInt(atts.getValue("reason"));
			} catch (Exception e)
			{
				throw new SAXException("Could not convert the reason to int: "+e.getMessage());
			}
		}
		
	}
	


	@Override
	public void endDocument() throws SAXException {
		
		if (!startSys)
			throw new SAXException("Start <sys> Tag not found");
		
		if (!endSys)
			throw new SAXException("End </sys> Tag not found");
		
		if ( (!startLogin && !endLogin) && (!startLogout && !endLogout))
			throw new SAXException("Found a valid <sys>, but no child <login> or >logout>");
			
		if (startLogin != endLogin)
			throw new SAXException("<login> missmatch: An opening or closing <login> is missing");
		
		if (startLogout != endLogout)
			throw new SAXException("<logout> missmatch: An opening or closing <logout> is missing");
		
		
		if (startLogin && endLogin && startLogout && endLogout)
			throw new SAXException("The message is a <login> and a <logout> message at the same time. That's not allowed.");
		
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
