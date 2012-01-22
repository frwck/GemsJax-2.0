package org.gemsjax.server.communication.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.communication.message.friend.CancelFriendshipMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.GetAllFriendsMessage;
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
public class FriendMessageParser extends AbstractContentHandler {
	
	boolean friendStart, friendEnd;
	boolean getAllStart, getAllEnd;
	boolean cancelFriendshipStart, cancelFriendshipEnd;
	
	private Set<Integer> friendsIds;
	
	/**
	 * This is used, to check, if for every {@link CancelFriendshipMessage#SUBTAG_FRIEND} the closing xml tag has been found.
	 * Every time a opening xml {@link CancelFriendshipMessage#SUBTAG_FRIEND} was found, this counter will be incremented.
	 * Every time a closing xml {@link CancelFriendshipMessage#SUBTAG_FRIEND} was found, this counter will be decremented.
	 * 
	 * so at the end of the parsing process, this counter must have the value zero. If the value is more than zero, at least one
	 * closing tag is missing.
	 * If the value is less then zero, at least one opening tag is missing.
	 */
	private int subTagCanceledCounter;
	

	public FriendMessageParser(){	
		
	}
	
	/**
	 * 
	 * @param xml The xml representation of a {@link LoginMessage}, which is parsed to a {@link LoginMessage} object
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public FriendMessage parse(String xml) throws SAXException, IOException
	{
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml));
	    
	    inputSource.setEncoding("UTF-8");
	    xmlReader.setContentHandler(this);
	    xmlReader.parse(inputSource);
	    
	    if (!friendStart || !friendEnd)
	    	throw new SAXException("The received message is not a valid message: The <"+FriendMessage.TAG+"> are missing");
	    
	    if (getAllStart && getAllEnd) 
	    	return new GetAllFriendsMessage();
	    
	    if (cancelFriendshipStart && cancelFriendshipEnd)
	    	return new CancelFriendshipMessage(friendsIds);
	    	
	    throw new SAXException("Unexcpected Parse error: Could not determine the type of the received message");
	}

	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (localName.equals(FriendMessage.TAG))
			friendEnd = true;
		else
		if (localName.equals(GetAllFriendsMessage.TAG))
			getAllEnd = true;
		else
		if (localName.equals(CancelFriendshipMessage.TAG))
			cancelFriendshipEnd = true;
		else
		if (localName.equals(CancelFriendshipMessage.SUBTAG_FRIEND))
			subTagCanceledCounter--;
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (localName.equals(FriendMessage.TAG))
			friendStart = true;
		else
		if (localName.equals(GetAllFriendsMessage.TAG))
			getAllStart = true;
		else
		if (localName.equals(CancelFriendshipMessage.TAG))
			cancelFriendshipStart = true;
		
		else
		if (localName.equals(CancelFriendshipMessage.SUBTAG_FRIEND))
		{
			subTagCanceledCounter++;
			
			String attId = atts.getValue(CancelFriendshipMessage.ATTRIBUTE_FRIEND_ID);
			try{
				int id = Integer.parseInt(attId);
				friendsIds.add(id);
			}catch (NumberFormatException e)
			{
				throw new SAXException("Could not parse the friends user id to an integer. received value: "+attId);
			}
		}
	}
	


	@Override
	public void endDocument() throws SAXException {
		
		if (!friendStart)
			throw new SAXException("Start <"+FriendMessage.TAG+"> Tag not found");
		
		if (!friendEnd)
			throw new SAXException("End </"+FriendMessage.TAG+"> Tag not found");
		
		if (getAllStart != getAllEnd)
			throw new SAXException("<"+GetAllFriendsMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (cancelFriendshipStart != cancelFriendshipEnd)
			throw new SAXException("<"+CancelFriendshipMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (getAllStart && getAllEnd && cancelFriendshipStart && cancelFriendshipEnd)
			throw new SAXException("The message is a <"+GetAllFriendsMessage.TAG+"> and a <"+CancelFriendshipMessage.TAG+"> message at the same time. That's not allowed.");
		
		if (subTagCanceledCounter<0)
			throw new SAXException("More closing than opening <"+CancelFriendshipMessage.SUBTAG_FRIEND+"> were found");
		
		if (subTagCanceledCounter>0)
			throw new SAXException("More opening than closing <"+CancelFriendshipMessage.SUBTAG_FRIEND+"> were found");
		
	}


	@Override
	public void startDocument() throws SAXException {

		subTagCanceledCounter = 0;
		friendsIds = new LinkedHashSet<Integer>();
		friendStart = false;
		friendEnd = false;
		cancelFriendshipEnd = false;
		cancelFriendshipStart = false;
		getAllEnd = false;
		getAllStart = false;
		
	}

}
