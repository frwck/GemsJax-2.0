package org.gemsjax.server.communication.parser;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import org.gemsjax.shared.communication.message.LoginMessage;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A parser to parse a {@link LoginMessage}
 * @author Hannes Dorfmann
 *
 */
public class LoginMessageParser extends AbstractContentHandler {
	
	private String username;
	private String password;
	private boolean experimentLogin;
	
	private boolean startSys;
	private boolean endSys;
	

	public LoginMessageParser(){	
		
	}
	
	/**
	 * 
	 * @param xml The xml representation of a {@link LoginMessage}, which is parsed to a {@link LoginMessage} object
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public LoginMessage parse(String xml) throws SAXException, IOException
	{
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml));
	    
	    inputSource.setEncoding("UTF-8");
	    xmlReader.setContentHandler(this);
	    xmlReader.parse(inputSource);
	    
	    
	    LoginMessage m = new LoginMessage(username, password, experimentLogin);
	    
	    return m;
	}


	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if (localName.equals("sys"))
			endSys = true;
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
	
		
		if (localName.equals("sys"))
			startSys = true;
		
		if (localName.equals("login"))
		{
			username = atts.getValue("username");
			password = atts.getValue("password");
			
			try{
				experimentLogin = Boolean.parseBoolean(atts.getValue("exp"));
			}catch(Exception e)
			{
				throw new SAXException("exp is not set to a valid boolean");
			}
			
		}
		
	}
	


	@Override
	public void endDocument() throws SAXException {
		if (!startSys)
			throw new SAXException("Start <sys> Tag not found");
		
		if (!endSys)
			throw new SAXException("End </sys> Tag not found");
		
	}


	@Override
	public void startDocument() throws SAXException {
		startSys = false;
		endSys = false;
		
	}
}
