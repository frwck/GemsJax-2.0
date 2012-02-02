package org.gemsjax.server.communication.parser;

import java.io.IOException;
import java.io.StringReader;

import org.gemsjax.shared.communication.message.friend.CancelFriendshipMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.GetAllFriendsMessage;
import org.gemsjax.shared.communication.message.friend.ReferenceableFriendMessage;
import org.gemsjax.shared.communication.message.search.GlobalSearchMessage;
import org.gemsjax.shared.communication.message.search.ReferenceableSearchMessage;
import org.gemsjax.shared.communication.message.search.SearchRegisteredUserMessage;
import org.gemsjax.shared.communication.message.system.LoginMessage;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * This parser parse the following {@link FriendMessage}s:
 * <ul>
 * <li> {@link GetAllFriendsMessage}</li>
 * <li> {@link CancelFriendshipMessage} </li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class SearchMessageParser extends AbstractContentHandler {
	
	private boolean startSearch, endSearch;
	private boolean startGlobal, endGlobal;
	private boolean startUser, endUser;
	
	private String referenceId;
	private String searchString;
	
	
	
	public SearchMessageParser(){	
		
	}
	
	/**
	 * 
	 * @param xml The xml representation of a {@link LoginMessage}, which is parsed to a {@link LoginMessage} object
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public ReferenceableSearchMessage parse(String xml) throws SAXException, IOException
	{
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml));
	    
	    inputSource.setEncoding("UTF-8");
	    xmlReader.setContentHandler(this);
	    xmlReader.parse(inputSource);
	    
	   
	    if (startGlobal && endGlobal) 
	    	return new GlobalSearchMessage(referenceId, searchString);
	    
	    if (startUser && endUser)
	    	return new SearchRegisteredUserMessage(referenceId, searchString);
	    	
	    throw new SAXException("Unexcpected Parse error: Could not determine the type of the received message");
	}

	
	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (localName.equals(ReferenceableSearchMessage.TAG))
			endSearch = true;
		else
		if (localName.equals(GlobalSearchMessage.TAG)){
			endGlobal = true;
			searchString = getCurrentValue();
		}
		else
		if (localName.equals(SearchRegisteredUserMessage.TAG)){
			endUser = true;
			searchString = getCurrentValue();
		}
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (localName.equals(ReferenceableSearchMessage.TAG))
		{
			startSearch = true;
			referenceId = atts.getValue(ReferenceableSearchMessage.ATTRIBUTE_REFERENCE_ID);
		}
		else
		if (localName.equals(GlobalSearchMessage.TAG))
			startGlobal = true;
		else
		if (localName.equals(SearchRegisteredUserMessage.TAG))
			startUser = true;
	
	}
	


	@Override
	public void endDocument() throws SAXException {
		
		if (!startSearch)
			throw new SAXException("Start <"+ReferenceableFriendMessage.TAG+"> Tag not found");
		
		if (!endSearch)
			throw new SAXException("End </"+ReferenceableFriendMessage.TAG+"> Tag not found");
		
		if (startGlobal != endGlobal)
			throw new SAXException("<"+GlobalSearchMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (startUser != endUser)
			throw new SAXException("<"+SearchRegisteredUserMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (referenceId == null)
			throw new SAXException("Reference Id is missing");
		
		if (searchString == null)
			throw new SAXException("Search string is missing");
		
		
		if (startGlobal && startUser)
			throw new SAXException("The received message is a <"+GlobalSearchMessage.TAG+"> and <"+SearchRegisteredUserMessage.TAG+"> at the same time. Thats not allowed.");
		
	}


	@Override
	public void startDocument() throws SAXException {
		
		startSearch = false;
		endSearch = false;
		startGlobal = false;
		endGlobal = false;
		startUser = false;
		endUser = false;
		
		referenceId = null;
		searchString = null;
		
	}
	
	
	
	public String getCurrentReferenceId()
	{
		return referenceId;
	}

}
