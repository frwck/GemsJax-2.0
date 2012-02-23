package org.gemsjax.server.communication.parser;

import java.io.IOException;
import java.io.StringReader;

import org.gemsjax.shared.communication.message.notification.DeleteNotificationMessage;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsMessage;
import org.gemsjax.shared.communication.message.notification.NotificationAsReadMessage;
import org.gemsjax.shared.communication.message.notification.NotificationMessage;
import org.gemsjax.shared.communication.message.notification.ReferenceableNotificationMessage;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Hannes Dorfmann
 *
 */
public class NotificationMessageParser extends AbstractContentHandler {
	
	private boolean startNotification, endNotification;
	private boolean startDelete, endDelete;
	private boolean startRead, endRead;
	private boolean startGetAll, endGetAll;
	private String referenceId;
	private Long notificationId;
	
	
	
	public NotificationMessageParser(){	
		
	}
	
	/**
	 * 
	 * @param xml The xml representation of a {@link LoginMessage}, which is parsed to a {@link LoginMessage} object
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public NotificationMessage parse(String xml) throws SAXException, IOException
	{
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml));
	    
	    inputSource.setEncoding("UTF-8");
	    xmlReader.setContentHandler(this);
	    xmlReader.parse(inputSource);
	    
	   
	    if (startDelete && endDelete) 
	    	if (referenceId!=null)
	    		if (notificationId!=null)
	    			return new DeleteNotificationMessage(referenceId, notificationId);
	    		else
	    			throw new SAXException("Notification id is null");
	    	else
	    		throw new SAXException("reference id is null");
	    
	    if (startRead && endRead)
	    	if (referenceId!=null)	
		    	if (notificationId!=null)
		    		return new NotificationAsReadMessage(referenceId, notificationId);
    		else
    			throw new SAXException("Notification id is null");
    	else
    		throw new SAXException("reference id is null");
	    	
	    
	    if (startGetAll && endGetAll){
	    	if (referenceId!=null)
	    		return new GetAllNotificationsMessage(referenceId);
	    	else
	    		throw new SAXException("ReferenceId was null");
	    }
	    throw new SAXException("Unexcpected Parse error: Could not determine the type of the received message");
	}

	
	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (localName.equals(DeleteNotificationMessage.TAG))
			endDelete = true;
		else
		if (localName.equals(NotificationAsReadMessage.TAG)){
			endRead = true;
		}
		else
		if (localName.equals(NotificationMessage.TAG)){
			endNotification = true;
		}
		else
		if (localName.equals(GetAllNotificationsMessage.TAG)){
			endGetAll = true;
		}
		
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (localName.equals(ReferenceableNotificationMessage.TAG))
		{
			startNotification = true;
			referenceId = atts.getValue(ReferenceableNotificationMessage.ATTRIBUTE_REFERENCE_ID);
		}
		else
		if (localName.equals(DeleteNotificationMessage.TAG)){
			startDelete = true;
			try{
				notificationId = Long.parseLong(atts.getValue(DeleteNotificationMessage.ATTRIBUTE_NOTIFICATION_ID));
			}
			catch (NumberFormatException e)
			{
				throw new SAXException("Could not parse the notification id. Value is: "+atts.getValue(DeleteNotificationMessage.ATTRIBUTE_NOTIFICATION_ID));
			}
		}
			
		else
		if (localName.equals(NotificationAsReadMessage.TAG)){
			startRead = true;
			try{
				notificationId = Long.parseLong(atts.getValue(NotificationAsReadMessage.ATTRIBUTE_NOTIFICATION_ID));
			}
			catch (NumberFormatException e)
			{
				throw new SAXException("Could not parse the notification id. Value is: "+atts.getValue(NotificationAsReadMessage.ATTRIBUTE_NOTIFICATION_ID));
			}
		}
		else
		if (localName.equals(GetAllNotificationsMessage.TAG)){	
			startGetAll = true;
		}
	
	}
	


	@Override
	public void endDocument() throws SAXException {
		
		if (!startNotification)
			throw new SAXException("Start <"+NotificationMessage.TAG+"> Tag not found");
		
		if (!endNotification)
			throw new SAXException("End </"+NotificationMessage.TAG+"> Tag not found");
		
		if (startDelete != endDelete)
			throw new SAXException("<"+DeleteNotificationMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (startRead != endRead)
			throw new SAXException("<"+NotificationAsReadMessage.TAG+"> missmatch: An opening or closing tag is missing");
		

		if (startGetAll != endGetAll)
			throw new SAXException("<"+GetAllNotificationsMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		
		if ((startDelete && startRead) || (startDelete && startGetAll) || (startRead && startGetAll))
			throw new SAXException("The received message is a <"+DeleteNotificationMessage.TAG+"> and <"+NotificationAsReadMessage.TAG+"> at the same time. Thats not allowed.");
		
		
	}


	@Override
	public void startDocument() throws SAXException {
		
		startDelete = false;
		endDelete = false;
		startRead = false;
		endRead = false;
		startNotification = false;
		endNotification = false;
		startGetAll = false;
		endGetAll = false;
		
		referenceId = null;
		notificationId = null;
		
	}
	
	
	
	public String getCurrentReferenceId()
	{
		return referenceId;
	}

}
